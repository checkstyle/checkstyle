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

package com.puppycrawl.tools.checkstyle.filefilters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

public class BeforeExecutionExclusionFileFilterExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filefilters/beforeexecutionexclusionfilefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("1: File name must start with an uppercase.");
        expected.put(getNonCompilablePath("test/generated_StubBankRemote.java"), messages);
        expected.put(getNonCompilablePath("test/generated_TestCase1.java"), messages);
        expected.put(getNonCompilablePath("module-info.java"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources-noncompilable/"
                + getPackageLocation() + "/");

        final String fileWithConfig = getNonCompilablePath("Example1.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("1: File name must start with an uppercase.");
        expected.put(getNonCompilablePath("test/generated_StubBankRemote.java"), messages);
        expected.put(getNonCompilablePath("test/generated_TestCase1.java"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources-noncompilable/"
                + getPackageLocation() + "/");

        final String fileWithConfig = getNonCompilablePath("Example2.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("1: File name must start with an uppercase.");
        expected.put(getNonCompilablePath("test/generated_StubBankRemote.java"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources-noncompilable/"
                + getPackageLocation() + "/");

        final String fileWithConfig = getNonCompilablePath("Example3.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();

        final Path path = Paths.get("src/xdocs-examples/resources-noncompilable/"
                + getPackageLocation() + "/");

        final String fileWithConfig = getNonCompilablePath("Example4.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    private static File[] getFilesInFolder(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path.toAbsolutePath())) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toArray(File[]::new);
        }
    }

}
