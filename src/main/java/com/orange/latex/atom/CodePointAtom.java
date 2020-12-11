package com.orange.latex.atom;

/**
 * @author 小天
 * @date 2020/12/9 18:22
 */
public class CodePointAtom extends Atom {

    private int codePoint;

    public CodePointAtom(int codePoint) {
        this.codePoint = codePoint;
    }

    public int getCodePoint() {
        return codePoint;
    }
}
