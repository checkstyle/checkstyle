////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.XpathFileGeneratorAstFilter;
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
        assertTrue(isUtilsClassHasPrivateConstructor(ModuleReflectionUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testIsCheckstyleModule() {
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(CheckClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(FileSetModuleClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(FilterClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(TreeWalkerFilterClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(FileFilterModuleClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(AuditListenerClass.class),
                "Should return true when checkstyle module is passed");
        assertTrue(ModuleReflectionUtil.isCheckstyleModule(RootModuleClass.class),
                "Should return true when checkstyle module is passed");
    }

    @Test
    public void testIsValidCheckstyleClass() {
        assertTrue(ModuleReflectionUtil.isValidCheckstyleClass(ValidCheckstyleClass.class),
            "Should return true when valid checkstyle class is passed");
        assertFalse(ModuleReflectionUtil.isValidCheckstyleClass(InvalidNonAutomaticBeanClass.class),
            "Should return false when invalid class is passed");
        assertFalse(ModuleReflectionUtil.isValidCheckstyleClass(AbstractInvalidClass.class),
            "Should return false when invalid class is passed");
        assertFalse(
            ModuleReflectionUtil.isValidCheckstyleClass(InvalidNonDefaultConstructorClass.class),
            "Should return false when invalid class is passed");
        assertFalse(ModuleReflectionUtil.isValidCheckstyleClass(XpathFileGeneratorAstFilter.class),
            "Should return false when forced invalid class is passed");
    }

    @Test
    public void testIsCheckstyleCheck() {
        assertTrue(ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(CheckClass.class),
                "Should return true when valid checkstyle check is passed");
        assertFalse(ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsFileSetModule() {
        assertTrue(ModuleReflectionUtil.isFileSetModule(FileSetModuleClass.class),
                "Should return true when valid checkstyle file set module is passed");
        assertFalse(ModuleReflectionUtil.isFileSetModule(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsFilterModule() {
        assertTrue(ModuleReflectionUtil.isFilterModule(FilterClass.class),
                "Should return true when valid checkstyle filter module is passed");
        assertFalse(ModuleReflectionUtil.isFilterModule(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsFileFilterModule() {
        assertTrue(ModuleReflectionUtil.isFileFilterModule(FileFilterModuleClass.class),
                "Should return true when valid checkstyle file filter module is passed");
        assertFalse(ModuleReflectionUtil.isFileFilterModule(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsTreeWalkerFilterModule() {
        assertTrue(ModuleReflectionUtil.isTreeWalkerFilterModule(TreeWalkerFilterClass.class),
                "Should return true when valid checkstyle TreeWalker filter module is passed");
        assertFalse(ModuleReflectionUtil.isTreeWalkerFilterModule(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsAuditListener() {
        assertTrue(ModuleReflectionUtil.isAuditListener(DefaultLogger.class),
                "Should return true when valid checkstyle AuditListener module is passed");
        assertFalse(ModuleReflectionUtil.isAuditListener(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testIsRootModule() {
        assertTrue(ModuleReflectionUtil.isRootModule(RootModuleClass.class),
                "Should return true when valid checkstyle root module is passed");
        assertFalse(ModuleReflectionUtil.isRootModule(NotCheckstyleCheck.class),
                "Should return false when invalid class is passed");
    }

    @Test
    public void testKeepEclipseHappy() {
        final InvalidNonDefaultConstructorClass test = new InvalidNonDefaultConstructorClass(0);
        assertNotNull(test, "should use constructor");
        assertEquals(1, test.getField(), "should use field");
    }

    private static class ValidCheckstyleClass extends AutomaticBean {

        // empty, use default constructor

        @Override
        protected void finishLocalSetup() {
            // dummy method
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
            // dummy method
        }

    }

    private static class FilterClass extends AutomaticBean implements Filter {

        @Override
        protected void finishLocalSetup() {
            // dummy method
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
            // dummy method
        }

        @Override
        public boolean accept(String uri) {
            return false;
        }

    }

    private static class RootModuleClass extends AutomaticBean implements RootModule {

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

        @Override
        public void addListener(AuditListener listener) {
            // dummy method
        }

        @Override
        public int process(List<File> files) {
            return 0;
        }

        @Override
        public void destroy() {
            // dummy method
        }

        @Override
        public void setModuleClassLoader(ClassLoader moduleClassLoader) {
            // dummy method
        }

    }

    private static class TreeWalkerFilterClass extends AutomaticBean implements TreeWalkerFilter {

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

        @Override
        public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
            return false;
        }

    }

    private static class AuditListenerClass extends AutomaticBean implements AuditListener {

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

        @Override
        public void auditStarted(AuditEvent event) {
            // dummy method
        }

        @Override
        public void auditFinished(AuditEvent event) {
            // dummy method
        }

        @Override
        public void fileStarted(AuditEvent event) {
            // dummy method
        }

        @Override
        public void fileFinished(AuditEvent event) {
            // dummy method
        }

        @Override
        public void addError(AuditEvent event) {
            // dummy method
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            // dummy method
        }

    }

    private static class NotCheckstyleCheck {

        // empty, use default constructor

    }

    private static class InvalidNonDefaultConstructorClass extends AutomaticBean {

        private int field;

        protected InvalidNonDefaultConstructorClass(int data) {
            // keep pmd calm and happy
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
            // dummy method
        }

    }

}
