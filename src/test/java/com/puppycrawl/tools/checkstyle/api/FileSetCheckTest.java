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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FileSetCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/fileset";
    }

    @Test
    public void testTranslation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputFileSetIllegalTokens.java"), expected);

        assertWithMessage("destroy() not called by Checker")
                .that(TestFileSetCheck.isDestroyed())
                .isTrue();
    }

    @Test
    public void testProcessCallsFinishBeforeCallingDestroy() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputFileSetIllegalTokens.java"), expected);

        assertWithMessage("FileContent should be available during finishProcessing() call")
                .that(TestFileSetCheck.isFileContentAvailable())
                .isTrue();
    }

    public static class TestFileSetCheck extends AbstractFileSetCheck {

        private static boolean destroyed;
        private static boolean fileContentAvailable;
        private static FileContents contents;

        @Override
        public void destroy() {
            destroyed = true;
        }

        public static boolean isDestroyed() {
            return destroyed;
        }

        public static boolean isFileContentAvailable() {
            return fileContentAvailable;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            contents = new FileContents(fileText);
        }

        @Override
        public void finishProcessing() {
            fileContentAvailable = contents != null;
        }

    }

}
