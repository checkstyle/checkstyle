///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package org.checkstyle.modulepath;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies module-info.java end to end: Checkstyle is started as a named module from the
 * JVM module path, without any {@code --add-modules} or {@code --add-reads} options,
 * exercising module resolution, module instantiation by short and by fully qualified name,
 * property setting through beanutils, XPath suppression (Saxon), localized message bundles
 * and SARIF rule metadata (org.reflections and slf4j). These paths are invisible to all
 * other tests, which run Checkstyle on the class path where the descriptor is ignored.
 */
public class ModulePathTest {

    /** System property holding the path of the built Checkstyle jar. */
    private static final String JAR_PROPERTY = "checkstyle.modulepath.jar";

    /** System property holding the directory with the runtime dependency jars. */
    private static final String DEPS_PROPERTY = "checkstyle.modulepath.deps";

    /** Temporary folder for the config and input files of each run. */
    @TempDir
    private Path temporaryFolder;

    /** Input file with one reported and two suppressed magic numbers. */
    private Path inputFile;

    /** Configuration exercising short name, fully qualified name and a property. */
    private Path configFile;

    @BeforeEach
    public void prepareFiles() throws IOException {
        inputFile = temporaryFolder.resolve("Input.java");
        Files.writeString(inputFile,
                """
                public class Input {
                    private int x = 12345;
                    public void foo() {
                        int y = 42;
                    }
                    public void bar() {
                        int z = 99;
                    }
                }
                """);
        final Path suppressionsFile = temporaryFolder.resolve("suppressions.xml");
        Files.writeString(suppressionsFile,
                """
                <?xml version="1.0"?>
                <!DOCTYPE suppressions PUBLIC "-//Checkstyle//DTD SuppressionXpathFilter \
                Experimental Configuration 1.2//EN" \
                "https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd">
                <suppressions>
                  <suppress-xpath checks="MagicNumber"
                    query="//METHOD_DEF[./IDENT[@text='bar']]//NUM_INT"/>
                </suppressions>
                """);
        configFile = temporaryFolder.resolve("config.xml");
        Files.writeString(configFile,
                """
                <?xml version="1.0"?>
                <!DOCTYPE module PUBLIC "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN" \
                "https://checkstyle.org/dtds/configuration_1_3.dtd">
                <module name="Checker">
                  <module name="TreeWalker">
                    <module name="SuppressionXpathFilter">
                      <property name="file" value="%s"/>
                    </module>
                    <module name="MagicNumber">
                      <property name="ignoreNumbers" value="42"/>
                    </module>
                    <module name="com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck">
                      <property name="format" value="^[a-z][a-zA-Z0-9]{2,}$"/>
                    </module>
                  </module>
                </module>
                """.formatted(suppressionsFile));
    }

    @Test
    public void testViolationsFromModulePath() throws Exception {
        final String output = runFromModulePath("-Duser.language=de", "-Duser.country=DE");

        assertWithMessage("Output should contain the localized violation for 12345")
                .that(output)
                .contains("Die magische Zahl '12345'");
        assertWithMessage("42 should be ignored via the beanutils-set property"
                        + " and 99 suppressed via xpath, output: " + output)
                .that(output.split("\\[ERROR]", -1).length - 1)
                .isEqualTo(2);
    }

    @Test
    public void testSarifMetadataFromModulePath() throws Exception {
        final Path sarifFile = temporaryFolder.resolve("output.sarif");
        runFromModulePath("--", "-f", "sarif", "-o", sarifFile.toString());

        assertWithMessage("SARIF output should contain rule metadata loaded via XmlMetaReader")
                .that(Files.readString(sarifFile))
                .contains("com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck");
    }

    /**
     * Starts Checkstyle as a named module in a forked JVM and returns its combined output.
     *
     * @param options JVM options, optionally followed by {@code "--"} and extra CLI arguments.
     * @return the combined stdout and stderr of the forked JVM.
     * @throws Exception if the JVM cannot be started or times out.
     */
    private String runFromModulePath(String... options) throws Exception {
        final String jar = System.getProperty(JAR_PROPERTY);
        final String deps = System.getProperty(DEPS_PROPERTY);
        final List<String> command = new ArrayList<>();
        command.add(Path.of(System.getProperty("java.home"), "bin", "java").toString());
        final List<String> cliArguments = new ArrayList<>();
        boolean afterSeparator = false;
        for (String option : options) {
            if ("--".equals(option)) {
                afterSeparator = true;
            }
            else if (afterSeparator) {
                cliArguments.add(option);
            }
            else {
                command.add(option);
            }
        }
        command.add("--module-path");
        command.add(jar + File.pathSeparator + deps);
        command.add("--module");
        command.add("com.puppycrawl.tools.checkstyle/com.puppycrawl.tools.checkstyle.Main");
        command.add("-c");
        command.add(configFile.toString());
        command.addAll(cliArguments);
        command.add(inputFile.toString());

        final Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();
        final String output = new String(process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8);
        final boolean finished = process.waitFor(2, TimeUnit.MINUTES);
        assertWithMessage("Forked Checkstyle JVM did not finish, output: " + output)
                .that(finished)
                .isTrue();
        return output;
    }

}
