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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.transform.TransformerException;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;

@ExtendWith(SystemOutGuard.class)
public final class MetadataGeneratorUtilTest extends AbstractModuleTestSupport {

    private static final Set<String> MODULES_CONTAINING_NO_METADATA_FILE = Set.of(
            "Checker",
            "TreeWalker",
            "ClassAndPropertiesSettersJavadocScraper"
    );

    @Override
    public String getPackageLocation() {
        return null;
    }

    /**
     * Generates metadata for checkstyle modules and verifies number of
     * generated metadata modules match the number of checkstyle modules.
     * Also verifies whether every checkstyle module contains description.
     *
     * @param systemOut wrapper for {@code System.out}
     * @throws Exception if exception occurs during generating metadata or
     *                   if an I/O error is thrown when accessing the starting f
     */
    @Test
    public void testMetadataFilesGenerationAllFiles(@SystemOutGuard.SysOut Capturable systemOut)
            throws Exception {
        systemOut.captureMuted();

        MetadataGeneratorUtil.generate(System.getProperty("user.dir")
                        + "/src/main/java/com/puppycrawl/tools/checkstyle",
                "checks", "filters", "filefilters");

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
        checkstyleModules.removeAll(MODULES_CONTAINING_NO_METADATA_FILE);
        assertWithMessage("Number of generated metadata files dont match with "
                + "number of checkstyle module")
                .that(metaFiles)
                .isEqualTo(checkstyleModules);
    }

    /**
     * Verifies that generate() catches MacroExecutionException
     * it wrapped in a CheckstyleException.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testGenerateRethrowsMacroExecutionExceptionAsCheckstyleException()
            throws Exception {
        try (MockedStatic<SiteUtil> mocked = mockStatic(SiteUtil.class,
                Mockito.CALLS_REAL_METHODS)) {
            mocked.when(() -> SiteUtil.getModuleInstance(anyString()))
                    .thenThrow(new MacroExecutionException("simulated"));

            try {
                MetadataGeneratorUtil.generate(
                        System.getProperty("user.dir")
                                + "/src/main/java/com/puppycrawl/tools/checkstyle",
                        "checks");
                assertWithMessage("CheckstyleException should have been thrown").fail();
            }
            catch (CheckstyleException exception) {
                assertWithMessage("Cause must be MacroExecutionException")
                        .that(exception.getCause())
                        .isInstanceOf(MacroExecutionException.class);
                assertWithMessage("Message should mention macro failure")
                        .that(exception.getMessage())
                        .contains("Failed to execute macro");
            }
        }
    }

    /**
     * Verifies that writeMetadataFile() catches TransformerException
     * it wrapped in a CheckstyleException.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testWriteMetadataFileRethrowsAsCheckstyleException() throws Exception {
        try (MockedStatic<XmlMetaWriter> mocked = mockStatic(XmlMetaWriter.class)) {
            mocked.when(() -> XmlMetaWriter.write(any(ModuleDetails.class)))
                    .thenThrow(new TransformerException("simulated"));

            try {
                MetadataGeneratorUtil.generate(
                        System.getProperty("user.dir")
                                + "/src/main/java/com/puppycrawl/tools/checkstyle",
                        "checks");
                assertWithMessage("CheckstyleException should have been thrown").fail();
            }
            catch (CheckstyleException exception) {
                assertWithMessage("Message should mention module name")
                        .that(exception.getMessage())
                        .contains("Failed to write metadata into XML file for module");
            }
        }
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
