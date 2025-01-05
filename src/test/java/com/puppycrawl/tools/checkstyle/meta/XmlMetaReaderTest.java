///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

public class XmlMetaReaderTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/meta/xmlmetareader";
    }

    @Test
    public void test() {
        assertThat(XmlMetaReader.readAllModulesIncludingThirdPartyIfAny()).hasSize(207);
    }

    @Test
    public void testDuplicatePackage() {
        assertThat(XmlMetaReader
                    .readAllModulesIncludingThirdPartyIfAny("com.puppycrawl.tools.checkstyle.meta"))
                .hasSize(207);
    }

    @Test
    public void testBadPackage() {
        assertThat(XmlMetaReader.readAllModulesIncludingThirdPartyIfAny("DOES.NOT.EXIST"))
                .hasSize(207);
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
            assertThat(violationMessageKeys.get(0)).isEqualTo("test.key");
            final List<ModulePropertyDetails> props = result.getProperties();
            assertThat(props).hasSize(2);
            final ModulePropertyDetails prop1 = props.get(0);
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
            assertThat(violationMessageKeys.get(0)).isEqualTo("test.key1");
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
            final ModulePropertyDetails prop1 = props.get(0);
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
            final ModulePropertyDetails prop1 = props.get(0);
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
        try (InputStream is = IOUtils.toInputStream("", "UTF-8")) {
            assertThat(XmlMetaReader.read(is, null)).isNull();
        }
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
