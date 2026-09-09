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

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XmlMetaReaderTest extends AbstractPathTestSupport {

    /** Modules that intentionally ship no metadata file. */
    private static final Set<String> MODULES_WITHOUT_METADATA = Set.of(
            "com.puppycrawl.tools.checkstyle.Checker",
            "com.puppycrawl.tools.checkstyle.TreeWalker"
    );

    /** Plain scan result, shared to avoid repeating an expensive classpath scan. */
    private static final List<ModuleDetails> ALL_MODULES =
            XmlMetaReader.readAllModulesIncludingThirdPartyIfAny();

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/meta/xmlmetareader";
    }

    /**
     * Verifies that metadata for every checkstyle module can be located on the classpath
     * and parsed.
     *
     * <p>This is "contains at least", not "contains exactly", because this test's own input
     * files live inside the scanned package and are therefore picked up by the scan. The
     * reverse direction, metadata files with no matching module, is covered by
     * {@code MetadataGeneratorUtilTest}.
     *
     * <p>When running this test directly from an IDE, run {@code ./mvnw process-classes}
     * first to generate the metadata files under {@code target/classes}.
     *
     * @throws Exception if the attempt to read class path resources failed
     */
    @Test
    public void testAllModulesHaveMetadata() throws Exception {
        final Set<String> expected = CheckUtil.getCheckstyleModules().stream()
                .map(Class::getName)
                .filter(name -> !MODULES_WITHOUT_METADATA.contains(name))
                .collect(Collectors.toCollection(TreeSet::new));

        assertWithMessage("Metadata should be readable for every checkstyle module")
                .that(fullNames(ALL_MODULES))
                .containsAtLeastElementsIn(expected);
    }

    @Test
    public void test() {
        assertThat(XmlMetaReader.readAllModulesIncludingThirdPartyIfAny()).hasSize(235);
    }

    @Test
    public void testDuplicatePackage() {
        final List<ModuleDetails> actual = XmlMetaReader
                .readAllModulesIncludingThirdPartyIfAny("com.puppycrawl.tools.checkstyle.meta");

        assertWithMessage("Rescanning a scanned package should not duplicate modules")
                .that(actual)
                .hasSize(ALL_MODULES.size());
        assertWithMessage("Rescanning a scanned package should not change the modules")
                .that(fullNames(actual))
                .containsExactlyElementsIn(fullNames(ALL_MODULES));
        assertThat(XmlMetaReader
                .readAllModulesIncludingThirdPartyIfAny("com.puppycrawl.tools.checkstyle.meta"))
                .hasSize(235);
    }

    @Test
    public void testBadPackage() {
        final List<ModuleDetails> actual =
                XmlMetaReader.readAllModulesIncludingThirdPartyIfAny("DOES.NOT.EXIST");

        assertWithMessage("A nonexistent third party package should not add modules")
                .that(actual)
                .hasSize(ALL_MODULES.size());
        assertWithMessage("A nonexistent third party package should not change the modules")
                .that(fullNames(actual))
                .containsExactlyElementsIn(fullNames(ALL_MODULES));
        assertThat(XmlMetaReader.readAllModulesIncludingThirdPartyIfAny("DOES.NOT.EXIST"))
                .hasSize(235);
    }

    @Test
    public void testReadXmlMetaCheckWithProperties() throws Exception {
        final String path = getPath("InputXmlMetaReaderCheckWithProps.xml");
        try (InputStream is = Files.newInputStream(Path.of(path))) {
            final ModuleDetails result = XmlMetaReader.read(is, ModuleType.CHECK);
            checkModuleProps(result, ModuleType.CHECK, "Some description for check",
                    "com.puppycrawl.tools.checkstyle.checks.misc.InputCheck",
                    "com.puppycrawl.tools.checkstyle.TreeWalker");
            assertThat(result.getName()).isEqualTo("InputCheck");
            final List<String> violationMessageKeys = result.getViolationMessageKeys();
            assertThat(violationMessageKeys).hasSize(1);
            assertThat(violationMessageKeys.getFirst()).isEqualTo("test.key");
            final List<ModulePropertyDetails> props = result.getProperties();
            assertThat(props).hasSize(2);
            final ModulePropertyDetails prop1 = props.getFirst();
            checkProperty(prop1, "propertyOne", "java.lang.String",
                    "propertyOneDefaultValue",
                    "Property wrapped\n            description.");
            assertThat(prop1.getValidationType()).isNull();

            final ModulePropertyDetails prop2 = props.get(1);
            checkProperty(prop2, "propertyTwo", "java.lang.String[]",
                    "", "Property two desc");
            assertThat(prop2.getValidationType()).isEqualTo("tokenTypesSet");
        }
    }

    @Test
    public void testReadXmlMetaCheckNoProperties() throws Exception {
        final String path = getPath("InputXmlMetaReaderCheckNoProps.xml");
        try (InputStream is = Files.newInputStream(Path.of(path))) {
            final ModuleDetails result = XmlMetaReader.read(is, ModuleType.CHECK);
            checkModuleProps(result, ModuleType.CHECK,
                    "Some description for check with no properties",
                    "com.puppycrawl.tools.checkstyle.checks.misc.InputCheckNoProps",
                    "com.puppycrawl.tools.checkstyle.TreeWalker");
            assertThat(result.getName()).isEqualTo("InputCheckNoProps");
            final List<String> violationMessageKeys = result.getViolationMessageKeys();
            assertThat(violationMessageKeys).hasSize(2);
            assertThat(violationMessageKeys.getFirst()).isEqualTo("test.key1");
            assertThat(violationMessageKeys.get(1)).isEqualTo("test.key2");
            assertThat(result.getProperties()).isEmpty();
        }
    }

    @Test
    public void testReadXmlMetaFilter() throws Exception {
        final String path = getPath("InputXmlMetaReaderFilter.xml");
        try (InputStream is = Files.newInputStream(Path.of(path))) {
            final ModuleDetails result = XmlMetaReader.read(is, ModuleType.FILTER);
            checkModuleProps(result, ModuleType.FILTER, "Description for filter",
                    "com.puppycrawl.tools.checkstyle.filters.SomeFilter",
                    "com.puppycrawl.tools.checkstyle.TreeWalker");
            assertThat(result.getName()).isEqualTo("SomeFilter");
            assertThat(result.getViolationMessageKeys()).isEmpty();
            final List<ModulePropertyDetails> props = result.getProperties();
            assertThat(props).hasSize(1);
            final ModulePropertyDetails prop1 = props.getFirst();
            checkProperty(prop1, "propertyOne", "java.util.regex.Pattern",
                    "propertyDefaultValue", "Property description.");
            assertThat(prop1.getValidationType()).isNull();
        }
    }

    @Test
    public void testReadXmlMetaFileFilter() throws Exception {
        final String path = getPath("InputXmlMetaReaderFileFilter.xml");
        try (InputStream is = Files.newInputStream(Path.of(path))) {
            final ModuleDetails result = XmlMetaReader.read(is, ModuleType.FILEFILTER);
            checkModuleProps(result, ModuleType.FILEFILTER,
                    "File filter description",
                    "com.puppycrawl.tools.checkstyle.filefilters.FileFilter",
                    "com.puppycrawl.tools.checkstyle.Checker");
            assertThat(result.getName()).isEqualTo("FileFilter");
            assertThat(result.getViolationMessageKeys()).isEmpty();
            final List<ModulePropertyDetails> props = result.getProperties();
            assertThat(props).hasSize(1);
            final ModulePropertyDetails prop1 = props.getFirst();
            assertThat(prop1.getName()).isEqualTo("fileNamePattern");
            assertThat(prop1.getType()).isEqualTo("java.util.regex.Pattern");
            assertThat(prop1.getDefaultValue()).isNull();
            assertThat(prop1.getValidationType()).isNull();
            assertThat(prop1.getDescription())
                    .isEqualTo("Define regular expression to match the file name against.");
        }
    }

    @Test
    public void testReadXmlMetaModuleTypeNull() throws Exception {
        try (InputStream is = IOUtils.toInputStream("", StandardCharsets.UTF_8)) {
            assertThat(XmlMetaReader.read(is, null)).isNull();
        }
    }

    private static Set<String> fullNames(List<ModuleDetails> modules) {
        return modules.stream()
                .map(ModuleDetails::getFullQualifiedName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static void checkModuleProps(ModuleDetails result, ModuleType moduleType,
                                         String description,
                                         String fullName, String parent) {
        assertThat(result.getModuleType()).isEqualTo(moduleType);
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getFullQualifiedName()).isEqualTo(fullName);
        assertThat(result.getParent()).isEqualTo(parent);
    }

    private static void checkProperty(ModulePropertyDetails prop, String name,
                                      String type, String defaultValue, String description) {
        assertThat(prop.getName()).isEqualTo(name);
        assertThat(prop.getType()).isEqualTo(type);
        assertThat(prop.getDefaultValue()).isEqualTo(defaultValue);
        assertThat(prop.getDescription()).isEqualTo(description);
    }

}
