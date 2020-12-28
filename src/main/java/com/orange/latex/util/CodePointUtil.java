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
     * 除号: /
     */
    public static final int CP_SOLIDUS           = 0x002F;
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
     * 冒号： :
     */
    public static final int CP_COLON            = 0x003A;
    /**
     * 分号： ;
     */
    public static final int CP_SEMICOLON        = 0x003B;
    /**
     * 问号：?
     */
    public static final int CP_QUESTION_MASK    = 0x003F;
    /**
     * 符号：@
     */
    public static final int CP_COMMERCIAL_AT    = 0x0040;

    /**
     * 符号： ±
     */
    public static final int CP_PLUS_MINUS_SIGN     = 0x00B1;
    /**
     * 符号： ·
     */
    public static final int CP_MIDDLE_DOT          = 0x00B7;
    /**
     * 符号： ×
     */
    public static final int CP_MULTIPLICATION_SIGN = 0x00D7;
    /**
     * 符号： ÷
     */
    public static final int CP_DIVISION_SIGN       = 0x00F7;

    /**
     * 符号： ≠
     */
    public static final int CP_NOT_EQUAL_TO          = 0x2260;
    /**
     * 符号： ≤
     */
    public static final int CP_LESS_THAN_EQUAL_TO    = 0x2264;
    /**
     * 符号： ≥
     */
    public static final int CP_GREATER_THAN_EQUAL_TO = 0x2265;

    /**
     * 加号:  ＋
     */
    public static final int CP_PLUS_SIGN_FW         = 0xFF0B;
    /**
     * 减号:  －
     */
    public static final int CP_HYPHEN_MINUS_FW      = 0xFF0D;
    /**
     * 除号:  ／
     */
    public static final int CP_SOLIDUS_FW           = 0xFF0F;
    /**
     * 小于号： ＜
     */
    public static final int CP_LESS_THAN_SIGN_FW    = 0xFF1C;
    /**
     * 等号:  ＝
     */
    public static final int CP_EQUALS_SIGN_FW       = 0xFF1D;
    /**
     * 大于号： ＞
     */
    public static final int CP_GREATER_THAN_SIGN_FW = 0xFF1E;

    /**
     * 是否为英文字母（不区分大小写）
     *
     * @param codePoint
     *
     * @return
     *
     * @see #isUppercaseLatinAlphabet(int)
     * @see #isLowercaseLatinAlphabet(int)
     */
    public static boolean isLatinAlphabet(int codePoint) {
        return (codePoint >= 97 && codePoint <= 120)
                || (codePoint >= 65 && codePoint <= 90);
    }

    /**
     * 是否为大写字母。范围： [0x0041 ~ 0x005A]
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isUppercaseLatinAlphabet(int codePoint) {
        return codePoint >= 0x0041 && codePoint <= 0x005A;
    }

    /**
     * 是否为小写字母。范围： [0x0061 ~ 0x007A]
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isLowercaseLatinAlphabet(int codePoint) {
        return codePoint >= 0x0061 && codePoint <= 0x007A;
    }

    /**
     * 是否为基本的拉丁文。范围： [0x0021 ~ 0x007E]
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isBasicLatin(int codePoint) {
        return codePoint >= 0x0021 && codePoint <= 0x007E;
    }

    /**
     * 是否为 ascii 数字。范围： [0x0030 ~ 0x0039]
     *
     * @param codePoint
     *
     * @return
     */
    public static boolean isAsciiDigit(int codePoint) {
        return codePoint >= 0x0030 && codePoint <= 0x0039;
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
     * 转换为 ascii 数字
     *
     * @param codePoint
     *
     * @return
     *
     * @see #isAsciiDigit(int)
     */
    public static int toAsciiDigit(int codePoint) {
        return codePoint - 0x0030;
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
