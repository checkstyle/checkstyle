////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * AllTestsTest.
 * @noinspection ClassIndependentOfModule
 */
public class AllTestsTest {

    @Test
    public void testAllInputsHaveTest() throws Exception {
        final Map<String, List<String>> allTests = new HashMap<>();

        Files.walk(Paths.get("src/test/java"))
            .forEach(filePath -> {
                grabAllTests(allTests, filePath.toFile());
            });

        Assert.assertTrue("found tests", !allTests.keySet().isEmpty());

        Files.walk(Paths.get("src/test/resources/com/puppycrawl"))
            .forEach(filePath -> {
                verifyInputFile(allTests, filePath.toFile());
            });
        Files.walk(Paths.get("src/test/resources-noncompilable/com/puppycrawl"))
            .forEach(filePath -> {
                verifyInputFile(allTests, filePath.toFile());
            });
    }

    @Test
    public void testAllTestsHaveProductionCode() throws Exception {
        final Map<String, List<String>> allTests = new HashMap<>();

        Files.walk(Paths.get("src/main/java"))
            .forEach(filePath -> {
                grabAllFiles(allTests, filePath.toFile());
            });

        Assert.assertTrue("found tests", !allTests.keySet().isEmpty());

        Files.walk(Paths.get("src/test/java"))
            .forEach(filePath -> {
                verifyHasProductionFile(allTests, filePath.toFile());
            });
    }

    private static void grabAllTests(Map<String, List<String>> allTests, File file) {
        if (file.isFile() && file.getName().endsWith("Test.java")) {
            String path;

            try {
                path = getSimplePath(file.getCanonicalPath()).replace("CheckTest.java", "")
                        .replace("Test.java", "");
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }

            // override for 'AbstractCheck' naming
            if (path.endsWith(File.separator + "Abstract")) {
                path += "Check";
            }

            final int slash = path.lastIndexOf(File.separatorChar);
            final String packge = path.substring(0, slash);

            List<String> classes = allTests.get(packge);

            if (classes == null) {
                classes = new ArrayList<>();

                allTests.put(packge, classes);
            }

            classes.add(path.substring(slash + 1));
        }
    }

    private static void grabAllFiles(Map<String, List<String>> allTests, File file) {
        if (file.isFile()) {
            final String path;

            try {
                path = getSimplePath(file.getCanonicalPath());
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }

            final int slash = path.lastIndexOf(File.separatorChar);
            final String packge = path.substring(0, slash);

            List<String> classes = allTests.get(packge);

            if (classes == null) {
                classes = new ArrayList<>();

                allTests.put(packge, classes);
            }

            classes.add(path.substring(slash + 1));
        }
    }

    private static void verifyInputFile(Map<String, List<String>> allTests, File file) {
        if (file.isFile()) {
            final String path;

            try {
                path = getSimplePath(file.getCanonicalPath());
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }

            // until https://github.com/checkstyle/checkstyle/issues/5105
            if (!path.contains(File.separatorChar + "grammar" + File.separatorChar)
                    && !path.contains(File.separatorChar + "foo" + File.separatorChar)
                    && !path.contains(File.separatorChar + "bar" + File.separatorChar)) {
                String fileName = file.getName();
                final boolean skipFileNaming = shouldSkipInputFileNameCheck(path, fileName);

                if (!skipFileNaming) {
                    Assert.assertTrue("Resource must start with 'Input' or 'Expected': " + path,
                            fileName.startsWith("Input") || fileName.startsWith("Expected"));

                    if (fileName.startsWith("Input")) {
                        fileName = fileName.substring(5);
                    }
                    else {
                        fileName = fileName.substring(8);
                    }

                    final int period = fileName.lastIndexOf('.');

                    if (period > 0) {
                        fileName = fileName.substring(0, period);
                    }
                }

                verifyInputFile(allTests, skipFileNaming, path, fileName);
            }
        }
    }

    private static void verifyInputFile(Map<String, List<String>> allTests, boolean skipFileNaming,
            String path, String fileName) {
        List<String> classes;
        int slash = path.lastIndexOf(File.separatorChar);
        String packge = path.substring(0, slash);
        boolean found = false;

        for (int depth = 0; depth < 4; depth++) {
            // -@cs[MoveVariableInsideIf] assignment value is modified later so it can't be
            // moved
            final String folderPath = packge;
            slash = packge.lastIndexOf(File.separatorChar);
            packge = path.substring(0, slash);
            classes = allTests.get(packge);

            if (classes != null
                    && checkInputMatchCorrectFileStructure(classes, folderPath, skipFileNaming,
                            fileName)) {
                found = true;
                break;
            }
        }

        Assert.assertTrue("Resource must be named after a Test like 'InputMyCustomCase.java' "
                + "and be in the sub-package of the test like 'mycustom' "
                + "for test 'MyCustomCheckTest': " + path, found);
    }

    private static void verifyHasProductionFile(Map<String, List<String>> allTests, File file) {
        if (file.isFile()) {
            final String fileName = file.getName().replace("Test.java", ".java");

            if (!fileName.endsWith("TestSupport.java")
                    // tests external utility XPathEvaluator
                    && !"XpathMapper.java".equals(fileName)) {
                final String path;

                try {
                    path = getSimplePath(file.getCanonicalPath());
                }
                catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }

                if (!path.contains(File.separatorChar + "grammar" + File.separatorChar)
                        && !path.contains(File.separatorChar + "internal" + File.separatorChar)) {
                    final int slash = path.lastIndexOf(File.separatorChar);
                    final String packge = path.substring(0, slash);
                    final List<String> classes = allTests.get(packge);

                    Assert.assertTrue("Test must be named after a production class "
                            + "and must be in the same package of the production class: " + path,
                            classes != null && classes.contains(fileName));
                }
            }
        }
    }

    private static boolean checkInputMatchCorrectFileStructure(List<String> classes,
            String folderPath, boolean skipFileNaming, String fileName) {
        boolean result = false;

        for (String clss : classes) {
            if (folderPath.endsWith(File.separatorChar + clss.toLowerCase(Locale.ENGLISH))
                    && (skipFileNaming || fileName.startsWith(clss))) {
                result = true;
                break;
            }
        }

        return result;
    }

    private static boolean shouldSkipInputFileNameCheck(String path, String fileName) {
        return "package-info.java".equals(fileName)
                || "package.html".equals(fileName)
                // special directory for files that can't be renamed or are secondary inputs
                || path.contains(File.separatorChar + "inputs" + File.separatorChar)
                // all inputs must start with 'messages'
                || path.contains(File.separatorChar + "translation" + File.separatorChar);
    }

    private static String getSimplePath(String path) {
        return path.substring(path.lastIndexOf("com" + File.separator + "puppycrawl"));
    }

}
