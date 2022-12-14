package com.orange.latex;

import com.orange.latex.atom.Atom;
import com.orange.latex.atom.AtomArray;
import com.orange.latex.atom.BracketAtom;
import com.orange.latex.atom.CellAtom;
import com.orange.latex.atom.CharAtom;
import com.orange.latex.atom.CmdAtom;
import com.orange.latex.atom.CodePointAtom;
import com.orange.latex.atom.EnvironmentAtom;
import com.orange.latex.atom.GroupAtom;
import com.orange.latex.atom.LeftRightAtom;
import com.orange.latex.atom.QuadAtom;
import com.orange.latex.atom.RowAtom;
import com.orange.latex.atom.SubAtom;
import com.orange.latex.atom.SupAtom;
import com.orange.latex.atom.TextAtom;
import com.orange.latex.atom.number.BigDecimalAtom;
import com.orange.latex.atom.number.IntAtom;
import com.orange.latex.atom.number.LongAtom;
import com.orange.latex.util.CodePointUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * tex 解析器
 *
 * @author 小天
 * @date 2020/12/7 14:10
 */
public class TexParser {

    /**
     * 空格 的 codePoint
     */
    public static final int SPACE_CP = CodePointUtil.SPACE_CP;

    /**
     * 反斜杠（latex 转义符） 的 codePoint
     */
    public static final int ESCAPE_CP = CodePointUtil.ESCAPE_CP;

    /**
     * 上标 的 codePoint
     */
    public static final int SUPER_SCRIPT_CP = CodePointUtil.SUPER_SCRIPT_CP;
    /**
     * 下标 的 codePoint
     */
    public static final int SUB_SCRIPT_CP   = CodePointUtil.SUB_SCRIPT_CP;

    /**
     * 左大括号 的 codePoint
     */
    public static final int L_GROUP_CP = CodePointUtil.L_GROUP_CP;
    /**
     * 右大括号 的 codePoint
     */
    public static final int R_GROUP_CP = CodePointUtil.R_GROUP_CP;

    /**
     * 百分号: %
     */
    public static final int CP_PERCENT_SIGN = CodePointUtil.CP_PERCENT_SIGN;
    /**
     * 符号：&
     */
    public static final int CP_AMPERSAND    = CodePointUtil.CP_AMPERSAND;
    /**
     * 符号：#
     */
    public static final int CP_NUMBER_SIGN  = CodePointUtil.CP_NUMBER_SIGN;
    /**
     * 符号：$
     */
    public static final int CP_DOLLAR_SIGN  = CodePointUtil.CP_DOLLAR_SIGN;
    /**
     * 符号：~
     */
    public static final int TILDE_CP        = CodePointUtil.TILDE_CP;

    /**
     * 左中括号 的 codePoint
     */
    public static final int L_BRACKET_CP = CodePointUtil.L_BRACKET_CP;
    /**
     * 右中括号 的 codePoint
     */
    public static final int R_BRACKET_CP = CodePointUtil.R_BRACKET_CP;

    private String plainText;
    private int[]  codePoints;
    private int    pos = 0;

    private Stack<Atom> atomStack;

    /**
     * 是否开启数字检测
     */
    private boolean enableDigitalDetect = false;

    /**
     * @param plainText 待检测的公式文本
     */
    public TexParser(String plainText) {
        this(plainText, false);
    }

    /**
     * @param plainText           待检测的公式文本
     * @param enableDigitalDetect 是否开启数字检测
     */
    public TexParser(String plainText, boolean enableDigitalDetect) {
        this.plainText = plainText;
        this.codePoints = plainText.codePoints().toArray();
        this.pos = 0;

        this.atomStack = new Stack<>();

        this.enableDigitalDetect = enableDigitalDetect;
    }

    public Atom parse() {
        AtomArray rootAtomArray = new AtomArray();
        atomStack.push(rootAtomArray);

        parseInternal();

        return mergeAtomArray(rootAtomArray);
    }

    /**
     * 解析 cmd name
     *
     * @return
     */
    private String parseCmdName() {
        StringBuilder cmdBuilder = new StringBuilder();
        while (pos < codePoints.length) {
            int codePoint = codePoints[pos++];
            if (CodePointUtil.isLatinAlphabet(codePoint)) {
                cmdBuilder.appendCodePoint(codePoint);
            } else {
                // 指针回退
                pos--;
                break;
            }
        }

        return cmdBuilder.toString();
    }

