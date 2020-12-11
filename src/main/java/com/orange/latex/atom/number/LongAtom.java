package com.orange.latex.atom.number;

import com.orange.latex.atom.Atom;
import com.orange.latex.atom.Displayable;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class LongAtom extends Atom implements Displayable {
    private long value;

    public LongAtom(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
