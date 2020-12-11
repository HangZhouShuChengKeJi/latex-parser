package com.orange.latex.util;

/**
 * @author 小天
 * @date 2020/12/8 10:00
 */
public class CodePointUtil {

    /**
     * 空格 的 codePoint
     */
    public static final int SPACE_CP = 0x0020;

    /**
     * 反斜杠（latex 转义符） 的 codePoint
     */
    public static final int ESCAPE_CP = 0x005C;

    /**
     * 上标 的 codePoint
     */
    public static final int SUPER_SCRIPT_CP = 0x005E;
    /**
     * 下标 的 codePoint
     */
    public static final int SUB_SCRIPT_CP   = 0x005F;

    /**
     * 左大括号 的 codePoint
     */
    public static final int L_GROUP_CP = 0x007B;
    /**
     * 右大括号 的 codePoint
     */
    public static final int R_GROUP_CP = 0x007D;

    /**
     * 百分号: %
     */
    public static final int CP_PERCENT_SIGN = 0x0025;
    /**
     * 符号：&
     */
    public static final int CP_AMPERSAND    = 0x0026;
    /**
     * 符号：#
     */
    public static final int CP_NUMBER_SIGN  = 0x0023;
    /**
     * 符号：$
     */
    public static final int CP_DOLLAR_SIGN  = 0x0024;
    /**
     * 符号：~
     */
    public static final int TILDE_CP        = 0x007E;

    /**
     * 左中括号 的 codePoint
     */
    public static final int L_BRACKET_CP = 0x005B;
    /**
     * 右中括号 的 codePoint
     */
    public static final int R_BRACKET_CP = 0x005D;

    /**
     * 加号: +
     */
    public static final int CP_PLUS_SIGN         = 0x002B;
    /**
     * 减号: -
     */
    public static final int CP_HYPHEN_MINUS      = 0x002D;
    /**
     * 等号: =
     */
    public static final int CP_EQUALS_SIGN       = 0x003D;
    /**
     * 小于号：<
     */
    public static final int CP_LESS_THAN_SIGN    = 0x003C;
    /**
     * 大于号：>
     */
    public static final int CP_GREATER_THAN_SIGN = 0x003E;

    /**
     * 感叹号: !
     */
    public static final int CP_EXCLAMATION_MARK = 0x0021;
    /**
     * 竖线：|
     */
    public static final int CP_VERTICAL_LINE    = 0x007C;
    /**
     * 星号： *
     */
    public static final int CP_ASTERISK         = 0x002A;
    /**
     * 逗号： ,
     */
    public static final int CP_COMMA            = 0x002C;
    /**
     * 小数点： .
     */
    public static final int CP_FULL_STOP        = 0x002E;
    /**
     * 问号：?
     */
    public static final int CP_QUESTION_MASK    = 0x003F;
    /**
     * 符号：@
     */
    public static final int CP_COMMERCIAL_AT    = 0x0040;

    /**
     * 是否为英文字母（不区分大小写）
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isLatinAlphabet(int codePoint) {
        return (codePoint >= 97 && codePoint <= 120)
                || (codePoint >= 65 && codePoint <= 90);
    }

    /**
     * 是否为答谢字母
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isUppercaseLatinAlphabet(int codePoint) {
        return codePoint >= 97 && codePoint <= 120;
    }

    /**
     * 是否为小写字母
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isLowercaseLatinAlphabet(int codePoint) {
        return codePoint >= 65 && codePoint <= 90;
    }

    /**
     * 是否为运算符
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isOperator(int codePoint) {
        // TODO: 2020/12/11 运算符待补充
        return codePoint == CP_PLUS_SIGN
                || codePoint == CP_HYPHEN_MINUS
                || codePoint == CP_EQUALS_SIGN
                || codePoint == CP_LESS_THAN_SIGN
                || codePoint == CP_GREATER_THAN_SIGN
                ;
    }

    /**
     * 将 codePoint 转换为字符串
     *
     * @param codePoint 代码点
     *
     * @return
     */
    public static String toString(int codePoint) {
        return new String(Character.toChars(codePoint));
    }
}
