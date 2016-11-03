////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars;

import org.junit.Assert;
import org.junit.Test;

public class GeneratedJavaTokenTypesTest {
    /**
     * <p>
     * New tokens must be added onto the end of the list with new numbers, and
     * old tokens must remain and keep their current numbering. Old token
     * numberings are not allowed to change.
     * </p>
     * <p>
     * The reason behind this is Java inlines static final field values directly
     * into the compiled Java code. This loses all connections with the original
     * class, GeneratedJavaTokenTypes, and so numbering updates are not picked
     * up in user-created checks and causes conflicts.
     * </p>
     *
     * Issue: https://github.com/checkstyle/checkstyle/issues/505
     */
    @Test
    public void testTokenNumbering() {
        final String message = "A token's number has changed. Please open"
                + " 'GeneratedJavaTokenTypesTest' and confirm which token is at fault.\n"
                + "Token numbers must not change or else they will create a conflict"
                + " with users.\n\n"
                + "See Issue: https://github.com/checkstyle/checkstyle/issues/505";

        // Read JavaDoc before changing
        Assert.assertEquals(message, 1, GeneratedJavaTokenTypes.EOF);
        Assert.assertEquals(message, 3, GeneratedJavaTokenTypes.NULL_TREE_LOOKAHEAD);
        Assert.assertEquals(message, 4, GeneratedJavaTokenTypes.BLOCK);
        Assert.assertEquals(message, 5, GeneratedJavaTokenTypes.MODIFIERS);
        Assert.assertEquals(message, 6, GeneratedJavaTokenTypes.OBJBLOCK);
        Assert.assertEquals(message, 7, GeneratedJavaTokenTypes.SLIST);
        Assert.assertEquals(message, 8, GeneratedJavaTokenTypes.CTOR_DEF);
        Assert.assertEquals(message, 9, GeneratedJavaTokenTypes.METHOD_DEF);
        Assert.assertEquals(message, 10, GeneratedJavaTokenTypes.VARIABLE_DEF);
        Assert.assertEquals(message, 11, GeneratedJavaTokenTypes.INSTANCE_INIT);
        Assert.assertEquals(message, 12, GeneratedJavaTokenTypes.STATIC_INIT);
        Assert.assertEquals(message, 13, GeneratedJavaTokenTypes.TYPE);
        Assert.assertEquals(message, 14, GeneratedJavaTokenTypes.CLASS_DEF);
        Assert.assertEquals(message, 15, GeneratedJavaTokenTypes.INTERFACE_DEF);
        Assert.assertEquals(message, 16, GeneratedJavaTokenTypes.PACKAGE_DEF);
        Assert.assertEquals(message, 17, GeneratedJavaTokenTypes.ARRAY_DECLARATOR);
        Assert.assertEquals(message, 18, GeneratedJavaTokenTypes.EXTENDS_CLAUSE);
        Assert.assertEquals(message, 19, GeneratedJavaTokenTypes.IMPLEMENTS_CLAUSE);
        Assert.assertEquals(message, 20, GeneratedJavaTokenTypes.PARAMETERS);
        Assert.assertEquals(message, 21, GeneratedJavaTokenTypes.PARAMETER_DEF);
        Assert.assertEquals(message, 22, GeneratedJavaTokenTypes.LABELED_STAT);
        Assert.assertEquals(message, 23, GeneratedJavaTokenTypes.TYPECAST);
        Assert.assertEquals(message, 24, GeneratedJavaTokenTypes.INDEX_OP);
        Assert.assertEquals(message, 25, GeneratedJavaTokenTypes.POST_INC);
        Assert.assertEquals(message, 26, GeneratedJavaTokenTypes.POST_DEC);
        Assert.assertEquals(message, 27, GeneratedJavaTokenTypes.METHOD_CALL);
        Assert.assertEquals(message, 28, GeneratedJavaTokenTypes.EXPR);
        Assert.assertEquals(message, 29, GeneratedJavaTokenTypes.ARRAY_INIT);
        Assert.assertEquals(message, 30, GeneratedJavaTokenTypes.IMPORT);
        Assert.assertEquals(message, 31, GeneratedJavaTokenTypes.UNARY_MINUS);
        Assert.assertEquals(message, 32, GeneratedJavaTokenTypes.UNARY_PLUS);
        Assert.assertEquals(message, 33, GeneratedJavaTokenTypes.CASE_GROUP);
        Assert.assertEquals(message, 34, GeneratedJavaTokenTypes.ELIST);
        Assert.assertEquals(message, 35, GeneratedJavaTokenTypes.FOR_INIT);
        Assert.assertEquals(message, 36, GeneratedJavaTokenTypes.FOR_CONDITION);
        Assert.assertEquals(message, 37, GeneratedJavaTokenTypes.FOR_ITERATOR);
        Assert.assertEquals(message, 38, GeneratedJavaTokenTypes.EMPTY_STAT);
        Assert.assertEquals(message, 39, GeneratedJavaTokenTypes.FINAL);
        Assert.assertEquals(message, 40, GeneratedJavaTokenTypes.ABSTRACT);
        Assert.assertEquals(message, 41, GeneratedJavaTokenTypes.STRICTFP);
        Assert.assertEquals(message, 42, GeneratedJavaTokenTypes.SUPER_CTOR_CALL);
        Assert.assertEquals(message, 43, GeneratedJavaTokenTypes.CTOR_CALL);
        Assert.assertEquals(message, 44, GeneratedJavaTokenTypes.LITERAL_package);
        Assert.assertEquals(message, 45, GeneratedJavaTokenTypes.SEMI);
        Assert.assertEquals(message, 46, GeneratedJavaTokenTypes.LITERAL_import);
        Assert.assertEquals(message, 47, GeneratedJavaTokenTypes.LBRACK);
        Assert.assertEquals(message, 48, GeneratedJavaTokenTypes.RBRACK);
        Assert.assertEquals(message, 49, GeneratedJavaTokenTypes.LITERAL_void);
        Assert.assertEquals(message, 50, GeneratedJavaTokenTypes.LITERAL_boolean);
        Assert.assertEquals(message, 51, GeneratedJavaTokenTypes.LITERAL_byte);
        Assert.assertEquals(message, 52, GeneratedJavaTokenTypes.LITERAL_char);
        Assert.assertEquals(message, 53, GeneratedJavaTokenTypes.LITERAL_short);
        Assert.assertEquals(message, 54, GeneratedJavaTokenTypes.LITERAL_int);
        Assert.assertEquals(message, 55, GeneratedJavaTokenTypes.LITERAL_float);
        Assert.assertEquals(message, 56, GeneratedJavaTokenTypes.LITERAL_long);
        Assert.assertEquals(message, 57, GeneratedJavaTokenTypes.LITERAL_double);
        Assert.assertEquals(message, 58, GeneratedJavaTokenTypes.IDENT);
        Assert.assertEquals(message, 59, GeneratedJavaTokenTypes.DOT);
        Assert.assertEquals(message, 60, GeneratedJavaTokenTypes.STAR);
        Assert.assertEquals(message, 61, GeneratedJavaTokenTypes.LITERAL_private);
        Assert.assertEquals(message, 62, GeneratedJavaTokenTypes.LITERAL_public);
        Assert.assertEquals(message, 63, GeneratedJavaTokenTypes.LITERAL_protected);
        Assert.assertEquals(message, 64, GeneratedJavaTokenTypes.LITERAL_static);
        Assert.assertEquals(message, 65, GeneratedJavaTokenTypes.LITERAL_transient);
        Assert.assertEquals(message, 66, GeneratedJavaTokenTypes.LITERAL_native);
        Assert.assertEquals(message, 67, GeneratedJavaTokenTypes.LITERAL_synchronized);
        Assert.assertEquals(message, 68, GeneratedJavaTokenTypes.LITERAL_volatile);
        Assert.assertEquals(message, 69, GeneratedJavaTokenTypes.LITERAL_class);
        Assert.assertEquals(message, 70, GeneratedJavaTokenTypes.LITERAL_extends);
        Assert.assertEquals(message, 71, GeneratedJavaTokenTypes.LITERAL_interface);
        Assert.assertEquals(message, 72, GeneratedJavaTokenTypes.LCURLY);
        Assert.assertEquals(message, 73, GeneratedJavaTokenTypes.RCURLY);
        Assert.assertEquals(message, 74, GeneratedJavaTokenTypes.COMMA);
        Assert.assertEquals(message, 75, GeneratedJavaTokenTypes.LITERAL_implements);
        Assert.assertEquals(message, 76, GeneratedJavaTokenTypes.LPAREN);
        Assert.assertEquals(message, 77, GeneratedJavaTokenTypes.RPAREN);
        Assert.assertEquals(message, 78, GeneratedJavaTokenTypes.LITERAL_this);
        Assert.assertEquals(message, 79, GeneratedJavaTokenTypes.LITERAL_super);
        Assert.assertEquals(message, 80, GeneratedJavaTokenTypes.ASSIGN);
        Assert.assertEquals(message, 81, GeneratedJavaTokenTypes.LITERAL_throws);
        Assert.assertEquals(message, 82, GeneratedJavaTokenTypes.COLON);
        Assert.assertEquals(message, 83, GeneratedJavaTokenTypes.LITERAL_if);
        Assert.assertEquals(message, 84, GeneratedJavaTokenTypes.LITERAL_while);
        Assert.assertEquals(message, 85, GeneratedJavaTokenTypes.LITERAL_do);
        Assert.assertEquals(message, 86, GeneratedJavaTokenTypes.LITERAL_break);
        Assert.assertEquals(message, 87, GeneratedJavaTokenTypes.LITERAL_continue);
        Assert.assertEquals(message, 88, GeneratedJavaTokenTypes.LITERAL_return);
        Assert.assertEquals(message, 89, GeneratedJavaTokenTypes.LITERAL_switch);
        Assert.assertEquals(message, 90, GeneratedJavaTokenTypes.LITERAL_throw);
        Assert.assertEquals(message, 91, GeneratedJavaTokenTypes.LITERAL_for);
        Assert.assertEquals(message, 92, GeneratedJavaTokenTypes.LITERAL_else);
        Assert.assertEquals(message, 93, GeneratedJavaTokenTypes.LITERAL_case);
        Assert.assertEquals(message, 94, GeneratedJavaTokenTypes.LITERAL_default);
        Assert.assertEquals(message, 95, GeneratedJavaTokenTypes.LITERAL_try);
        Assert.assertEquals(message, 96, GeneratedJavaTokenTypes.LITERAL_catch);
        Assert.assertEquals(message, 97, GeneratedJavaTokenTypes.LITERAL_finally);
        Assert.assertEquals(message, 98, GeneratedJavaTokenTypes.PLUS_ASSIGN);
        Assert.assertEquals(message, 99, GeneratedJavaTokenTypes.MINUS_ASSIGN);
        Assert.assertEquals(message, 100, GeneratedJavaTokenTypes.STAR_ASSIGN);
        Assert.assertEquals(message, 101, GeneratedJavaTokenTypes.DIV_ASSIGN);
        Assert.assertEquals(message, 102, GeneratedJavaTokenTypes.MOD_ASSIGN);
        Assert.assertEquals(message, 103, GeneratedJavaTokenTypes.SR_ASSIGN);
        Assert.assertEquals(message, 104, GeneratedJavaTokenTypes.BSR_ASSIGN);
        Assert.assertEquals(message, 105, GeneratedJavaTokenTypes.SL_ASSIGN);
        Assert.assertEquals(message, 106, GeneratedJavaTokenTypes.BAND_ASSIGN);
        Assert.assertEquals(message, 107, GeneratedJavaTokenTypes.BXOR_ASSIGN);
        Assert.assertEquals(message, 108, GeneratedJavaTokenTypes.BOR_ASSIGN);
        Assert.assertEquals(message, 109, GeneratedJavaTokenTypes.QUESTION);
        Assert.assertEquals(message, 110, GeneratedJavaTokenTypes.LOR);
        Assert.assertEquals(message, 111, GeneratedJavaTokenTypes.LAND);
        Assert.assertEquals(message, 112, GeneratedJavaTokenTypes.BOR);
        Assert.assertEquals(message, 113, GeneratedJavaTokenTypes.BXOR);
        Assert.assertEquals(message, 114, GeneratedJavaTokenTypes.BAND);
        Assert.assertEquals(message, 115, GeneratedJavaTokenTypes.NOT_EQUAL);
        Assert.assertEquals(message, 116, GeneratedJavaTokenTypes.EQUAL);
        Assert.assertEquals(message, 117, GeneratedJavaTokenTypes.LT);
        Assert.assertEquals(message, 118, GeneratedJavaTokenTypes.GT);
        Assert.assertEquals(message, 119, GeneratedJavaTokenTypes.LE);
        Assert.assertEquals(message, 120, GeneratedJavaTokenTypes.GE);
        Assert.assertEquals(message, 121, GeneratedJavaTokenTypes.LITERAL_instanceof);
        Assert.assertEquals(message, 122, GeneratedJavaTokenTypes.SL);
        Assert.assertEquals(message, 123, GeneratedJavaTokenTypes.SR);
        Assert.assertEquals(message, 124, GeneratedJavaTokenTypes.BSR);
        Assert.assertEquals(message, 125, GeneratedJavaTokenTypes.PLUS);
        Assert.assertEquals(message, 126, GeneratedJavaTokenTypes.MINUS);
        Assert.assertEquals(message, 127, GeneratedJavaTokenTypes.DIV);
        Assert.assertEquals(message, 128, GeneratedJavaTokenTypes.MOD);
        Assert.assertEquals(message, 129, GeneratedJavaTokenTypes.INC);
        Assert.assertEquals(message, 130, GeneratedJavaTokenTypes.DEC);
        Assert.assertEquals(message, 131, GeneratedJavaTokenTypes.BNOT);
        Assert.assertEquals(message, 132, GeneratedJavaTokenTypes.LNOT);
        Assert.assertEquals(message, 133, GeneratedJavaTokenTypes.LITERAL_true);
        Assert.assertEquals(message, 134, GeneratedJavaTokenTypes.LITERAL_false);
        Assert.assertEquals(message, 135, GeneratedJavaTokenTypes.LITERAL_null);
        Assert.assertEquals(message, 136, GeneratedJavaTokenTypes.LITERAL_new);
        Assert.assertEquals(message, 137, GeneratedJavaTokenTypes.NUM_INT);
        Assert.assertEquals(message, 138, GeneratedJavaTokenTypes.CHAR_LITERAL);
        Assert.assertEquals(message, 139, GeneratedJavaTokenTypes.STRING_LITERAL);
        Assert.assertEquals(message, 140, GeneratedJavaTokenTypes.NUM_FLOAT);
        Assert.assertEquals(message, 141, GeneratedJavaTokenTypes.NUM_LONG);
        Assert.assertEquals(message, 142, GeneratedJavaTokenTypes.NUM_DOUBLE);
        Assert.assertEquals(message, 143, GeneratedJavaTokenTypes.WS);
        Assert.assertEquals(message, 144, GeneratedJavaTokenTypes.SINGLE_LINE_COMMENT);
        Assert.assertEquals(message, 145, GeneratedJavaTokenTypes.BLOCK_COMMENT_BEGIN);
        Assert.assertEquals(message, 146, GeneratedJavaTokenTypes.ESC);
        Assert.assertEquals(message, 147, GeneratedJavaTokenTypes.HEX_DIGIT);
        Assert.assertEquals(message, 148, GeneratedJavaTokenTypes.VOCAB);
        Assert.assertEquals(message, 149, GeneratedJavaTokenTypes.EXPONENT);
        Assert.assertEquals(message, 150, GeneratedJavaTokenTypes.FLOAT_SUFFIX);
        Assert.assertEquals(message, 151, GeneratedJavaTokenTypes.ASSERT);
        Assert.assertEquals(message, 152, GeneratedJavaTokenTypes.STATIC_IMPORT);
        Assert.assertEquals(message, 153, GeneratedJavaTokenTypes.ENUM);
        Assert.assertEquals(message, 154, GeneratedJavaTokenTypes.ENUM_DEF);
        Assert.assertEquals(message, 155, GeneratedJavaTokenTypes.ENUM_CONSTANT_DEF);
        Assert.assertEquals(message, 156, GeneratedJavaTokenTypes.FOR_EACH_CLAUSE);
        Assert.assertEquals(message, 157, GeneratedJavaTokenTypes.ANNOTATION_DEF);
        Assert.assertEquals(message, 158, GeneratedJavaTokenTypes.ANNOTATIONS);
        Assert.assertEquals(message, 159, GeneratedJavaTokenTypes.ANNOTATION);
        Assert.assertEquals(message, 160, GeneratedJavaTokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);
        Assert.assertEquals(message, 161, GeneratedJavaTokenTypes.ANNOTATION_FIELD_DEF);
        Assert.assertEquals(message, 162, GeneratedJavaTokenTypes.ANNOTATION_ARRAY_INIT);
        Assert.assertEquals(message, 163, GeneratedJavaTokenTypes.TYPE_ARGUMENTS);
        Assert.assertEquals(message, 164, GeneratedJavaTokenTypes.TYPE_ARGUMENT);
        Assert.assertEquals(message, 165, GeneratedJavaTokenTypes.TYPE_PARAMETERS);
        Assert.assertEquals(message, 166, GeneratedJavaTokenTypes.TYPE_PARAMETER);
        Assert.assertEquals(message, 167, GeneratedJavaTokenTypes.WILDCARD_TYPE);
        Assert.assertEquals(message, 168, GeneratedJavaTokenTypes.TYPE_UPPER_BOUNDS);
        Assert.assertEquals(message, 169, GeneratedJavaTokenTypes.TYPE_LOWER_BOUNDS);
        Assert.assertEquals(message, 170, GeneratedJavaTokenTypes.AT);
        Assert.assertEquals(message, 171, GeneratedJavaTokenTypes.ELLIPSIS);
        Assert.assertEquals(message, 172, GeneratedJavaTokenTypes.GENERIC_START);
        Assert.assertEquals(message, 173, GeneratedJavaTokenTypes.GENERIC_END);
        Assert.assertEquals(message, 174, GeneratedJavaTokenTypes.TYPE_EXTENSION_AND);
        Assert.assertEquals(message, 175, GeneratedJavaTokenTypes.DO_WHILE);
        Assert.assertEquals(message, 176, GeneratedJavaTokenTypes.RESOURCE_SPECIFICATION);
        Assert.assertEquals(message, 177, GeneratedJavaTokenTypes.RESOURCES);
        Assert.assertEquals(message, 178, GeneratedJavaTokenTypes.RESOURCE);
        Assert.assertEquals(message, 179, GeneratedJavaTokenTypes.DOUBLE_COLON);
        Assert.assertEquals(message, 180, GeneratedJavaTokenTypes.METHOD_REF);
        Assert.assertEquals(message, 181, GeneratedJavaTokenTypes.LAMBDA);
        Assert.assertEquals(message, 182, GeneratedJavaTokenTypes.BLOCK_COMMENT_END);
        Assert.assertEquals(message, 183, GeneratedJavaTokenTypes.COMMENT_CONTENT);
        Assert.assertEquals(message, 184, GeneratedJavaTokenTypes.SINGLE_LINE_COMMENT_CONTENT);
        Assert.assertEquals(message, 185, GeneratedJavaTokenTypes.BLOCK_COMMENT_CONTENT);
        Assert.assertEquals(message, 186, GeneratedJavaTokenTypes.STD_ESC);
        Assert.assertEquals(message, 187, GeneratedJavaTokenTypes.BINARY_DIGIT);
        Assert.assertEquals(message, 188, GeneratedJavaTokenTypes.ID_START);
        Assert.assertEquals(message, 189, GeneratedJavaTokenTypes.ID_PART);
        Assert.assertEquals(message, 190, GeneratedJavaTokenTypes.INT_LITERAL);
        Assert.assertEquals(message, 191, GeneratedJavaTokenTypes.LONG_LITERAL);
        Assert.assertEquals(message, 192, GeneratedJavaTokenTypes.FLOAT_LITERAL);
        Assert.assertEquals(message, 193, GeneratedJavaTokenTypes.DOUBLE_LITERAL);
        Assert.assertEquals(message, 194, GeneratedJavaTokenTypes.HEX_FLOAT_LITERAL);
        Assert.assertEquals(message, 195, GeneratedJavaTokenTypes.HEX_DOUBLE_LITERAL);
        Assert.assertEquals(message, 196, GeneratedJavaTokenTypes.SIGNED_INTEGER);
        Assert.assertEquals(message, 197, GeneratedJavaTokenTypes.BINARY_EXPONENT);
        // Read JavaDoc before changing
        Assert.assertEquals("all tokens must be added to list in"
                + " 'GeneratedJavaTokenTypesTest' and verified"
                + " that their old numbering didn't change", 196,
                GeneratedJavaTokenTypes.class.getDeclaredFields().length);
    }
}
