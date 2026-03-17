package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrderCheck.MSG_JAVADOC_PARAM_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderCorrect.java"), expected);
    }

    @Test
    public void testCorrectDuplicate() throws Exception {
        final String[] expected = {
            "55: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "75: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamCorrectDuplicate.java"), expected);
    }

    @Test
    public void testViolation() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "24: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "25: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "35: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "36: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "37: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "47: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "57: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "68: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "69: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "78: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "88: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "93: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderViolation.java"), expected);
    }

    @Test
    public void testTypeParams() throws Exception {
        final String[] expected = {
            "46: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "57: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "58: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "68: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "69: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "79: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "80: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "81: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "92: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "108: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderTypeParams.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = {
            "31: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "45: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "46: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "56: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "75: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "96: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderEdgeCases.java"), expected);
    }

    @Test
    public void testComplex() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "21: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "33: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "35: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "37: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "38: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "55: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "56: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "68: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "80: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "81: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "88: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "96: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "104: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "111: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderComplex.java"), expected);
    }
}
