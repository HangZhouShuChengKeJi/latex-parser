package com.orange.latex;

import com.orange.latex.atom.CmdAtom;

/**
 * cmd 元素定义
 *
 * @author 小天
 * @date 2020/12/9 16:05
 */
public class CmdAtomDefinition {
    /**
     * 命令
     */
    private   String cmd;
    /**
     * 参数数量
     */
    private   int    argSize;
    /**
     * 可选参数数量
     */
    protected int    optionalArgSize;
    /**
     * 实现类
     */
    private Class<? extends CmdAtom> atomClass;

    public CmdAtomDefinition(String cmd, int argSize, int optionalArgSize) {
        this.cmd = cmd;
        this.argSize = argSize;
        this.optionalArgSize = optionalArgSize;
    }

    public CmdAtomDefinition(String cmd, Class<? extends CmdAtom> atomClass) {
        this.cmd = cmd;
        this.atomClass = atomClass;
    }

    public String getCmd() {
        return cmd;
    }

    public int getArgSize() {
        return argSize;
    }

    public int getOptionalArgSize() {
        return optionalArgSize;
    }

    public Class<? extends CmdAtom> getAtomClass() {
        return atomClass;
    }
}
