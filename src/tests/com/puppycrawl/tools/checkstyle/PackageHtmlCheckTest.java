package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.checks.PackageHtmlCheck;

public class PackageHtmlCheckTest
    extends BaseCheckTestCase
{
    protected Checker createChecker(CheckConfiguration aCheckConfig)
        throws Exception
    {
        final Checker c = new Checker(new GlobalProperties(mProps, mStream),
                                      new CheckConfiguration[0]);
        final AuditListener listener = new BriefLogger(mStream);
        c.addListener(listener);
        return c;
    }

    public void testPackageHtml()
         throws Exception
    {
        CheckConfiguration checkConfig = new CheckConfiguration();
        // no Checks in config, but register new PackageHtml as a FileSetCheck
        Checker c = createChecker(checkConfig);
        final FileSetCheck fsc = new PackageHtmlCheck();
        c.addFileSetCheck(fsc);

        final String packageHtmlPath = getPath("package.html");
        final String filepath = getPath("InputScopeAnonInner.java");

        final String[] expected = {
            "0: Missing package documentation file.",
        };
        verify(c, filepath, packageHtmlPath, expected);
    }


}
