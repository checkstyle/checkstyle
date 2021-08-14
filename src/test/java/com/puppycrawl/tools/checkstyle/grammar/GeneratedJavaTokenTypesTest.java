////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.antlr.v4.runtime.Recognizer;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;

/**
 * GeneratedJavaTokenTypesTest.
 *
 * @noinspection ClassIndependentOfModule
 */
public class GeneratedJavaTokenTypesTest {

    /**
     * <p>
     * New tokens must be added onto the end of the list with new numbers, and
     * old tokens must remain and keep their current numbering. Old token
     * numberings are not allowed to change.
     * </p>
     *
     * <p>
     * The reason behind this is Java inlines static final field values directly
     * into the compiled Java code. This loses all connections with the original
     * class, JavaLanguageLexer, and so numbering updates are not picked
     * up in user-created checks and causes conflicts.
     * </p>
     *
     * <p>
     * Issue: https://github.com/checkstyle/checkstyle/issues/505
     * </p>
     */
    @Test
    public void testTokenNumbering() {
        final String message = "A token's number has changed. Please open"
                + " 'GeneratedJavaTokenTypesTest' and confirm which token is at fault.\n"
                + "Token numbers must not change or else they will create a conflict"
                + " with users.\n\n"
                + "See Issue: https://github.com/checkstyle/checkstyle/issues/505";

        // Read JavaDoc before changing
        assertEquals(-1, Recognizer.EOF, message);
        assertEquals(3, JavaLanguageLexer.NULL_TREE_LOOKAHEAD, message);
        assertEquals(4, JavaLanguageLexer.BLOCK, message);
        assertEquals(5, JavaLanguageLexer.MODIFIERS, message);
        assertEquals(6, JavaLanguageLexer.OBJBLOCK, message);
        assertEquals(7, JavaLanguageLexer.SLIST, message);
        assertEquals(8, JavaLanguageLexer.CTOR_DEF, message);
        assertEquals(9, JavaLanguageLexer.METHOD_DEF, message);
        assertEquals(10, JavaLanguageLexer.VARIABLE_DEF, message);
        assertEquals(11, JavaLanguageLexer.INSTANCE_INIT, message);
        assertEquals(12, JavaLanguageLexer.STATIC_INIT, message);
        assertEquals(13, JavaLanguageLexer.TYPE, message);
        assertEquals(14, JavaLanguageLexer.CLASS_DEF, message);
        assertEquals(15, JavaLanguageLexer.INTERFACE_DEF, message);
        assertEquals(16, JavaLanguageLexer.PACKAGE_DEF, message);
        assertEquals(17, JavaLanguageLexer.ARRAY_DECLARATOR, message);
        assertEquals(18, JavaLanguageLexer.EXTENDS_CLAUSE, message);
        assertEquals(19, JavaLanguageLexer.IMPLEMENTS_CLAUSE, message);
        assertEquals(20, JavaLanguageLexer.PARAMETERS, message);
        assertEquals(21, JavaLanguageLexer.PARAMETER_DEF, message);
        assertEquals(22, JavaLanguageLexer.LABELED_STAT, message);
        assertEquals(23, JavaLanguageLexer.TYPECAST, message);
        assertEquals(24, JavaLanguageLexer.INDEX_OP, message);
        assertEquals(25, JavaLanguageLexer.POST_INC, message);
        assertEquals(26, JavaLanguageLexer.POST_DEC, message);
        assertEquals(27, JavaLanguageLexer.METHOD_CALL, message);
        assertEquals(28, JavaLanguageLexer.EXPR, message);
        assertEquals(29, JavaLanguageLexer.ARRAY_INIT, message);
        assertEquals(30, JavaLanguageLexer.IMPORT, message);
        assertEquals(31, JavaLanguageLexer.UNARY_MINUS, message);
        assertEquals(32, JavaLanguageLexer.UNARY_PLUS, message);
        assertEquals(33, JavaLanguageLexer.CASE_GROUP, message);
        assertEquals(34, JavaLanguageLexer.ELIST, message);
        assertEquals(35, JavaLanguageLexer.FOR_INIT, message);
        assertEquals(36, JavaLanguageLexer.FOR_CONDITION, message);
        assertEquals(37, JavaLanguageLexer.FOR_ITERATOR, message);
        assertEquals(38, JavaLanguageLexer.EMPTY_STAT, message);
        assertEquals(39, JavaLanguageLexer.FINAL, message);
        assertEquals(40, JavaLanguageLexer.ABSTRACT, message);
        assertEquals(41, JavaLanguageLexer.STRICTFP, message);
        assertEquals(42, JavaLanguageLexer.SUPER_CTOR_CALL, message);
        assertEquals(43, JavaLanguageLexer.CTOR_CALL, message);
        assertEquals(44, JavaLanguageLexer.LITERAL_PACKAGE, message);
        assertEquals(45, JavaLanguageLexer.SEMI, message);
        assertEquals(46, JavaLanguageLexer.LITERAL_IMPORT, message);
        assertEquals(47, JavaLanguageLexer.LBRACK, message);
        assertEquals(48, JavaLanguageLexer.RBRACK, message);
        assertEquals(49, JavaLanguageLexer.LITERAL_VOID, message);
        assertEquals(50, JavaLanguageLexer.LITERAL_BOOLEAN, message);
        assertEquals(51, JavaLanguageLexer.LITERAL_BYTE, message);
        assertEquals(52, JavaLanguageLexer.LITERAL_CHAR, message);
        assertEquals(53, JavaLanguageLexer.LITERAL_SHORT, message);
        assertEquals(54, JavaLanguageLexer.LITERAL_INT, message);
        assertEquals(55, JavaLanguageLexer.LITERAL_FLOAT, message);
        assertEquals(56, JavaLanguageLexer.LITERAL_LONG, message);
        assertEquals(57, JavaLanguageLexer.LITERAL_DOUBLE, message);
        assertEquals(58, JavaLanguageLexer.IDENT, message);
        assertEquals(59, JavaLanguageLexer.DOT, message);
        assertEquals(60, JavaLanguageLexer.STAR, message);
        assertEquals(61, JavaLanguageLexer.LITERAL_PRIVATE, message);
        assertEquals(62, JavaLanguageLexer.LITERAL_PUBLIC, message);
        assertEquals(63, JavaLanguageLexer.LITERAL_PROTECTED, message);
        assertEquals(64, JavaLanguageLexer.LITERAL_STATIC, message);
        assertEquals(65, JavaLanguageLexer.LITERAL_TRANSIENT, message);
        assertEquals(66, JavaLanguageLexer.LITERAL_NATIVE, message);
        assertEquals(67, JavaLanguageLexer.LITERAL_SYNCHRONIZED, message);
        assertEquals(68, JavaLanguageLexer.LITERAL_VOLATILE, message);
        assertEquals(69, JavaLanguageLexer.LITERAL_CLASS, message);
        assertEquals(70, JavaLanguageLexer.LITERAL_EXTENDS, message);
        assertEquals(71, JavaLanguageLexer.LITERAL_INTERFACE, message);
        assertEquals(72, JavaLanguageLexer.LCURLY, message);
        assertEquals(73, JavaLanguageLexer.RCURLY, message);
        assertEquals(74, JavaLanguageLexer.COMMA, message);
        assertEquals(75, JavaLanguageLexer.LITERAL_IMPLEMENTS, message);
        assertEquals(76, JavaLanguageLexer.LPAREN, message);
        assertEquals(77, JavaLanguageLexer.RPAREN, message);
        assertEquals(78, JavaLanguageLexer.LITERAL_THIS, message);
        assertEquals(79, JavaLanguageLexer.LITERAL_SUPER, message);
        assertEquals(80, JavaLanguageLexer.ASSIGN, message);
        assertEquals(81, JavaLanguageLexer.LITERAL_THROWS, message);
        assertEquals(82, JavaLanguageLexer.COLON, message);
        assertEquals(83, JavaLanguageLexer.LITERAL_IF, message);
        assertEquals(84, JavaLanguageLexer.LITERAL_WHILE, message);
        assertEquals(85, JavaLanguageLexer.LITERAL_DO, message);
        assertEquals(86, JavaLanguageLexer.LITERAL_BREAK, message);
        assertEquals(87, JavaLanguageLexer.LITERAL_CONTINUE, message);
        assertEquals(88, JavaLanguageLexer.LITERAL_RETURN, message);
        assertEquals(89, JavaLanguageLexer.LITERAL_SWITCH, message);
        assertEquals(90, JavaLanguageLexer.LITERAL_THROW, message);
        assertEquals(91, JavaLanguageLexer.LITERAL_FOR, message);
        assertEquals(92, JavaLanguageLexer.LITERAL_ELSE, message);
        assertEquals(93, JavaLanguageLexer.LITERAL_CASE, message);
        assertEquals(94, JavaLanguageLexer.LITERAL_DEFAULT, message);
        assertEquals(95, JavaLanguageLexer.LITERAL_TRY, message);
        assertEquals(96, JavaLanguageLexer.LITERAL_CATCH, message);
        assertEquals(97, JavaLanguageLexer.LITERAL_FINALLY, message);
        assertEquals(98, JavaLanguageLexer.PLUS_ASSIGN, message);
        assertEquals(99, JavaLanguageLexer.MINUS_ASSIGN, message);
        assertEquals(100, JavaLanguageLexer.STAR_ASSIGN, message);
        assertEquals(101, JavaLanguageLexer.DIV_ASSIGN, message);
        assertEquals(102, JavaLanguageLexer.MOD_ASSIGN, message);
        assertEquals(103, JavaLanguageLexer.SR_ASSIGN, message);
        assertEquals(104, JavaLanguageLexer.BSR_ASSIGN, message);
        assertEquals(105, JavaLanguageLexer.SL_ASSIGN, message);
        assertEquals(106, JavaLanguageLexer.BAND_ASSIGN, message);
        assertEquals(107, JavaLanguageLexer.BXOR_ASSIGN, message);
        assertEquals(108, JavaLanguageLexer.BOR_ASSIGN, message);
        assertEquals(109, JavaLanguageLexer.QUESTION, message);
        assertEquals(110, JavaLanguageLexer.LOR, message);
        assertEquals(111, JavaLanguageLexer.LAND, message);
        assertEquals(112, JavaLanguageLexer.BOR, message);
        assertEquals(113, JavaLanguageLexer.BXOR, message);
        assertEquals(114, JavaLanguageLexer.BAND, message);
        assertEquals(115, JavaLanguageLexer.NOT_EQUAL, message);
        assertEquals(116, JavaLanguageLexer.EQUAL, message);
        assertEquals(117, JavaLanguageLexer.LT, message);
        assertEquals(118, JavaLanguageLexer.GT, message);
        assertEquals(119, JavaLanguageLexer.LE, message);
        assertEquals(120, JavaLanguageLexer.GE, message);
        assertEquals(121, JavaLanguageLexer.LITERAL_INSTANCEOF, message);
        assertEquals(122, JavaLanguageLexer.SL, message);
        assertEquals(123, JavaLanguageLexer.SR, message);
        assertEquals(124, JavaLanguageLexer.BSR, message);
        assertEquals(125, JavaLanguageLexer.PLUS, message);
        assertEquals(126, JavaLanguageLexer.MINUS, message);
        assertEquals(127, JavaLanguageLexer.DIV, message);
        assertEquals(128, JavaLanguageLexer.MOD, message);
        assertEquals(129, JavaLanguageLexer.INC, message);
        assertEquals(130, JavaLanguageLexer.DEC, message);
        assertEquals(131, JavaLanguageLexer.BNOT, message);
        assertEquals(132, JavaLanguageLexer.LNOT, message);
        assertEquals(133, JavaLanguageLexer.LITERAL_TRUE, message);
        assertEquals(134, JavaLanguageLexer.LITERAL_FALSE, message);
        assertEquals(135, JavaLanguageLexer.LITERAL_NULL, message);
        assertEquals(136, JavaLanguageLexer.LITERAL_NEW, message);
        assertEquals(137, JavaLanguageLexer.NUM_INT, message);
        assertEquals(138, JavaLanguageLexer.CHAR_LITERAL, message);
        assertEquals(139, JavaLanguageLexer.STRING_LITERAL, message);
        assertEquals(140, JavaLanguageLexer.NUM_FLOAT, message);
        assertEquals(141, JavaLanguageLexer.NUM_LONG, message);
        assertEquals(142, JavaLanguageLexer.NUM_DOUBLE, message);
        assertEquals(143, JavaLanguageLexer.WS, message);
        assertEquals(144, JavaLanguageLexer.SINGLE_LINE_COMMENT, message);
        assertEquals(145, JavaLanguageLexer.BLOCK_COMMENT_BEGIN, message);
        assertEquals(146, JavaLanguageLexer.ESC, message);
        assertEquals(147, JavaLanguageLexer.HEX_DIGIT, message);
        assertEquals(148, JavaLanguageLexer.VOCAB, message);
        assertEquals(149, JavaLanguageLexer.EXPONENT, message);
        assertEquals(150, JavaLanguageLexer.FLOAT_SUFFIX, message);
        assertEquals(151, JavaLanguageLexer.ASSERT, message);
        assertEquals(152, JavaLanguageLexer.STATIC_IMPORT, message);
        assertEquals(153, JavaLanguageLexer.ENUM, message);
        assertEquals(154, JavaLanguageLexer.ENUM_DEF, message);
        assertEquals(155, JavaLanguageLexer.ENUM_CONSTANT_DEF, message);
        assertEquals(156, JavaLanguageLexer.FOR_EACH_CLAUSE, message);
        assertEquals(157, JavaLanguageLexer.ANNOTATION_DEF, message);
        assertEquals(158, JavaLanguageLexer.ANNOTATIONS, message);
        assertEquals(159, JavaLanguageLexer.ANNOTATION, message);
        assertEquals(160, JavaLanguageLexer.ANNOTATION_MEMBER_VALUE_PAIR, message);
        assertEquals(161, JavaLanguageLexer.ANNOTATION_FIELD_DEF, message);
        assertEquals(162, JavaLanguageLexer.ANNOTATION_ARRAY_INIT, message);
        assertEquals(163, JavaLanguageLexer.TYPE_ARGUMENTS, message);
        assertEquals(164, JavaLanguageLexer.TYPE_ARGUMENT, message);
        assertEquals(165, JavaLanguageLexer.TYPE_PARAMETERS, message);
        assertEquals(166, JavaLanguageLexer.TYPE_PARAMETER, message);
        assertEquals(167, JavaLanguageLexer.WILDCARD_TYPE, message);
        assertEquals(168, JavaLanguageLexer.TYPE_UPPER_BOUNDS, message);
        assertEquals(169, JavaLanguageLexer.TYPE_LOWER_BOUNDS, message);
        assertEquals(170, JavaLanguageLexer.AT, message);
        assertEquals(171, JavaLanguageLexer.ELLIPSIS, message);
        assertEquals(172, JavaLanguageLexer.GENERIC_START, message);
        assertEquals(173, JavaLanguageLexer.GENERIC_END, message);
        assertEquals(174, JavaLanguageLexer.TYPE_EXTENSION_AND, message);
        assertEquals(175, JavaLanguageLexer.DO_WHILE, message);
        assertEquals(176, JavaLanguageLexer.RESOURCE_SPECIFICATION, message);
        assertEquals(177, JavaLanguageLexer.RESOURCES, message);
        assertEquals(178, JavaLanguageLexer.RESOURCE, message);
        assertEquals(179, JavaLanguageLexer.DOUBLE_COLON, message);
        assertEquals(180, JavaLanguageLexer.METHOD_REF, message);
        assertEquals(181, JavaLanguageLexer.LAMBDA, message);
        assertEquals(182, JavaLanguageLexer.BLOCK_COMMENT_END, message);
        assertEquals(183, JavaLanguageLexer.COMMENT_CONTENT, message);
        assertEquals(184, JavaLanguageLexer.SINGLE_LINE_COMMENT_CONTENT, message);
        assertEquals(185, JavaLanguageLexer.BLOCK_COMMENT_CONTENT, message);
        assertEquals(186, JavaLanguageLexer.STD_ESC, message);
        assertEquals(187, JavaLanguageLexer.BINARY_DIGIT, message);
        assertEquals(188, JavaLanguageLexer.ID_START, message);
        assertEquals(189, JavaLanguageLexer.ID_PART, message);
        assertEquals(190, JavaLanguageLexer.INT_LITERAL, message);
        assertEquals(191, JavaLanguageLexer.LONG_LITERAL, message);
        assertEquals(192, JavaLanguageLexer.FLOAT_LITERAL, message);
        assertEquals(193, JavaLanguageLexer.DOUBLE_LITERAL, message);
        assertEquals(194, JavaLanguageLexer.HEX_FLOAT_LITERAL, message);
        assertEquals(195, JavaLanguageLexer.HEX_DOUBLE_LITERAL, message);
        assertEquals(196, JavaLanguageLexer.SIGNED_INTEGER, message);
        assertEquals(197, JavaLanguageLexer.BINARY_EXPONENT, message);
        assertEquals(198, JavaLanguageLexer.PATTERN_VARIABLE_DEF, message);
        assertEquals(199, JavaLanguageLexer.RECORD_DEF, message);
        assertEquals(200, JavaLanguageLexer.LITERAL_RECORD, message);
        assertEquals(201, JavaLanguageLexer.RECORD_COMPONENTS, message);
        assertEquals(202, JavaLanguageLexer.RECORD_COMPONENT_DEF, message);
        assertEquals(203, JavaLanguageLexer.COMPACT_CTOR_DEF, message);
        assertEquals(204, JavaLanguageLexer.TEXT_BLOCK_LITERAL_BEGIN, message);
        assertEquals(205, JavaLanguageLexer.TEXT_BLOCK_CONTENT, message);
        assertEquals(206, JavaLanguageLexer.TEXT_BLOCK_LITERAL_END, message);
        assertEquals(207, JavaLanguageLexer.LITERAL_YIELD, message);
        assertEquals(208, JavaLanguageLexer.SWITCH_RULE, message);
        assertEquals(209, JavaLanguageLexer.LITERAL_NON_SEALED, message);
        assertEquals(210, JavaLanguageLexer.LITERAL_SEALED, message);
        assertEquals(211, JavaLanguageLexer.LITERAL_PERMITS, message);
        assertEquals(212, JavaLanguageLexer.PERMITS_CLAUSE, message);

        // Read JavaDoc before changing
        final int tokenCount = (int) Arrays.stream(JavaLanguageLexer.class.getDeclaredFields())
                .filter(GeneratedJavaTokenTypesTest::isPublicStaticFinalInt)
                .count();

        assertEquals(224, tokenCount,
                "all tokens must be added to list in"
                        + " 'GeneratedJavaTokenTypesTest' and verified"
                        + " that their old numbering didn't change");
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
