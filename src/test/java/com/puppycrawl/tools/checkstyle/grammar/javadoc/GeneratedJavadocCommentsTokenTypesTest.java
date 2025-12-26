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

package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * GeneratedJavadocTokenTypesTest.
 *
 * @noinspection ClassIndependentOfModule
 * @noinspectionreason ClassIndependentOfModule - architecture of test modules
 *      requires this structure
 */
public class GeneratedJavadocCommentsTokenTypesTest {

    private static final List<String> INTERNAL_TOKENS = List.of(
        "WS",
        "CODE",
        "LINK",
        "AUTHOR",
        "DEPRECATED",
        "RETURN",
        "PARAM",
        "VALUE",
        "SUMMARY",
        "SYSTEM_PROPERTY",
        "INDEX",
        "EXCEPTION",
        "THROWS",
        "SINCE",
        "VERSION",
        "SEE",
        "LITERAL_HIDDEN",
        "SERIAL",
        "SERIAL_DATA",
        "SERIAL_FIELD",
        "USES",
        "PROVIDES",
        "LITERAL",
        "CUSTOM_NAME",
        "Snippet_ATTRIBUTE",
        "See_TAG_OPEN",
        "ATTRIBUTE",
        "Link_COMMA"
    );

    private static final String MSG = "Ensure that token numbers generated for the elements"
            + "present in JavadocParser are consistent with what the tests assert.";

