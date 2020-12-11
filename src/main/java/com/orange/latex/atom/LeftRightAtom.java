package com.orange.latex.atom;

/**
 * "\left" 和 "\right" 包裹的内容
 *
 * @author 小天
 * @date 2020/12/10 11:34
 */
public class LeftRightAtom extends AtomArray {
    /**
     * 左边的符号
     */
    private int leftCodePoint;
    /**
     * 右边的符号
     */
    private int rightCodePoint;

    public int getLeftCodePoint() {
        return leftCodePoint;
    }

    public void setLeftCodePoint(int leftCodePoint) {
        this.leftCodePoint = leftCodePoint;
    }

    public int getRightCodePoint() {
        return rightCodePoint;
    }

    public void setRightCodePoint(int rightCodePoint) {
        this.rightCodePoint = rightCodePoint;
    }
}