    private void parseInternal() {
        while (pos < codePoints.length) {
            int codePoint = codePoints[pos++];
            if (codePoint == ESCAPE_CP) {
                // 转义符
                int nextCodePoint = codePoints[pos];
                if (CodePointUtil.isLatinAlphabet(nextCodePoint)) {
                    // 反斜杠后根字母时，都是命令

                    String cmdName = parseCmdName();

                    if (Cmds.CMD_LEFT.equals(cmdName)) {
                        LeftRightAtom leftRightAtom = new LeftRightAtom();
                        nextCodePoint = codePoints[pos++];
                        if (nextCodePoint == ESCAPE_CP) {
                            nextCodePoint = codePoints[pos++];
                        }
                        leftRightAtom.setLeftCodePoint(nextCodePoint);

                        // 入栈
                        pushToStack(leftRightAtom);
                    } else if (Cmds.CMD_RIGHT.equals(cmdName)) {
                        nextCodePoint = codePoints[pos++];
                        if (nextCodePoint == ESCAPE_CP) {
                            nextCodePoint = codePoints[pos++];
                        }

                        // 获取栈顶元素
                        Atom topAtom = atomStack.pop();
                        if (topAtom instanceof LeftRightAtom) {
                            LeftRightAtom leftRightAtom = (LeftRightAtom) topAtom;
                            leftRightAtom.setRightCodePoint(nextCodePoint);
                            // 添加到父级元素中
                            addToTopAtom(topAtom, false);
                        } else {
                            throw new LatexParseException("\\left 和 \\right 不匹配");
                        }
                        continue;
                    } else if (Cmds.CMD_BEGIN.equals(cmdName)) {
                        EnvironmentAtom envAtom = new EnvironmentAtom();
                        // 入栈
                        pushToStack(envAtom);
                    } else {
                        CmdAtom cmdAtom = CmdDefinitions.newCmd(cmdName);
                        // 入栈
                        pushToStack(cmdAtom);
                    }
                } else if (isSupportEscape(nextCodePoint)) {
                    // 转义字符

                    // 指针前进
                    pos++;
                    // 入栈
                    pushToStack(new CodePointAtom(nextCodePoint));
                } else {
                    if (nextCodePoint == CodePointUtil.CP_COMMA) {
                        // 3/18 quad

                        // 添加到父级元素中
                        addToTopAtom(new QuadAtom(nextCodePoint, 3), false);
                        // 指针前进
                        pos++;
                    } else if (nextCodePoint == CodePointUtil.CP_COLON) {
                        // 4/18 quad

                        // 添加到父级元素中
                        addToTopAtom(new QuadAtom(nextCodePoint, 4), false);
                        // 指针前进
                        pos++;
                    } else if (nextCodePoint == CodePointUtil.CP_SEMICOLON) {
                        // 5/18 quad

                        // 添加到父级元素中
                        addToTopAtom(new QuadAtom(nextCodePoint, 5), false);
                        // 指针前进
                        pos++;
                    } else if (nextCodePoint == CodePointUtil.SPACE_CP) {
                        // 中等大小空格，9/18 quad

                        // 添加到父级元素中
                        addToTopAtom(new QuadAtom(nextCodePoint, 9), false);
                        // 指针前进
                        pos++;
                    } else if (nextCodePoint == CodePointUtil.CP_EXCLAMATION_MARK) {
                        // 负空格，-3/18 quad

                        // 添加到父级元素中
                        addToTopAtom(new QuadAtom(nextCodePoint, -3), false);
                        // 指针前进
                        pos++;
                    } else {
                        // TODO: 2020/12/11 还有其它的转义字符，遇到了在这里补充
                        throw new LatexParseException("未知的转义符：\"\\" + CodePointUtil.toString(nextCodePoint) + "\"");
                    }
                }
                continue;
            } else if (codePoint == SPACE_CP) {
                continue;
            } else if (codePoint == L_GROUP_CP) {
                // 左 大括号

                // 入栈
                pushToStack(new GroupAtom());
                continue;
            } else if (codePoint == R_GROUP_CP) {
                // 右 大括号

                // 出栈
                Atom topAtom = atomStack.pop();
                if (topAtom instanceof GroupAtom) {
                    // 添加到父级元素中
                    addToTopAtom(mergeAtomArray((AtomArray) topAtom), false);
                } else {
                    throw new LatexParseException("语法错误，\"{\" 和 \"}\" 数量不匹配");
                }
                continue;
            } else if (codePoint == L_BRACKET_CP) {
                // 左 中括号

                // 入栈
                pushToStack(new BracketAtom());
                continue;
            } else if (codePoint == R_BRACKET_CP) {
                // 右 中括号

                // 出栈
                Atom topAtom = atomStack.pop();
                if (topAtom instanceof BracketAtom) {
                    // 添加到父级元素中
                    addToTopAtom(mergeAtomArray((AtomArray) topAtom), true);
                } else {
                    throw new LatexParseException("语法错误，\"[\" 和 \"]\" 数量不匹配");
                }
                continue;
            } else if (codePoint == SUB_SCRIPT_CP) {
                // 下标
                int nextCodePoint = codePoints[pos];
                SubAtom subAtom = new SubAtom();
                if (nextCodePoint != L_GROUP_CP) {
                    subAtom.addArg(buildSingleCodePointAtom(nextCodePoint));
                    // 指针前进
                    pos++;
                }
                // 入栈
                pushToStack(subAtom);
                continue;
            } else if (codePoint == SUPER_SCRIPT_CP) {
                // 上标
                int nextCodePoint = codePoints[pos];
                SupAtom supAtom = new SupAtom();

                if (nextCodePoint != L_GROUP_CP) {
                    supAtom.addArg(buildSingleCodePointAtom(nextCodePoint));
                    // 指针前进
                    pos++;
                }
                // 入栈
                pushToStack(supAtom);
                continue;
            } else {
                // 字符串
                // 入栈
                pushToStack(new CodePointAtom(codePoint));
            }
        }
    }

