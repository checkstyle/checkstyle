///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertWithMessage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public final class MetadataGeneratorUtilTest extends AbstractModuleTestSupport {

    private static final Set<String> MODULES_CONTAINING_NO_METADATA_FILE = Set.of(
            "Checker",
            "TreeWalker"
    );

    @Override
    public String getPackageLocation() {
        return null;
    }

    /**
     * Verifies number of generated metadata modules match the number of Checkstyle modules.
     * Also verifies whether every Checkstyle module contains description.
     *
     * <p>When running this test directly from an IDE, run
     * {@code ./mvnw process-classes} first to generate the metadata files
     * under {@code target/classes}.
     *
     * @throws Exception if an I/O error occurs while reading metadata files
     */
    @Test
    public void testMetadataFilesGenerationAllFiles() throws Exception {
        final Path sourceMetadataDirectory = Path.of(System.getProperty("user.dir"),
                "src", "main", "resources", "com", "puppycrawl", "tools", "checkstyle", "meta");
        final List<Path> metadataFiles;
        try (Stream<Path> fileStream = Files.walk(sourceMetadataDirectory)) {
            metadataFiles = fileStream
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toString().endsWith(".properties"))
                    .sorted()
                    .toList();
        }
        final Set<String> metaFiles = metadataFiles.stream()
                .map(MetadataGeneratorUtilTest::getMetaFileName)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        final Path outputMetadataDirectory = Path.of(System.getProperty("user.dir"),
                "target", "classes", "com", "puppycrawl", "tools", "checkstyle", "meta");
        for (Path metadataFile : metadataFiles) {
            final Path relativePath = sourceMetadataDirectory.relativize(metadataFile);
            final Path outputMetadataFile = outputMetadataDirectory.resolve(relativePath);
            assertWithMessage("Metadata differs in target/classes for %s", relativePath)
                    .that(Files.readString(outputMetadataFile))
                    .isEqualTo(Files.readString(metadataFile));
        }
        final Set<String> checkstyleModules =
                CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules())
                        .stream()
                        .sorted()
                        .collect(Collectors.toCollection(LinkedHashSet::new));
        checkstyleModules.removeAll(MODULES_CONTAINING_NO_METADATA_FILE);
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
