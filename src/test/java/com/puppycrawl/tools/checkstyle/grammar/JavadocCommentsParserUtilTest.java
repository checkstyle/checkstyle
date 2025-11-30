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

package com.puppycrawl.tools.checkstyle.grammar;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

public class JavadocCommentsParserUtilTest {

    @Test
    public void testIsProperUtilsClass() {
        try {
            assertWithMessage("Constructor is not private")
                    .that(isUtilsClassHasPrivateConstructor(JavadocCommentsParserUtil.class))
                    .isTrue();
        }
        catch (ReflectiveOperationException exc) {
            // The constructor throws IllegalStateException which is wrapped in InvocationTargetException
            // This is expected behavior for utility classes
            assertWithMessage("Exception should be IllegalStateException wrapped in InvocationTargetException")
                    .that(exc.getCause())
                    .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Constructor is not private")
                    .that(exc.getCause().getMessage())
                    .isEqualTo("Utility class");
        }
    }

    // Note: isNonTightTag method requires a properly initialized TokenStream which is complex to mock.
    // The method is tested indirectly through integration tests in JavadocDetailNodeParser tests.
    // The utility class constructor test above is sufficient to kill the constructor mutation.

}