    /**
     * 入栈
     *
     * @param atom
     */
    private void pushToStack(Atom atom) {
        if (atom instanceof CodePointAtom) {
            // CodePointAtom 自身属于已经完成的元素，不需要入栈，进行进一步解析
            addToTopAtom(atom, false);
            return;
        } else if (atom instanceof CmdAtom) {
            CmdAtom cmdAtom = (CmdAtom) atom;
            if (cmdAtom.isCompleted()) {
                // 部分 CmdAtom 不需要参数，也就不需要入栈，进行进一步解析
                addToTopAtom(atom, false);
                return;
            }
        }
        // 入栈
        atomStack.push(atom);
    }

    /**
     * 添加到栈顶元素中
     *
     * @param atom
     */
    private void addToTopAtom(Atom atom, boolean isOptional) {
        // 获取栈顶元素
        while (true) {
            // 获取栈顶元素
            Atom topAtom = atomStack.peek();
            if (topAtom instanceof CmdAtom) {
                CmdAtom cmdAtom = (CmdAtom) topAtom;

                // 添加到 cmd 的参数中
                if (isOptional) {
                    if (!cmdAtom.addOptionalArg(atom)) {
                        // 可选参数严格判断，如果添加失败，直接抛出异常
                        throw new LatexParseException("\"" + cmdAtom.getCmd() + "\" 的可选参数格式错误");
                    }
                    return;
                } else {
                    if (cmdAtom.addArg(atom)) {
                        // 添加后，再一次判断 cmd 元素是否已经完成
                        if (cmdAtom.isCompleted()) {
                            // 元素解析完成时，直接出栈
                            atomStack.pop();
                            // 添加到父级元素中
                            addToTopAtom(topAtom, false);
                        }
                        return;
                    } else {
                        // 继续寻找上一级元素
                        continue;
                    }
                }
            } else if (topAtom instanceof EnvironmentAtom) {
                EnvironmentAtom envAtom = (EnvironmentAtom) topAtom;
                // 添加到 env 的参数中
                if (addOptionAtomToEnv(envAtom, atom)) {
                    return;
                }
                if (!(atom instanceof RowAtom)) {
                    // 创建一个 RowAtom，并入栈
                    RowAtom rowAtom = new RowAtom();
                    // 入栈
                    pushToStack(rowAtom);

                    // 继续处理 atom
                    continue;
                }
                RowAtom rowAtom = (RowAtom) atom;
                envAtom.addRow(rowAtom);
                return;
            } else if (topAtom instanceof RowAtom) {
                if (!(atom instanceof CellAtom)) {
                    // 创建 CellAtom，并入栈
                    CellAtom cellAtom = new CellAtom();
                    pushToStack(cellAtom);
                    continue;
                }
                RowAtom rowAtom = (RowAtom) topAtom;
                rowAtom.addCell((CellAtom) atom);
                return;
            } else if (topAtom instanceof CellAtom) {
                CellAtom cellAtom = (CellAtom) topAtom;
                addToCell(cellAtom, atom);
                return;
            } else if (topAtom instanceof AtomArray) {
                AtomArray atomArray = (AtomArray) topAtom;
                atomArray.addAtom(atom);
                return;
            } else {
                throw new LatexParseException("格式错误");
            }
        }

    }

