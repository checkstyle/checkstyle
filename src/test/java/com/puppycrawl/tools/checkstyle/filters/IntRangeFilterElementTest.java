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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

class IntRangeFilterElementTest {

    @Test
    void decide() {
        final IntFilterElement filter = new IntRangeFilterElement(0, 10);
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("in range")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("in range")
                .that(filter.accept(5))
                .isTrue();
        assertWithMessage("in range")
                .that(filter.accept(10))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(11))
                .isFalse();
    }

    @Test
    void decideSingle() {
        final IntFilterElement filter = new IntRangeFilterElement(0, 0);
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("in range")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(1))
                .isFalse();
    }

    @Test
    void decideEmpty() {
        final IntFilterElement filter = new IntRangeFilterElement(10, 0);
        assertWithMessage("out")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("out")
                .that(filter.accept(0))
                .isFalse();
        assertWithMessage("out")
                .that(filter.accept(5))
                .isFalse();
        assertWithMessage("out")
                .that(filter.accept(10))
                .isFalse();
        assertWithMessage("out")
                .that(filter.accept(11))
                .isFalse();
    }

    @Test
    void equalsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(IntRangeFilterElement.class)
                .usingGetClass().report();
        assertWithMessage("Error: %s", ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    void testToString() {
        final IntFilterElement filter = new IntRangeFilterElement(1, 2);
        assertWithMessage("toString output")
                .that(filter.toString())
                .isEqualTo("IntRangeFilterElement{" + "lowerBound=" + 1
                        + ", upperBound=" + 2 + '}');
    }

}
