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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class FileSetCheckLifecycleTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + filename);
    }

    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(config);
        return dc;
    }

    @Test
    public void testGetRequiredTokens() {
        final FileContentsHolder checkObj = new FileContentsHolder();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testTranslation() throws Exception {
        final Configuration checkConfig =
            createCheckConfig(TestFileSetCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);

        assertTrue("destroy() not called by Checker", TestFileSetCheck.isDestroyed());
    }

    @Test
    public void testProcessCallsFinishBeforeCallingDestroy() throws Exception {

        final DefaultConfiguration dc = new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        dc.addAttribute("charset", "UTF-8");
        dc.addChild(twConf);
        twConf.addChild(new DefaultConfiguration(FileContentsHolder.class.getName()));
        twConf.addChild(new DefaultConfiguration(AvoidStarImportCheck.class.getName()));

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefUtLogger(stream));

        checker.addFileSetCheck(new TestFileSetCheck());

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, getPath("InputIllegalTokens.java"), expected);

        assertTrue("FileContent should be available during finishProcessing() call",
                TestFileSetCheck.isFileContentAvailable());
    }

    private static class TestFileSetCheck extends AbstractFileSetCheck {
        private static boolean destroyed;
        private static boolean fileContentAvailable;

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
        protected void processFiltered(File file, List<String> lines) {
            //dummy method
        }

        @Override
        public void finishProcessing() {
            fileContentAvailable = FileContentsHolder.getCurrentFileContents() != null;
        }
    }
}
