package com.orange.latex.atom;

import com.orange.latex.Cmds;

/**
 * 下标
 *
 * @author 小天
 * @date 2020/12/8 18:38
 */
public class SubAtom extends SingleOptionAtom {

    public SubAtom() {
        super(Cmds.CMD_SUB);
    }
}
