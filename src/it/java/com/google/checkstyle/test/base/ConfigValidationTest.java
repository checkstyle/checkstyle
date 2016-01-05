////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.google.checkstyle.test.base;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ConfigValidationTest extends BaseCheckTestSupport {
    @Test
    public void testGoogleChecks() throws Exception {
        final Configuration checkerConfig = getConfiguration();
        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final List<File> files = new ArrayList<>();
        listFiles(files, new File("src/it/"), "java");

        //runs over all input files;
        //as severity level is "warning", no errors expected
        verify(checker, files.toArray(new File[files.size()]), "",
                EMPTY_STRING_ARRAY, EMPTY_INTEGER_OBJECT_ARRAY);
    }

    private static void listFiles(final List<File> files, final File folder,
            final String extension) {
        if (folder.canRead()) {
            if (folder.isDirectory()) {
                for (final File file : folder.listFiles()) {
                    listFiles(files, file, extension);
                }
            }
            else if (folder.toString().endsWith("." + extension)) {
                files.add(folder);
            }
        }
    }
}
