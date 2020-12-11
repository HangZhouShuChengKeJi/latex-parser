package com.orange.latex;

import com.orange.latex.atom.CmdAtom;
import com.orange.latex.atom.FractionAtom;
import com.orange.latex.atom.SqrtAtom;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令定义集合
 *
 * @author 小天
 * @date 2020/12/9 16:07
 */
public class CmdDefinitions {
    private static Map<String, CmdAtomDefinition> atomMap = new HashMap<>();

    static {
        // 分数
        atomMap.put("frac", new CmdAtomDefinition("frac", FractionAtom.class));
        // 根号
        atomMap.put("sqrt", new CmdAtomDefinition("sqrt", SqrtAtom.class));
        // 加减符号
        atomMap.put("pm", new CmdAtomDefinition("pm", 0, 0));
    }

    public static CmdAtomDefinition get(String cmd) {
        return atomMap.getOrDefault(cmd, new CmdAtomDefinition(cmd, 0, 0));
    }

    public static CmdAtom newCmd(String cmd) {
        CmdAtomDefinition cmdNodeDefinition = get(cmd);
        Class<? extends CmdAtom> atomClass = cmdNodeDefinition.getAtomClass();
        if (atomClass == null) {
            // 未知命令
            return new CmdAtom(cmd, cmdNodeDefinition.getArgSize());
        }
        try {
            // 未知命令
            return atomClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LatexParseException(e);
        }
    }
}
