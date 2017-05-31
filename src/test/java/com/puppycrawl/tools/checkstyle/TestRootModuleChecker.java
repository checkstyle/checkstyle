////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.RootModule;

public class TestRootModuleChecker implements RootModule {
    private static boolean processed;
    private static List<File> filesToCheck;
    private static Configuration config;

    @Override
    public void configure(Configuration configuration) throws CheckstyleException {
        config = configuration;
    }

    @Override
    public void destroy() {
        // not used
    }

    @Override
    public int process(List<File> files) throws CheckstyleException {
        processed = true;
        filesToCheck = new ArrayList<>(files);
        return 0;
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

    public static void reset() {
        processed = false;
        filesToCheck = null;
        config = null;
    }

    public static List<File> getFilesToCheck() {
        return Collections.unmodifiableList(filesToCheck);
    }

    public static Configuration getConfig() {
        return config;
    }
}
