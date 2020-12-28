package com.orange.latex;

/**
 * 命令集合
 *
 * @author 小天
 * @date 2020/12/8 10:22
 */
public class Cmds {

    /**
     * left 指令
     */
    public static final String CMD_LEFT  = "left";
    /**
     * right 指令
     */
    public static final String CMD_RIGHT = "right";
    /**
     * begin 指令
     */
    public static final String CMD_BEGIN = "begin";
    /**
     * end 指令
     */
    public static final String CMD_END   = "end";

    /**
     * 上标
     */
    public static final String CMD_SUP = "sup";
    /**
     * 下标
     */
    public static final String CMD_SUB = "sub";

    /**
     * 圆周率符号：π
     */
    public static final String CMD_PI = "pi";

    ////////  数学空格  ////////

    /**
     * 数学空格： \quad 的大小对应于目前字体中字符 ‘M’ 的宽度。
     */
    public static final String CMD_quad  = "quad";
    /**
     * 数学空格： \qquad 的大小是 \quad 的两倍。
     */
    public static final String CMD_qquad = "qquad";

    ////////  关系运算符  ////////

    /**
     * 符号： ±
     */
    public static final String CMD_pm        = "pm";
    /**
     * 符号： ×
     */
    public static final String CMD_times     = "times";
    /**
     * 符号： ÷
     */
    public static final String CMD_div       = "div";
    /**
     * 符号： ∣
     */
    public static final String CMD_mid       = "mid";
    /**
     * 符号： ∤
     */
    public static final String CMD_nmid      = "nmid";
    /**
     * 符号： ⋅
     */
    public static final String CMD_cdot      = "cdot";
    /**
     * 符号： ∘
     */
    public static final String CMD_circ      = "circ";
    /**
     * 符号： ∗
     */
    public static final String CMD_ast       = "ast";
    /**
     * 符号： ⨀
     */
    public static final String CMD_bigodot   = "bigodot";
    /**
     * 符号： ⨂
     */
    public static final String CMD_bigotimes = "bigotimes";
    /**
     * 符号： ⨁
     */
    public static final String CMD_bigoplus  = "bigoplus";
    /**
     * 符号： <
     */
    public static final String CMD_lt        = "lt";
    /**
     * 符号： ≤
     */
    public static final String CMD_leq       = "leq";
    /**
     * 符号： >
     */
    public static final String CMD_gt        = "gt";
    /**
     * 符号： ≥
     */
    public static final String CMD_geq       = "geq";
    /**
     * 符号： ≠
     */
    public static final String CMD_neq       = "neq";
    /**
     * 符号： ≈
     */
    public static final String CMD_approx    = "approx";
    /**
     * 符号： ≡
     */
    public static final String CMD_equiv     = "equiv";
    /**
     * 符号： ∑
     */
    public static final String CMD_sum       = "sum";
    /**
     * 符号： ∏
     */
    public static final String CMD_prod      = "prod";
    /**
     * 符号： ∐
     */
    public static final String CMD_coprod    = "coprod";

    ////////  集合运算符  ////////

    /**
     * 符号： ∅
     */
    public static final String CMD_emptyset = "emptyset";
    /**
     * 符号： ∈
     */
    public static final String CMD_in       = "in";
    /**
     * 符号： ∉
     */
    public static final String CMD_notin    = "notin";
    /**
     * 符号： ⊂
     */
    public static final String CMD_subset   = "subset";
    /**
     * 符号： ⊃
     */
    public static final String CMD_supset   = "supset";
    /**
     * 符号： ⊆
     */
    public static final String CMD_subseteq = "subseteq";
    /**
     * 符号： ⊇
     */
    public static final String CMD_supseteq = "supseteq";
    /**
     * 符号： ⋂
     */
    public static final String CMD_bigcap   = "bigcap";
    /**
     * 符号： ⋃
     */
    public static final String CMD_bigcup   = "bigcup";
    /**
     * 符号： ⋁
     */
    public static final String CMD_bigvee   = "bigvee";
    /**
     * 符号： ⋀
     */
    public static final String CMD_bigwedge = "bigwedge";
    /**
     * 符号： ⨄
     */
    public static final String CMD_biguplus = "biguplus";
    /**
     * 符号： ⨆
     */
    public static final String CMD_bigsqcup = "bigsqcup";

