package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.jupiter.api.Test;

public class TextBlockGoogleStyleFormattingCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/textblockgooglestyleformatting";
    }

    @Test
    public void testTextBlockFormat() throws Exception {
        final String[] expected= {
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "23:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "37:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "43:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "48:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testTextBlockIndentation() throws Exception {
        final String[] expected= CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingIndentation.java"), expected);
    }
}
