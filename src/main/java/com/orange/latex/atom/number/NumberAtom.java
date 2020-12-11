package com.orange.latex.atom.number;

import com.orange.latex.atom.Atom;

/**
 * @author 小天
 * @date 2020/12/11 17:56
 */
public abstract class NumberAtom<T extends Number> extends Atom {
    protected T value;

    public NumberAtom(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
