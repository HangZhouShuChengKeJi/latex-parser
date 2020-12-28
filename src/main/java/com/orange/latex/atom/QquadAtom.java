package com.orange.latex.atom;

import com.orange.latex.Cmds;

/**
 * @author 小天
 * @date 2020/12/28 14:48
 */
public class QquadAtom extends QuadAtom {
    public QquadAtom() {
        super(Cmds.CMD_qquad, 2 * QuadAtom.BASE_LENGTH);
    }
}
