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

class IntMatchFilterElementTest {

    @Test
    void decide() {
        final IntFilterElement filter = new IntMatchFilterElement(0);
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
    void equalsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(IntMatchFilterElement.class)
                .report();
        assertWithMessage("Error: %s", ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    void testToString() {
        final IntFilterElement filter = new IntMatchFilterElement(6);
        assertWithMessage("Invalid toString result")
                .that(filter.toString())
                .isEqualTo("IntMatchFilterElement{" + "matchValue=" + 6 + '}');
    }

}
