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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/** Class which handles all the metadata generation and writing calls. */
public final class MetadataGeneratorUtil {

    /** To check whether to print output to console or not. */
    private static boolean printToConsole;

    /** Stop instances being created. **/
    private MetadataGeneratorUtil() {
    }

    /**
     * Getter method for {@code printToConsole}.
     *
     * @return printToConsole.
     */
    private static boolean isPrintToConsole() {
        return printToConsole;
    }

    /**
     * Setter method for {@code printToConsole}.
     *
     * @param printToConsole printToConsole.
     */
    private static void setPrintToConsole(boolean printToConsole) {
        MetadataGeneratorUtil.printToConsole = printToConsole;
    }

    /**
     * Generate metadata from the module source files available in the input argument path.
     *
     * @param args arguments
     * @return number of violations
     * @throws IOException ioException
     * @throws CheckstyleException checkstyleException
     */
    public static int generate(String... args) throws IOException, CheckstyleException {
        JavadocMetadataScraper.resetModuleDetailsStore();

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(JavadocMetadataScraper.class.getName());
        final DefaultConfiguration defaultConfiguration = new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        checker.configure(defaultConfiguration);
        return dumpMetadata(checker, args[0]);
    }

    /**
     * Process files using the checker passed and write to corresponding XML files.
     *
     * @param checker checker
     * @param path rootPath
     * @return number of violations
     * @throws CheckstyleException checkstyleException
     * @throws IOException ioException
     */
    private static int dumpMetadata(Checker checker, String path) throws CheckstyleException,
            IOException {
        final List<File> validFiles = new ArrayList<>();
        final List<String> moduleFolders = Arrays.asList("checks", "filters", "filefilters");
        for (String folder : moduleFolders) {
            try (Stream<Path> files = Files.walk(Paths.get(path
                    + "/" + folder))) {
                validFiles.addAll(
                        files.map(Path::toFile)
                        .filter(file -> {
                            return file.getName().endsWith("SuppressWarningsHolder.java")
                                    || file.getName().endsWith("Check.java")
                                    || file.getName().endsWith("Filter.java");
                        })
                        .collect(Collectors.toList()));
            }
        }

        if (isPrintToConsole()) {
            checker.addListener(new DefaultLogger(
                    System.out, AutomaticBean.OutputStreamOptions.NONE));
            setPrintToConsole(false);
        }
        return checker.process(validFiles);
    }
}
