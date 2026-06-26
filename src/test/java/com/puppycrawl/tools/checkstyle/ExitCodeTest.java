package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ExitCodeTest {

    private int runCheckstyle(String configPath, String filePath) throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new Exception("File not found");
        }

        if (!file.canRead()) {
            throw new Exception("File not readable");
        }

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());

        Configuration config = ConfigurationLoader.loadConfiguration(
                new File(configPath).getAbsolutePath(),
                new PropertiesExpander(System.getProperties()),
                ConfigurationLoader.IgnoredModulesOptions.EXECUTE
        );

        checker.configure(config);

        int errors = checker.process(
                java.util.Collections.singletonList(file)
        );

        checker.destroy();

        return errors;
    }

    private int runCheckstyleMulti(String configPath, File... files) throws Exception {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());

        Configuration config = ConfigurationLoader.loadConfiguration(
                new File(configPath).getAbsolutePath(),
                new PropertiesExpander(System.getProperties()),
                ConfigurationLoader.IgnoredModulesOptions.EXECUTE
        );

        checker.configure(config);

        int errors = checker.process(Arrays.asList(files));

        checker.destroy();

        return errors;
    }

    // ===== TS-01 =====
    @Test
    public void testCleanFile() throws Exception {
        assertEquals(0, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }

    // ===== TS-02 =====
    @Test
    public void testWarnOnly() throws Exception {
        assertEquals(0, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_warn.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/OneViolation.java"));
    }

    // ===== TS-03 =====
    @Test
    public void testMultipleCleanFiles() throws Exception {
        assertEquals(0, runCheckstyleMulti(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"),
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/AnotherCleanFile.java")
        ));
    }

    // ===== TS-04 =====
    @Test
    public void testEmptyFile() throws Exception {
        assertEquals(0, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/EmptyFile.java"));
    }

    // ===== TS-05 =====
    @Test
    public void testNoChecksConfig() throws Exception {
        assertEquals(0, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/empty_config.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/OneViolation.java"));
    }

    // ===== TS-06 =====
    @Test
    public void testDirectoryClean() throws Exception {
        assertEquals(0, runCheckstyleMulti(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"),
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/AnotherCleanFile.java")
        ));
    }

    // ===== TS-07 =====
    @Test
    public void testOneViolation() throws Exception {
        assertEquals(1, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/OneViolation.java"));
    }

    // ===== TS-08 =====
    @Test
    public void testTabViolation() throws Exception {
        assertEquals(1, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/tab.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/TabFile.java"));
    }

    // ===== TS-09 =====
    @Test
    public void testLineLength() throws Exception {
        assertEquals(1, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/line_length.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/LongLineFile.java"));
    }

    // ===== TS-10 =====
    @Test
    public void testJavadoc() throws Exception {
        assertEquals(1, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/javadoc.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/NoJavadocFile.java"));
    }

    // ===== TS-11 =====
    @Test
    public void testMultipleViolations() throws Exception {
        assertTrue(runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/TwoViolation.java") >= 2);
    }

    // ===== TS-12 =====
    @Test
    public void testMultiFileOneBad() throws Exception {
        assertEquals(1, runCheckstyleMulti(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"),
                new File("src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/OneViolation.java")
        ));
    }

    // ===== TS-13 =====
    @Test
    public void testMultipleCleanDuplicate() throws Exception {
        testMultipleCleanFiles();
    }

    // ===== TS-14 =====
    @Test
    public void testOutputFlagAdapted() throws Exception {
        assertEquals(1, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/OneViolation.java"));
    }

    // ===== TS-15 =====
    @Test
    public void testExcludedFileAdapted() throws Exception {
        assertEquals(0, runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }

    // ===== TS-16 =====
    @Test
    public void testTwoViolations() throws Exception {
        assertTrue(runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/TwoViolation.java") >= 2);
    }

    // ===== TS-17 =====
    @Test
    public void testMissingConfig() {
        assertThrows(Exception.class, () -> runCheckstyle("bad.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }

    // ===== TS-18 =====
    @Test
    public void testBadConfigPath() {
        assertThrows(Exception.class, () -> runCheckstyle("fake.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }

    // ===== TS-19 =====
    @Test
    public void testMissingFile() {
        assertThrows(Exception.class, () -> runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "fakeFile.java"));
    }

    // ===== TS-20 =====
    @Test
    public void testUnreadableFile() {
        assertThrows(Exception.class, () -> runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/typename_error.xml",
                "/restricted/File.java"));
    }

    // ===== TS-21 =====
    @Test
    public void testMalformedConfig() {
        assertThrows(Exception.class, () -> runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/bad_config.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }

    // ===== TS-22 =====
    @Test
    public void testUnknownModule() {
        assertThrows(Exception.class, () -> runCheckstyle(
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/bad_module.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/exitCode/CleanFile.java"));
    }
}

