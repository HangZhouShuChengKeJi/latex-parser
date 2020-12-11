package com.orange.latex.atom.number;

import com.orange.latex.atom.Displayable;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class LongAtom extends NumberAtom<Long> implements Displayable {

    public LongAtom(Long value) {
        super(value);
    }
}
