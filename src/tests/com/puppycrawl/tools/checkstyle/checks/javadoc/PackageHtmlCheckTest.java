package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;


public class PackageHtmlCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testPackageHtml()
         throws Exception
    {
        Configuration checkConfig = createCheckConfig(PackageHtmlCheck.class);
        final String[] expected = {
            "0: Missing package documentation file.",
        };
        verify(
            createChecker(checkConfig),
            getPath("InputScopeAnonInner.java"),
            getPath("package.html"),
            expected);
    }
}
