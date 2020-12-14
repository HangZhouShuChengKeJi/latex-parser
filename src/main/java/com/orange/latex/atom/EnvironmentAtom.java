package com.orange.latex.atom;

import java.util.ArrayList;
import java.util.List;

/**
 * env atom
 *
 * @author 小天
 * @date 2020/12/9 15:10
 */
public class EnvironmentAtom extends Atom {
    private String env;
    private int    optionSize = 1;

    /**
     * 参数集合
     */
    private List<Atom> optionList;

    /**
     * 行集合
     */
    private List<RowAtom> rowList;

    public EnvironmentAtom() {
        this.optionList = new ArrayList<>();
        this.rowList = new ArrayList<>();
    }

    public EnvironmentAtom(String env, int optionSize) {
        this.env = env;
        this.optionSize = optionSize;
        this.optionList = new ArrayList<>();
        this.rowList = new ArrayList<>();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public int getOptionSize() {
        return optionSize;
    }

    public List<Atom> getOptionList() {
        return optionList;
    }

    public List<RowAtom> getRowList() {
        return rowList;
    }

    public void addRow(RowAtom rowAtom) {
        this.rowList.add(rowAtom);

    }
}
