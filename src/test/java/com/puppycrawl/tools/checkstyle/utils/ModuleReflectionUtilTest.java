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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.BASE_PACKAGE;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.XpathFileGeneratorAstFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.RootModule;

public class ModuleReflectionUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(ModuleReflectionUtil.class))
                .isTrue();
    }

    @Test
    public void testIsCheckstyleModule() {
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(CheckClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(FileSetModuleClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(FilterClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(TreeWalkerFilterClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(FileFilterModuleClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(AuditListenerClass.class))
                .isTrue();
        assertWithMessage("Should return true when checkstyle module is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(RootModuleClass.class))
                .isTrue();
    }

    /**
     * This test case is designed to verify the behavior of getCheckstyleModules method.
     * It is provided with a package name that does not contain any checkstyle modules.
     * It ensures that ModuleReflectionUtil.getCheckstyleModules is returning an empty set.
     */
    @Test
    public void testGetCheckStyleModules() throws IOException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final Set<String> packages = Collections.singleton(BASE_PACKAGE + ".checks.javadoc.utils");

        assertWithMessage("specified package has no checkstyle modules")
                .that(ModuleReflectionUtil.getCheckstyleModules(packages, classLoader))
                .isEmpty();
    }

    @Test
    public void testIsValidCheckstyleClass() {
        assertWithMessage("Should return true when valid checkstyle class is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(ValidCheckstyleClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(
                    ModuleReflectionUtil.isCheckstyleModule(InvalidNonAutomaticBeanClass.class))
                .isFalse();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isCheckstyleModule(AbstractInvalidClass.class))
                .isFalse();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil
                    .isCheckstyleModule(InvalidNonDefaultConstructorClass.class))
                .isFalse();
        assertWithMessage("Should return false when forced invalid class is passed")
                .that(
                    ModuleReflectionUtil.isCheckstyleModule(XpathFileGeneratorAstFilter.class))
                .isFalse();
    }

    @Test
    public void testIsCheckstyleCheck() {
        assertWithMessage("Should return true when valid checkstyle check is passed")
                .that(ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(CheckClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsFileSetModule() {
        assertWithMessage("Should return true when valid checkstyle file set module is passed")
                .that(ModuleReflectionUtil.isFileSetModule(FileSetModuleClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isFileSetModule(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsFilterModule() {
        assertWithMessage("Should return true when valid checkstyle filter module is passed")
                .that(ModuleReflectionUtil.isFilterModule(FilterClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isFilterModule(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsFileFilterModule() {
        assertWithMessage("Should return true when valid checkstyle file filter module is passed")
                .that(ModuleReflectionUtil.isFileFilterModule(FileFilterModuleClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isFileFilterModule(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsTreeWalkerFilterModule() {
        assertWithMessage(
                    "Should return true when valid checkstyle TreeWalker filter module is passed")
                .that(ModuleReflectionUtil.isTreeWalkerFilterModule(TreeWalkerFilterClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isTreeWalkerFilterModule(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsAuditListener() {
        assertWithMessage("Should return true when valid checkstyle AuditListener module is passed")
                .that(ModuleReflectionUtil.isAuditListener(DefaultLogger.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isAuditListener(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testIsRootModule() {
        assertWithMessage("Should return true when valid checkstyle root module is passed")
                .that(ModuleReflectionUtil.isRootModule(RootModuleClass.class))
                .isTrue();
        assertWithMessage("Should return false when invalid class is passed")
                .that(ModuleReflectionUtil.isRootModule(NotCheckstyleCheck.class))
                .isFalse();
    }

    @Test
    public void testKeepEclipseHappy() {
        final InvalidNonDefaultConstructorClass test = new InvalidNonDefaultConstructorClass(0);
        assertWithMessage("should use constructor")
            .that(test)
            .isNotNull();
        assertWithMessage("should use field")
            .that(test.getField())
            .isEqualTo(1);
    }

    private static final class ValidCheckstyleClass extends AbstractAutomaticBean {

        // empty, use default constructor

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

    }

    private static final class InvalidNonAutomaticBeanClass {

        // empty, use default constructor

    }

    /**
     * AbstractInvalidClass.
     *
     * @noinspection AbstractClassNeverImplemented
     * @noinspectionreason AbstractClassNeverImplemented - class is only used in testing
     */
    private abstract static class AbstractInvalidClass extends AbstractAutomaticBean {

        public abstract void method();

    }

    private static final class CheckClass extends AbstractCheck {

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

    private static final class FileSetModuleClass extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // dummy method
        }

    }

    private static final class FilterClass extends AbstractAutomaticBean implements Filter {

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }

    }

    private static final class FileFilterModuleClass extends AbstractAutomaticBean
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

    private static final class RootModuleClass extends AbstractAutomaticBean implements RootModule {

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
        public int process(Collection<Path> files) {
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

    private static final class TreeWalkerFilterClass
            extends AbstractAutomaticBean implements TreeWalkerFilter {

        @Override
        protected void finishLocalSetup() {
            // dummy method
        }

        @Override
        public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
            return false;
        }

    }

    private static final class AuditListenerClass
            extends AbstractAutomaticBean implements AuditListener {

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

    private static final class NotCheckstyleCheck {

        // empty, use default constructor

    }

    private static class InvalidNonDefaultConstructorClass extends AbstractAutomaticBean {

        private int field;

        protected InvalidNonDefaultConstructorClass(int data) {
            // keep pmd calm and happy
            field = 0;
            method(data);
        }

        /**
         * Increments the field.
         *
         * @param data of int type.
         */
        public final void method(int data) {
            field = data + 1;
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
