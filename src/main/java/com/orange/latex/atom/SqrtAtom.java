package com.orange.latex.atom;

import com.orange.latex.Cmds;
import com.orange.latex.LatexParseException;

/**
 * 开根号
 *
 * @author 小天
 * @date 2020/12/8 10:44
 */
public class SqrtAtom extends SingleOptionAtom {

    /**
     * 开 n 次方的 n 表达式
     */
    private Atom root;
    /**
     * 被开次方的书
     */
    private Atom arg;

    public SqrtAtom() {
        super(Cmds.CMD_SQRT);
    }

    public Atom getRoot() {
        return root;
    }

    public Atom getArg() {
        return arg;
    }

    @Override
    public boolean addOptionalArg(Atom atom) {
        if (root == null) {
            root = atom;
            return true;
        } else {
            throw new LatexParseException("\\sqrt 参数格式错误");
        }
    }

    @Override
    public boolean addArg(Atom atom) {
        if (arg == null) {
            this.arg = atom;
            return true;
        }
        return false;
    }

    @Override
    public boolean isCompleted() {
        return arg != null;
    }
}
