package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISSING;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


class MultiFileRegexpHeaderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/regexpheader";
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderFilesNull() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class, () -> instance.setHeaderFiles(null));
    }


    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", "");
        try {
            createChecker(checkConfig);
            assertWithMessage("Checker creation should not succeed with invalid headerFiles").fail();
        } catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message").that(ex.getMessage()).isEqualTo(
                    "cannot initialize module" + " com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck" + " - Cannot set property 'headerFiles' to ''");
        }
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002")};
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
        verifyWithInlineConfigParser(getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeader.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),};
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeaderNewLines.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),};
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader1.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti52() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader3.header"));
        final String[] expected = {"1: " + getCheckMessage(MSG_HEADER_MISSING),};
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testHeaderWithInvalidRegexp() throws Exception {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class,
                () -> instance.setHeaderFiles(getPath("InputRegexpHeader.invalid.header")));
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader4.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testOneHeaderValid() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        String validHeader = getPath("InputRegexpHeader4.header");
        String invalidHeader = getPath("InputRegexpHeader.header");
        String multiHeaderFiles = validHeader + "," + invalidHeader;
        checkConfig.addProperty("headerFiles", multiHeaderFiles);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig), getPath("InputRegexpHeaderMulti5.java"), expected);
    }
}

