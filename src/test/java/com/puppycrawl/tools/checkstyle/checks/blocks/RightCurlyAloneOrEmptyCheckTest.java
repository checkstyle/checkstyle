package com.puppycrawl.tools.checkstyle.checks.blocks;

import org.junit.jupiter.api.Test;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class RightCurlyAloneOrEmptyCheckTest extends AbstractModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testAnnotationsAndEnums() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyNoViolations.java"), expected);
    }
}
