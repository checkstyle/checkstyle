package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class PackageDeclarationCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
            "4: Missing package declaration.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputNoPackage.java"), expected);
    }

    @Test
    public void testDefault1() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);
        final String dname = "com/puppycrawl/tools/checkstyle/checks/coding"
            .replace('/', File.separatorChar);

        String[] expected = {
            "1:9: Package declaration does not match directory '" + dname + "'."
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalCatchCheck.java"), expected);
    }

    @Test
    public void testQuiet() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("ignoreDirectoryName", "true");

        String[] expected = {
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalCatchCheck.java"), expected);
    }
}
