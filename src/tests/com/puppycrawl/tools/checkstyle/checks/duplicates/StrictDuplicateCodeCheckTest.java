package com.puppycrawl.tools.checkstyle.checks.duplicates;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class StrictDuplicateCodeCheckTest extends BaseCheckTestCase {

    protected DefaultConfiguration createCheckerConfig(
            Configuration aCheckConfig) 
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testDefaultSettings() throws Exception 
    {
        final Configuration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        final String innerDupPath = getPath("duplicates/InnerDup.java");
        final String[] expected = { 
                "6: Found duplicate of 13 lines in " + innerDupPath + ", starting from line 22",
                };
        final File[] checkedFiles = new File[] { 
                new File(innerDupPath),
                new File(getPath("duplicates/Shorty.java")),
                };
        verify(createChecker(checkConfig), checkedFiles, innerDupPath, expected);
    }

    public void testSmallMin() throws Exception 
    {
        final DefaultConfiguration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        checkConfig.addAttribute("min", "3");
        final String aPath = getPath("duplicates/A.java");
        final String bPath = getPath("duplicates/B.java");
        final String[] expected = { 
                // imports should not be marked because developer cannot avoid them
                // same constant def should not be marked because order is important for this check
                };
        final File[] checkedFiles = new File[] { 
                new File(aPath),
                new File(bPath),
                };
        verify(createChecker(checkConfig), checkedFiles, aPath, expected);
    }

}
