package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.Test;

/**
 * Exact tests for {@link ArrayBracketNoWhitespaceCheck} using the two provided inputs.
 * The expected arrays were generated from the `// violation` comments in the input files.
 */
public class ArrayBracketNoWhitespaceCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketnowhitespace";
    }

    /**
     * Test against InputArrayBracketNoWhitespaceDefault.java
     */
    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "32:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "32:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "35:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "35:18: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "36:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "36:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "42:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "43:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:34: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:33: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:34: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "50:12: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "51:12: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "53:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "53:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "53:31: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "58:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "58:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "58:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "66:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "72:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "72:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "72:33: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "78:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "78:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "82:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "82:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "86:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "86:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "90:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "90:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "94:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "94:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "98:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "98:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "102:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "102:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "106:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "106:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "110:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "110:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "114:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "114:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "118:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "118:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "122:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "122:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "126:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "126:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "130:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "130:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "134:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "134:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "138:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "138:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "142:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "142:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "146:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "146:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "150:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "150:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };

        verifyWithInlineConfigParser(getPath("InputArrayBracketNoWhitespaceDefault.java"), expected);
    }

    /**
     * Test against InputArrayBracketNoWhitespaceDeclarations.java
     */
    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "24:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "25:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "26:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "28:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "30:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "31:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "37:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "38:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "40:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "41:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "42:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "44:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "49:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "50:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "51:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "53:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "56:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "57:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "58:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "61:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "62:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "64:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "66:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "68:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "71:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "73:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "74:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "76:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "78:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "80:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "82:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "84:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "86:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "88:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "90:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "92:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "94:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "96:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "98:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "100:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "102:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "104:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "106:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        
        verifyWithInlineConfigParser(getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }
}
