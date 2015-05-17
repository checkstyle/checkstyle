////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AllChecksPresentOnAvailableChecksPageTest {
    private static final File JAVA_SOURCES_DIRECTORY = new File("src/main/java");
    private static final String AVAILABLE_CHECKS_PATH = "src/xdocs/checks.xml";
    private static final File AVAILABLE_CHECKS_FILE = new File(AVAILABLE_CHECKS_PATH);
    private static final String CHECK_FILE_NAME = ".+Check.java$";
    private static final String CHECK_SUFFIX = "Check.java";
    private static final String LINK_TEMPLATE =
            "(?s).*<a href=\"config_\\w+\\.html#%1$s\">%1$s</a>.*";
    private static final String NAMING_LINK_TEMPLATE =
            "(?s).*<a href=\"config_naming\\.html#Modules\">%s</a>.*";

    private static final List<String> IGNORE_LIST = Arrays.asList(
            "AbstractAccessControlNameCheck.java",
            "AbstractClassCouplingCheck.java",
            "AbstractComplexityCheck.java",
            "AbstractFileSetCheck.java",
            "AbstractFormatCheck.java",
            "AbstractHeaderCheck.java",
            "AbstractIllegalCheck.java",
            "AbstractIllegalMethodCheck.java",
            "AbstractJavadocCheck.java",
            "AbstractNameCheck.java",
            "AbstractNestedDepthCheck.java",
            "AbstractOptionCheck.java",
            "AbstractParenPadCheck.java",
            "AbstractSuperCheck.java",
            "AbstractTypeAwareCheck.java",
            "AbstractTypeParameterNameCheck.java",
            "FileSetCheck.java"
    );

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws IOException {
        final String availableChecks = Files.toString(AVAILABLE_CHECKS_FILE, UTF_8);
        for (File file : Files.fileTreeTraverser().preOrderTraversal(JAVA_SOURCES_DIRECTORY)) {
            final String fileName = file.getName();
            if (fileName.matches(CHECK_FILE_NAME) && !IGNORE_LIST.contains(fileName)) {
                final String checkName = fileName.replace(CHECK_SUFFIX, "");
                if (!isPresent(availableChecks, checkName)) {
                    Assert.fail(checkName + " is not correctly listed on Available Checks page"
                            + " - add it to " + AVAILABLE_CHECKS_PATH);
                }
            }
        }
    }

    private static boolean isPresent(String availableChecks, String checkName) {
        final String linkPattern = String.format(LINK_TEMPLATE, checkName);
        final String namingLinkPattern = String.format(NAMING_LINK_TEMPLATE, checkName);
        return availableChecks.matches(linkPattern)
                || checkName.endsWith("Name") && availableChecks.matches(namingLinkPattern);
    }
}
