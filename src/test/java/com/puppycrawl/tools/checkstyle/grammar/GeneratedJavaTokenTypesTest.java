///
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
///

package com.puppycrawl.tools.checkstyle.grammar;

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.VocabularyImpl;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;

/**
 * GeneratedJavaTokenTypesTest.
 *
 * @noinspection ClassIndependentOfModule
 * @noinspectionreason ClassIndependentOfModule - architecture of test modules
 *      requires this structure
 */
public class GeneratedJavaTokenTypesTest {

    /**
     * The following tokens are not declared in the lexer's 'tokens' block,
     * they will always appear last in the list of symbolic names provided
     * by the vocabulary. They are not part of the public API and are only
     * used as components of parser rules.
     */
    private static final List<String> INTERNAL_TOKENS = List.of(
            "DECIMAL_LITERAL_LONG",
            "DECIMAL_LITERAL",
            "HEX_LITERAL_LONG",
            "HEX_LITERAL",
            "OCT_LITERAL_LONG",
            "OCT_LITERAL",
            "BINARY_LITERAL_LONG",
            "BINARY_LITERAL"
    );

    /**
     * New tokens must be added onto the end of the list with new numbers, and
     * old tokens must remain and keep their current numbering. Old token
     * numberings are not allowed to change.
     *
     * <p>
     * The reason behind this is Java inlines static final field values directly
     * into the compiled Java code. This loses all connections with the original
     * class, JavaLanguageLexer, and so numbering updates are not picked
     * up in user-created checks and causes conflicts.
     * </p>
     *
     * <p>
     * Issue: <a href="https://github.com/checkstyle/checkstyle/issues/505">#505</a>
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
        assertWithMessage(message)
            .that(JavaLanguageLexer.COMPILATION_UNIT)
            .isEqualTo(1);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PLACEHOLDER1)
            .isEqualTo(2);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NULL_TREE_LOOKAHEAD)
            .isEqualTo(3);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BLOCK)
            .isEqualTo(4);
        assertWithMessage(message)
            .that(JavaLanguageLexer.MODIFIERS)
            .isEqualTo(5);
        assertWithMessage(message)
            .that(JavaLanguageLexer.OBJBLOCK)
            .isEqualTo(6);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SLIST)
            .isEqualTo(7);
        assertWithMessage(message)
            .that(JavaLanguageLexer.CTOR_DEF)
            .isEqualTo(8);
        assertWithMessage(message)
            .that(JavaLanguageLexer.METHOD_DEF)
            .isEqualTo(9);
        assertWithMessage(message)
            .that(JavaLanguageLexer.VARIABLE_DEF)
            .isEqualTo(10);
        assertWithMessage(message)
            .that(JavaLanguageLexer.INSTANCE_INIT)
            .isEqualTo(11);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STATIC_INIT)
            .isEqualTo(12);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE)
            .isEqualTo(13);
        assertWithMessage(message)
            .that(JavaLanguageLexer.CLASS_DEF)
            .isEqualTo(14);
        assertWithMessage(message)
            .that(JavaLanguageLexer.INTERFACE_DEF)
            .isEqualTo(15);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PACKAGE_DEF)
            .isEqualTo(16);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ARRAY_DECLARATOR)
            .isEqualTo(17);
        assertWithMessage(message)
            .that(JavaLanguageLexer.EXTENDS_CLAUSE)
            .isEqualTo(18);
        assertWithMessage(message)
            .that(JavaLanguageLexer.IMPLEMENTS_CLAUSE)
            .isEqualTo(19);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PARAMETERS)
            .isEqualTo(20);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PARAMETER_DEF)
            .isEqualTo(21);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LABELED_STAT)
            .isEqualTo(22);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPECAST)
            .isEqualTo(23);
        assertWithMessage(message)
            .that(JavaLanguageLexer.INDEX_OP)
            .isEqualTo(24);
        assertWithMessage(message)
            .that(JavaLanguageLexer.POST_INC)
            .isEqualTo(25);
        assertWithMessage(message)
            .that(JavaLanguageLexer.POST_DEC)
            .isEqualTo(26);
        assertWithMessage(message)
            .that(JavaLanguageLexer.METHOD_CALL)
            .isEqualTo(27);
        assertWithMessage(message)
            .that(JavaLanguageLexer.EXPR)
            .isEqualTo(28);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ARRAY_INIT)
            .isEqualTo(29);
        assertWithMessage(message)
            .that(JavaLanguageLexer.IMPORT)
            .isEqualTo(30);
        assertWithMessage(message)
            .that(JavaLanguageLexer.UNARY_MINUS)
            .isEqualTo(31);
        assertWithMessage(message)
            .that(JavaLanguageLexer.UNARY_PLUS)
            .isEqualTo(32);
        assertWithMessage(message)
            .that(JavaLanguageLexer.CASE_GROUP)
            .isEqualTo(33);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ELIST)
            .isEqualTo(34);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FOR_INIT)
            .isEqualTo(35);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FOR_CONDITION)
            .isEqualTo(36);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FOR_ITERATOR)
            .isEqualTo(37);
        assertWithMessage(message)
            .that(JavaLanguageLexer.EMPTY_STAT)
            .isEqualTo(38);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FINAL)
            .isEqualTo(39);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ABSTRACT)
            .isEqualTo(40);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STRICTFP)
            .isEqualTo(41);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SUPER_CTOR_CALL)
            .isEqualTo(42);
        assertWithMessage(message)
            .that(JavaLanguageLexer.CTOR_CALL)
            .isEqualTo(43);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_PACKAGE)
            .isEqualTo(44);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SEMI)
            .isEqualTo(45);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_IMPORT)
            .isEqualTo(46);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LBRACK)
            .isEqualTo(47);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RBRACK)
            .isEqualTo(48);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_VOID)
            .isEqualTo(49);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_BOOLEAN)
            .isEqualTo(50);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_BYTE)
            .isEqualTo(51);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_CHAR)
            .isEqualTo(52);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_SHORT)
            .isEqualTo(53);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_INT)
            .isEqualTo(54);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_FLOAT)
            .isEqualTo(55);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_LONG)
            .isEqualTo(56);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_DOUBLE)
            .isEqualTo(57);
        assertWithMessage(message)
            .that(JavaLanguageLexer.IDENT)
            .isEqualTo(58);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DOT)
            .isEqualTo(59);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STAR)
            .isEqualTo(60);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_PRIVATE)
            .isEqualTo(61);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_PUBLIC)
            .isEqualTo(62);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_PROTECTED)
            .isEqualTo(63);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_STATIC)
            .isEqualTo(64);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_TRANSIENT)
            .isEqualTo(65);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_NATIVE)
            .isEqualTo(66);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_SYNCHRONIZED)
            .isEqualTo(67);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_VOLATILE)
            .isEqualTo(68);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_CLASS)
            .isEqualTo(69);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_EXTENDS)
            .isEqualTo(70);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_INTERFACE)
            .isEqualTo(71);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LCURLY)
            .isEqualTo(72);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RCURLY)
            .isEqualTo(73);
        assertWithMessage(message)
            .that(JavaLanguageLexer.COMMA)
            .isEqualTo(74);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_IMPLEMENTS)
            .isEqualTo(75);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LPAREN)
            .isEqualTo(76);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RPAREN)
            .isEqualTo(77);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_THIS)
            .isEqualTo(78);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_SUPER)
            .isEqualTo(79);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ASSIGN)
            .isEqualTo(80);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_THROWS)
            .isEqualTo(81);
        assertWithMessage(message)
            .that(JavaLanguageLexer.COLON)
            .isEqualTo(82);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_IF)
            .isEqualTo(83);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_WHILE)
            .isEqualTo(84);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_DO)
            .isEqualTo(85);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_BREAK)
            .isEqualTo(86);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_CONTINUE)
            .isEqualTo(87);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_RETURN)
            .isEqualTo(88);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_SWITCH)
            .isEqualTo(89);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_THROW)
            .isEqualTo(90);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_FOR)
            .isEqualTo(91);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_ELSE)
            .isEqualTo(92);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_CASE)
            .isEqualTo(93);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_DEFAULT)
            .isEqualTo(94);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_TRY)
            .isEqualTo(95);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_CATCH)
            .isEqualTo(96);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_FINALLY)
            .isEqualTo(97);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PLUS_ASSIGN)
            .isEqualTo(98);
        assertWithMessage(message)
            .that(JavaLanguageLexer.MINUS_ASSIGN)
            .isEqualTo(99);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STAR_ASSIGN)
            .isEqualTo(100);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DIV_ASSIGN)
            .isEqualTo(101);
        assertWithMessage(message)
            .that(JavaLanguageLexer.MOD_ASSIGN)
            .isEqualTo(102);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SR_ASSIGN)
            .isEqualTo(103);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BSR_ASSIGN)
            .isEqualTo(104);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SL_ASSIGN)
            .isEqualTo(105);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BAND_ASSIGN)
            .isEqualTo(106);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BXOR_ASSIGN)
            .isEqualTo(107);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BOR_ASSIGN)
            .isEqualTo(108);
        assertWithMessage(message)
            .that(JavaLanguageLexer.QUESTION)
            .isEqualTo(109);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LOR)
            .isEqualTo(110);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LAND)
            .isEqualTo(111);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BOR)
            .isEqualTo(112);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BXOR)
            .isEqualTo(113);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BAND)
            .isEqualTo(114);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NOT_EQUAL)
            .isEqualTo(115);
        assertWithMessage(message)
            .that(JavaLanguageLexer.EQUAL)
            .isEqualTo(116);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LT)
            .isEqualTo(117);
        assertWithMessage(message)
            .that(JavaLanguageLexer.GT)
            .isEqualTo(118);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LE)
            .isEqualTo(119);
        assertWithMessage(message)
            .that(JavaLanguageLexer.GE)
            .isEqualTo(120);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_INSTANCEOF)
            .isEqualTo(121);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SL)
            .isEqualTo(122);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SR)
            .isEqualTo(123);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BSR)
            .isEqualTo(124);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PLUS)
            .isEqualTo(125);
        assertWithMessage(message)
            .that(JavaLanguageLexer.MINUS)
            .isEqualTo(126);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DIV)
            .isEqualTo(127);
        assertWithMessage(message)
            .that(JavaLanguageLexer.MOD)
            .isEqualTo(128);
        assertWithMessage(message)
            .that(JavaLanguageLexer.INC)
            .isEqualTo(129);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DEC)
            .isEqualTo(130);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BNOT)
            .isEqualTo(131);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LNOT)
            .isEqualTo(132);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_TRUE)
            .isEqualTo(133);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_FALSE)
            .isEqualTo(134);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_NULL)
            .isEqualTo(135);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_NEW)
            .isEqualTo(136);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NUM_INT)
            .isEqualTo(137);
        assertWithMessage(message)
            .that(JavaLanguageLexer.CHAR_LITERAL)
            .isEqualTo(138);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STRING_LITERAL)
            .isEqualTo(139);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NUM_FLOAT)
            .isEqualTo(140);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NUM_LONG)
            .isEqualTo(141);
        assertWithMessage(message)
            .that(JavaLanguageLexer.NUM_DOUBLE)
            .isEqualTo(142);
        assertWithMessage(message)
            .that(JavaLanguageLexer.WS)
            .isEqualTo(143);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SINGLE_LINE_COMMENT)
            .isEqualTo(144);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BLOCK_COMMENT_BEGIN)
            .isEqualTo(145);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ESC)
            .isEqualTo(146);
        assertWithMessage(message)
            .that(JavaLanguageLexer.HEX_DIGIT)
            .isEqualTo(147);
        assertWithMessage(message)
            .that(JavaLanguageLexer.VOCAB)
            .isEqualTo(148);
        assertWithMessage(message)
            .that(JavaLanguageLexer.EXPONENT)
            .isEqualTo(149);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FLOAT_SUFFIX)
            .isEqualTo(150);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ASSERT)
            .isEqualTo(151);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STATIC_IMPORT)
            .isEqualTo(152);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ENUM)
            .isEqualTo(153);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ENUM_DEF)
            .isEqualTo(154);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ENUM_CONSTANT_DEF)
            .isEqualTo(155);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FOR_EACH_CLAUSE)
            .isEqualTo(156);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATION_DEF)
            .isEqualTo(157);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATIONS)
            .isEqualTo(158);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATION)
            .isEqualTo(159);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATION_MEMBER_VALUE_PAIR)
            .isEqualTo(160);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATION_FIELD_DEF)
            .isEqualTo(161);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ANNOTATION_ARRAY_INIT)
            .isEqualTo(162);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_ARGUMENTS)
            .isEqualTo(163);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_ARGUMENT)
            .isEqualTo(164);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_PARAMETERS)
            .isEqualTo(165);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_PARAMETER)
            .isEqualTo(166);
        assertWithMessage(message)
            .that(JavaLanguageLexer.WILDCARD_TYPE)
            .isEqualTo(167);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_UPPER_BOUNDS)
            .isEqualTo(168);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_LOWER_BOUNDS)
            .isEqualTo(169);
        assertWithMessage(message)
            .that(JavaLanguageLexer.AT)
            .isEqualTo(170);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ELLIPSIS)
            .isEqualTo(171);
        assertWithMessage(message)
            .that(JavaLanguageLexer.GENERIC_START)
            .isEqualTo(172);
        assertWithMessage(message)
            .that(JavaLanguageLexer.GENERIC_END)
            .isEqualTo(173);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TYPE_EXTENSION_AND)
            .isEqualTo(174);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DO_WHILE)
            .isEqualTo(175);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RESOURCE_SPECIFICATION)
            .isEqualTo(176);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RESOURCES)
            .isEqualTo(177);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RESOURCE)
            .isEqualTo(178);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DOUBLE_COLON)
            .isEqualTo(179);
        assertWithMessage(message)
            .that(JavaLanguageLexer.METHOD_REF)
            .isEqualTo(180);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LAMBDA)
            .isEqualTo(181);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BLOCK_COMMENT_END)
            .isEqualTo(182);
        assertWithMessage(message)
            .that(JavaLanguageLexer.COMMENT_CONTENT)
            .isEqualTo(183);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SINGLE_LINE_COMMENT_CONTENT)
            .isEqualTo(184);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BLOCK_COMMENT_CONTENT)
            .isEqualTo(185);
        assertWithMessage(message)
            .that(JavaLanguageLexer.STD_ESC)
            .isEqualTo(186);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BINARY_DIGIT)
            .isEqualTo(187);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ID_START)
            .isEqualTo(188);
        assertWithMessage(message)
            .that(JavaLanguageLexer.ID_PART)
            .isEqualTo(189);
        assertWithMessage(message)
            .that(JavaLanguageLexer.INT_LITERAL)
            .isEqualTo(190);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LONG_LITERAL)
            .isEqualTo(191);
        assertWithMessage(message)
            .that(JavaLanguageLexer.FLOAT_LITERAL)
            .isEqualTo(192);
        assertWithMessage(message)
            .that(JavaLanguageLexer.DOUBLE_LITERAL)
            .isEqualTo(193);
        assertWithMessage(message)
            .that(JavaLanguageLexer.HEX_FLOAT_LITERAL)
            .isEqualTo(194);
        assertWithMessage(message)
            .that(JavaLanguageLexer.HEX_DOUBLE_LITERAL)
            .isEqualTo(195);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SIGNED_INTEGER)
            .isEqualTo(196);
        assertWithMessage(message)
            .that(JavaLanguageLexer.BINARY_EXPONENT)
            .isEqualTo(197);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PATTERN_VARIABLE_DEF)
            .isEqualTo(198);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RECORD_DEF)
            .isEqualTo(199);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_RECORD)
            .isEqualTo(200);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RECORD_COMPONENTS)
            .isEqualTo(201);
        assertWithMessage(message)
            .that(JavaLanguageLexer.RECORD_COMPONENT_DEF)
            .isEqualTo(202);
        assertWithMessage(message)
            .that(JavaLanguageLexer.COMPACT_CTOR_DEF)
            .isEqualTo(203);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TEXT_BLOCK_LITERAL_BEGIN)
            .isEqualTo(204);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TEXT_BLOCK_CONTENT)
            .isEqualTo(205);
        assertWithMessage(message)
            .that(JavaLanguageLexer.TEXT_BLOCK_LITERAL_END)
            .isEqualTo(206);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_YIELD)
            .isEqualTo(207);
        assertWithMessage(message)
            .that(JavaLanguageLexer.SWITCH_RULE)
            .isEqualTo(208);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_NON_SEALED)
            .isEqualTo(209);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_SEALED)
            .isEqualTo(210);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_PERMITS)
            .isEqualTo(211);
        assertWithMessage(message)
            .that(JavaLanguageLexer.PERMITS_CLAUSE)
            .isEqualTo(212);
        assertWithMessage(message)
             .that(JavaLanguageLexer.PATTERN_DEF)
             .isEqualTo(213);
        assertWithMessage(message)
             .that(JavaLanguageLexer.LITERAL_WHEN)
             .isEqualTo(214);
        assertWithMessage(message)
             .that(JavaLanguageLexer.RECORD_PATTERN_DEF)
             .isEqualTo(215);
        assertWithMessage(message)
             .that(JavaLanguageLexer.RECORD_PATTERN_COMPONENTS)
             .isEqualTo(216);
        assertWithMessage(message)
            .that(JavaLanguageLexer.LITERAL_UNDERSCORE)
            .isEqualTo(224);
        assertWithMessage(message)
            .that(JavaLanguageLexer.UNNAMED_PATTERN_DEF)
            .isEqualTo(225);

        final Set<String> modeNames = Set.of(JavaLanguageLexer.modeNames);
        final Set<String> channelNames = Set.of(JavaLanguageLexer.channelNames);

        final int tokenCount = (int) Arrays.stream(JavaLanguageLexer.class.getDeclaredFields())
                .filter(GeneratedJavaTokenTypesTest::isPublicStaticFinalInt)
                .filter(field -> !modeNames.contains(field.getName()))
                .filter(field -> !channelNames.contains(field.getName()))
                .filter(field -> !INTERNAL_TOKENS.contains(field.getName()))
                .count();

        // Read JavaDoc before changing count below, the count should be equal to
        // the number of the last token asserted above.
        assertWithMessage("all tokens must be added to list in"
                        + " 'GeneratedJavaTokenTypesTest' and verified"
                        + " that their old numbering didn't change")
            .that(tokenCount)
            .isEqualTo(225);
    }

    /**
     * This test was created to make sure that new tokens are added to the 'tokens'
     * block in the lexer grammar. If a new token is not added at the end of the list,
     * it will become "mixed in" with the unused tokens and cause
     * Collections#lastIndexOfSubList to return a -1 and fail the test.
     */
    @Test
    public void testTokenHasBeenAddedToTokensBlockInLexerGrammar() {
        final VocabularyImpl vocabulary = (VocabularyImpl) JavaLanguageLexer.VOCABULARY;
        final String[] nullableSymbolicNames = vocabulary.getSymbolicNames();
        final List<String> allTokenNames = Arrays.stream(nullableSymbolicNames)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());

        // Get the starting index of the sublist of tokens, or -1 if sublist
        // is not present.
        final int lastIndexOfSublist =
                Collections.lastIndexOfSubList(allTokenNames, INTERNAL_TOKENS);
        final int expectedNumberOfUsedTokens = allTokenNames.size() - INTERNAL_TOKENS.size();
        final String message = "New tokens must be added to the 'tokens' block in the"
                + " lexer grammar.";

        assertWithMessage(message)
                .that(expectedNumberOfUsedTokens)
                .isEqualTo(lastIndexOfSublist);
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
