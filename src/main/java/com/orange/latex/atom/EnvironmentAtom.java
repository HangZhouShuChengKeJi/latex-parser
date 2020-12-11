package com.orange.latex.atom;

import java.util.LinkedList;
import java.util.List;

/**
 * env atom
 *
 * @author 小天
 * @date 2020/12/9 15:10
 */
public class EnvironmentAtom extends Atom {
    private String     env;
    private int        optionSize = 1;
    private List<Atom> optionList;
    private List<Atom> contentList;

    public EnvironmentAtom() {
        this.optionList = new LinkedList<>();
        this.contentList = new LinkedList<>();
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

    public void setOptionSize(int optionSize) {
        this.optionSize = optionSize;
    }

    public List<Atom> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Atom> optionList) {
        this.optionList = optionList;
    }

    public List<Atom> getContentList() {
        return contentList;
    }

    public void setContentList(List<Atom> contentList) {
        this.contentList = contentList;
    }

    public boolean isOptionCompleted() {
        return this.optionList.size() == optionSize;
    }

}
