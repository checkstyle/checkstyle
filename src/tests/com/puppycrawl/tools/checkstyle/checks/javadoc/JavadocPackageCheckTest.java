package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;


public class JavadocPackageCheckTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
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
    @Test
    public void testBoth() throws Exception
    {
        Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {"0: Legacy package.html file should be removed.",};
        verify(createChecker(checkConfig),
            getPath("javadoc/bothfiles/Ignored.java"),
            getPath("javadoc/bothfiles/package-info.java"), expected);
    }

    @Test
    public void testHtmlDisallowed() throws Exception
    {
        Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {"0: Missing package-info.java file.",};
        verify(createChecker(checkConfig),
            getPath("javadoc/pkghtml/Ignored.java"),
            getPath("javadoc/pkghtml/package-info.java"), expected);
    }

    @Test
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
