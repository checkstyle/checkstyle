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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

/**
 * Unit test for HandlerFactory.
 */
public class HandlerFactoryTest {

    @Test
    public void testRegisterHandlersInConstructor() {
        final List<Integer> expectedTokenTypes = List.of(
                TokenTypes.CASE_GROUP, TokenTypes.LITERAL_SWITCH, TokenTypes.SLIST,
                TokenTypes.PACKAGE_DEF, TokenTypes.LITERAL_ELSE, TokenTypes.LITERAL_IF,
                TokenTypes.LITERAL_TRY, TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_FINALLY,
                TokenTypes.LITERAL_DO, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR,
                TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF, TokenTypes.CLASS_DEF,
                TokenTypes.ENUM_DEF, TokenTypes.OBJBLOCK, TokenTypes.INTERFACE_DEF,
                TokenTypes.IMPORT, TokenTypes.ARRAY_INIT, TokenTypes.ANNOTATION_ARRAY_INIT,
                TokenTypes.METHOD_CALL, TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL,
                TokenTypes.LABELED_STAT, TokenTypes.STATIC_INIT, TokenTypes.INSTANCE_INIT,
                TokenTypes.VARIABLE_DEF, TokenTypes.LITERAL_NEW, TokenTypes.INDEX_OP,
                TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LAMBDA, TokenTypes.ANNOTATION_DEF,
                TokenTypes.ANNOTATION_FIELD_DEF, TokenTypes.SWITCH_RULE, TokenTypes.LITERAL_YIELD,
                TokenTypes.RECORD_DEF, TokenTypes.COMPACT_CTOR_DEF
        );

        final HandlerFactory handlerFactory = new HandlerFactory();
        final int[] actualTokenTypes = handlerFactory.getHandledTypes();

        assertWithMessage("Actual and expected token types differ.")
                .that(actualTokenTypes).asList()
                .containsExactlyElementsIn(expectedTokenTypes);
    }

    @Test
    public void testClearCreatedHandlers() {
        final HandlerFactory handlerFactory = new HandlerFactory();

        final Map<?, ?> createdHandlers =
                TestUtil.getInternalState(handlerFactory, "createdHandlers", Map.class);
        handlerFactory.clearCreatedHandlers();

        assertWithMessage("Created handlers must be empty.")
                .that(createdHandlers)
                .isEmpty();
    }
}
