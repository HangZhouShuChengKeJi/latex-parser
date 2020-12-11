package com.orange.latex.atom;

import com.orange.latex.util.CodePointUtil;

/**
 * @author 小天
 * @date 2020/12/9 18:22
 */
public class CodePointAtom extends Atom implements Displayable {

    private int value;

    public CodePointAtom(int value) {
        this.value = value;
    }

    /**
     * 获取 codePoint
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return CodePointUtil.toString(value);
    }
}
