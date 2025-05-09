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

class CsvFilterElementTest {

    @Test
    void decideSingle() {
        final IntFilterElement filter = new CsvFilterElement("0");
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("equal")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(1))
                .isFalse();
    }

    @Test
    void decidePair() {
        final IntFilterElement filter = new CsvFilterElement("0, 2");
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("equal 0")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(1))
                .isFalse();
        assertWithMessage("equal 2")
                .that(filter.accept(2))
                .isTrue();
    }

    @Test
    void decideRange() {
        final IntFilterElement filter = new CsvFilterElement("0-2");
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("equal 0")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("equal 1")
                .that(filter.accept(1))
                .isTrue();
        assertWithMessage("equal 2")
                .that(filter.accept(2))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(3))
                .isFalse();
    }

    @Test
    void decideEmptyRange() {
        final IntFilterElement filter = new CsvFilterElement("2-0");
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("equal 0")
                .that(filter.accept(0))
                .isFalse();
        assertWithMessage("equal 1")
                .that(filter.accept(1))
                .isFalse();
        assertWithMessage("equal 2")
                .that(filter.accept(2))
                .isFalse();
        assertWithMessage("greater than")
                .that(filter.accept(3))
                .isFalse();
    }

    @Test
    void decideRangePlusValue() {
        final IntFilterElement filter = new CsvFilterElement("0-2, 10");
        assertWithMessage("less than")
                .that(filter.accept(-1))
                .isFalse();
        assertWithMessage("equal 0")
                .that(filter.accept(0))
                .isTrue();
        assertWithMessage("equal 1")
                .that(filter.accept(1))
                .isTrue();
        assertWithMessage("equal 2")
                .that(filter.accept(2))
                .isTrue();
        assertWithMessage("greater than")
                .that(filter.accept(3))
                .isFalse();
        assertWithMessage("equal 10")
                .that(filter.accept(10))
                .isTrue();
    }

    @Test
    void emptyChain() {
        final CsvFilterElement filter = new CsvFilterElement("");
        assertWithMessage("0")
                .that(filter.accept(0))
                .isFalse();
    }

    @Test
    void equalsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(CsvFilterElement.class)
                .usingGetClass().report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

}
