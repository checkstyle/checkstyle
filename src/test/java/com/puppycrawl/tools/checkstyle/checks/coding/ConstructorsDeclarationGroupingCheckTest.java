package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Test;

import static com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck.MSG_KEY;

public class ConstructorsDeclarationGroupingCheckTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/constructorsdeclarationgrouping";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
                "23:5: " + getCheckMessage(MSG_KEY),
                "27:5: " + getCheckMessage(MSG_KEY),
                "40:9: " + getCheckMessage(MSG_KEY),
                "51:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGrouping.java"), expected);
    }
}
