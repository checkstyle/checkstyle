package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class PackageDeclarationCheckTest extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
            "4: Missing package declaration.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputNoPackage.java"), expected);
    }

    public void testDefault1() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalCatchCheck.java"), expected);
    }
}
