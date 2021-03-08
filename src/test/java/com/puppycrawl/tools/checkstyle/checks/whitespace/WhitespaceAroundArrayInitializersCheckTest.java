package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundArrayInitializers.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundArrayInitializers.MSG_WS_NOT_PRECEDED;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAroundArrayInitializersCheckTest extends
        AbstractModuleTestSupport{

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearoundarrayinit";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAroundArrayInitializers checkObj = new WhitespaceAroundArrayInitializers();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "WhitespaceAroundArrayInitializers#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final WhitespaceAroundArrayInitializers whitespaceAroundArrayInitializersObj = new WhitespaceAroundArrayInitializers();
        final int[] actual = whitespaceAroundArrayInitializersObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RCURLY,
            TokenTypes.ARRAY_INIT,
            TokenTypes.ANNOTATION_ARRAY_INIT,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testArrayInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundArrayInitializers.class);
        final String[] expected = {
            "5:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "5:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "5:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
        };
        verify(checkConfig, getPath("custom.java"), expected);
    }
}
