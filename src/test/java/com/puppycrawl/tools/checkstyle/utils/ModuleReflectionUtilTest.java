////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.RootModule;

public class ModuleReflectionUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(ModuleReflectionUtil.class, true));
    }

    @Test
    public void testIsCheckstyleModule() {
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(CheckClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(FileSetModuleClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(FilterClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(TreeWalkerFilterClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(FileFilterModuleClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(AuditListenerClass.class));
        assertTrue("Should return true when checkstyle module is passed",
                ModuleReflectionUtil.isCheckstyleModule(RootModuleClass.class));
    }

    @Test
    public void testIsValidCheckstyleClass() {
        assertTrue("Should return true when valid checkstyle class is passed",
                ModuleReflectionUtil.isValidCheckstyleClass(ValidCheckstyleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil
                .isValidCheckstyleClass(InvalidNonAutomaticBeanClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isValidCheckstyleClass(AbstractInvalidClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil
                        .isValidCheckstyleClass(InvalidNonDefaultConstructorClass.class));
    }

    @Test
    public void testIsCheckstyleCheck() {
        assertTrue("Should return true when valid checkstyle check is passed",
                ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(CheckClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFileSetModule() {
        assertTrue("Should return true when valid checkstyle file set module is passed",
                ModuleReflectionUtil.isFileSetModule(FileSetModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isFileSetModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFilterModule() {
        assertTrue("Should return true when valid checkstyle filter module is passed",
                ModuleReflectionUtil.isFilterModule(FilterClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsFileFilterModule() {
        assertTrue("Should return true when valid checkstyle file filter module is passed",
                ModuleReflectionUtil.isFileFilterModule(FileFilterModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isFileFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsTreeWalkerFilterModule() {
        assertTrue("Should return true when valid checkstyle TreeWalker filter module is passed",
                ModuleReflectionUtil.isTreeWalkerFilterModule(TreeWalkerFilterClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isTreeWalkerFilterModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsAuditListener() {
        assertTrue("Should return true when valid checkstyle AuditListener module is passed",
                ModuleReflectionUtil.isAuditListener(DefaultLogger.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isAuditListener(NotCheckstyleCheck.class));
    }

    @Test
    public void testIsRootModule() {
        assertTrue("Should return true when valid checkstyle root module is passed",
                ModuleReflectionUtil.isRootModule(RootModuleClass.class));
        assertFalse("Should return false when invalid class is passed",
                ModuleReflectionUtil.isRootModule(NotCheckstyleCheck.class));
    }

    @Test
    public void testKeepEclipseHappy() {
        final InvalidNonDefaultConstructorClass test = new InvalidNonDefaultConstructorClass(0);
        assertNotNull("should use constructor", test);
        assertEquals("should use field", 1, test.getField());
    }

    private static class ValidCheckstyleClass extends AutomaticBean {

        // empty, use default constructor

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

    }

    private static class InvalidNonAutomaticBeanClass {

        // empty, use default constructor

    }

    /**
     * AbstractInvalidClass.
     * @noinspection AbstractClassNeverImplemented
     */
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
        protected void processFiltered(File file, FileText fileText) {
            //dummy method
        }

    }

    private static class FilterClass extends AutomaticBean implements Filter {

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }

    }

    private static class FileFilterModuleClass extends AutomaticBean
            implements BeforeExecutionFileFilter {

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

        @Override
        public boolean accept(String uri) {
            return false;
        }

    }

    private static class RootModuleClass extends AutomaticBean implements RootModule {

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

        @Override
        public void addListener(AuditListener listener) {
            //dummy method
        }

        @Override
        public int process(List<File> files) {
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
        protected void finishLocalSetup() {
            //dummy method
        }

        @Override
        public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
            return false;
        }

    }

    private static class AuditListenerClass extends AutomaticBean implements AuditListener {

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

        @Override
        public void auditStarted(AuditEvent event) {
            //dummy method
        }

        @Override
        public void auditFinished(AuditEvent event) {
            //dummy method
        }

        @Override
        public void fileStarted(AuditEvent event) {
            //dummy method
        }

        @Override
        public void fileFinished(AuditEvent event) {
            //dummy method
        }

        @Override
        public void addError(AuditEvent event) {
            //dummy method
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            //dummy method
        }

    }

    private static class NotCheckstyleCheck {

        // empty, use default constructor

    }

    private static class InvalidNonDefaultConstructorClass extends AutomaticBean {

        private int field;

        protected InvalidNonDefaultConstructorClass(int data) {
            //keep pmd calm and happy
            field = 0;
            method(data);
        }

        public final void method(int data) {
            field++;
            if (data > 0) {
                method(data - 1);
            }
        }

        public int getField() {
            return field;
        }

        @Override
        protected void finishLocalSetup() {
            //dummy method
        }

    }

}
