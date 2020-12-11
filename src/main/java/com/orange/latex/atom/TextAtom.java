package com.orange.latex.atom;

/**
 * 文本
 *
 * @author 小天
 * @date 2020/12/7 13:10
 */
public class TextAtom extends Atom {
    private String text;

    public TextAtom(String text) {
        this.text = text;
    }

    public TextAtom(int codePoint) {
        this.text = new String(Character.toChars(codePoint));
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
