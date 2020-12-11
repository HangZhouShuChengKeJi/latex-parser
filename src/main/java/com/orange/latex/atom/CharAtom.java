package com.orange.latex.atom;

/**
 * @author 小天
 * @date 2020/12/11 17:19
 */
public class CharAtom extends Atom implements Displayable {
    private char value;

    public CharAtom(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
