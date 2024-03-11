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
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);

        final String[] expected = {
            "8: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "10: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "22: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
        };

        verify(config, getPath("InputLeadingAsteriskAlignJavadocRight.java"), expected);
    }

    @Test
    public void testJavadocLeft() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);
        config.addProperty("option", "LEFT");

        final String[] expected = {
            "9: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "37: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
        };

        verify(config, getPath("InputLeadingAsteriskAlignJavadocLeft.java"), expected);
    }

    @Test
    public void testCommentRight() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);
        config.addProperty("option", "RIGHT");

        final String[] expected = {
            "8: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "10: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "22: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
        };

        verify(config, getPath("InputLeadingAsteriskAlignCommentRight.java"), expected);
    }

    @Test
    public void testCommentLeft() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);
        config.addProperty("option", "LEFT");

        final String[] expected = {
            "9: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "37: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
        };

        verify(config, getPath("InputLeadingAsteriskAlignCommentLeft.java"), expected);
    }
}
