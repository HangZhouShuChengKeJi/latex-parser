package com.orange.latex.atom.number;

import com.orange.latex.atom.Atom;
import com.orange.latex.atom.Displayable;

import java.math.BigDecimal;

/**
 * @author 小天
 * @date 2020/12/11 16:45
 */
public class BigDecimalAtom extends Atom implements Displayable {
    private BigDecimal value;

    public BigDecimalAtom(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
