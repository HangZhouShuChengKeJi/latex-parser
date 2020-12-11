package com.orange.latex.atom;

import com.orange.latex.AtomParseSupport;
import com.orange.latex.LatexParseException;

import java.util.LinkedList;
import java.util.List;

/**
 * 命令元素
 *
 * @author 小天
 * @date 2020/12/8 12:23
 */
public class CmdAtom extends Atom implements AtomParseSupport {
    /**
     * 命令名称
     */
    protected String     cmd;
    /**
     * 参数数量
     */
    protected int        argSize;
    /**
     * 参数列表
     */
    protected List<Atom> argList;
    /**
     * 可选参数数量
     */
    protected int        optionalArgSize;
    /**
     * 可选参数列表
     */
    protected List<Atom> optionalArgList;

    public CmdAtom(String cmd) {
        this.cmd = cmd;
    }

    public CmdAtom(String cmd, int argSize) {
        this.cmd = cmd;
        this.argSize = argSize;
        if (argSize > 0) {
            this.argList = new LinkedList<>();
        }
    }

    public CmdAtom(String cmd, int argSize, int optionalArgSize) {
        this.cmd = cmd;
        this.argSize = argSize;
        if (argSize > 0) {
            this.argList = new LinkedList<>();
        }
        this.optionalArgSize = optionalArgSize;
        if (argSize > 0) {
            this.optionalArgList = new LinkedList<>();
        }
    }

    public String getCmd() {
        return cmd;
    }

    public int getArgSize() {
        return argSize;
    }

    public List<Atom> getArgList() {
        return argList;
    }

    public int getOptionalArgSize() {
        return optionalArgSize;
    }

    public List<Atom> getOptionalArgList() {
        return optionalArgList;
    }

    /**
     * 给命令添加参数。
     *
     * @param atom
     *
     * @return true: 添加成功；false: 添加失败
     */
    public boolean addArg(Atom atom) {
        if (isCompleted()) {
            return false;
        } else {
            this.argList.add(atom);
            return true;
        }
    }

    /**
     * 给命令添加可选参数。
     *
     * @param atom
     *
     * @return true: 添加成功；false: 添加失败
     */
    public boolean addOptionalArg(Atom atom) {
        if (isCompleted()) {
            return false;
        }
        if (this.optionalArgSize == 0) {
            throw new LatexParseException("\"" + cmd + "\" 不需要可选参数");
        }
        if (this.optionalArgList.size() >= this.optionalArgSize) {
            throw new LatexParseException("超出 \"" + cmd + "\" 的可选参数数量");
        }
        this.optionalArgList.add(atom);
        return true;
    }

    @Override
    public boolean isCompleted() {
        return (argSize == 0 && optionalArgSize == 0)
                || (argSize > 0 && this.argList.size() == argSize);
    }
}
