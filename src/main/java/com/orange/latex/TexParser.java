package com.orange.latex;

import com.orange.latex.atom.Atom;
import com.orange.latex.atom.AtomArray;
import com.orange.latex.atom.BracketAtom;
import com.orange.latex.atom.CmdAtom;
import com.orange.latex.atom.CodePointAtom;
import com.orange.latex.atom.EnvironmentAtom;
import com.orange.latex.atom.GroupAtom;
import com.orange.latex.atom.LeftRightAtom;
import com.orange.latex.atom.SubAtom;
import com.orange.latex.atom.SupAtom;
import com.orange.latex.atom.TextAtom;
import com.orange.latex.util.CodePointUtil;

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
    public static final int SPACE_CP = 0x0020;

    /**
     * 反斜杠（latex 转义符） 的 codePoint
     */
    public static final int ESCAPE_CP = 0x005C;

    /**
     * 上标 的 codePoint
     */
    public static final int SUPER_SCRIPT_CP = 0x005E;
    /**
     * 下标 的 codePoint
     */
    public static final int SUB_SCRIPT_CP   = 0x005F;

    /**
     * 左大括号 的 codePoint
     */
    public static final int L_GROUP_CP = 0x007B;
    /**
     * 右大括号 的 codePoint
     */
    public static final int R_GROUP_CP = 0x007D;

    /**
     * 百分号: %
     */
    public static final int CP_PERCENT_SIGN = 0x0025;
    /**
     * 符号：&
     */
    public static final int CP_AMPERSAND    = 0x0026;
    /**
     * 符号：#
     */
    public static final int CP_NUMBER_SIGN  = 0x0023;
    /**
     * 符号：$
     */
    public static final int CP_DOLLAR_SIGN  = 0x0024;
    /**
     * 符号：~
     */
    public static final int TILDE_CP        = 0x007E;

    /**
     * 左中括号 的 codePoint
     */
    public static final int L_BRACKET_CP = 0x005B;
    /**
     * 右中括号 的 codePoint
     */
    public static final int R_BRACKET_CP = 0x005D;

    private String plainText;
    private int[]  codePoints;
    private int    pos = 0;

    private Stack<Atom> atomStack;

    public TexParser(String plainText) {
        this.plainText = plainText;
        this.codePoints = plainText.codePoints().toArray();
        this.pos = 0;

        atomStack = new Stack<>();
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
        int codePoint = codePoints[pos++];
        while (CodePointUtil.isLatinAlphabet(codePoint)) {
            cmdBuilder.appendCodePoint(codePoint);
            codePoint = codePoints[pos++];
        }
        // 指针回退
        pos--;
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
                } else {
                    // 转义字符视为字符串
                    if (isSupportEscape(nextCodePoint)) {
                        // 指针前进
                        pos++;
                        // 入栈
                        pushToStack(new CodePointAtom(nextCodePoint));
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
                    subAtom.addArg(new TextAtom(nextCodePoint));
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
                    supAtom.addArg(new TextAtom(nextCodePoint));
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
            if (topAtom instanceof AtomArray) {
                AtomArray atomArray = (AtomArray) topAtom;
                atomArray.addAtom(atom);
                return;
            } else if (topAtom instanceof CmdAtom) {
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
                // 添加到 env 的内容中
                if (addContentAtomToEnv(envAtom, atom)) {
                    return;
                }
                // 添加失败时，表示该 env 需要的参数已经解析完毕，将该命令出栈
                atomStack.pop();

                // 添加到父级元素中
                addToTopAtom(topAtom, false);

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

    /**
     * 添加元素到 env 的 内容中
     *
     * @param envAtom
     * @param atom
     *
     * @return true: 添加成功；false：添加失败（遇到了 env 的 end 部分）
     */
    private boolean addContentAtomToEnv(EnvironmentAtom envAtom, Atom atom) {
        List<Atom> contentList = envAtom.getContentList();

        if (!contentList.isEmpty()) {
            Atom prevAtom = contentList.get(contentList.size() - 1);
            if (prevAtom instanceof CmdAtom) {
                CmdAtom cmdAtom = (CmdAtom) prevAtom;
                // 如果前一个元素是 "end" 命令，则终止 env
                if (Cmds.CMD_END.equals(cmdAtom.getCmd())) {
                    // end 命令处理。 end 命令后的元素格式只能是： "{envName}"
                    if (atom instanceof TextAtom) {
                        String envName = ((TextAtom) atom).getText();
                        if (!envAtom.getEnv().equals(envName)) {
                            throw new LatexParseException("环境 \"" + envAtom.getEnv() + "\" 的 \\end 参数错误");
                        }
                    } else {
                        throw new LatexParseException("环境 \"" + envAtom.getEnv() + "\" 格式错误");
                    }
                    // 移除最后的 end 元素
                    contentList.remove(contentList.size() - 1);
                    return false;
                }
            }
        }
        envAtom.getContentList().add(atom);
        return true;
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
                // 跳过运算符
                if (!CodePointUtil.isOperator(cpAtom.getCodePoint())) {
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
                    builder.appendCodePoint(cpAtom.getCodePoint());
                    continue;
                }
            }

            if (builder != null) {
                newAtomList.add(new TextAtom(builder.toString()));
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
            newAtomList.add(new TextAtom(builder.toString()));
        }

        if (newAtomList.size() == 1) {
            // 压缩新的元素集合
            return newAtomList.get(0);
        }

        // 返回新的元素
        return new AtomArray(newAtomList);
    }

    /**
     * 判断 codePoint 是否为可转义字符。真正使用时，必须优先判断前一个字符是否为转义字符。
     *
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
