package com.orange.latex.atom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小天
 * @date 2020/12/8 15:04
 */
public class AtomArray extends Atom {

    private List<Atom> atomList;

    public AtomArray() {
        this.atomList = new ArrayList<>();
    }

    public AtomArray(List<Atom> atomList) {
        this.atomList = atomList;
    }

    public List<Atom> getAtomList() {
        return atomList;
    }

    public void addAtom(Atom atom) {
        if (atom instanceof TextAtom) {
            int prevIndex = atomList.size() - 1;
            if (prevIndex >= 0) {
                Atom prevAtom = atomList.get(prevIndex);
                if (prevAtom instanceof TextAtom) {

                }
            }
        }
        atomList.add(atom);
    }
}
