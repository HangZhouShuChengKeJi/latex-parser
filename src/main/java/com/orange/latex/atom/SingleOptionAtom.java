package com.orange.latex.atom;

/**
 * 单个选项的公式元素
 *
 * @author 小天
 * @date 2020/12/10 18:37
 */
public class SingleOptionAtom extends CmdAtom {

    private Atom atom;

    public SingleOptionAtom(String cmd) {
        super(cmd, 1);
    }

    public Atom getAtom() {
        return atom;
    }

    @Override
    public boolean addArg(Atom atom) {
        if (this.atom == null) {
            this.atom = atom;
            return true;
        }
        return false;
    }

    @Override
    public boolean isCompleted() {
        return atom != null;
    }
}
