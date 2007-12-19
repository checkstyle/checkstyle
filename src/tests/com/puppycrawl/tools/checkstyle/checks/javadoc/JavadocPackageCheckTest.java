package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;


public class JavadocPackageCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testMissing()
         throws Exception
    {
        Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: Missing package-info.java file.",
        };
        verify(
            createChecker(checkConfig),
            getPath("javadoc/BadCls.java"),
            getPath("javadoc/package-info.java"),
            expected);
    }
    public void testBoth() throws Exception
    {
        Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {"0: Legacy package.html file should be removed.",};
        verify(createChecker(checkConfig),
            getPath("javadoc/bothfiles/Ignored.java"),
            getPath("javadoc/bothfiles/package-info.java"), expected);
    }

    public void testHtmlDisallowed() throws Exception
    {
        Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {"0: Missing package-info.java file.",};
        verify(createChecker(checkConfig),
            getPath("javadoc/pkghtml/Ignored.java"),
            getPath("javadoc/pkghtml/package-info.java"), expected);
    }

    public void testHtmlAllowed() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = {};
        verify(createChecker(checkConfig),
            getPath("javadoc/pkghtml/Ignored.java"),
            getPath("javadoc/pkghtml/package-info.java"), expected);
    }
}
