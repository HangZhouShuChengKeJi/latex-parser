package com.orange.latex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.orange.latex.atom.Atom;
import com.orange.latex.atom.AtomArray;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小天
 * @date 2020/12/9 17:45
 */
public class TexParserTest {

    @Before
    public void setUp() throws Exception {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parse() {
        TexParser texParser;
        Atom atom;
        AtomArray rootAtomArray;

        List<String> texList = new ArrayList<>();

        texList.add("A_1");
        texList.add("A_{11}");
        texList.add("A_3 + B_{33}");
        texList.add("A^2");
        texList.add("A^{22}");
        texList.add("A^4 + B^{44}");
        texList.add("A_2 + B_{22} + C^3 + D^{33}");

        texList.add("\\frac{9}{4}");
        texList.add("\\frac{\\sqrt{1}}{2a}");
        texList.add("\\frac{1}{\\sqrt{36}}");

        texList.add("\\sqrt{x}");
        texList.add("\\sqrt[3]{x}");
        texList.add("\\sqrt[2a]{x}");
        texList.add("\\sqrt{2_{3a}}");
        texList.add("\\sqrt{{(-6)}^{2}}");
        texList.add("\\sqrt{\\frac{9}{4}}");
        texList.add("\\sqrt{1\\frac{9}{16}}");
        texList.add("\\sqrt{{(-\\frac{1}{5})}^{2}}");

        texList.add("x = 3a + \\frac{-b \\pm \\sqrt{b^2-4ac}}{2a}");

        texList.add("\\left\\{ \\begin{array}{l}{x=\\frac{1}{2}} \\\\ {y=\\sqrt{2^{9 + a}}} \\end{array} \\right.");

        for (int i = 0; i < texList.size(); i++) {
            String plainText = texList.get(i);
            texParser = new TexParser(plainText);
            atom = texParser.parse();
            System.out.printf("[%d] %s : %s \n", i, plainText, JSON.toJSONString(atom));
        }

        texParser = new TexParser("A_1");
        atom = texParser.parse();
        Assert.assertTrue(atom instanceof AtomArray);
        rootAtomArray = (AtomArray)atom;
        Assert.assertEquals(2, rootAtomArray.getAtomList().size());
    }

    @Test
    public void testEnv() {

        TexParser texParser;
        Atom atom;
        AtomArray rootAtomArray;

        List<String> texList = new ArrayList<>();

        texList.add("\\left\\{ " +
                "\\begin{array}{l}" +
                "{x=\\frac{1}{2}} \\\\ " +
                "{y=\\sqrt{2^{9 + a}}} " +
                "\\end{array} " +
                "\\right.");

        for (int i = 0; i < texList.size(); i++) {
            String plainText = texList.get(i);
            texParser = new TexParser(plainText);
            atom = texParser.parse();
            System.out.printf("[%d] %s : %s \n", i, plainText, JSON.toJSONString(atom));
        }
    }
}