package com.orange.latex.atom.number;

import com.orange.latex.atom.Displayable;

import java.math.BigDecimal;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class BigDecimalAtom extends NumberAtom<BigDecimal> implements Displayable {

    public BigDecimalAtom(BigDecimal value) {
        super(value);
    }
}
