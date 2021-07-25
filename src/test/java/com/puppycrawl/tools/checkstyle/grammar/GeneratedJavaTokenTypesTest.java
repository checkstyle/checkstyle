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

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLexer;

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
     * class, JavaLexer, and so numbering updates are not picked
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
        assertEquals(3, JavaLexer.NULL_TREE_LOOKAHEAD, message);
        assertEquals(4, JavaLexer.BLOCK, message);
        assertEquals(5, JavaLexer.MODIFIERS, message);
        assertEquals(6, JavaLexer.OBJBLOCK, message);
        assertEquals(7, JavaLexer.SLIST, message);
        assertEquals(8, JavaLexer.CTOR_DEF, message);
        assertEquals(9, JavaLexer.METHOD_DEF, message);
        assertEquals(10, JavaLexer.VARIABLE_DEF, message);
        assertEquals(11, JavaLexer.INSTANCE_INIT, message);
        assertEquals(12, JavaLexer.STATIC_INIT, message);
        assertEquals(13, JavaLexer.TYPE, message);
        assertEquals(14, JavaLexer.CLASS_DEF, message);
        assertEquals(15, JavaLexer.INTERFACE_DEF, message);
        assertEquals(16, JavaLexer.PACKAGE_DEF, message);
        assertEquals(17, JavaLexer.ARRAY_DECLARATOR, message);
        assertEquals(18, JavaLexer.EXTENDS_CLAUSE, message);
        assertEquals(19, JavaLexer.IMPLEMENTS_CLAUSE, message);
        assertEquals(20, JavaLexer.PARAMETERS, message);
        assertEquals(21, JavaLexer.PARAMETER_DEF, message);
        assertEquals(22, JavaLexer.LABELED_STAT, message);
        assertEquals(23, JavaLexer.TYPECAST, message);
        assertEquals(24, JavaLexer.INDEX_OP, message);
        assertEquals(25, JavaLexer.POST_INC, message);
        assertEquals(26, JavaLexer.POST_DEC, message);
        assertEquals(27, JavaLexer.METHOD_CALL, message);
        assertEquals(28, JavaLexer.EXPR, message);
        assertEquals(29, JavaLexer.ARRAY_INIT, message);
        assertEquals(30, JavaLexer.IMPORT, message);
        assertEquals(31, JavaLexer.UNARY_MINUS, message);
        assertEquals(32, JavaLexer.UNARY_PLUS, message);
        assertEquals(33, JavaLexer.CASE_GROUP, message);
        assertEquals(34, JavaLexer.ELIST, message);
        assertEquals(35, JavaLexer.FOR_INIT, message);
        assertEquals(36, JavaLexer.FOR_CONDITION, message);
        assertEquals(37, JavaLexer.FOR_ITERATOR, message);
        assertEquals(38, JavaLexer.EMPTY_STAT, message);
        assertEquals(39, JavaLexer.FINAL, message);
        assertEquals(40, JavaLexer.ABSTRACT, message);
        assertEquals(41, JavaLexer.STRICTFP, message);
        assertEquals(42, JavaLexer.SUPER_CTOR_CALL, message);
        assertEquals(43, JavaLexer.CTOR_CALL, message);
        assertEquals(44, JavaLexer.LITERAL_PACKAGE, message);
        assertEquals(45, JavaLexer.SEMI, message);
        assertEquals(46, JavaLexer.LITERAL_IMPORT, message);
        assertEquals(47, JavaLexer.LBRACK, message);
        assertEquals(48, JavaLexer.RBRACK, message);
        assertEquals(49, JavaLexer.LITERAL_VOID, message);
        assertEquals(50, JavaLexer.LITERAL_BOOLEAN, message);
        assertEquals(51, JavaLexer.LITERAL_BYTE, message);
        assertEquals(52, JavaLexer.LITERAL_CHAR, message);
        assertEquals(53, JavaLexer.LITERAL_SHORT, message);
        assertEquals(54, JavaLexer.LITERAL_INT, message);
        assertEquals(55, JavaLexer.LITERAL_FLOAT, message);
        assertEquals(56, JavaLexer.LITERAL_LONG, message);
        assertEquals(57, JavaLexer.LITERAL_DOUBLE, message);
        assertEquals(58, JavaLexer.IDENT, message);
        assertEquals(59, JavaLexer.DOT, message);
        assertEquals(60, JavaLexer.STAR, message);
        assertEquals(61, JavaLexer.LITERAL_PRIVATE, message);
        assertEquals(62, JavaLexer.LITERAL_PUBLIC, message);
        assertEquals(63, JavaLexer.LITERAL_PROTECTED, message);
        assertEquals(64, JavaLexer.LITERAL_STATIC, message);
        assertEquals(65, JavaLexer.LITERAL_TRANSIENT, message);
        assertEquals(66, JavaLexer.LITERAL_NATIVE, message);
        assertEquals(67, JavaLexer.LITERAL_SYNCHRONIZED, message);
        assertEquals(68, JavaLexer.LITERAL_VOLATILE, message);
        assertEquals(69, JavaLexer.LITERAL_CLASS, message);
        assertEquals(70, JavaLexer.LITERAL_EXTENDS, message);
        assertEquals(71, JavaLexer.LITERAL_INTERFACE, message);
        assertEquals(72, JavaLexer.LCURLY, message);
        assertEquals(73, JavaLexer.RCURLY, message);
        assertEquals(74, JavaLexer.COMMA, message);
        assertEquals(75, JavaLexer.LITERAL_IMPLEMENTS, message);
        assertEquals(76, JavaLexer.LPAREN, message);
        assertEquals(77, JavaLexer.RPAREN, message);
        assertEquals(78, JavaLexer.LITERAL_THIS, message);
        assertEquals(79, JavaLexer.LITERAL_SUPER, message);
        assertEquals(80, JavaLexer.ASSIGN, message);
        assertEquals(81, JavaLexer.LITERAL_THROWS, message);
        assertEquals(82, JavaLexer.COLON, message);
        assertEquals(83, JavaLexer.LITERAL_IF, message);
        assertEquals(84, JavaLexer.LITERAL_WHILE, message);
        assertEquals(85, JavaLexer.LITERAL_DO, message);
        assertEquals(86, JavaLexer.LITERAL_BREAK, message);
        assertEquals(87, JavaLexer.LITERAL_CONTINUE, message);
        assertEquals(88, JavaLexer.LITERAL_RETURN, message);
        assertEquals(89, JavaLexer.LITERAL_SWITCH, message);
        assertEquals(90, JavaLexer.LITERAL_THROW, message);
        assertEquals(91, JavaLexer.LITERAL_FOR, message);
        assertEquals(92, JavaLexer.LITERAL_ELSE, message);
        assertEquals(93, JavaLexer.LITERAL_CASE, message);
        assertEquals(94, JavaLexer.LITERAL_DEFAULT, message);
        assertEquals(95, JavaLexer.LITERAL_TRY, message);
        assertEquals(96, JavaLexer.LITERAL_CATCH, message);
        assertEquals(97, JavaLexer.LITERAL_FINALLY, message);
        assertEquals(98, JavaLexer.PLUS_ASSIGN, message);
        assertEquals(99, JavaLexer.MINUS_ASSIGN, message);
        assertEquals(100, JavaLexer.STAR_ASSIGN, message);
        assertEquals(101, JavaLexer.DIV_ASSIGN, message);
        assertEquals(102, JavaLexer.MOD_ASSIGN, message);
        assertEquals(103, JavaLexer.SR_ASSIGN, message);
        assertEquals(104, JavaLexer.BSR_ASSIGN, message);
        assertEquals(105, JavaLexer.SL_ASSIGN, message);
        assertEquals(106, JavaLexer.BAND_ASSIGN, message);
        assertEquals(107, JavaLexer.BXOR_ASSIGN, message);
        assertEquals(108, JavaLexer.BOR_ASSIGN, message);
        assertEquals(109, JavaLexer.QUESTION, message);
        assertEquals(110, JavaLexer.LOR, message);
        assertEquals(111, JavaLexer.LAND, message);
        assertEquals(112, JavaLexer.BOR, message);
        assertEquals(113, JavaLexer.BXOR, message);
        assertEquals(114, JavaLexer.BAND, message);
        assertEquals(115, JavaLexer.NOT_EQUAL, message);
        assertEquals(116, JavaLexer.EQUAL, message);
        assertEquals(117, JavaLexer.LT, message);
        assertEquals(118, JavaLexer.GT, message);
        assertEquals(119, JavaLexer.LE, message);
        assertEquals(120, JavaLexer.GE, message);
        assertEquals(121, JavaLexer.LITERAL_INSTANCEOF, message);
        assertEquals(122, JavaLexer.SL, message);
        assertEquals(123, JavaLexer.SR, message);
        assertEquals(124, JavaLexer.BSR, message);
        assertEquals(125, JavaLexer.PLUS, message);
        assertEquals(126, JavaLexer.MINUS, message);
        assertEquals(127, JavaLexer.DIV, message);
        assertEquals(128, JavaLexer.MOD, message);
        assertEquals(129, JavaLexer.INC, message);
        assertEquals(130, JavaLexer.DEC, message);
        assertEquals(131, JavaLexer.BNOT, message);
        assertEquals(132, JavaLexer.LNOT, message);
        assertEquals(133, JavaLexer.LITERAL_TRUE, message);
        assertEquals(134, JavaLexer.LITERAL_FALSE, message);
        assertEquals(135, JavaLexer.LITERAL_NULL, message);
        assertEquals(136, JavaLexer.LITERAL_NEW, message);
        assertEquals(137, JavaLexer.NUM_INT, message);
        assertEquals(138, JavaLexer.CHAR_LITERAL, message);
        assertEquals(139, JavaLexer.STRING_LITERAL, message);
        assertEquals(140, JavaLexer.NUM_FLOAT, message);
        assertEquals(141, JavaLexer.NUM_LONG, message);
        assertEquals(142, JavaLexer.NUM_DOUBLE, message);
        assertEquals(143, JavaLexer.WS, message);
        assertEquals(144, JavaLexer.SINGLE_LINE_COMMENT, message);
        assertEquals(145, JavaLexer.BLOCK_COMMENT_BEGIN, message);
        assertEquals(146, JavaLexer.ESC, message);
        assertEquals(147, JavaLexer.HEX_DIGIT, message);
        assertEquals(148, JavaLexer.VOCAB, message);
        assertEquals(149, JavaLexer.EXPONENT, message);
        assertEquals(150, JavaLexer.FLOAT_SUFFIX, message);
        assertEquals(151, JavaLexer.ASSERT, message);
        assertEquals(152, JavaLexer.STATIC_IMPORT, message);
        assertEquals(153, JavaLexer.ENUM, message);
        assertEquals(154, JavaLexer.ENUM_DEF, message);
        assertEquals(155, JavaLexer.ENUM_CONSTANT_DEF, message);
        assertEquals(156, JavaLexer.FOR_EACH_CLAUSE, message);
        assertEquals(157, JavaLexer.ANNOTATION_DEF, message);
        assertEquals(158, JavaLexer.ANNOTATIONS, message);
        assertEquals(159, JavaLexer.ANNOTATION, message);
        assertEquals(160, JavaLexer.ANNOTATION_MEMBER_VALUE_PAIR, message);
        assertEquals(161, JavaLexer.ANNOTATION_FIELD_DEF, message);
        assertEquals(162, JavaLexer.ANNOTATION_ARRAY_INIT, message);
        assertEquals(163, JavaLexer.TYPE_ARGUMENTS, message);
        assertEquals(164, JavaLexer.TYPE_ARGUMENT, message);
        assertEquals(165, JavaLexer.TYPE_PARAMETERS, message);
        assertEquals(166, JavaLexer.TYPE_PARAMETER, message);
        assertEquals(167, JavaLexer.WILDCARD_TYPE, message);
        assertEquals(168, JavaLexer.TYPE_UPPER_BOUNDS, message);
        assertEquals(169, JavaLexer.TYPE_LOWER_BOUNDS, message);
        assertEquals(170, JavaLexer.AT, message);
        assertEquals(171, JavaLexer.ELLIPSIS, message);
        assertEquals(172, JavaLexer.GENERIC_START, message);
        assertEquals(173, JavaLexer.GENERIC_END, message);
        assertEquals(174, JavaLexer.TYPE_EXTENSION_AND, message);
        assertEquals(175, JavaLexer.DO_WHILE, message);
        assertEquals(176, JavaLexer.RESOURCE_SPECIFICATION, message);
        assertEquals(177, JavaLexer.RESOURCES, message);
        assertEquals(178, JavaLexer.RESOURCE, message);
        assertEquals(179, JavaLexer.DOUBLE_COLON, message);
        assertEquals(180, JavaLexer.METHOD_REF, message);
        assertEquals(181, JavaLexer.LAMBDA, message);
        assertEquals(182, JavaLexer.BLOCK_COMMENT_END, message);
        assertEquals(183, JavaLexer.COMMENT_CONTENT, message);
        assertEquals(184, JavaLexer.SINGLE_LINE_COMMENT_CONTENT, message);
        assertEquals(185, JavaLexer.BLOCK_COMMENT_CONTENT, message);
        assertEquals(186, JavaLexer.STD_ESC, message);
        assertEquals(187, JavaLexer.BINARY_DIGIT, message);
        assertEquals(188, JavaLexer.ID_START, message);
        assertEquals(189, JavaLexer.ID_PART, message);
        assertEquals(190, JavaLexer.INT_LITERAL, message);
        assertEquals(191, JavaLexer.LONG_LITERAL, message);
        assertEquals(192, JavaLexer.FLOAT_LITERAL, message);
        assertEquals(193, JavaLexer.DOUBLE_LITERAL, message);
        assertEquals(194, JavaLexer.HEX_FLOAT_LITERAL, message);
        assertEquals(195, JavaLexer.HEX_DOUBLE_LITERAL, message);
        assertEquals(196, JavaLexer.SIGNED_INTEGER, message);
        assertEquals(197, JavaLexer.BINARY_EXPONENT, message);
        assertEquals(198, JavaLexer.PATTERN_VARIABLE_DEF, message);
        assertEquals(199, JavaLexer.RECORD_DEF, message);
        assertEquals(200, JavaLexer.LITERAL_RECORD, message);
        assertEquals(201, JavaLexer.RECORD_COMPONENTS, message);
        assertEquals(202, JavaLexer.RECORD_COMPONENT_DEF, message);
        assertEquals(203, JavaLexer.COMPACT_CTOR_DEF, message);
        assertEquals(204, JavaLexer.TEXT_BLOCK_LITERAL_BEGIN, message);
        assertEquals(205, JavaLexer.TEXT_BLOCK_CONTENT, message);
        assertEquals(206, JavaLexer.TEXT_BLOCK_LITERAL_END, message);
        assertEquals(207, JavaLexer.LITERAL_YIELD, message);
        assertEquals(208, JavaLexer.SWITCH_RULE, message);
        assertEquals(209, JavaLexer.LITERAL_NON_SEALED, message);
        assertEquals(210, JavaLexer.LITERAL_SEALED, message);
        assertEquals(211, JavaLexer.LITERAL_PERMITS, message);
        assertEquals(212, JavaLexer.PERMITS_CLAUSE, message);

        // Read JavaDoc before changing
        final int tokenCount = (int) Arrays.stream(JavaLexer.class.getDeclaredFields())
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
