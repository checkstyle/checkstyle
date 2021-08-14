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

import com.puppycrawl.tools.checkstyle.grammar.java.CheckstyleJavaLexer;

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
     * class, CheckstyleJavaLexer, and so numbering updates are not picked
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
        assertEquals(3, CheckstyleJavaLexer.NULL_TREE_LOOKAHEAD, message);
        assertEquals(4, CheckstyleJavaLexer.BLOCK, message);
        assertEquals(5, CheckstyleJavaLexer.MODIFIERS, message);
        assertEquals(6, CheckstyleJavaLexer.OBJBLOCK, message);
        assertEquals(7, CheckstyleJavaLexer.SLIST, message);
        assertEquals(8, CheckstyleJavaLexer.CTOR_DEF, message);
        assertEquals(9, CheckstyleJavaLexer.METHOD_DEF, message);
        assertEquals(10, CheckstyleJavaLexer.VARIABLE_DEF, message);
        assertEquals(11, CheckstyleJavaLexer.INSTANCE_INIT, message);
        assertEquals(12, CheckstyleJavaLexer.STATIC_INIT, message);
        assertEquals(13, CheckstyleJavaLexer.TYPE, message);
        assertEquals(14, CheckstyleJavaLexer.CLASS_DEF, message);
        assertEquals(15, CheckstyleJavaLexer.INTERFACE_DEF, message);
        assertEquals(16, CheckstyleJavaLexer.PACKAGE_DEF, message);
        assertEquals(17, CheckstyleJavaLexer.ARRAY_DECLARATOR, message);
        assertEquals(18, CheckstyleJavaLexer.EXTENDS_CLAUSE, message);
        assertEquals(19, CheckstyleJavaLexer.IMPLEMENTS_CLAUSE, message);
        assertEquals(20, CheckstyleJavaLexer.PARAMETERS, message);
        assertEquals(21, CheckstyleJavaLexer.PARAMETER_DEF, message);
        assertEquals(22, CheckstyleJavaLexer.LABELED_STAT, message);
        assertEquals(23, CheckstyleJavaLexer.TYPECAST, message);
        assertEquals(24, CheckstyleJavaLexer.INDEX_OP, message);
        assertEquals(25, CheckstyleJavaLexer.POST_INC, message);
        assertEquals(26, CheckstyleJavaLexer.POST_DEC, message);
        assertEquals(27, CheckstyleJavaLexer.METHOD_CALL, message);
        assertEquals(28, CheckstyleJavaLexer.EXPR, message);
        assertEquals(29, CheckstyleJavaLexer.ARRAY_INIT, message);
        assertEquals(30, CheckstyleJavaLexer.IMPORT, message);
        assertEquals(31, CheckstyleJavaLexer.UNARY_MINUS, message);
        assertEquals(32, CheckstyleJavaLexer.UNARY_PLUS, message);
        assertEquals(33, CheckstyleJavaLexer.CASE_GROUP, message);
        assertEquals(34, CheckstyleJavaLexer.ELIST, message);
        assertEquals(35, CheckstyleJavaLexer.FOR_INIT, message);
        assertEquals(36, CheckstyleJavaLexer.FOR_CONDITION, message);
        assertEquals(37, CheckstyleJavaLexer.FOR_ITERATOR, message);
        assertEquals(38, CheckstyleJavaLexer.EMPTY_STAT, message);
        assertEquals(39, CheckstyleJavaLexer.FINAL, message);
        assertEquals(40, CheckstyleJavaLexer.ABSTRACT, message);
        assertEquals(41, CheckstyleJavaLexer.STRICTFP, message);
        assertEquals(42, CheckstyleJavaLexer.SUPER_CTOR_CALL, message);
        assertEquals(43, CheckstyleJavaLexer.CTOR_CALL, message);
        assertEquals(44, CheckstyleJavaLexer.LITERAL_PACKAGE, message);
        assertEquals(45, CheckstyleJavaLexer.SEMI, message);
        assertEquals(46, CheckstyleJavaLexer.LITERAL_IMPORT, message);
        assertEquals(47, CheckstyleJavaLexer.LBRACK, message);
        assertEquals(48, CheckstyleJavaLexer.RBRACK, message);
        assertEquals(49, CheckstyleJavaLexer.LITERAL_VOID, message);
        assertEquals(50, CheckstyleJavaLexer.LITERAL_BOOLEAN, message);
        assertEquals(51, CheckstyleJavaLexer.LITERAL_BYTE, message);
        assertEquals(52, CheckstyleJavaLexer.LITERAL_CHAR, message);
        assertEquals(53, CheckstyleJavaLexer.LITERAL_SHORT, message);
        assertEquals(54, CheckstyleJavaLexer.LITERAL_INT, message);
        assertEquals(55, CheckstyleJavaLexer.LITERAL_FLOAT, message);
        assertEquals(56, CheckstyleJavaLexer.LITERAL_LONG, message);
        assertEquals(57, CheckstyleJavaLexer.LITERAL_DOUBLE, message);
        assertEquals(58, CheckstyleJavaLexer.IDENT, message);
        assertEquals(59, CheckstyleJavaLexer.DOT, message);
        assertEquals(60, CheckstyleJavaLexer.STAR, message);
        assertEquals(61, CheckstyleJavaLexer.LITERAL_PRIVATE, message);
        assertEquals(62, CheckstyleJavaLexer.LITERAL_PUBLIC, message);
        assertEquals(63, CheckstyleJavaLexer.LITERAL_PROTECTED, message);
        assertEquals(64, CheckstyleJavaLexer.LITERAL_STATIC, message);
        assertEquals(65, CheckstyleJavaLexer.LITERAL_TRANSIENT, message);
        assertEquals(66, CheckstyleJavaLexer.LITERAL_NATIVE, message);
        assertEquals(67, CheckstyleJavaLexer.LITERAL_SYNCHRONIZED, message);
        assertEquals(68, CheckstyleJavaLexer.LITERAL_VOLATILE, message);
        assertEquals(69, CheckstyleJavaLexer.LITERAL_CLASS, message);
        assertEquals(70, CheckstyleJavaLexer.LITERAL_EXTENDS, message);
        assertEquals(71, CheckstyleJavaLexer.LITERAL_INTERFACE, message);
        assertEquals(72, CheckstyleJavaLexer.LCURLY, message);
        assertEquals(73, CheckstyleJavaLexer.RCURLY, message);
        assertEquals(74, CheckstyleJavaLexer.COMMA, message);
        assertEquals(75, CheckstyleJavaLexer.LITERAL_IMPLEMENTS, message);
        assertEquals(76, CheckstyleJavaLexer.LPAREN, message);
        assertEquals(77, CheckstyleJavaLexer.RPAREN, message);
        assertEquals(78, CheckstyleJavaLexer.LITERAL_THIS, message);
        assertEquals(79, CheckstyleJavaLexer.LITERAL_SUPER, message);
        assertEquals(80, CheckstyleJavaLexer.ASSIGN, message);
        assertEquals(81, CheckstyleJavaLexer.LITERAL_THROWS, message);
        assertEquals(82, CheckstyleJavaLexer.COLON, message);
        assertEquals(83, CheckstyleJavaLexer.LITERAL_IF, message);
        assertEquals(84, CheckstyleJavaLexer.LITERAL_WHILE, message);
        assertEquals(85, CheckstyleJavaLexer.LITERAL_DO, message);
        assertEquals(86, CheckstyleJavaLexer.LITERAL_BREAK, message);
        assertEquals(87, CheckstyleJavaLexer.LITERAL_CONTINUE, message);
        assertEquals(88, CheckstyleJavaLexer.LITERAL_RETURN, message);
        assertEquals(89, CheckstyleJavaLexer.LITERAL_SWITCH, message);
        assertEquals(90, CheckstyleJavaLexer.LITERAL_THROW, message);
        assertEquals(91, CheckstyleJavaLexer.LITERAL_FOR, message);
        assertEquals(92, CheckstyleJavaLexer.LITERAL_ELSE, message);
        assertEquals(93, CheckstyleJavaLexer.LITERAL_CASE, message);
        assertEquals(94, CheckstyleJavaLexer.LITERAL_DEFAULT, message);
        assertEquals(95, CheckstyleJavaLexer.LITERAL_TRY, message);
        assertEquals(96, CheckstyleJavaLexer.LITERAL_CATCH, message);
        assertEquals(97, CheckstyleJavaLexer.LITERAL_FINALLY, message);
        assertEquals(98, CheckstyleJavaLexer.PLUS_ASSIGN, message);
        assertEquals(99, CheckstyleJavaLexer.MINUS_ASSIGN, message);
        assertEquals(100, CheckstyleJavaLexer.STAR_ASSIGN, message);
        assertEquals(101, CheckstyleJavaLexer.DIV_ASSIGN, message);
        assertEquals(102, CheckstyleJavaLexer.MOD_ASSIGN, message);
        assertEquals(103, CheckstyleJavaLexer.SR_ASSIGN, message);
        assertEquals(104, CheckstyleJavaLexer.BSR_ASSIGN, message);
        assertEquals(105, CheckstyleJavaLexer.SL_ASSIGN, message);
        assertEquals(106, CheckstyleJavaLexer.BAND_ASSIGN, message);
        assertEquals(107, CheckstyleJavaLexer.BXOR_ASSIGN, message);
        assertEquals(108, CheckstyleJavaLexer.BOR_ASSIGN, message);
        assertEquals(109, CheckstyleJavaLexer.QUESTION, message);
        assertEquals(110, CheckstyleJavaLexer.LOR, message);
        assertEquals(111, CheckstyleJavaLexer.LAND, message);
        assertEquals(112, CheckstyleJavaLexer.BOR, message);
        assertEquals(113, CheckstyleJavaLexer.BXOR, message);
        assertEquals(114, CheckstyleJavaLexer.BAND, message);
        assertEquals(115, CheckstyleJavaLexer.NOT_EQUAL, message);
        assertEquals(116, CheckstyleJavaLexer.EQUAL, message);
        assertEquals(117, CheckstyleJavaLexer.LT, message);
        assertEquals(118, CheckstyleJavaLexer.GT, message);
        assertEquals(119, CheckstyleJavaLexer.LE, message);
        assertEquals(120, CheckstyleJavaLexer.GE, message);
        assertEquals(121, CheckstyleJavaLexer.LITERAL_INSTANCEOF, message);
        assertEquals(122, CheckstyleJavaLexer.SL, message);
        assertEquals(123, CheckstyleJavaLexer.SR, message);
        assertEquals(124, CheckstyleJavaLexer.BSR, message);
        assertEquals(125, CheckstyleJavaLexer.PLUS, message);
        assertEquals(126, CheckstyleJavaLexer.MINUS, message);
        assertEquals(127, CheckstyleJavaLexer.DIV, message);
        assertEquals(128, CheckstyleJavaLexer.MOD, message);
        assertEquals(129, CheckstyleJavaLexer.INC, message);
        assertEquals(130, CheckstyleJavaLexer.DEC, message);
        assertEquals(131, CheckstyleJavaLexer.BNOT, message);
        assertEquals(132, CheckstyleJavaLexer.LNOT, message);
        assertEquals(133, CheckstyleJavaLexer.LITERAL_TRUE, message);
        assertEquals(134, CheckstyleJavaLexer.LITERAL_FALSE, message);
        assertEquals(135, CheckstyleJavaLexer.LITERAL_NULL, message);
        assertEquals(136, CheckstyleJavaLexer.LITERAL_NEW, message);
        assertEquals(137, CheckstyleJavaLexer.NUM_INT, message);
        assertEquals(138, CheckstyleJavaLexer.CHAR_LITERAL, message);
        assertEquals(139, CheckstyleJavaLexer.STRING_LITERAL, message);
        assertEquals(140, CheckstyleJavaLexer.NUM_FLOAT, message);
        assertEquals(141, CheckstyleJavaLexer.NUM_LONG, message);
        assertEquals(142, CheckstyleJavaLexer.NUM_DOUBLE, message);
        assertEquals(143, CheckstyleJavaLexer.WS, message);
        assertEquals(144, CheckstyleJavaLexer.SINGLE_LINE_COMMENT, message);
        assertEquals(145, CheckstyleJavaLexer.BLOCK_COMMENT_BEGIN, message);
        assertEquals(146, CheckstyleJavaLexer.ESC, message);
        assertEquals(147, CheckstyleJavaLexer.HEX_DIGIT, message);
        assertEquals(148, CheckstyleJavaLexer.VOCAB, message);
        assertEquals(149, CheckstyleJavaLexer.EXPONENT, message);
        assertEquals(150, CheckstyleJavaLexer.FLOAT_SUFFIX, message);
        assertEquals(151, CheckstyleJavaLexer.ASSERT, message);
        assertEquals(152, CheckstyleJavaLexer.STATIC_IMPORT, message);
        assertEquals(153, CheckstyleJavaLexer.ENUM, message);
        assertEquals(154, CheckstyleJavaLexer.ENUM_DEF, message);
        assertEquals(155, CheckstyleJavaLexer.ENUM_CONSTANT_DEF, message);
        assertEquals(156, CheckstyleJavaLexer.FOR_EACH_CLAUSE, message);
        assertEquals(157, CheckstyleJavaLexer.ANNOTATION_DEF, message);
        assertEquals(158, CheckstyleJavaLexer.ANNOTATIONS, message);
        assertEquals(159, CheckstyleJavaLexer.ANNOTATION, message);
        assertEquals(160, CheckstyleJavaLexer.ANNOTATION_MEMBER_VALUE_PAIR, message);
        assertEquals(161, CheckstyleJavaLexer.ANNOTATION_FIELD_DEF, message);
        assertEquals(162, CheckstyleJavaLexer.ANNOTATION_ARRAY_INIT, message);
        assertEquals(163, CheckstyleJavaLexer.TYPE_ARGUMENTS, message);
        assertEquals(164, CheckstyleJavaLexer.TYPE_ARGUMENT, message);
        assertEquals(165, CheckstyleJavaLexer.TYPE_PARAMETERS, message);
        assertEquals(166, CheckstyleJavaLexer.TYPE_PARAMETER, message);
        assertEquals(167, CheckstyleJavaLexer.WILDCARD_TYPE, message);
        assertEquals(168, CheckstyleJavaLexer.TYPE_UPPER_BOUNDS, message);
        assertEquals(169, CheckstyleJavaLexer.TYPE_LOWER_BOUNDS, message);
        assertEquals(170, CheckstyleJavaLexer.AT, message);
        assertEquals(171, CheckstyleJavaLexer.ELLIPSIS, message);
        assertEquals(172, CheckstyleJavaLexer.GENERIC_START, message);
        assertEquals(173, CheckstyleJavaLexer.GENERIC_END, message);
        assertEquals(174, CheckstyleJavaLexer.TYPE_EXTENSION_AND, message);
        assertEquals(175, CheckstyleJavaLexer.DO_WHILE, message);
        assertEquals(176, CheckstyleJavaLexer.RESOURCE_SPECIFICATION, message);
        assertEquals(177, CheckstyleJavaLexer.RESOURCES, message);
        assertEquals(178, CheckstyleJavaLexer.RESOURCE, message);
        assertEquals(179, CheckstyleJavaLexer.DOUBLE_COLON, message);
        assertEquals(180, CheckstyleJavaLexer.METHOD_REF, message);
        assertEquals(181, CheckstyleJavaLexer.LAMBDA, message);
        assertEquals(182, CheckstyleJavaLexer.BLOCK_COMMENT_END, message);
        assertEquals(183, CheckstyleJavaLexer.COMMENT_CONTENT, message);
        assertEquals(184, CheckstyleJavaLexer.SINGLE_LINE_COMMENT_CONTENT, message);
        assertEquals(185, CheckstyleJavaLexer.BLOCK_COMMENT_CONTENT, message);
        assertEquals(186, CheckstyleJavaLexer.STD_ESC, message);
        assertEquals(187, CheckstyleJavaLexer.BINARY_DIGIT, message);
        assertEquals(188, CheckstyleJavaLexer.ID_START, message);
        assertEquals(189, CheckstyleJavaLexer.ID_PART, message);
        assertEquals(190, CheckstyleJavaLexer.INT_LITERAL, message);
        assertEquals(191, CheckstyleJavaLexer.LONG_LITERAL, message);
        assertEquals(192, CheckstyleJavaLexer.FLOAT_LITERAL, message);
        assertEquals(193, CheckstyleJavaLexer.DOUBLE_LITERAL, message);
        assertEquals(194, CheckstyleJavaLexer.HEX_FLOAT_LITERAL, message);
        assertEquals(195, CheckstyleJavaLexer.HEX_DOUBLE_LITERAL, message);
        assertEquals(196, CheckstyleJavaLexer.SIGNED_INTEGER, message);
        assertEquals(197, CheckstyleJavaLexer.BINARY_EXPONENT, message);
        assertEquals(198, CheckstyleJavaLexer.PATTERN_VARIABLE_DEF, message);
        assertEquals(199, CheckstyleJavaLexer.RECORD_DEF, message);
        assertEquals(200, CheckstyleJavaLexer.LITERAL_RECORD, message);
        assertEquals(201, CheckstyleJavaLexer.RECORD_COMPONENTS, message);
        assertEquals(202, CheckstyleJavaLexer.RECORD_COMPONENT_DEF, message);
        assertEquals(203, CheckstyleJavaLexer.COMPACT_CTOR_DEF, message);
        assertEquals(204, CheckstyleJavaLexer.TEXT_BLOCK_LITERAL_BEGIN, message);
        assertEquals(205, CheckstyleJavaLexer.TEXT_BLOCK_CONTENT, message);
        assertEquals(206, CheckstyleJavaLexer.TEXT_BLOCK_LITERAL_END, message);
        assertEquals(207, CheckstyleJavaLexer.LITERAL_YIELD, message);
        assertEquals(208, CheckstyleJavaLexer.SWITCH_RULE, message);
        assertEquals(209, CheckstyleJavaLexer.LITERAL_NON_SEALED, message);
        assertEquals(210, CheckstyleJavaLexer.LITERAL_SEALED, message);
        assertEquals(211, CheckstyleJavaLexer.LITERAL_PERMITS, message);
        assertEquals(212, CheckstyleJavaLexer.PERMITS_CLAUSE, message);

        // Read JavaDoc before changing
        final int tokenCount = (int) Arrays.stream(CheckstyleJavaLexer.class.getDeclaredFields())
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
