package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrderCheck.MSG_JAVADOC_PARAM_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderCorrect.java"), expected);
    }

    @Test
    public void testCorrectDuplicate() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamCorrectDuplicate.java"), expected);
    }

    @Test
    public void testViolation() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "14: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "15: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "22: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "24: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "32: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "33: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "42: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "44: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "51: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "52: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "63: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "64: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "72: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "73: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderViolation.java"), expected);
    }

//    @Test
//    public void testTypeParams() throws Exception {
//        final String[] expected = {
//            "19: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "27: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "28: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "44: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "45: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "54: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "55: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "56: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "82: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "83: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "84: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "85: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//            "101: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
//        };
//        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderTypeParams.java"), expected);
//    }
//
    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderEdgeCases.java"), expected);
    }

    @Test
    public void testComplex() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "15: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "24: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "26: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "30: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "38: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "40: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "49: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "50: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "51: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "52: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "59: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "60: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "61: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "62: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "63: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "86: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "87: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "94: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "96: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "103: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "104: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "109: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "111: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "112: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderComplex.java"), expected);
    }
}
