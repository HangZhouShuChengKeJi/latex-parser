package com.orange.latex.atom;

import com.orange.latex.Cmds;

/**
 * 上标
 *
 * @author 小天
 * @date 2020/12/8 18:38
 */
public class SupAtom extends SingleOptionAtom {

    public SupAtom() {
        super(Cmds.CMD_SUP);
    }
}