    ////////  对数运算符  ////////

    /**
     * 符号： log
     */
    public static final String CMD_log = "log";
    /**
     * 符号： lg
     */
    public static final String CMD_lg  = "lg";
    /**
     * 符号： ln
     */
    public static final String CMD_ln  = "ln";

    ////////  三角运算符  ////////

    /**
     * 符号： ⊥
     */
    public static final String CMD_bot   = "bot";
    /**
     * 符号： ∠
     */
    public static final String CMD_angle = "angle";
    /**
     * 符号： sin
     */
    public static final String CMD_sin   = "sin";
    /**
     * 符号： cos
     */
    public static final String CMD_cos   = "cos";
    /**
     * 符号： tan
     */
    public static final String CMD_tan   = "tan";
    /**
     * 符号： cot
     */
    public static final String CMD_cot   = "cot";
    /**
     * 符号： sec
     */
    public static final String CMD_sec   = "sec";
    /**
     * 符号： csc
     */
    public static final String CMD_csc   = "csc";

    ////////  微积分运算符  ////////

    /**
     * 符号： ′
     */
    public static final String CMD_prime  = "prime";
    /**
     * 符号： ∫
     */
    public static final String CMD_int    = "int";
    /**
     * 符号： ∬
     */
    public static final String CMD_iint   = "iint";
    /**
     * 符号： ∭
     */
    public static final String CMD_iiint  = "iiint";
    /**
     * 符号： ⨌
     */
    public static final String CMD_iiiint = "iiiint";
    /**
     * 符号： ∮
     */
    public static final String CMD_oint   = "oint";
    /**
     * 符号： lim
     */
    public static final String CMD_lim    = "lim";
    /**
     * 符号： ∞
     */
    public static final String CMD_infty  = "infty";
    /**
     * 符号： ∇
     */
    public static final String CMD_nabla  = "nabla";

    ////////  逻辑运算符  ////////

    /**
     * 符号： ∵
     */
    public static final String CMD_because   = "because";
    /**
     * 符号： ∴
     */
    public static final String CMD_therefore = "therefore";
    /**
     * 符号： ∀
     */
    public static final String CMD_forall    = "forall";
    /**
     * 符号： ∃
     */
    public static final String CMD_exists    = "exists";

    ////////  箭头符号  ////////

    /**
     * 符号： ↑
     */
    public static final String CMD_uparrow        = "uparrow";
    /**
     * 符号： ↓
     */
    public static final String CMD_downarrow      = "downarrow";
    /**
     * 符号： ⇑
     */
    public static final String CMD_Uparrow        = "Uparrow";
    /**
     * 符号： ⇓
     */
    public static final String CMD_Downarrow      = "Downarrow";
    /**
     * 符号： →
     */
    public static final String CMD_rightarrow     = "rightarrow";
    /**
     * 符号： ←
     */
    public static final String CMD_leftarrow      = "leftarrow";
    /**
     * 符号： ⇒
     */
    public static final String CMD_Rightarrow     = "Rightarrow";
    /**
     * 符号： ⇐
     */
    public static final String CMD_Leftarrow      = "Leftarrow";
    /**
     * 符号： ⟶
     */
    public static final String CMD_longrightarrow = "longrightarrow";
    /**
     * 符号： ⟵
     */
    public static final String CMD_longleftarrow  = "longleftarrow";
    /**
     * 符号： ⟹
     */
    public static final String CMD_Longrightarrow = "Longrightarrow";
    /**
     * 符号： ⟸
     */
    public static final String CMD_Longleftarrow  = "Longleftarrow";

    ////////  常用命令  ////////

    /**
     * 分数
     */
    public static final String CMD_FRAC = "frac";
    /**
     * 根号
     */
    public static final String CMD_SQRT = "sqrt";

    /**
     * 和分数相同的效果
     */
    public static final String CMD_over = "over";

    /**
     * 符号： "..."
     */
    public static final String CMD_cdots = "cdots";

}