    /**
     * 添加到 env 的参数中
     *
     * @param envAtom
     * @param atom
     *
     * @return
     */
    private boolean addOptionAtomToEnv(EnvironmentAtom envAtom, Atom atom) {
        List<Atom> optionList = envAtom.getOptionList();
        if (optionList.isEmpty() && envAtom.getEnv() == null) {
            // 第一个 optionAtom 是 env 的名称
            if (atom instanceof TextAtom) {
                String envName = ((TextAtom) atom).getText();
                if (envName.length() == 0) {
                    throw new LatexParseException("环境名称不能为空");
                }
                envAtom.setEnv(envName);
                return true;
            } else {
                throw new LatexParseException("环境名称格式错误");
            }
        }
        if (optionList.size() == envAtom.getOptionSize()) {
            return false;
        }
        optionList.add(atom);
        return true;
    }

    private void addToCell(CellAtom cellAtom, Atom atom) {
        if (atom instanceof CodePointAtom) {
            CodePointAtom cpAtom = (CodePointAtom) atom;
            if (cpAtom.getValue() == CodePointUtil.CP_AMPERSAND) {
                // 列分隔符

                // 出栈
                atomStack.pop();

                // 取出栈顶的 RowAtom
                RowAtom rowAtom = (RowAtom) atomStack.peek();
                rowAtom.addCell(mergeAtomArray(cellAtom));
                return;
            }
            if (cpAtom.getValue() == CodePointUtil.ESCAPE_CP) {
                // 行分隔符

                // 出栈
                atomStack.pop();

                // 栈顶的 RowAtom 出栈
                RowAtom rowAtom = (RowAtom) atomStack.pop();
                rowAtom.addCell(mergeAtomArray(cellAtom));

                // 添加到父级元素中
                addToTopAtom(rowAtom, false);
                return;
            }
        }

        List<Atom> contentList = cellAtom.getAtomList();
        if (!cellAtom.getAtomList().isEmpty()) {
            Atom prevAtom = contentList.get(contentList.size() - 1);
            if (prevAtom instanceof CmdAtom) {
                CmdAtom cmdAtom = (CmdAtom) prevAtom;
                // 如果前一个元素是 "end" 命令，则终止 env
                if (Cmds.CMD_END.equals(cmdAtom.getCmd())) {
                    // 出栈
                    atomStack.pop();

                    // 栈顶的 RowAtom 出栈
                    RowAtom rowAtom = (RowAtom) atomStack.pop();

                    // 栈顶的 EnvironmentAtom 出栈
                    EnvironmentAtom envAtom = (EnvironmentAtom) atomStack.pop();

                    // end 命令处理。 end 命令后的元素格式只能是： "{envName}"
                    if (!(atom instanceof TextAtom)) {
                        throw new LatexParseException("环境 \"" + envAtom.getEnv() + "\" 格式错误");
                    }
                    // 判断环境名称
                    String envName = ((TextAtom) atom).getText();
                    if (!envAtom.getEnv().equals(envName)) {
                        throw new LatexParseException("环境 \"" + envAtom.getEnv() + "\" 的 \\end 参数错误");
                    }

                    // 移除最后的 end 元素
                    contentList.remove(contentList.size() - 1);

                    // 添加到 行
                    rowAtom.addCell(mergeAtomArray(cellAtom));

                    // 添加到 环境
                    envAtom.addRow(rowAtom);

                    // 添加到父级元素中
                    addToTopAtom(envAtom, false);
                    return;
                }
            }
        }
        cellAtom.addAtom(atom);
    }

