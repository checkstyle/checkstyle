package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.LeadingAsteriskAlignCheck.MSG_WRONG_ALIGNMENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/leadingasteriskalign";
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
        config.addAttribute("option", "LEFT");

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
        config.addAttribute("option", "RIGHT");

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
        config.addAttribute("option", "LEFT");

        final String[] expected = {
            "9: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "37: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
        };

        verify(config, getPath("InputLeadingAsteriskAlignCommentLeft.java"), expected);
    }

    @Test
    public void testTabRight() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);
        config.addAttribute("option", "RIGHT");
        config.addAttribute("tabSize", "8");

        final String[] expected = {
            "8: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "10: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 9),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 9),
            "22: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 10),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 10),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 9),
        };

        verify(config, getPath("InputLeadingAsteriskAlignTabRight.java"), expected);
    }

    @Test
    public void testTabLeft() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);
        config.addAttribute("option", "LEFT");
        config.addAttribute("tabSize", "8");

        final String[] expected = {
            "9: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "17: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 8),
            "18: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 8),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 8),
            "37: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
        };

        verify(config, getPath("InputLeadingAsteriskAlignTabLeft.java"), expected);
    }

    @Test
    public void testTabAndSpaces() throws Exception {
        DefaultConfiguration config = createModuleConfig(LeadingAsteriskAlignCheck.class);

        verify(config, getPath("InputLeadingAsteriskAlignTabsAndSpaces.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

}
