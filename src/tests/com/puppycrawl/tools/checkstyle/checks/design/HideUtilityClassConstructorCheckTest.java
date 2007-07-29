package com.puppycrawl.tools.checkstyle.checks.design;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class HideUtilityClassConstructorCheckTest
    extends BaseCheckTestCase
{
    /** only static methods and no constructor - default ctor is visible */
    public void testUtilClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
            "11:1: Utility classes should not have a public or default constructor.",
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    /** nonstatic methods - always OK */
    public void testNonUtilClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputDesignForExtension.java"), expected);
    }

    public void testDerivedNonUtilClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("design" + File.separator + "InputNonUtilityClass.java"), expected);
    }

    public void testOnlyNonstaticFieldNonUtilClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("design" + File.separator + "InputRegression1762702.java"), expected);
    }

}