    /**
     * 合并 AtomArray 中的节点。
     * <ul>
     *     <li>合并连续的文本节点</li>
     *     <li>只有一个节点时，直接返回该节点</li>
     *     <li>其它情况，返回原值</li>
     * </ul>
     *
     * @param atomArray
     *
     * @return
     */
    public static Atom mergeAtomArray(AtomArray atomArray) {
        List<Atom> newAtomList = null;
        List<Atom> oldAtomList = atomArray.getAtomList();

        StringBuilder builder = null;
        for (int i = 0, len = oldAtomList.size(); i < len; i++) {
            Atom atom = oldAtomList.get(i);
            if (atom instanceof CodePointAtom) {
                CodePointAtom cpAtom = (CodePointAtom) atom;
                if (builder == null) {
                    builder = new StringBuilder();
                    if (newAtomList == null) {
                        if (i > 0) {
                            newAtomList = new ArrayList<>(oldAtomList.subList(0, i));
                        } else {
                            newAtomList = new ArrayList<>();
                        }
                    }
                }
                builder.appendCodePoint(cpAtom.getValue());
                continue;
            }

            if (builder != null) {
                newAtomList.add(buildAtomFromText(builder.toString()));
                builder = null;
            }
            if (newAtomList != null) {
                newAtomList.add(atom);
            }
        }
        if (newAtomList == null) {
            if (oldAtomList.size() == 1) {
                // 压缩旧的元素集合
                return oldAtomList.get(0);
            }

            // 返回原值
            return atomArray;
        }

        // 处理未结束的 builder
        if (builder != null) {
            newAtomList.add(buildAtomFromText(builder.toString()));
        }

        if (newAtomList.size() == 1) {
            // 压缩新的元素集合
            return newAtomList.get(0);
        }

        // 返回新的元素
        return new AtomArray(newAtomList);
    }

    /**
     * 根据单个 CodePoint 创建 atom。
     * <ul>
     *     <li>cp 为单个数字时，返回 IntAtom</li>
     *     <li>cp 为基本拉丁文字时，返回 CharAtom</li>
     *     <li>否则，返回 CodePointAtom</li>
     * </ul>
     *
     * @param cp
     *
     * @return
     */
    public static Atom buildSingleCodePointAtom(int cp) {
        if (CodePointUtil.isAsciiDigit(cp)) {
            return new IntAtom(CodePointUtil.toAsciiDigit(cp));
        }
        if (CodePointUtil.isBasicLatin(cp)) {
            return new CharAtom((char) cp);
        }
        return new CodePointAtom(cp);
    }

    /**
     * 根据字符串构建 atom。
     * <ul>
     *     <li>plainText 为 int 时，返回 IntAtom</li>
     *     <li>plainText 为 long 时，返回 LongAtom</li>
     *     <li>plainText 为 BigDecimal 时，返回 BigDecimal</li>
     *     <li>否则，返回 TextAtom</li>
     * </ul>
     *
     * @param plainText
     *
     * @return
     *
     * @see BigDecimal
     */
    public static Atom buildAtomFromText(String plainText) {
        try {
            // TODO: 2020/12/11 这里简单的使用 BigDecimal 来解析数字，会导致 科学计数法 的特征被忽略掉
            BigDecimal value = new BigDecimal(plainText);
            if (value.scale() == 0) {
                long longValue = value.longValue();
                if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
                    return new LongAtom(longValue);
                } else {
                    return new IntAtom((int) longValue);
                }
            } else {
                return new BigDecimalAtom(value);
            }
        } catch (NumberFormatException e) {
        }
        return new TextAtom(plainText);
    }

    /**
     * 判断 codePoint 是否为可转义字符。真正使用时，必须优先判断前一个字符是否为转义字符。
     * <p>
     * 支持的转义字符："# $ % ^ & _ { } ~ \"
     *
     * @return
     */
    private boolean isSupportEscape(int codePoint) {
        switch (codePoint) {
            case ESCAPE_CP:
            case L_GROUP_CP:
            case R_GROUP_CP:
            case SUPER_SCRIPT_CP:
            case SUB_SCRIPT_CP:
            case CP_NUMBER_SIGN:
            case CP_PERCENT_SIGN:
            case CP_DOLLAR_SIGN:
            case TILDE_CP:
            case CP_AMPERSAND:
                return true;
            default:
                return false;
        }
    }


}
