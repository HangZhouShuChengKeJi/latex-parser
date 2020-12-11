package com.orange.latex.atom;

import com.orange.latex.Cmds;

/**
 * @author 小天
 * @date 2020/12/8 10:44
 */
public class FractionAtom extends CmdAtom {

    /**
     * 分子
     */
    private Atom num;
    /**
     * 分母
     */
    private Atom den;

    public FractionAtom() {
        super(Cmds.CMD_FRAC, 2);
    }

    public Atom getNum() {
        return num;
    }

    public Atom getDen() {
        return den;
    }

    @Override
    public boolean addArg(Atom atom) {
        if (num == null) {
            num = atom;
            return true;
        }
        if (den == null) {
            den = atom;
            return true;
        }
        return false;
    }

    @Override
    public boolean isCompleted() {
        return den != null && num != null;
    }
}
