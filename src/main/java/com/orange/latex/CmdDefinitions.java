package com.orange.latex;

import com.orange.latex.atom.CmdAtom;
import com.orange.latex.atom.FractionAtom;
import com.orange.latex.atom.QquadAtom;
import com.orange.latex.atom.QuadAtom;
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
        atomMap.put(Cmds.CMD_PI, new CmdAtomDefinition(Cmds.CMD_PI));

        // 算术运算符
        atomMap.put(Cmds.CMD_pm, new CmdAtomDefinition(Cmds.CMD_pm));
        atomMap.put(Cmds.CMD_times, new CmdAtomDefinition(Cmds.CMD_times));
        atomMap.put(Cmds.CMD_div, new CmdAtomDefinition(Cmds.CMD_div));

        // 关系运算符
        atomMap.put(Cmds.CMD_neq, new CmdAtomDefinition(Cmds.CMD_geq));
        atomMap.put(Cmds.CMD_lt, new CmdAtomDefinition(Cmds.CMD_lt));
        atomMap.put(Cmds.CMD_leq, new CmdAtomDefinition(Cmds.CMD_leq));
        atomMap.put(Cmds.CMD_gt, new CmdAtomDefinition(Cmds.CMD_gt));
        atomMap.put(Cmds.CMD_geq, new CmdAtomDefinition(Cmds.CMD_geq));

        // 对数运算符
        atomMap.put(Cmds.CMD_lg, new CmdAtomDefinition(Cmds.CMD_lg));
        atomMap.put(Cmds.CMD_ln, new CmdAtomDefinition(Cmds.CMD_ln));
        atomMap.put(Cmds.CMD_log, new CmdAtomDefinition(Cmds.CMD_log));

        // 数学空格
        atomMap.put(Cmds.CMD_quad, new CmdAtomDefinition(Cmds.CMD_quad, QuadAtom.class));
        atomMap.put(Cmds.CMD_qquad, new CmdAtomDefinition(Cmds.CMD_qquad, QquadAtom.class));
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
