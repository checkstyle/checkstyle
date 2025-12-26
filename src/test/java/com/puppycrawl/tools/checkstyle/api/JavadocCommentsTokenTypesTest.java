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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

public class JavadocCommentsTokenTypesTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(JavadocCommentsTokenTypes.class))
                .isTrue();
    }

    @Test
    public void testTokenValues() {
        final String msg = "Please ensure that token values in `JavadocTokenTypes.java` have not"
                + " changed.";

        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.JAVADOC_CONTENT)
                .isEqualTo(1);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LEADING_ASTERISK)
                .isEqualTo(2);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.NEWLINE)
                .isEqualTo(3);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TEXT)
                .isEqualTo(4);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG)
                .isEqualTo(6);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_START)
                .isEqualTo(7);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_END)
                .isEqualTo(8);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.IDENTIFIER)
                .isEqualTo(11);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HASH)
                .isEqualTo(12);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LPAREN)
                .isEqualTo(13);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.RPAREN)
                .isEqualTo(14);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.COMMA)
                .isEqualTo(15);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_OPEN)
                .isEqualTo(21);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_CLOSE)
                .isEqualTo(22);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_SLASH_CLOSE)
                .isEqualTo(23);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_SLASH)
                .isEqualTo(24);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.EQUALS)
                .isEqualTo(25);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_NAME)
                .isEqualTo(26);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.ATTRIBUTE_VALUE)
                .isEqualTo(27);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SLASH)
                .isEqualTo(28);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.PARAMETER_TYPE)
                .isEqualTo(29);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LT)
                .isEqualTo(30);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.GT)
                .isEqualTo(31);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.EXTENDS)
                .isEqualTo(32);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SUPER)
                .isEqualTo(33);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.QUESTION)
                .isEqualTo(34);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.FORMAT_SPECIFIER)
                .isEqualTo(36);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.INDEX_TERM)
                .isEqualTo(41);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SNIPPET_ATTR_NAME)
                .isEqualTo(43);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.COLON)
                .isEqualTo(44);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.PARAMETER_NAME)
                .isEqualTo(47);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.STRING_LITERAL)
                .isEqualTo(51);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.FIELD_TYPE)
                .isEqualTo(56);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.AT_SIGN)
                .isEqualTo(57);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.REFERENCE)
                .isEqualTo(59);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.MEMBER_REFERENCE)
                .isEqualTo(60);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.PARAMETER_TYPE_LIST)
                .isEqualTo(61);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TYPE_ARGUMENTS)
                .isEqualTo(62);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TYPE_ARGUMENT)
                .isEqualTo(63);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.DESCRIPTION)
                .isEqualTo(64);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SNIPPET_ATTRIBUTES)
                .isEqualTo(65);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SNIPPET_ATTRIBUTE)
                .isEqualTo(66);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SNIPPET_BODY)
                .isEqualTo(67);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_ELEMENT)
                .isEqualTo(68);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.VOID_ELEMENT)
                .isEqualTo(69);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_CONTENT)
                .isEqualTo(70);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_TAG_START)
                .isEqualTo(71);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_TAG_END)
                .isEqualTo(72);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_ATTRIBUTES)
                .isEqualTo(73);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_ATTRIBUTE)
                .isEqualTo(74);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG)
                .isEqualTo(75);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.CODE_INLINE_TAG)
                .isEqualTo(76);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LINK_INLINE_TAG)
                .isEqualTo(77);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG)
                .isEqualTo(78);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.VALUE_INLINE_TAG)
                .isEqualTo(79);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG)
                .isEqualTo(80);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SUMMARY_INLINE_TAG)
                .isEqualTo(81);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SYSTEM_PROPERTY_INLINE_TAG)
                .isEqualTo(82);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.INDEX_INLINE_TAG)
                .isEqualTo(83);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.RETURN_INLINE_TAG)
                .isEqualTo(84);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.LITERAL_INLINE_TAG)
                .isEqualTo(85);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SNIPPET_INLINE_TAG)
                .isEqualTo(86);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.CUSTOM_INLINE_TAG)
                .isEqualTo(87);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG)
                .isEqualTo(88);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG)
                .isEqualTo(89);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.RETURN_BLOCK_TAG)
                .isEqualTo(90);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.PARAM_BLOCK_TAG)
                .isEqualTo(91);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG)
                .isEqualTo(92);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.THROWS_BLOCK_TAG)
                .isEqualTo(93);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SINCE_BLOCK_TAG)
                .isEqualTo(94);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.VERSION_BLOCK_TAG)
                .isEqualTo(95);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SEE_BLOCK_TAG)
                .isEqualTo(96);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HIDDEN_BLOCK_TAG)
                .isEqualTo(97);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.USES_BLOCK_TAG)
                .isEqualTo(98);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG)
                .isEqualTo(99);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SERIAL_BLOCK_TAG)
                .isEqualTo(100);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG)
                .isEqualTo(101);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG)
                .isEqualTo(102);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG)
                .isEqualTo(103);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_COMMENT_START)
                .isEqualTo(104);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_COMMENT_END)
                .isEqualTo(105);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_COMMENT)
                .isEqualTo(106);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.HTML_COMMENT_CONTENT)
                .isEqualTo(107);
        assertWithMessage(msg)
                .that(JavadocCommentsTokenTypes.TAG_ATTR_NAME)
                .isEqualTo(114);
    }

}
