package com.puppycrawl.tools.checkstyle.checks.blocks;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class RightCurlyAloneOrEmptyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testAnnotationsAndEnums() throws Exception {
        Configuration checkConfig = createModuleConfig(RightCurlyAloneOrEmptyCheck.class);

        final String[] expected = {
            "9:59: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 59),
            "12:25: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 25),
            "22:17: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 17),
        };

        verify(checkConfig, getPath("InputRightCurlyAloneOrEmptyNoViolations.java"), expected);
    }
}
