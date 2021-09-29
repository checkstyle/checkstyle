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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public final class MetadataGeneratorUtilTest {

    private final String invalidMetadataPackage = System.getProperty("user.dir")
        + "/src/test/resources/com/puppycrawl/tools/checkstyle"
        + "/meta/javadocmetadatascraper/invalid_metadata";

    private final List<String> modulesContainingNoMetadataFile = Arrays.asList(
            "Checker",
            "TreeWalker",
            "JavadocMetadataScraper"
    );

    @Test
    public void testMetadataFilesGenerationAllFiles() throws Exception {
        final ImmutablePair<Checker, List<File>> immutablePair = MetadataGeneratorUtil.generate(
                System.getProperty("user.dir")
                + "/src/main/java/com/puppycrawl/tools/checkstyle");
        immutablePair.getLeft().process(immutablePair.getRight());
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
    public void testMetadataFileGenerationDefaultValueMisplaced() throws CheckstyleException,
            IOException {

        final ImmutablePair<Checker, List<File>> immutablePair = MetadataGeneratorUtil.generate(
                invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMisplacedDefaultValueCheck.java");

        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            immutablePair.getLeft().process(immutablePair.getRight());
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Default value for property 'misplacedDefaultValue' is missing");
    }

    @Test
    public void testMetadataFileGenerationTypeMisplaced() throws CheckstyleException,
            IOException {

        final ImmutablePair<Checker, List<File>> immutablePair = MetadataGeneratorUtil.generate(
                invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMisplacedTypeCheck.java");

        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            immutablePair.getLeft().process(immutablePair.getRight());
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Type for property 'misplacedType' is missing");
    }

    @Test
    public void testMetadataFileGenerationTypeMissing() throws CheckstyleException,
            IOException {

        final ImmutablePair<Checker, List<File>> immutablePair = MetadataGeneratorUtil.generate(
                invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMissingTypeCheck.java");

        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            immutablePair.getLeft().process(immutablePair.getRight());
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage()).isEqualTo(
            "Type for property 'missingType' is missing");
    }

    @Test
    public void testMetadataFileGenerationDefaultValueMissing() throws CheckstyleException,
            IOException {
        final ImmutablePair<Checker, List<File>> immutablePair = MetadataGeneratorUtil.generate(
                invalidMetadataPackage
                + "/InputJavadocMetadataScraperPropertyMissingDefaultValueCheck.java");

        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            immutablePair.getLeft().process(immutablePair.getRight());
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
}
