///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.testmodules;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.RootModule;

public class TestRootModuleChecker implements RootModule {

    private static boolean processed;
    private static List<File> filesToCheck;
    private static Configuration config;
    private static boolean destroyed;
    private static String property;

    @Override
    public void configure(Configuration configuration) throws CheckstyleException {
        config = configuration;
        property = configuration.getProperty("property");
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public int processX(List<File> files) {
        processed = true;
        filesToCheck = new ArrayList<>(files);
        return 0;
    }

    @Override
    public int process(Collection<Path> files) {
        return process(files.stream()
                .map(Path::toFile)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public void addListener(AuditListener listener) {
        // not used
    }

    @Override
    public void setModuleClassLoader(ClassLoader moduleClassLoader) {
        // not used
    }

    public static boolean isProcessed() {
        return processed;
    }

    public static boolean isDestroyed() {
        return destroyed;
    }

    public static String getProperty() {
        return property;
    }

    public static void reset() {
        processed = false;
        destroyed = false;
        filesToCheck = null;
        config = null;
        property = null;
    }

    public static List<File> getFilesToCheck() {
        return Collections.unmodifiableList(filesToCheck);
    }

    public static Configuration getConfig() {
        return config;
    }

}
