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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ModuleDetailsTest {

    @Test
    public void testDefaultConstructor() {
        final ModuleDetails details = new ModuleDetails();
        details.setName("MyCheck");
        details.setFullQualifiedName("com.example.MyCheck");
        details.setParent("TreeWalker");
        details.setDescription("Module description");
        details.setModuleType(ModuleType.CHECK);

        final ModulePropertyDetails prop1 = new ModulePropertyDetails();
        prop1.setName("prop1");
        details.addToProperties(prop1);

        final ModulePropertyDetails prop2 = new ModulePropertyDetails();
        prop2.setName("prop2");
        details.addToProperties(List.of(prop2));

        details.addToViolationMessages("key1");
        details.addToViolationMessages(List.of("key2", "key3"));

        assertWithMessage("Invalid module name")
                .that(details.getName())
                .isEqualTo("MyCheck");
        assertWithMessage("Invalid fully qualified name")
                .that(details.getFullQualifiedName())
                .isEqualTo("com.example.MyCheck");
        assertWithMessage("Invalid parent")
                .that(details.getParent())
                .isEqualTo("TreeWalker");
        assertWithMessage("Invalid description")
                .that(details.getDescription())
                .isEqualTo("Module description");
        assertWithMessage("Invalid module type")
                .that(details.getModuleType())
                .isEqualTo(ModuleType.CHECK);
        assertWithMessage("Invalid properties list")
                .that(details.getProperties())
                .containsExactly(prop1, prop2)
                .inOrder();
        assertWithMessage("Invalid violation message keys")
                .that(details.getViolationMessageKeys())
                .containsExactly("key1", "key2", "key3")
                .inOrder();
    }

    @Test
    public void testAllArgumentsConstructor() {
        final ModulePropertyDetails prop = new ModulePropertyDetails();
        prop.setName("propA");
        final List<ModulePropertyDetails> properties = List.of(prop);
        final List<String> messageKeys = List.of("msgKey1", "msgKey2");

        final ModuleDetails details = new ModuleDetails(
                "FilterModule",
                "com.example.FilterModule",
                "Checker",
                "Filter description",
                ModuleType.FILTER,
                properties,
                messageKeys
        );

        assertWithMessage("Invalid module name")
                .that(details.getName())
                .isEqualTo("FilterModule");
        assertWithMessage("Invalid fully qualified name")
                .that(details.getFullQualifiedName())
                .isEqualTo("com.example.FilterModule");
        assertWithMessage("Invalid parent")
                .that(details.getParent())
                .isEqualTo("Checker");
        assertWithMessage("Invalid description")
                .that(details.getDescription())
                .isEqualTo("Filter description");
        assertWithMessage("Invalid module type")
                .that(details.getModuleType())
                .isEqualTo(ModuleType.FILTER);
        assertWithMessage("Invalid properties list")
                .that(details.getProperties())
                .containsExactly(prop);
        assertWithMessage("Invalid violation message keys")
                .that(details.getViolationMessageKeys())
                .containsExactly("msgKey1", "msgKey2")
                .inOrder();
    }

    @Test
    public void testUnmodifiableProperties() {
        final ModuleDetails details = new ModuleDetails();
        final List<ModulePropertyDetails> properties = details.getProperties();
        final ModulePropertyDetails prop = new ModulePropertyDetails();

        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    properties.add(prop);
                }, "Expected exception when modifying properties list");
        assertWithMessage("Expected UnsupportedOperationException")
                .that(exc)
                .isNotNull();
    }

    @Test
    public void testUnmodifiableViolationMessageKeys() {
        final ModuleDetails details = new ModuleDetails();
        final List<String> keys = details.getViolationMessageKeys();

        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    keys.add("newKey");
                }, "Expected exception when modifying violation message keys list");
        assertWithMessage("Expected UnsupportedOperationException")
                .that(exc)
                .isNotNull();
    }

}

