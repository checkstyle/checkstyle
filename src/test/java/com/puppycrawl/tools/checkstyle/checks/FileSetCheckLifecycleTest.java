////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck;

public class FileSetCheckLifecycleTest
    extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration checkConfig) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(checkConfig);
        return dc;
    }

    public static class TestFileSetCheck extends AbstractFileSetCheck {
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
        }

        @Override
        public void finishProcessing() {
            fileContentAvailable = FileContentsHolder.getContents() != null;
        }
    }

    @Test
    public void testTranslation() throws Exception {
        final Configuration checkConfig =
            createCheckConfig(TestFileSetCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);

        assertTrue("destroy() not called by Checker", TestFileSetCheck.isDestroyed());
    }

    @Test
    public void testProcessCallsFinishBeforeCallingDestroy() throws Exception {

        DefaultConfiguration dc = new DefaultConfiguration("configuration");
        DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
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
        checker.addListener(new BriefLogger(stream));

        checker.addFileSetCheck(new TestFileSetCheck());

        final String[] expected = {
        };

        verify(checker, getPath("InputScopeAnonInner.java"), expected);

        assertTrue("FileContent should be available during finishProcessing() call",
                TestFileSetCheck.isFileContentAvailable());
    }
}
