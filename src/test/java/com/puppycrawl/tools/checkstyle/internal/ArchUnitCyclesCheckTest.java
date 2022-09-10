package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.freeze.FreezingArchRule;

public class ArchUnitCyclesCheckTest {

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
