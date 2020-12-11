package com.orange.latex.atom.number;

import com.orange.latex.atom.Atom;
import com.orange.latex.atom.Displayable;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class IntAtom extends Atom implements Displayable {
    private int value;

    public IntAtom(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
