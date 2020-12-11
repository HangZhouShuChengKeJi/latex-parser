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
        atomMap.put(Cmds.CMD_FRAC, new CmdAtomDefinition(Cmds.CMD_FRAC, FractionAtom.class));
        atomMap.put(Cmds.CMD_SQRT, new CmdAtomDefinition(Cmds.CMD_SQRT, SqrtAtom.class));
        atomMap.put(Cmds.CMD_PM, new CmdAtomDefinition(Cmds.CMD_PM));
        atomMap.put(Cmds.CMD_PI, new CmdAtomDefinition(Cmds.CMD_PI));
    }

    public static CmdAtomDefinition get(String cmd) {
        return atomMap.getOrDefault(cmd, new CmdAtomDefinition(cmd));
    }

    public static CmdAtom newCmd(String cmd) {
        CmdAtomDefinition cmdNodeDefinition = get(cmd);
        Class<? extends CmdAtom> atomClass = cmdNodeDefinition.getAtomClass();
        if (atomClass == null) {
            // 未知命令
            return new CmdAtom(cmd, cmdNodeDefinition.getArgSize(), cmdNodeDefinition.getOptionalArgSize());
        }
        try {
            // 未知命令
            return atomClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LatexParseException(e);
        }
    }
}
