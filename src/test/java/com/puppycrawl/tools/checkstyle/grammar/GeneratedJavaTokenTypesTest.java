////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import org.junit.jupiter.api.Test;

/**
 * GeneratedJavaTokenTypesTest.
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
     * class, GeneratedJavaTokenTypes, and so numbering updates are not picked
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
        assertEquals(1, GeneratedJavaTokenTypes.EOF, message);
        assertEquals(3, GeneratedJavaTokenTypes.NULL_TREE_LOOKAHEAD, message);
        assertEquals(4, GeneratedJavaTokenTypes.BLOCK, message);
        assertEquals(5, GeneratedJavaTokenTypes.MODIFIERS, message);
        assertEquals(6, GeneratedJavaTokenTypes.OBJBLOCK, message);
        assertEquals(7, GeneratedJavaTokenTypes.SLIST, message);
        assertEquals(8, GeneratedJavaTokenTypes.CTOR_DEF, message);
        assertEquals(9, GeneratedJavaTokenTypes.METHOD_DEF, message);
        assertEquals(10, GeneratedJavaTokenTypes.VARIABLE_DEF, message);
        assertEquals(11, GeneratedJavaTokenTypes.INSTANCE_INIT, message);
        assertEquals(12, GeneratedJavaTokenTypes.STATIC_INIT, message);
        assertEquals(13, GeneratedJavaTokenTypes.TYPE, message);
        assertEquals(14, GeneratedJavaTokenTypes.CLASS_DEF, message);
        assertEquals(15, GeneratedJavaTokenTypes.INTERFACE_DEF, message);
        assertEquals(16, GeneratedJavaTokenTypes.PACKAGE_DEF, message);
        assertEquals(17, GeneratedJavaTokenTypes.ARRAY_DECLARATOR, message);
        assertEquals(18, GeneratedJavaTokenTypes.EXTENDS_CLAUSE, message);
        assertEquals(19, GeneratedJavaTokenTypes.IMPLEMENTS_CLAUSE, message);
        assertEquals(20, GeneratedJavaTokenTypes.PARAMETERS, message);
        assertEquals(21, GeneratedJavaTokenTypes.PARAMETER_DEF, message);
        assertEquals(22, GeneratedJavaTokenTypes.LABELED_STAT, message);
        assertEquals(23, GeneratedJavaTokenTypes.TYPECAST, message);
        assertEquals(24, GeneratedJavaTokenTypes.INDEX_OP, message);
        assertEquals(25, GeneratedJavaTokenTypes.POST_INC, message);
        assertEquals(26, GeneratedJavaTokenTypes.POST_DEC, message);
        assertEquals(27, GeneratedJavaTokenTypes.METHOD_CALL, message);
        assertEquals(28, GeneratedJavaTokenTypes.EXPR, message);
        assertEquals(29, GeneratedJavaTokenTypes.ARRAY_INIT, message);
        assertEquals(30, GeneratedJavaTokenTypes.IMPORT, message);
        assertEquals(31, GeneratedJavaTokenTypes.UNARY_MINUS, message);
        assertEquals(32, GeneratedJavaTokenTypes.UNARY_PLUS, message);
        assertEquals(33, GeneratedJavaTokenTypes.CASE_GROUP, message);
        assertEquals(34, GeneratedJavaTokenTypes.ELIST, message);
        assertEquals(35, GeneratedJavaTokenTypes.FOR_INIT, message);
        assertEquals(36, GeneratedJavaTokenTypes.FOR_CONDITION, message);
        assertEquals(37, GeneratedJavaTokenTypes.FOR_ITERATOR, message);
        assertEquals(38, GeneratedJavaTokenTypes.EMPTY_STAT, message);
        assertEquals(39, GeneratedJavaTokenTypes.FINAL, message);
        assertEquals(40, GeneratedJavaTokenTypes.ABSTRACT, message);
        assertEquals(41, GeneratedJavaTokenTypes.STRICTFP, message);
        assertEquals(42, GeneratedJavaTokenTypes.SUPER_CTOR_CALL, message);
        assertEquals(43, GeneratedJavaTokenTypes.CTOR_CALL, message);
        assertEquals(44, GeneratedJavaTokenTypes.LITERAL_package, message);
        assertEquals(45, GeneratedJavaTokenTypes.SEMI, message);
        assertEquals(46, GeneratedJavaTokenTypes.LITERAL_import, message);
        assertEquals(47, GeneratedJavaTokenTypes.LBRACK, message);
        assertEquals(48, GeneratedJavaTokenTypes.RBRACK, message);
        assertEquals(49, GeneratedJavaTokenTypes.LITERAL_void, message);
        assertEquals(50, GeneratedJavaTokenTypes.LITERAL_boolean, message);
        assertEquals(51, GeneratedJavaTokenTypes.LITERAL_byte, message);
        assertEquals(52, GeneratedJavaTokenTypes.LITERAL_char, message);
        assertEquals(53, GeneratedJavaTokenTypes.LITERAL_short, message);
        assertEquals(54, GeneratedJavaTokenTypes.LITERAL_int, message);
        assertEquals(55, GeneratedJavaTokenTypes.LITERAL_float, message);
        assertEquals(56, GeneratedJavaTokenTypes.LITERAL_long, message);
        assertEquals(57, GeneratedJavaTokenTypes.LITERAL_double, message);
        assertEquals(58, GeneratedJavaTokenTypes.IDENT, message);
        assertEquals(59, GeneratedJavaTokenTypes.DOT, message);
        assertEquals(60, GeneratedJavaTokenTypes.STAR, message);
        assertEquals(61, GeneratedJavaTokenTypes.LITERAL_private, message);
        assertEquals(62, GeneratedJavaTokenTypes.LITERAL_public, message);
        assertEquals(63, GeneratedJavaTokenTypes.LITERAL_protected, message);
        assertEquals(64, GeneratedJavaTokenTypes.LITERAL_static, message);
        assertEquals(65, GeneratedJavaTokenTypes.LITERAL_transient, message);
        assertEquals(66, GeneratedJavaTokenTypes.LITERAL_native, message);
        assertEquals(67, GeneratedJavaTokenTypes.LITERAL_synchronized, message);
        assertEquals(68, GeneratedJavaTokenTypes.LITERAL_volatile, message);
        assertEquals(69, GeneratedJavaTokenTypes.LITERAL_class, message);
        assertEquals(70, GeneratedJavaTokenTypes.LITERAL_extends, message);
        assertEquals(71, GeneratedJavaTokenTypes.LITERAL_interface, message);
        assertEquals(72, GeneratedJavaTokenTypes.LCURLY, message);
        assertEquals(73, GeneratedJavaTokenTypes.RCURLY, message);
        assertEquals(74, GeneratedJavaTokenTypes.COMMA, message);
        assertEquals(75, GeneratedJavaTokenTypes.LITERAL_implements, message);
        assertEquals(76, GeneratedJavaTokenTypes.LPAREN, message);
        assertEquals(77, GeneratedJavaTokenTypes.RPAREN, message);
        assertEquals(78, GeneratedJavaTokenTypes.LITERAL_this, message);
        assertEquals(79, GeneratedJavaTokenTypes.LITERAL_super, message);
        assertEquals(80, GeneratedJavaTokenTypes.ASSIGN, message);
        assertEquals(81, GeneratedJavaTokenTypes.LITERAL_throws, message);
        assertEquals(82, GeneratedJavaTokenTypes.COLON, message);
        assertEquals(83, GeneratedJavaTokenTypes.LITERAL_if, message);
        assertEquals(84, GeneratedJavaTokenTypes.LITERAL_while, message);
        assertEquals(85, GeneratedJavaTokenTypes.LITERAL_do, message);
        assertEquals(86, GeneratedJavaTokenTypes.LITERAL_break, message);
        assertEquals(87, GeneratedJavaTokenTypes.LITERAL_continue, message);
        assertEquals(88, GeneratedJavaTokenTypes.LITERAL_return, message);
        assertEquals(89, GeneratedJavaTokenTypes.LITERAL_switch, message);
        assertEquals(90, GeneratedJavaTokenTypes.LITERAL_throw, message);
        assertEquals(91, GeneratedJavaTokenTypes.LITERAL_for, message);
        assertEquals(92, GeneratedJavaTokenTypes.LITERAL_else, message);
        assertEquals(93, GeneratedJavaTokenTypes.LITERAL_case, message);
        assertEquals(94, GeneratedJavaTokenTypes.LITERAL_default, message);
        assertEquals(95, GeneratedJavaTokenTypes.LITERAL_try, message);
        assertEquals(96, GeneratedJavaTokenTypes.LITERAL_catch, message);
        assertEquals(97, GeneratedJavaTokenTypes.LITERAL_finally, message);
        assertEquals(98, GeneratedJavaTokenTypes.PLUS_ASSIGN, message);
        assertEquals(99, GeneratedJavaTokenTypes.MINUS_ASSIGN, message);
        assertEquals(100, GeneratedJavaTokenTypes.STAR_ASSIGN, message);
        assertEquals(101, GeneratedJavaTokenTypes.DIV_ASSIGN, message);
        assertEquals(102, GeneratedJavaTokenTypes.MOD_ASSIGN, message);
        assertEquals(103, GeneratedJavaTokenTypes.SR_ASSIGN, message);
        assertEquals(104, GeneratedJavaTokenTypes.BSR_ASSIGN, message);
        assertEquals(105, GeneratedJavaTokenTypes.SL_ASSIGN, message);
        assertEquals(106, GeneratedJavaTokenTypes.BAND_ASSIGN, message);
        assertEquals(107, GeneratedJavaTokenTypes.BXOR_ASSIGN, message);
        assertEquals(108, GeneratedJavaTokenTypes.BOR_ASSIGN, message);
        assertEquals(109, GeneratedJavaTokenTypes.QUESTION, message);
        assertEquals(110, GeneratedJavaTokenTypes.LOR, message);
        assertEquals(111, GeneratedJavaTokenTypes.LAND, message);
        assertEquals(112, GeneratedJavaTokenTypes.BOR, message);
        assertEquals(113, GeneratedJavaTokenTypes.BXOR, message);
        assertEquals(114, GeneratedJavaTokenTypes.BAND, message);
        assertEquals(115, GeneratedJavaTokenTypes.NOT_EQUAL, message);
        assertEquals(116, GeneratedJavaTokenTypes.EQUAL, message);
        assertEquals(117, GeneratedJavaTokenTypes.LT, message);
        assertEquals(118, GeneratedJavaTokenTypes.GT, message);
        assertEquals(119, GeneratedJavaTokenTypes.LE, message);
        assertEquals(120, GeneratedJavaTokenTypes.GE, message);
        assertEquals(121, GeneratedJavaTokenTypes.LITERAL_instanceof, message);
        assertEquals(122, GeneratedJavaTokenTypes.SL, message);
        assertEquals(123, GeneratedJavaTokenTypes.SR, message);
        assertEquals(124, GeneratedJavaTokenTypes.BSR, message);
        assertEquals(125, GeneratedJavaTokenTypes.PLUS, message);
        assertEquals(126, GeneratedJavaTokenTypes.MINUS, message);
        assertEquals(127, GeneratedJavaTokenTypes.DIV, message);
        assertEquals(128, GeneratedJavaTokenTypes.MOD, message);
        assertEquals(129, GeneratedJavaTokenTypes.INC, message);
        assertEquals(130, GeneratedJavaTokenTypes.DEC, message);
        assertEquals(131, GeneratedJavaTokenTypes.BNOT, message);
        assertEquals(132, GeneratedJavaTokenTypes.LNOT, message);
        assertEquals(133, GeneratedJavaTokenTypes.LITERAL_true, message);
        assertEquals(134, GeneratedJavaTokenTypes.LITERAL_false, message);
        assertEquals(135, GeneratedJavaTokenTypes.LITERAL_null, message);
        assertEquals(136, GeneratedJavaTokenTypes.LITERAL_new, message);
        assertEquals(137, GeneratedJavaTokenTypes.NUM_INT, message);
        assertEquals(138, GeneratedJavaTokenTypes.CHAR_LITERAL, message);
        assertEquals(139, GeneratedJavaTokenTypes.STRING_LITERAL, message);
        assertEquals(140, GeneratedJavaTokenTypes.NUM_FLOAT, message);
        assertEquals(141, GeneratedJavaTokenTypes.NUM_LONG, message);
        assertEquals(142, GeneratedJavaTokenTypes.NUM_DOUBLE, message);
        assertEquals(143, GeneratedJavaTokenTypes.WS, message);
        assertEquals(144, GeneratedJavaTokenTypes.SINGLE_LINE_COMMENT, message);
        assertEquals(145, GeneratedJavaTokenTypes.BLOCK_COMMENT_BEGIN, message);
        assertEquals(146, GeneratedJavaTokenTypes.ESC, message);
        assertEquals(147, GeneratedJavaTokenTypes.HEX_DIGIT, message);
        assertEquals(148, GeneratedJavaTokenTypes.VOCAB, message);
        assertEquals(149, GeneratedJavaTokenTypes.EXPONENT, message);
        assertEquals(150, GeneratedJavaTokenTypes.FLOAT_SUFFIX, message);
        assertEquals(151, GeneratedJavaTokenTypes.ASSERT, message);
        assertEquals(152, GeneratedJavaTokenTypes.STATIC_IMPORT, message);
        assertEquals(153, GeneratedJavaTokenTypes.ENUM, message);
        assertEquals(154, GeneratedJavaTokenTypes.ENUM_DEF, message);
        assertEquals(155, GeneratedJavaTokenTypes.ENUM_CONSTANT_DEF, message);
        assertEquals(156, GeneratedJavaTokenTypes.FOR_EACH_CLAUSE, message);
        assertEquals(157, GeneratedJavaTokenTypes.ANNOTATION_DEF, message);
        assertEquals(158, GeneratedJavaTokenTypes.ANNOTATIONS, message);
        assertEquals(159, GeneratedJavaTokenTypes.ANNOTATION, message);
        assertEquals(160, GeneratedJavaTokenTypes.ANNOTATION_MEMBER_VALUE_PAIR, message);
        assertEquals(161, GeneratedJavaTokenTypes.ANNOTATION_FIELD_DEF, message);
        assertEquals(162, GeneratedJavaTokenTypes.ANNOTATION_ARRAY_INIT, message);
        assertEquals(163, GeneratedJavaTokenTypes.TYPE_ARGUMENTS, message);
        assertEquals(164, GeneratedJavaTokenTypes.TYPE_ARGUMENT, message);
        assertEquals(165, GeneratedJavaTokenTypes.TYPE_PARAMETERS, message);
        assertEquals(166, GeneratedJavaTokenTypes.TYPE_PARAMETER, message);
        assertEquals(167, GeneratedJavaTokenTypes.WILDCARD_TYPE, message);
        assertEquals(168, GeneratedJavaTokenTypes.TYPE_UPPER_BOUNDS, message);
        assertEquals(169, GeneratedJavaTokenTypes.TYPE_LOWER_BOUNDS, message);
        assertEquals(170, GeneratedJavaTokenTypes.AT, message);
        assertEquals(171, GeneratedJavaTokenTypes.ELLIPSIS, message);
        assertEquals(172, GeneratedJavaTokenTypes.GENERIC_START, message);
        assertEquals(173, GeneratedJavaTokenTypes.GENERIC_END, message);
        assertEquals(174, GeneratedJavaTokenTypes.TYPE_EXTENSION_AND, message);
        assertEquals(175, GeneratedJavaTokenTypes.DO_WHILE, message);
        assertEquals(176, GeneratedJavaTokenTypes.RESOURCE_SPECIFICATION, message);
        assertEquals(177, GeneratedJavaTokenTypes.RESOURCES, message);
        assertEquals(178, GeneratedJavaTokenTypes.RESOURCE, message);
        assertEquals(179, GeneratedJavaTokenTypes.DOUBLE_COLON, message);
        assertEquals(180, GeneratedJavaTokenTypes.METHOD_REF, message);
        assertEquals(181, GeneratedJavaTokenTypes.LAMBDA, message);
        assertEquals(182, GeneratedJavaTokenTypes.BLOCK_COMMENT_END, message);
        assertEquals(183, GeneratedJavaTokenTypes.COMMENT_CONTENT, message);
        assertEquals(184, GeneratedJavaTokenTypes.SINGLE_LINE_COMMENT_CONTENT, message);
        assertEquals(185, GeneratedJavaTokenTypes.BLOCK_COMMENT_CONTENT, message);
        assertEquals(186, GeneratedJavaTokenTypes.STD_ESC, message);
        assertEquals(187, GeneratedJavaTokenTypes.BINARY_DIGIT, message);
        assertEquals(188, GeneratedJavaTokenTypes.ID_START, message);
        assertEquals(189, GeneratedJavaTokenTypes.ID_PART, message);
        assertEquals(190, GeneratedJavaTokenTypes.INT_LITERAL, message);
        assertEquals(191, GeneratedJavaTokenTypes.LONG_LITERAL, message);
        assertEquals(192, GeneratedJavaTokenTypes.FLOAT_LITERAL, message);
        assertEquals(193, GeneratedJavaTokenTypes.DOUBLE_LITERAL, message);
        assertEquals(194, GeneratedJavaTokenTypes.HEX_FLOAT_LITERAL, message);
        assertEquals(195, GeneratedJavaTokenTypes.HEX_DOUBLE_LITERAL, message);
        assertEquals(196, GeneratedJavaTokenTypes.SIGNED_INTEGER, message);
        assertEquals(197, GeneratedJavaTokenTypes.BINARY_EXPONENT, message);
        // Read JavaDoc before changing
        assertEquals(196, GeneratedJavaTokenTypes.class.getDeclaredFields().length,
                "all tokens must be added to list in"
                        + " 'GeneratedJavaTokenTypesTest' and verified"
                        + " that their old numbering didn't change");
    }

}
