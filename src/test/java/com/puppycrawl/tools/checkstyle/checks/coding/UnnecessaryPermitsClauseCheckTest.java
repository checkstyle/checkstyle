package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck.MSG_SEMI;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.Test;

public class UnnecessaryPermitsClauseCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/"
            + "unnecessarypermitsclause";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnnecessaryPermitsClauseCheck checkObj =
                new UnnecessaryPermitsClauseCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnnecessaryPermitsClauseCheck checkObj =
                new UnnecessaryPermitsClauseCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

     @Test
    public void testDefault() throws Exception {

        final String[] expected = {
        };

        verifyWithInlineConfigParser(
                getPath("Shape.java"),
            expected);
    }


}
