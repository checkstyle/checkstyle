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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.RootModule;

public class ModuleReflectionUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(ModuleReflectionUtils.class, true);
    }

    @Test
    public void testIsCheckstyleModule() {
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(CheckClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(FileSetModuleClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(FilterClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(TreeWalkerFilterClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(FileFilterModuleClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtils.isCheckstyleModule(RootModuleClass.class));
    }

    @Test
    public void testIsValidCheckstyleClass() {
        assertTrue("Should return true when valid checkstyle class is passed",
                ModuleReflectionUtils.isValidCheckstyleClass(ValidCheckstyleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils
                .isValidCheckstyleClass(InvalidNonAutomaticBeanClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isValidCheckstyleClass(AbstractInvalidClass.class));
    }

    @Test
    public void testIsCheckstyleCheck() {
        assertTrue("Should return true when valid checkstyle check is passed",
                ModuleReflectionUtils.isCheckstyleCheck(CheckClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isCheckstyleCheck(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFileSetModule() {
        assertTrue("Should return true when valid checkstyle file set module is passed",
                ModuleReflectionUtils.isFileSetModule(FileSetModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isFileSetModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFilterModule() {
        assertTrue("Should return true when valid checkstyle filter module is passed",
                ModuleReflectionUtils.isFilterModule(FilterClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFileFilterModule() {
        assertTrue("Should return true when valid checkstyle file filter module is passed",
                ModuleReflectionUtils.isFileFilterModule(FileFilterModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isFileFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsTreeWalkerFilterModule() {
        assertTrue("Should return true when valid checkstyle TreeWalker filter module is passed",
                ModuleReflectionUtils.isTreeWalkerFilterModule(TreeWalkerFilterClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isTreeWalkerFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsRootModule() {
        assertTrue("Should return true when valid checkstyle root module is passed",
                ModuleReflectionUtils.isRootModule(RootModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtils.isRootModule(NotCheckstyleCheck.class));
    }

    private static class ValidCheckstyleClass extends AutomaticBean {
        protected ValidCheckstyleClass() {
            //keep pmd calm and happy
        }
    }

    private static class InvalidNonAutomaticBeanClass {
        protected InvalidNonAutomaticBeanClass() {
            //keep pmd calm and happy
        }
    }

    /** @noinspection AbstractClassNeverImplemented */
    private abstract static class AbstractInvalidClass extends AutomaticBean {
        public abstract void method();
    }

    private static class CheckClass extends AbstractCheck {
        @Override
        public int[] getDefaultTokens() {
            return new int[] {0};
        }

        @Override
        public int[] getAcceptableTokens() {
            return getDefaultTokens();
        }

        @Override
        public int[] getRequiredTokens() {
            return getDefaultTokens();
        }
    }

    private static class FileSetModuleClass extends AbstractFileSetCheck {
        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            //dummy method
        }
    }

    private static class FilterClass extends AutomaticBean implements Filter {
        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }
    }

    private static class FileFilterModuleClass extends AutomaticBean
            implements BeforeExecutionFileFilter {
        @Override
        public boolean accept(String uri) {
            return false;
        }
    }

    private static class RootModuleClass extends AutomaticBean implements RootModule {
        @Override
        public void addListener(AuditListener listener) {
            //dummy method
        }

        @Override
        public int process(List<File> files) throws CheckstyleException {
            return 0;
        }

        @Override
        public void destroy() {
            //dummy method
        }

        @Override
        public void setModuleClassLoader(ClassLoader moduleClassLoader) {
            //dummy method
        }
    }

    private static class TreeWalkerFilterClass extends AutomaticBean implements TreeWalkerFilter {
        @Override
        public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
            return false;
        }
    }

    private static class NotCheckstyleCheck {
        protected NotCheckstyleCheck() {
            //keep pmd calm and happy
        }
    }
}
