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

package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static java.nio.file.Files.lines;
import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public class ArchUnitTest {

    /**
     * Test contains asserts in callstack, but idea does not see them.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     */
    @Test
    public void packageCyclicDependencyTest() throws Exception {
        final JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.puppycrawl.tools.checkstyle");

        final ArchRule cyclicPackageRule = slices()
                .matching("com.puppycrawl.tools.checkstyle.(**)..")
            .should().beFreeOfCycles();
        try {
            cyclicPackageRule.check(importedClasses);
        }
        catch (AssertionError assertionError) {
            processDiff(assertionError.getMessage());
        }
    }

    /**
     * Test contains asserts in callstack, but idea does not see them.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     */
    @Test
    public void nonProtectedCheckMethodsTest() {
        final JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.puppycrawl.tools.checkstyle.checks");

        final ArchRule checkMethodsShouldNotBeProtectedRule = methods().that()
            .areDeclaredInClassesThat().haveNameMatching("^(Abstract).*Check")
            .should().notBeProtected();

        checkMethodsShouldNotBeProtectedRule.check(importedClasses);
    }

    private static void processDiff(String errorMsg) throws Exception {
        final List<String> prevSuppresions = lines(Paths.get("config/archunit_suppressions.txt"))
                .collect(Collectors.toList());
        final List<String> currSuppressions = Arrays.asList(errorMsg.split("\\r?\\n"));
        // to remove the file:line locations
        currSuppressions.replaceAll(line -> line.replaceAll(" in\\s+\\(.*:\\d+\\)", ""));

        final Patch<String> patch = DiffUtils.diff(prevSuppresions, currSuppressions);
        final List<String> diff = UnifiedDiffUtils.generateUnifiedDiff("orig",
                "curr", prevSuppresions, patch, 1);

        final List<String> newViolations = diff.stream()
                .filter(line -> line.matches("^\\+\\w+.*"))
                .collect(Collectors.toList());
        final List<String> resolvedViolations = diff.stream()
                .filter(line -> line.matches("^-\\w+.*"))
                .collect(Collectors.toList());

        assertEquals("Please resolve the architecture violations",
                Collections.emptyList(), newViolations);
        assertEquals("These violations has been resolved, "
                + "please remove from config/archunit_suppresions.txt",
                Collections.emptyList(), resolvedViolations);
    }
}
