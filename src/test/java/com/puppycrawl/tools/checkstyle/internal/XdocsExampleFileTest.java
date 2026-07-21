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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public class XdocsExampleFileTest {

    private static final Set<String> COMMON_PROPERTIES = Set.of(
        "severity",
        "id",
        "fileExtensions",
        "tabWidth",
        "fileContents",
        "tokens",
        "javadocTokens",
        "violateExecutionOnNonTightHtml"
    );

    @Test
    public void testAllCheckPropertiesAreUsedInXdocsExamples() throws Exception {
        final Map<String, Set<String>> usedPropertiesByCheck =
            XdocUtil.extractUsedPropertiesFromXdocsExamples();
        final List<String> failures = new ArrayList<>();

        for (Class<?> checkClass : CheckUtil.getCheckstyleChecks()) {
            final String checkSimpleName = checkClass.getSimpleName();

            final Set<String> definedProperties = Arrays.stream(
                    PropertyUtils.getPropertyDescriptors(checkClass))
                .filter(descriptor -> descriptor.getWriteMethod() != null)
                .map(PropertyDescriptor::getName)
                .filter(property -> !COMMON_PROPERTIES.contains(property))
                .collect(Collectors.toUnmodifiableSet());

            final Set<String> usedProperties =
                usedPropertiesByCheck.getOrDefault(checkSimpleName, Collections.emptySet());

            for (String property : definedProperties) {
                if (!usedProperties.contains(property)) {
                    failures.add("Missing property in xdoc: '"
                            + property + "' of " + checkSimpleName);
                }
            }
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Xdocs are missing properties:\n" + String.join("\n", failures))
                    .fail();
        }
    }

    @Test
    public void testAllExampleFilesHaveCorrespondingTestMethods() throws Exception {
        final Path examplesResources = Path.of("src/xdocs-examples/resources");
        final Path examplesNonCompilable = Path.of("src/xdocs-examples/resources-noncompilable");
        final Path examplesTestRoot = Path.of(
            "src/xdocs-examples/java/com/puppycrawl/tools/checkstyle/checks");
        final List<String> failures = new ArrayList<>();

        try (Stream<Path> testFiles = Files.walk(examplesTestRoot)) {
            testFiles
                .filter(path -> path.toString().endsWith("ExamplesTest.java"))
                .forEach(testFile -> {
                    try {
                        scanFile(testFile, examplesResources, examplesNonCompilable, failures);
                    }
                    catch (IOException exception) {
                        throw new IllegalStateException("Error processing: "
                                     + testFile, exception);
                    }
                });
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Example files are missing corresponding test methods:\n"
                    + String.join("\n", failures))
                    .fail();
        }
    }

    @Test
    public void testAllExampleFilesAreReferencedInXdocs() throws Exception {
        final Set<String> referencedPaths = collectReferencedExamplePaths();
        final Path xdocsExamplesBase = Path.of("src/xdocs-examples");
        final List<Path> exampleRoots = List.of(
            xdocsExamplesBase.resolve("resources"),
            xdocsExamplesBase.resolve("resources-noncompilable")
        );
        final List<String> failures = new ArrayList<>();

        for (Path root : exampleRoots) {
            if (Files.exists(root)) {
                try (Stream<Path> paths = Files.walk(root)) {
                    paths
                        .filter(path -> {
                            final String fileName = path.getFileName().toString();
                            return Files.isRegularFile(path)
                                && (fileName.startsWith("Example")
                                    || fileName.startsWith("UseCase"));
                        })
                        .forEach(exampleFile -> {
                            final String relative = xdocsExamplesBase
                                .relativize(exampleFile)
                                .toString()
                                .replace(File.separatorChar, '/');
                            if (!referencedPaths.contains(relative)) {
                                failures.add(relative);
                            }
                        });
                }
            }
        }

        if (!failures.isEmpty()) {
            assertWithMessage(
                "The following example files are not referenced in any xml.template file:\n"
                    + String.join("\n", failures))
                .fail();
        }
    }

    private static Set<String> collectReferencedExamplePaths() throws Exception {
        final Set<String> referenced = new HashSet<>();

        for (Path template : XdocUtil.getXdocsTemplatesFilePaths()) {
            final String input = Files.readString(template);
            final Document document = XmlUtil.getRawXml(template.toString(), input, input);
            final NodeList macros = document.getElementsByTagName("macro");

            for (int idx = 0; idx < macros.getLength(); idx++) {
                final Element macro = (Element) macros.item(idx);
                if ("example".equals(macro.getAttribute("name"))) {
                    final String path = getMacroParamValue(macro, "path");
                    if (path != null && !path.isEmpty()) {
                        referenced.add(normalizePath(path));
                    }
                }
            }
        }
        return referenced;
    }

    private static String getMacroParamValue(Element macro, String paramName) {
        String result = null;
        final NodeList params = macro.getElementsByTagName("param");

        for (int idx = 0; idx < params.getLength(); idx++) {
            final Element param = (Element) params.item(idx);
            if (paramName.equals(param.getAttribute("name"))) {
                result = param.getAttribute("value");
                break;
            }
        }
        return result;
    }

    private static String normalizePath(String path) {
        String result = path;
        if (result.startsWith("/")) {
            result = result.substring(1);
        }
        return result;
    }

    private static void scanFile(Path testFile, Path examplesResources, Path examplesNonCompilable,
            List<String> failures)
            throws IOException {
        final String testContent = Files.readString(testFile);

        final String className = Path.of("src/xdocs-examples/java").toAbsolutePath()
                .relativize(testFile.toAbsolutePath()).toString()
                .replace(File.separator, ".")
                .replaceFirst("\\.java$", "");

        try {
            final Class<?> testClass = Class.forName(className);
            final AbstractPathTestSupport instance = (AbstractPathTestSupport) testClass
                    .getDeclaredConstructor().newInstance();
            final String packageLocation = instance.getPackageLocation();

            scanExampleDirectory(examplesResources.resolve(packageLocation),
                    testContent, testFile, failures);
            scanExampleDirectory(examplesNonCompilable.resolve(packageLocation),
                    testContent, testFile, failures);
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to instantiate " + className, exception);
        }
    }

    private static void scanExampleDirectory(Path exampleDir, String testContent,
            Path testFile, List<String> failures) throws IOException {
        if (Files.exists(exampleDir) && Files.isDirectory(exampleDir)) {
            try (Stream<Path> exampleFiles = Files.list(exampleDir)) {
                exampleFiles
                    .filter(path -> {
                        final String fileName = path.getFileName()
                                .toString();
                        return fileName.matches("Example\\d+\\.java");
                    })
                    .forEach(exampleFile -> {
                        final String fileName = exampleFile.getFileName()
                                .toString();
                        if (!testContent.contains("\"" + fileName + "\"")) {
                            failures.add("Missing test for " + fileName + " in "
                                        + testFile.getFileName());
                        }
                    });
            }
        }
    }

}