    /**
     * This method checks that the numbers generated for tokens in <tt>JavadocLexer.g4</tt> don't
     * change with the lexer grammar itself.
     * <br>ANTLR maps all the lexer elements to compile-time constants used internally by ANTLR.
     * Compatibility damage is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile-time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testTokenNumbers() {
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.JAVADOC)
            .isEqualTo(1);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LEADING_ASTERISK)
            .isEqualTo(2);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.NEWLINE)
            .isEqualTo(3);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TEXT)
            .isEqualTo(4);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.JAVADOC_INLINE_TAG)
            .isEqualTo(6);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.JAVADOC_INLINE_TAG_START)
            .isEqualTo(7);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.JAVADOC_INLINE_TAG_END)
            .isEqualTo(8);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.IDENTIFIER)
            .isEqualTo(11);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HASH)
            .isEqualTo(12);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LPAREN)
            .isEqualTo(13);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.RPAREN)
            .isEqualTo(14);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.COMMA)
            .isEqualTo(15);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_OPEN)
            .isEqualTo(21);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_CLOSE)
            .isEqualTo(22);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_SLASH_CLOSE)
            .isEqualTo(23);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_SLASH)
            .isEqualTo(24);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.EQUALS)
            .isEqualTo(25);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_NAME)
            .isEqualTo(26);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.ATTRIBUTE_VALUE)
            .isEqualTo(27);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SLASH)
            .isEqualTo(28);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.PARAMETER_TYPE)
            .isEqualTo(29);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LT)
            .isEqualTo(30);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.GT)
            .isEqualTo(31);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.EXTENDS)
            .isEqualTo(32);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SUPER)
            .isEqualTo(33);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.QUESTION)
            .isEqualTo(34);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.FORMAT_SPECIFIER)
            .isEqualTo(36);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.INHERIT_DOC)
            .isEqualTo(37);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.INDEX_TERM)
            .isEqualTo(41);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET)
            .isEqualTo(42);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET_ATTR_NAME)
            .isEqualTo(43);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.COLON)
            .isEqualTo(44);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.PARAMETER_NAME)
            .isEqualTo(47);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.STRING_LITERAL)
            .isEqualTo(51);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.FIELD_TYPE)
            .isEqualTo(56);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.AT_SIGN)
            .isEqualTo(57);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.REFERENCE)
            .isEqualTo(59);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.MEMBER_REFERENCE)
            .isEqualTo(60);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.PARAMETER_TYPE_LIST)
            .isEqualTo(61);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TYPE_ARGUMENTS)
            .isEqualTo(62);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TYPE_ARGUMENT)
            .isEqualTo(63);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.DESCRIPTION)
            .isEqualTo(64);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET_ATTRIBUTES)
            .isEqualTo(65);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET_ATTRIBUTE)
            .isEqualTo(66);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET_BODY)
            .isEqualTo(67);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_ELEMENT)
            .isEqualTo(68);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.VOID_ELEMENT)
            .isEqualTo(69);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_CONTENT)
            .isEqualTo(70);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_TAG_START)
            .isEqualTo(71);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_TAG_END)
            .isEqualTo(72);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_ATTRIBUTES)
            .isEqualTo(73);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_ATTRIBUTE)
            .isEqualTo(74);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.JAVADOC_BLOCK_TAG)
            .isEqualTo(75);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.CODE_INLINE_TAG)
            .isEqualTo(76);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LINK_INLINE_TAG)
            .isEqualTo(77);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LINKPLAIN_INLINE_TAG)
            .isEqualTo(78);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.VALUE_INLINE_TAG)
            .isEqualTo(79);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.INHERIT_DOC_INLINE_TAG)
            .isEqualTo(80);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SUMMARY_INLINE_TAG)
            .isEqualTo(81);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SYSTEM_PROPERTY_INLINE_TAG)
            .isEqualTo(82);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.INDEX_INLINE_TAG)
            .isEqualTo(83);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.RETURN_INLINE_TAG)
            .isEqualTo(84);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.LITERAL_INLINE_TAG)
            .isEqualTo(85);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SNIPPET_INLINE_TAG)
            .isEqualTo(86);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.CUSTOM_INLINE_TAG)
            .isEqualTo(87);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.AUTHOR_BLOCK_TAG)
            .isEqualTo(88);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.DEPRECATED_BLOCK_TAG)
            .isEqualTo(89);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.RETURN_BLOCK_TAG)
            .isEqualTo(90);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.PARAM_BLOCK_TAG)
            .isEqualTo(91);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.EXCEPTION_BLOCK_TAG)
            .isEqualTo(92);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.THROWS_BLOCK_TAG)
            .isEqualTo(93);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SINCE_BLOCK_TAG)
            .isEqualTo(94);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.VERSION_BLOCK_TAG)
            .isEqualTo(95);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SEE_BLOCK_TAG)
            .isEqualTo(96);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HIDDEN_BLOCK_TAG)
            .isEqualTo(97);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.USES_BLOCK_TAG)
            .isEqualTo(98);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.PROVIDES_BLOCK_TAG)
            .isEqualTo(99);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SERIAL_BLOCK_TAG)
            .isEqualTo(100);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SERIAL_DATA_BLOCK_TAG)
            .isEqualTo(101);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.SERIAL_FIELD_BLOCK_TAG)
            .isEqualTo(102);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.CUSTOM_BLOCK_TAG)
            .isEqualTo(103);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_COMMENT_START)
            .isEqualTo(104);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_COMMENT_END)
            .isEqualTo(105);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_COMMENT)
            .isEqualTo(106);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.HTML_COMMENT_CONTENT)
            .isEqualTo(107);
        assertWithMessage(MSG)
            .that(JavadocCommentsLexer.TAG_ATTR_NAME)
            .isEqualTo(114);

        final Set<String> modeNames = Set.of(JavadocCommentsLexer.modeNames);
        final Set<String> channelNames = Set.of(JavadocCommentsLexer.channelNames);

        final int tokenCount = (int) Arrays.stream(JavadocCommentsLexer.class.getDeclaredFields())
                .filter(GeneratedJavadocCommentsTokenTypesTest::isPublicStaticFinalInt)
                .filter(field -> !modeNames.contains(field.getName()))
                .filter(field -> !channelNames.contains(field.getName()))
                .filter(field -> !INTERNAL_TOKENS.contains(field.getName()))
                .count();

        // Read JavaDoc before changing count below
        assertWithMessage("all tokens must be added to list in"
                        + " 'GeneratedJavadocTokenTypesTest' and verified"
                        + " that their old numbering didn't change")
            .that(tokenCount)
            .isEqualTo(88);
    }

    /**
     * Checks that a given field is 'public static final int'.
     *
     * @param field field to verify type and visibility of
     * @return true if field is declared as 'public static final int'
     */
    private static boolean isPublicStaticFinalInt(Field field) {
        final Class<?> fieldType = field.getType();
        final int mods = field.getModifiers();
        return fieldType.equals(Integer.TYPE)
                && Modifier.isPublic(mods)
                && Modifier.isStatic(mods)
                && Modifier.isFinal(mods);
    }

}
