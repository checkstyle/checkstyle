package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.checks.PackageHtmlCheck;

public class PackageHtmlCheckTest
    extends BaseCheckTestCase
{
    public void testPackageHtml()
         throws Exception
    {
        final FileSetCheck fsc = new PackageHtmlCheck();
        final String packageHtmlPath = getPath("package.html");
        final String filepath = getPath("InputScopeAnonInner.java");
        final String[] expected = {
            packageHtmlPath + ":0: Missing package documentation file.",
        };
        // TODO: verify(fsc, filepath, expected);
    }


}
