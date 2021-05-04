package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.LeadingAsteriskAlignCheck.MSG_WRONG_ALIGNMENT;

import com.puppycrawl.tools.checkstyle.checks.LeadingAsteriskAlignCheck;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class LeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/leadingasteriskalign";
    }

    @Test
    public void testJavadocRight() throws Exception {
        final String[] expected = {
            "7: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "13: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "46: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
        };

        verifyWithInlineConfigParser(getPath("InputLeadingAsteriskAlignJavadocRight.java"), expected);
    }

    @Test
    public void testJavadocLeft() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "24: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "30: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "39: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
        };

        verifyWithInlineConfigParser(getPath("InputLeadingAsteriskAlignJavadocLeft.java"), expected);
    }

    @Test
    public void testCommentRight() throws Exception {
        final String[] expected = {
            "7: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "13: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "30: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "54: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "60: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
        };

        verifyWithInlineConfigParser(getPath("InputLeadingAsteriskAlignCommentRight.java"), expected);
    }

    @Test
    public void testCommentLeft() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "24: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "46: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "55: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "61: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
        };

        verifyWithInlineConfigParser(getPath("InputLeadingAsteriskAlignCommentLeft.java"), expected);
    }
}
