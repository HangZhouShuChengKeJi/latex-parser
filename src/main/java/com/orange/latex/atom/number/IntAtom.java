package com.orange.latex.atom.number;

import com.orange.latex.atom.Displayable;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class IntAtom extends NumberAtom<Integer> implements Displayable {

    public IntAtom(Integer value) {
        super(value);
    }
}
