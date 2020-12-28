package com.orange.latex.atom;

import com.orange.latex.Cmds;
import com.orange.latex.util.CodePointUtil;

/**
 * @author 小天
 * @date 2020/12/28 14:18
 */
public class QuadAtom extends CmdAtom {

    public final static int BASE_LENGTH = 18;

    private int spaceLength;

    public QuadAtom() {
        super(Cmds.CMD_quad);
        this.spaceLength = BASE_LENGTH;
    }

    public QuadAtom(String cmd, int spaceLength) {
        super(cmd);
        this.spaceLength = spaceLength;
    }

    public QuadAtom(int codePoint, int spaceLength) {
        super(CodePointUtil.toString(codePoint));
        this.spaceLength = spaceLength;
    }

    public int getSpaceLength() {
        return spaceLength;
    }
}
