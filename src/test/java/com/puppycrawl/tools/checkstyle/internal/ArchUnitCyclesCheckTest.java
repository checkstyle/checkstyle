///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
     * This test checks that
     * <a href="https://www.archunit.org/userguide/html/000_Index.html#_slices">slices</a> should
     * be free of
     * <a href="https://www.archunit.org/userguide/html/000_Index.html#_cycle_checks">cycles</a>.
     * In this particular test, each slice is a different package. Currently the violations from
     * this test are frozen using
     * <a href="https://www.archunit.org/userguide/html/000_Index.html#_freezing_arch_rules">
     * FreezingArchRule</a>.
     *
     * <p>Whenever a new cycle is introduced or removed, the test will pass successfully but the
     * frozen violations will be updated. It is highly recommended to avoid new cycles and, it
     * is preferred to remove them. In both cases commit the changes for further discussion.
     *
     * <p>The frozen violations are present in {@code config/archunit-store} directory.
     */
    @Test
    public void testSlicesShouldBeFreeOfCycles() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle");

        final ArchRule slicesShouldBeFreeOfCycles = FreezingArchRule.freeze(
            slices()
            .matching("com.puppycrawl.tools.checkstyle.(**)")
            .should().beFreeOfCycles());

        slicesShouldBeFreeOfCycles.check(importedClasses);
    }
}
