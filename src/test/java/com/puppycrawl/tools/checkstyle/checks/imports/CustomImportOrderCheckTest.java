////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class CustomImportOrderCheckTest extends BaseCheckTestSupport
{
    /**
     * @throws Exception
     */
    @Test
    public void testCustom() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("standardPackageRegExp", "java|javax");
        checkConfig.addAttribute("thirdPartyPackageRegExp", "com|org");
        checkConfig
                .addAttribute("customImportOrderRules",
                        "STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Wrong lexicographical order for 'java.awt.Button.ABORT' import.",
            "7: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "8: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "9: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "10: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "11: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "12: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "13: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "14: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "15: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "16: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    /**
     * Checks different group orderings and imports which are out of those ones
     * specified in the configuration.
     * @throws Exception
     */
    @Test
    public void testDefaultPackage() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "com.|org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Wrong lexicographical order for 'java.awt.Button.ABORT' import.",
            "9: Wrong lexicographical order for 'java.awt.Dialog' import.",
            "13: Wrong lexicographical order for 'java.io.File' import.",
            "15: Wrong lexicographical order for 'java.io.InputStream' import.",
            "20: Wrong lexicographical order for 'com.google.common.*' import.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    /**
     * Checks different combinations for same_package group.
     * @throws Exception
     */
    @Test
    public void testNonSpecifiedImports() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig
                .addAttribute("customImportOrderRules",
                        "STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Wrong lexicographical order for 'java.awt.Button.ABORT' import.",
            "9: Wrong lexicographical order for 'java.awt.Dialog' import.",
            "13: Wrong lexicographical order for 'java.io.File' import.",
            "15: Wrong lexicographical order for 'java.io.InputStream' import.",
            "18: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "20: Imports without groups should be placed at the end of the import list.",
            "21: 'org.apache.*'should be separated from previous import group.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    @Test
    public void testOrderRuleWithOneGroup() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "7: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "8: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "9: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "10: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
            "11: Import statement is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder2.java"), expected);
    }

    @Test
    public void testSamePackageAndStatic() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "5: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "6: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "7: Import statement is in the wrong order. Should be in the 'STATIC' group.",
            "8: Import statement is in the wrong order. Should be in the 'STATIC' group.",
            "10: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "11: Import statement is in the wrong order. Should be in the 'STATIC' group.",
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackage.java").getCanonicalPath(), expected);
    }

    @Test
    public void testOnlySamePackage() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "6: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "7: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "8: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "9: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackage2.java").getCanonicalPath(), expected);
    }

    @Test
    public void testWithoutLineSeparator() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "5: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "6: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "7: Import statement is in the wrong order. Should be in the 'STATIC' group.",
            "8: Import statement is in the wrong order. Should be in the 'STATIC' group.",
            "10: Import statement is in the wrong order. Should be in the 'SAME_PACKAGE' group.",
            "11: Import statement is in the wrong order. Should be in the 'STATIC' group.",
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackage.java").getCanonicalPath(), expected);
    }

    @Test
    public void testNoValid() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", ".*");
        checkConfig.addAttribute("specialImportsRegExp", "com.google");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrderNoValid.java"), expected);
    }

    @Test
    public void testPossibleIndexOutOfBoundsException() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", ".*");
        checkConfig.addAttribute("specialImportsRegExp", "com.google");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        final String[] expected = {
            "5: Import statement is in the wrong order. Should be in the 'THIRD_PARTY_PACKAGE' group.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "DOMSource.java"), expected);
    }
}
