////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public final class MetadataGeneratorUtilTest extends AbstractModuleTestSupport {

    private final String invalidMetadataPackage = System.getProperty("user.dir")
        + "/src/test/resources/com/puppycrawl/tools/checkstyle"
        + "/meta/javadocmetadatascraper/invalid_metadata";

    private final List<String> modulesContainingNoMetadataFile = Arrays.asList(
            "Checker",
            "TreeWalker",
            "JavadocMetadataScraper"
    );

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/meta/javadocmetadatascraper/invalid_metadata";
    }

    @Test
    public void testMetadataFilesGenerationAllFiles() throws Exception {
        MetadataGeneratorUtil.generate(System.getProperty("user.dir")
                + "/src/main/java/com/puppycrawl/tools/checkstyle");
        final Set<String> metaFiles;

        try (Stream<Path> fileStream = Files.walk(
                Paths.get(System.getProperty("user.dir") + "/src/main/resources/com/puppycrawl"
                        + "/tools/checkstyle/meta"))) {
            metaFiles = fileStream
                    .map(Path::toString)
                    .filter(fileName -> !fileName.endsWith(".properties"))
                    .map(Paths::get)
                    .filter(Files::isRegularFile)
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
        assertEquals("Number of generated metadata files dont match with number of checkstyle "
                        + "module", checkstyleModules, metaFiles);
    }

    @Test
    public void testMetadataFilesWithNoDescription() throws Exception {

        final String path = System.getProperty("user.dir")
                + "/src/main/java/com/puppycrawl/tools/checkstyle";

        final DefaultConfiguration scraperCheckConfig =
                createModuleConfig(JavadocMetadataScraper.class);
        final DefaultConfiguration treeWalkerConfig =
                createModuleConfig(TreeWalker.class);

        treeWalkerConfig.addChild(scraperCheckConfig);

        final Checker checker = createChecker(treeWalkerConfig);

        final List<File> validFiles = MetadataGeneratorUtil.extractValidFiles(path);

        final String[] expected = {
            "31: " + getCheckMessage(JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractSuper"),
            "41: " + getCheckMessage(JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractJavadoc"),
            "26: "
                    + getCheckMessage(
                            JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractAccessControlName"),
            "30: " + getCheckMessage(JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractName"),
            "43: " + getCheckMessage(JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractHeader"),
            "29: "
                    + getCheckMessage(JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractParenPad"),
            "45: " + getCheckMessage(
                    JavadocMetadataScraper.MSG_DESC_MISSING, "AbstractClassCoupling"),
        };
        final Map<String, List<String>> expectedViolation = new HashMap<>();
        addExpectedViolationsToMap(validFiles, expected, expectedViolation);

        final File[] filesToBeProcessed = new File[validFiles.size()];

        verify(checker, validFiles.toArray(filesToBeProcessed), expectedViolation);

    }

    @Test
    public void testMetadataFileGenerationDefaultValueMisplaced() {
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            MetadataGeneratorUtil.generate(invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMisplacedDefaultValueCheck.java");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Default value for property 'misplacedDefaultValue' is missing");
    }

    @Test
    public void testMetadataFileGenerationTypeMisplaced() {
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            MetadataGeneratorUtil.generate(invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMisplacedTypeCheck.java");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Type for property 'misplacedType' is missing");
    }

    @Test
    public void testMetadataFileGenerationTypeMissing() {
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            MetadataGeneratorUtil.generate(invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMissingTypeCheck.java");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Type for property 'missingType' is missing");
    }

    @Test
    public void testMetadataFileGenerationDefaultValueMissing() {
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            MetadataGeneratorUtil.generate(invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMissingDefaultValueCheck.java");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Default value for property 'missingDefaultValue' is missing");
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

    /**
     * Fill the expectedViolation map.
     *
     * @param validFiles files to be processed
     * @param expected expected violation
     * @param expectedViolation map of expected violations
     */
    private static void addExpectedViolationsToMap(List<File> validFiles, String[] expected,
                                                   Map<String, List<String>> expectedViolation) {
        final String[] empty = CommonUtil.EMPTY_STRING_ARRAY;

        String className;

        String classPath;
        for (File currFile : validFiles) {
            className = currFile.getName();
            classPath = currFile.getAbsolutePath();
            switch (className.split("Check.java")[0]) {
                case "AbstractSuper":
                    expectedViolation.put(classPath, Collections.singletonList(expected[0]));
                    break;
                case "AbstractJavadoc":
                    expectedViolation.put(classPath, Collections.singletonList(expected[1]));
                    break;
                case "AbstractAccessControlName":
                    expectedViolation.put(classPath, Collections.singletonList(expected[2]));
                    break;
                case "AbstractName":
                    expectedViolation.put(classPath, Collections.singletonList(expected[3]));
                    break;
                case "AbstractHeader":
                    expectedViolation.put(classPath, Collections.singletonList(expected[4]));
                    break;
                case "AbstractParenPad":
                    expectedViolation.put(classPath, Collections.singletonList(expected[5]));
                    break;
                case "AbstractClassCoupling":
                    expectedViolation.put(classPath, Collections.singletonList(expected[6]));
                    break;
                default:
                    expectedViolation.put(classPath, Arrays.asList(empty));
                    break;
            }
        }
    }
}
