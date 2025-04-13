////
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.MetadataGeneratorLogger;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/** Class which handles all the metadata generation and writing calls. */
public final class MetadataGeneratorUtil {

    /** Stop instances being created. **/
    private MetadataGeneratorUtil() {
    }

    /**
     * Generate metadata from the module source files available in the input argument path.
     *
     * @param path arguments
     * @param out OutputStream for error messages
     * @param moduleFolders folders to check
     * @throws IOException ioException
     * @throws CheckstyleException checkstyleException
     */
    public static void generate(String path, OutputStream out, String... moduleFolders)
        throws IOException, CheckstyleException {
        JavadocMetadataScraper.resetModuleDetailsStore();

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
            new DefaultConfiguration(JavadocMetadataScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
            new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        checker.configure(defaultConfiguration);

        checker.addListener(new MetadataGeneratorLogger(out, OutputStreamOptions.NONE));

        final List<File> moduleFiles = getTargetFiles(path, moduleFolders);

        checker.process(moduleFiles);
    }

    /**
     * Get files that represent modules.
     *
     * @param moduleFolders folders to check
     * @param path          rootPath
     * @return files for scrapping javadoc and generation of metadata files
     * @throws IOException ioException
     */
    private static List<File> getTargetFiles(String path, String... moduleFolders)
        throws IOException {
        final List<File> validFiles = new ArrayList<>();
        for (String folder : moduleFolders) {
            try (Stream<Path> files = Files.walk(Path.of(path + "/" + folder))) {
                validFiles.addAll(
                    files.map(Path::toFile)
                        .filter(file -> {
                            final String fileName = file.getName();
                            return fileName.endsWith("SuppressWarningsHolder.java")
                                || fileName.endsWith("Check.java")
                                || fileName.endsWith("Filter.java");
                        })
                        .collect(Collectors.toUnmodifiableList()));
            }
        }

        return validFiles;
    }
}
