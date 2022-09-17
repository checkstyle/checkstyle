///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.freeze.FreezingArchRule;

public class ArchUnitCyclesCheckTest {

    /**
     * Tests that there are no
     * <a href="https://www.archunit.org/userguide/html/000_Index.html#_cycle_checks">cycles</a>
     * in the package dependencies. Currently the violations from this test are frozen using
     * <a href="https://www.archunit.org/userguide/html/000_Index.html#_freezing_arch_rules">
     * FreezingArchRule</a>.
     *
     * <p>Whenever a new violation is introduced, the test will pass successfully but the frozen
     * violations will be updated. In that case developers should check if the new violation is
     * expected and if so, state the reason for the new violation in the PR.
     *
     * <p>The frozen violations are present in {@code config/archunit-store} directory.
     */
    @Test
    public void testSlicesShouldBeFreeOfCycle() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle");

        final ArchRule slicesShouldBeFreeOfCycle = FreezingArchRule.freeze(
            slices()
            .matching("com.puppycrawl.tools.checkstyle.(**)")
            .should().beFreeOfCycles());

        slicesShouldBeFreeOfCycle.check(importedClasses);
    }
}
