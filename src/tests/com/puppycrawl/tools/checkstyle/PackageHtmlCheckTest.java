package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.PackageHtmlCheck;


public class PackageHtmlCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testPackageHtml()
         throws Exception
    {
        Configuration checkConfig = createCheckConfig(PackageHtmlCheck.class);
        Checker c = createChecker(checkConfig);
        final String packageHtmlPath = getPath("package.html");
        final String filepath = getPath("InputScopeAnonInner.java");

        final String[] expected = {
            "0: Missing package documentation file.",
        };
        verify(c, filepath, packageHtmlPath, expected);
    }
}
