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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.header.AbstractHeaderCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertWithMessage;

public class XdocsPropertyTypeTest {

    @Test
    public void testAllPropertyTypesAreUsed() throws IOException {
        final Set<PropertyType> propertyTypes = Stream.concat(
                Stream.of(AbstractHeaderCheck.class, Checker.class),
                CheckUtil.getCheckstyleChecks().stream())
            .map(Class::getDeclaredFields)
            .flatMap(Arrays::stream)
            .map(field -> field.getAnnotation(XdocsPropertyType.class))
            .filter(Objects::nonNull)
            .map(XdocsPropertyType::value)
            .collect(Collectors.toUnmodifiableSet());

        assertWithMessage("All property types should be used")
            .that(propertyTypes)
            .containsExactlyElementsIn(PropertyType.values());
    }

    @Test
    public void testAllPropertyTypesHaveDescription() {
        for (PropertyType value : PropertyType.values()) {
            assertWithMessage("Property type '%s' has no description", value)
                .that(StringUtils.isBlank(value.getDescription()))
                .isFalse();
        }
    }

}
