package com.puppycrawl.tools.checkstyle.internal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.CheckProperty;
import com.puppycrawl.tools.checkstyle.FileFilterProperty;
import com.puppycrawl.tools.checkstyle.FilterProperty;
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

    @Test
    public void filterPropertyAnnotatedTest() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.filters");

        final ArchRule checkPropertiesInFiltersShouldBeAnnotated = fields()
            .that(areProperties("filter"))
            .should()
            .beAnnotatedWith(FilterProperty.class);

        checkPropertiesInFiltersShouldBeAnnotated.check(importedClasses);
    }

    @Test
    public void fileFilterPropertyAnnotatedTest() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.filefilters");

        final ArchRule checkPropertiesInFileFiltersShouldBeAnnotated = fields()
            .that(areProperties("file filter"))
            .should()
            .beAnnotatedWith(FileFilterProperty.class);

        checkPropertiesInFileFiltersShouldBeAnnotated.check(importedClasses);
    }

    @Test
    public void checkPropertyAnnotatedTest() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.puppycrawl.tools.checkstyle.checks");

        final ArchRule checkPropertiesInFileFiltersShouldBeAnnotated = fields()
            .that(areProperties("check"))
            .should()
            .beAnnotatedWith(CheckProperty.class);

        checkPropertiesInFileFiltersShouldBeAnnotated.check(importedClasses);
    }

    private static DescribedPredicate<JavaField> areProperties(String type) {
        return new DescribedPredicate<JavaField>(
            "are " + type + " properties") {
            @Override
            public boolean apply(JavaField input) {
                boolean result = false;
                final JavaClass containingClass = input.getOwner();
                final ModuleDetails moduleDetails = MODULE_DETAILS_MAP.get(
                    containingClass.getFullName());
                if (moduleDetails != null) {
                    final List<ModulePropertyDetails> properties = moduleDetails.getProperties();
                    result = properties.stream()
                        .map(modulePropertyDetails -> modulePropertyDetails.getName())
                        .anyMatch(moduleName -> moduleName.equals(input.getName()));
                }
                return result;
            }
        };
    }
}
