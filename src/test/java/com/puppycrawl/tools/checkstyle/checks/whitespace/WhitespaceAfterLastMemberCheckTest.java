package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterLastMemberCheck.MSG_WS_AFTER_LAST_MEMBER;
import static org.junit.Assert.*;

public class WhitespaceAfterLastMemberCheckTest
        extends BaseCheckTestSupport {

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAfterLastMemberCheck checkObj = new WhitespaceAfterLastMemberCheck();
        final int[] requiredTokens = {
            TokenTypes.RCURLY,
        };
        assertArrayEquals(requiredTokens, checkObj.getRequiredTokens());
    }

    @Test
    public void testAllowNoEmptyLineInEmptyClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(WhitespaceAfterLastMemberCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputWhitespaceAfterLastMemberEmptyClass.java"),
                expected);
    }

    @Test
    public void testNotAllowNoEmptyLineAfterFieldInClassWithOneField() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(WhitespaceAfterLastMemberCheck.class);
        final String[] expected = {
                "3: " + getCheckMessage(MSG_WS_AFTER_LAST_MEMBER),
        };
        verify(checkConfig,
                getPath("InputWhitespaceAfterLastMemberOneFieldClass.java"),
                expected);
    }

    @Test
    public void testAllowEmptyLineAfterLastMethod() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(WhitespaceAfterLastMemberCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputWhitespaceAfterLastMemberWSAfterLastMethod.java"),
                expected);
    }
}