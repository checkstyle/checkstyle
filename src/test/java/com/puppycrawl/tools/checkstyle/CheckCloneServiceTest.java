////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CheckCloneServiceTest extends AbstractModuleTestSupport {
    /**
     * A parent logger for CheckCloneService. Used to inject our own handlers
     * to verify the log output.
     * @noinspection LoggerInitializedWithForeignClass
     */
    // -@cs[AvoidModifiersForTypes] Allow logger.
    private static final Logger CHECK_CLONE_SERVICE_PARENT_LOG =
            Logger.getLogger(CheckCloneService.class.getName()).getParent();

    private static final Set<Handler> ORIGINAL_PARENT_LOG_HANDLERS =
            Arrays.stream(CHECK_CLONE_SERVICE_PARENT_LOG.getHandlers()).collect(Collectors.toSet());
    private static final Level ORIGINAL_PARENT_LOG_LEVEL =
            CHECK_CLONE_SERVICE_PARENT_LOG.getLevel();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checkcloneservice";
    }

    @SuppressWarnings("unchecked")
    private static List<FileSetCheck> getFileSetChecks(Checker checker) {
        return (List<FileSetCheck>) Whitebox.getInternalState(checker, "fileSetChecks");
    }

    private static AbstractCheck getFirstTreeWalkerCheck(Checker checker) {
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final TreeWalker treeWalker = fileSetChecks.stream()
                .filter(check -> check instanceof TreeWalker).findFirst()
                .map(check -> (TreeWalker) check).orElseThrow(() -> new IllegalArgumentException(
                        "The checker does not contain TreeWalker"));

        final Collection<AbstractCheck> checks =
                Whitebox.getInternalState(treeWalker, "ordinaryChecks");

        return checks.stream().findFirst().orElseThrow(() -> new IllegalArgumentException(
                "The tree walker does not contain checks"));
    }

    private static TestLogHandler createTestLogHandler(Level level) {
        final TestLogHandler handler = new TestLogHandler();
        handler.setLevel(level);
        handler.setFilter(new Filter() {
            private final String packageName = CheckCloneService.class.getPackage().getName();

            @Override
            public boolean isLoggable(LogRecord record) {
                return record.getLoggerName().startsWith(packageName);
            }
        });
        CHECK_CLONE_SERVICE_PARENT_LOG.addHandler(handler);
        CHECK_CLONE_SERVICE_PARENT_LOG.setLevel(level);
        return handler;
    }

    @Before
    public void setUp() {
        // restore original logging level and HANDLERS to prevent bleeding into other tests
        CHECK_CLONE_SERVICE_PARENT_LOG.setLevel(ORIGINAL_PARENT_LOG_LEVEL);

        for (Handler handler : CHECK_CLONE_SERVICE_PARENT_LOG.getHandlers()) {
            if (!ORIGINAL_PARENT_LOG_HANDLERS.contains(handler)) {
                CHECK_CLONE_SERVICE_PARENT_LOG.removeHandler(handler);
            }
        }
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                TestUtil.isUtilsClassHasPrivateConstructor(CheckCloneService.class, true));
    }

    @Test
    public void testCloneFileSetCheckMarkedFileStatefulCheck() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.FINE);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestFileStatefulFileSetCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final List<FileSetCheck> fileSetCheckClones =
                CheckCloneService.cloneFileSetChecks(fileSetChecks);

        final TestFileStatefulFileSetCheck original =
                (TestFileStatefulFileSetCheck) fileSetChecks.get(0);
        final TestFileStatefulFileSetCheck clone =
                (TestFileStatefulFileSetCheck) fileSetCheckClones.get(0);

        assertEquals("The CheckCloneService must not log anything if the debug is enabled",
                0, handler.getLogs().size());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
        assertEquals("The clone must refer to original check",
                original, clone.getInitialFileSetCheck());
        assertTrue("The clone called the init", clone.initCalled);
    }

    @Test
    public void testCloneFileSetCheckMarkedAsStatelessCheck() throws Exception {
        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestStatelessFileSetCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final List<FileSetCheck> fileSetCheckClones =
                CheckCloneService.cloneFileSetChecks(fileSetChecks);
        final TestStatelessFileSetCheck original =
                (TestStatelessFileSetCheck) fileSetChecks.get(0);
        final TestStatelessFileSetCheck clone =
                (TestStatelessFileSetCheck) fileSetCheckClones.get(0);

        assertSame("The check must not be cloned", original, clone);
    }

    @Test
    public void testCloneFileSetCheckMarkedAsGlobalStatefulCheck() throws Exception {
        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestGlobalStatefulFileSetCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final List<FileSetCheck> fileSetCheckClones =
                CheckCloneService.cloneFileSetChecks(fileSetChecks);
        final TestGlobalStatefulFileSetCheck original =
                (TestGlobalStatefulFileSetCheck) fileSetChecks.get(0);
        final TestGlobalStatefulFileSetCheck clone =
                (TestGlobalStatefulFileSetCheck) fileSetCheckClones.get(0);

        assertSame("The check must not be cloned", original, clone);
    }

    @Test
    public void testCloneUnmarkedFileSetCheckWithLogDisabled() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.OFF);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestUnmarkedFileSetCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final List<FileSetCheck> fileSetCheckClones =
                CheckCloneService.cloneFileSetChecks(fileSetChecks);
        final TestUnmarkedFileSetCheck original =
                (TestUnmarkedFileSetCheck) fileSetChecks.get(0);
        final TestUnmarkedFileSetCheck clone =
                (TestUnmarkedFileSetCheck) fileSetCheckClones.get(0);

        assertEquals("The CheckCloneService must not log anything if the debug is disabled",
                0, handler.getLogs().size());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
    }

    @Test
    public void testCloneUnmarkedFileSetCheckWithLogEnabled() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.FINE);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestUnmarkedFileSetCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final List<FileSetCheck> fileSetChecks = getFileSetChecks(checker);
        final List<FileSetCheck> fileSetCheckClones =
                CheckCloneService.cloneFileSetChecks(fileSetChecks);
        final TestUnmarkedFileSetCheck original =
                (TestUnmarkedFileSetCheck) fileSetChecks.get(0);
        final TestUnmarkedFileSetCheck clone =
                (TestUnmarkedFileSetCheck) fileSetCheckClones.get(0);

        assertEquals("There should be exactly one log message", 1, handler.getLogs().size());
        final LogRecord logEntry = handler.getLogs().get(0);
        final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE,
                "CheckCloneService.moduleMustImplementAtLeastOneMtInterface",
                new String[] {"TestUnmarkedFileSetCheck"}, null, getClass(), null);

        assertEquals("The check clone service must log a warning for a check without a marker",
                localizedMessage.getMessage(),
                logEntry.getMessage());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
    }

    @Test
    public void testCloneCheckMarkedAsFileStatefulCheck() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.FINE);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestFileStatefulCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final TestFileStatefulCheck original =
                (TestFileStatefulCheck) getFirstTreeWalkerCheck(checker);
        final TestFileStatefulCheck clone =
                (TestFileStatefulCheck) CheckCloneService.cloneCheck(original);

        assertEquals("The CheckCloneService must not log anything if the debug is enabled",
                0, handler.getLogs().size());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
        assertTrue("The clone called the init", clone.initCalled);
    }

    @Test
    public void testCloneCheckMarkedAsStatelessCheck() throws Exception {
        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestStatelessCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final TestStatelessCheck original =
                (TestStatelessCheck) getFirstTreeWalkerCheck(checker);
        final TestStatelessCheck clone =
                (TestStatelessCheck) CheckCloneService.cloneCheck(original);

        assertSame("The check must not be cloned", original, clone);
    }

    @Test
    public void testCloneCheckMarkedAsGlobalStatefulCheck() throws Exception {
        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestGlobalStatefulCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final TestGlobalStatefulCheck original =
                (TestGlobalStatefulCheck) getFirstTreeWalkerCheck(checker);
        final TestGlobalStatefulCheck clone =
                (TestGlobalStatefulCheck) CheckCloneService.cloneCheck(original);

        assertSame("The check must not be cloned", original, clone);
    }

    @Test
    public void testCloneUnmarkedCheckWithDebugLogDisabled() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.OFF);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestUnmarkedCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final TestUnmarkedCheck original = (TestUnmarkedCheck) getFirstTreeWalkerCheck(checker);
        final TestUnmarkedCheck clone = (TestUnmarkedCheck) CheckCloneService.cloneCheck(original);

        assertEquals("The CheckCloneService must not log anything if the debug is disabled",
                0, handler.getLogs().size());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
    }

    @Test
    public void testCloneUnmarkedCheckWithDebugLogEnabled() throws Exception {
        final TestLogHandler handler = createTestLogHandler(Level.FINE);

        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestUnmarkedCheck.class);
        fileSetConfiguration.addAttribute("strProperty", "just a test");
        fileSetConfiguration.addAttribute("intProperty", "42");
        final Checker checker = createChecker(fileSetConfiguration);
        final TestUnmarkedCheck original = (TestUnmarkedCheck) getFirstTreeWalkerCheck(checker);
        final TestUnmarkedCheck clone = (TestUnmarkedCheck) CheckCloneService.cloneCheck(original);

        assertEquals("There must be exactly one log message", 1, handler.getLogs().size());
        final LogRecord logEntry = handler.getLogs().get(0);
        final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE,
                "CheckCloneService.moduleMustImplementAtLeastOneMtInterface",
                new String[] {"TestUnmarkedCheck"}, null, getClass(), null);

        assertEquals("The check clone service must log a warning for a check without a marker",
                localizedMessage.getMessage(),
                logEntry.getMessage());
        assertNotSame("The clone check must not be same as the original check", original, clone);
        assertSame("The clone and original check must have same class",
                original.getClass(), clone.getClass());
        assertEquals("The clone must have a valid strProperty value",
                "just a test", clone.getStrProperty());
        assertEquals("The clone must have a valid intProperty value",
                42, clone.getIntProperty());
    }

    @Test
    public void testExceptionWhileCloningCheck() throws Exception {
        final DefaultConfiguration fileSetConfiguration =
                createModuleConfig(TestFileStatefulCheck.class);
        final Checker checker = createChecker(fileSetConfiguration);
        final TestFileStatefulCheck original =
                (TestFileStatefulCheck) getFirstTreeWalkerCheck(checker);
        final DefaultConfiguration originalConfig =
                (DefaultConfiguration) original.getConfiguration();
        final Field nameField = originalConfig.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(originalConfig, "invalid module name");

        try {
            CheckCloneService.cloneCheck(original);
            fail("An exception is expected");
        }
        catch (IllegalStateException ex) {
            final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE,
                    "CheckCloneService.unableToCloneModule",
                    new String[] {"invalid module name"}, null, getClass(), null);

            assertEquals("The exception must contain a valid message",
                    localizedMessage.getMessage(), ex.getMessage());
            assertTrue("The cause exception must contain the 'invalid module name', "
                            + "because it was mentioned in the config",
                    ex.getCause().getMessage().contains(
                            "'invalid module name'"));
        }
    }

    public static final class TestLogHandler extends Handler {
        private final List<LogRecord> logs = new ArrayList<>();

        @Override
        public void publish(LogRecord logRecord) {
            if (isLoggable(logRecord)) {
                logs.add(logRecord);
            }
        }

        @Override
        public void flush() {
            // nothing to flush
        }

        @Override
        public void close() {
            // nothing to close
        }

        public List<LogRecord> getLogs() {
            return Collections.unmodifiableList(logs);
        }
    }

    @StatelessCheck
    public static final class TestStatelessCheck extends AbstractCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }
    }

    @StatelessCheck
    public static final class TestStatelessFileSetCheck
            extends AbstractFileSetCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // do noting
        }
    }

    @GlobalStatefulCheck
    public static final class TestGlobalStatefulCheck extends AbstractCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }
    }

    @GlobalStatefulCheck
    public static final class TestGlobalStatefulFileSetCheck
            extends AbstractFileSetCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // do noting
        }
    }

    @FileStatefulCheck
    public static final class TestFileStatefulCheck extends AbstractCheck {
        private String strProperty;
        private int intProperty;
        private boolean initCalled;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public void init() {
            initCalled = true;
        }

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }
    }

    @FileStatefulCheck
    public static final class TestFileStatefulFileSetCheck extends AbstractFileSetCheck {
        private String strProperty;
        private int intProperty;
        private boolean initCalled;
        private FileSetCheck initialFileSetCheck;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        public FileSetCheck getInitialFileSetCheck() {
            return initialFileSetCheck;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // do noting
        }

        @Override
        public void init() {
            initCalled = true;
        }

        @Override
        public void finishCloning(FileSetCheck originalFileSetCheck) {
            super.finishCloning(originalFileSetCheck);
            initialFileSetCheck = originalFileSetCheck;
        }
    }

    public static final class TestUnmarkedCheck extends AbstractCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }
    }

    public static final class TestUnmarkedFileSetCheck extends AbstractFileSetCheck {
        private String strProperty;
        private int intProperty;

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // do noting
        }
    }
}
