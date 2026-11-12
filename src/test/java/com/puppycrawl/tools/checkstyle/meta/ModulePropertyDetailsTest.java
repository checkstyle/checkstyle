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

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class ModulePropertyDetailsTest {

    @Test
    public void testDefaultConstructor() {
        final ModulePropertyDetails details = new ModulePropertyDetails();
        details.setName("myProperty");
        details.setType("int");
        details.setDefaultValue("10");
        details.setValidationType("integer");
        details.setDescription("Property description");

        assertWithMessage("Invalid property name")
                .that(details.getName())
                .isEqualTo("myProperty");
        assertWithMessage("Invalid property type")
                .that(details.getType())
                .isEqualTo("int");
        assertWithMessage("Invalid default value")
                .that(details.getDefaultValue())
                .isEqualTo("10");
        assertWithMessage("Invalid validation type")
                .that(details.getValidationType())
                .isEqualTo("integer");
        assertWithMessage("Invalid description")
                .that(details.getDescription())
                .isEqualTo("Property description");
    }

    @Test
    public void testAllArgumentsConstructor() {
        final ModulePropertyDetails details = new ModulePropertyDetails(
                "propName", "String", "defaultVal", "tokenTypesSet", "Some description");

        assertWithMessage("Invalid property name")
                .that(details.getName())
                .isEqualTo("propName");
        assertWithMessage("Invalid property type")
                .that(details.getType())
                .isEqualTo("String");
        assertWithMessage("Invalid default value")
                .that(details.getDefaultValue())
                .isEqualTo("defaultVal");
        assertWithMessage("Invalid validation type")
                .that(details.getValidationType())
                .isEqualTo("tokenTypesSet");
        assertWithMessage("Invalid description")
                .that(details.getDescription())
                .isEqualTo("Some description");
    }

}

