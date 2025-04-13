///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper.MSG_DESC_MISSING;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

@ExtendWith(SystemOutGuard.class)
public final class MetadataGeneratorUtilTest extends AbstractModuleTestSupport {

    private final Set<String> modulesContainingNoMetadataFile = Set.of(
            "Checker",
            "TreeWalker",
            "JavadocMetadataScraper",
            "ClassAndPropertiesSettersJavadocScraper"
    );

    @Override
    protected String getPackageLocation() {
        return null;
    }

    /**
     * Generates metadata for checkstyle modules and verifies number of
     * generated metadata modules match the number of checkstyle modules.
     * Also verifies whether every checkstyle module contains description.
     *
     * @param systemOut wrapper for {@code System.out}
     * @throws Exception if exception occurs during generating metadata or
     *                   if an I/O error is thrown when accessing the starting file.
     * @noinspection UseOfSystemOutOrSystemErr
     * @noinspectionreason UseOfSystemOutOrSystemErr - generation of metadata
     *      requires {@code System.out} for error messages
     */
    @Test
    public void testMetadataFilesGenerationAllFiles(@SystemOutGuard.SysOut Capturable systemOut)
            throws Exception {
        systemOut.captureMuted();

        MetadataGeneratorUtil.generate(System.getProperty("user.dir")
                        + "/src/main/java/com/puppycrawl/tools/checkstyle",
                System.out, "checks", "filters", "filefilters");

        final String[] expectedErrorMessages = {
            "31: " + getCheckMessage(MSG_DESC_MISSING, "AbstractSuperCheck"),
            "43: " + getCheckMessage(MSG_DESC_MISSING, "AbstractHeaderCheck"),
            "42: " + getCheckMessage(MSG_DESC_MISSING, "AbstractJavadocCheck"),
            "45: " + getCheckMessage(MSG_DESC_MISSING, "AbstractClassCouplingCheck"),
            "26: " + getCheckMessage(MSG_DESC_MISSING, "AbstractAccessControlNameCheck"),
            "30: " + getCheckMessage(MSG_DESC_MISSING, "AbstractNameCheck"),
            "30: " + getCheckMessage(MSG_DESC_MISSING, "AbstractParenPadCheck"),
        };

        final String[] actualViolations = systemOut.getCapturedData().split("\\n");
        final Pattern violationExtractingPattern = Pattern.compile("((?<=:)\\d.*:.*(?=\\s\\[))");

        Arrays.setAll(actualViolations, id -> {
            final Matcher matcher = violationExtractingPattern.matcher(actualViolations[id]);
            matcher.find();
            return matcher.group(1);
        });

        assertWithMessage("Expected and actual errors do not match")
                .that(expectedErrorMessages)
                .asList()
                .containsExactlyElementsIn(actualViolations);

        final Set<String> metaFiles;
        try (Stream<Path> fileStream = Files.walk(
                Path.of(System.getProperty("user.dir") + "/src/main/resources/com/puppycrawl"
                        + "/tools/checkstyle/meta"))) {
            metaFiles = fileStream
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toString().endsWith(".properties"))
                    .map(MetadataGeneratorUtilTest::getMetaFileName)
                    .sorted()
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        final Set<String> checkstyleModules =
                CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules())
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        checkstyleModules.removeAll(modulesContainingNoMetadataFile);
        assertWithMessage("Number of generated metadata files dont match with "
                + "number of checkstyle module")
                .that(metaFiles)
                .isEqualTo(checkstyleModules);
    }

    /**
     * Get meta file name from full file name.
     *
     * @param file file to process
     * @return meta file name
     */
    private static String getMetaFileName(Path file) {
        final String fileName = file.getFileName().toString();
        final int lengthToOmit;
        if (fileName.contains("Check")) {
            lengthToOmit = "Check.xml".length();
        }
        else {
            lengthToOmit = ".xml".length();
        }
        return fileName.substring(0, fileName.length() - lengthToOmit);
    }
}
