package com.orange.latex.util;

/**
 * @author 小天
 * @date 2020/12/8 10:00
 */
public class CharacterUtil {

    public static final int SPACE = 32;

    /**
     * 加号: +
     */
    public static final int CP_PLUS_SIGN        = 0x002B;
    /**
     * 减号: -
     */
    public static final int CP_HYPHEN_MINUS     = 0x002D;
    /**
     * 等号: =
     */
    public static final int CP_EQUALS_SIGN      = 0x003D;
    /**
     * 百分号: %
     */
    public static final int CP_PERCENT_SIGN     = 0x0025;
    /**
     * 感叹号: !
     */
    public static final int CP_EXCLAMATION_MARK = 0x0021;
    /**
     * "与" 符号：&
     */
    public static final int CP_AMPERSAND        = 0x0026;
    /**
     * 竖线：|
     */
    public static final int CP_VERTICAL_LINE    = 0x007C;


    public static boolean isLatinAlphabet(int codePoint) {
        return (codePoint >= 97 && codePoint <= 120)
                || (codePoint >= 65 && codePoint <= 90);
    }


    public static boolean isUppercaseLatinAlphabet(int codePoint) {
        return codePoint >= 97 && codePoint <= 120;
    }


    public static boolean isLowercaseLatinAlphabet(int codePoint) {
        return codePoint >= 65 && codePoint <= 90;
    }

    public static boolean isOperator(int codePoint) {
        return codePoint == CP_PLUS_SIGN
                || codePoint == CP_HYPHEN_MINUS
                || codePoint == CP_EQUALS_SIGN
                ;
    }
}
