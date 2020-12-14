package com.orange.latex.atom;

import java.util.ArrayList;
import java.util.List;

/**
 * "行" 元素
 *
 * @author 小天
 * @date 2020/12/14 9:55
 */
public class RowAtom extends Atom {
    private List<Atom> atomList;

    public RowAtom() {
        this.atomList = new ArrayList<>();
    }

    public List<Atom> getAtomList() {
        return atomList;
    }

    /**
     * 每一列，可以是 CellAtom，也可以是其它的 Atom
     *
     * @param atom
     */
    public void addCell(Atom atom) {
        atomList.add(atom);
    }
}
