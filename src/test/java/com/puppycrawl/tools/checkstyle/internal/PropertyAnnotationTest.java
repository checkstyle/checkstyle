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

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.ModuleProperty;
import com.puppycrawl.tools.checkstyle.meta.ModuleDetails;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.meta.XmlMetaReader;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public class PropertyAnnotationTest {

    private static final Map<String, ModuleDetails> MODULE_DETAILS_MAP =
        XmlMetaReader.readAllModulesIncludingThirdPartyIfAny();

    private static final DescribedPredicate<JavaField> MODULE_PROPERTIES = moduleProperties();

    private static final JavaClasses IMPORTED_CLASSES = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.puppycrawl.tools.checkstyle.filters",
                        "com.puppycrawl.tools.checkstyle.filefilters",
                        "com.puppycrawl.tools.checkstyle.checks");

    private static DescribedPredicate<JavaField> moduleProperties() {
        return new DescribedPredicate<>(
            "module properties") {
            @Override
            public boolean apply(JavaField input) {
                boolean result = false;
                final JavaClass containingClass = input.getOwner();
                final ModuleDetails moduleDetails = MODULE_DETAILS_MAP.get(
                    containingClass.getFullName());
                if (moduleDetails != null) {
                    final List<ModulePropertyDetails> properties = moduleDetails.getProperties();
                    result = properties.stream()
                        .map(ModulePropertyDetails::getName)
                        .anyMatch(moduleName -> moduleName.equals(input.getName()));
                }
                return result;
            }
        };
    }

    /**
     * Test that fields that are module properties should be annotated with
     * {@link ModuleProperty}.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void modulePropertiesAnnotatedTest() {
        final ArchRule modulePropertiesShouldBeAnnotated = fields()
            .that(are(MODULE_PROPERTIES))
            .should()
            .beAnnotatedWith(ModuleProperty.class);

        modulePropertiesShouldBeAnnotated.check(IMPORTED_CLASSES);
    }

    /**
     * Test that fields that are not module properties should not be annotated with
     * {@link ModuleProperty}.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     * @noinspectionreason JUnitTestMethodWithNoAssertions - asserts in callstack,
     *     but not in this method
     */
    @Test
    public void nonModulePropertiesAreNotAnnotatedTest() {
        final ArchRule nonModulePropertiesShouldNotBeAnnotated = fields()
            .that(are(not(MODULE_PROPERTIES)))
            .should()
            .notBeAnnotatedWith(ModuleProperty.class);

        nonModulePropertiesShouldNotBeAnnotated.check(IMPORTED_CLASSES);
    }
}
