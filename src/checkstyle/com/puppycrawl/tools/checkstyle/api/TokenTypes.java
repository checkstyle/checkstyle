////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;

/**
 * Contains the constants for all the tokens contained in the Abstract Syntax
 * Tree.
 *
 * Implementation detail: This class has been introduced to break the circular
 * dependency being the packages.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class TokenTypes
{
    /** token representing a EOF */
    public static final int EOF = 1;
    /** token representing a NULL_TREE_LOOKAHEAD */
    public static final int NULL_TREE_LOOKAHEAD = 3;
    /** token representing a BLOCK */
    public static final int BLOCK = 4;
    /** token representing a MODIFIERS */
    public static final int MODIFIERS = 5;
    /** token representing a OBJBLOCK */
    public static final int OBJBLOCK = 6;
    /** token representing a SLIST */
    public static final int SLIST = 7;
    /** token representing a CTOR_DEF */
    public static final int CTOR_DEF = 8;
    /** token representing a METHOD_DEF */
    public static final int METHOD_DEF = 9;
    /** token representing a VARIABLE_DEF */
    public static final int VARIABLE_DEF = 10;
    /** token representing a INSTANCE_INIT */
    public static final int INSTANCE_INIT = 11;
    /** token representing a STATIC_INIT */
    public static final int STATIC_INIT = 12;
    /** token representing a TYPE */
    public static final int TYPE = 13;
    /** token representing a CLASS_DEF */
    public static final int CLASS_DEF = 14;
    /** token representing a INTERFACE_DEF */
    public static final int INTERFACE_DEF = 15;
    /** token representing a PACKAGE_DEF */
    public static final int PACKAGE_DEF = 16;
    /** token representing a ARRAY_DECLARATOR */
    public static final int ARRAY_DECLARATOR = 17;
    /** token representing a EXTENDS_CLAUSE */
    public static final int EXTENDS_CLAUSE = 18;
    /** token representing a IMPLEMENTS_CLAUSE */
    public static final int IMPLEMENTS_CLAUSE = 19;
    /** token representing a PARAMETERS */
    public static final int PARAMETERS = 20;
    /** token representing a PARAMETER_DEF */
    public static final int PARAMETER_DEF = 21;
    /** token representing a LABELED_STAT */
    public static final int LABELED_STAT = 22;
    /** token representing a TYPECAST */
    public static final int TYPECAST = 23;
    /** token representing a INDEX_OP */
    public static final int INDEX_OP = 24;
    /** token representing a POST_INC */
    public static final int POST_INC = 25;
    /** token representing a POST_DEC */
    public static final int POST_DEC = 26;
    /** token representing a METHOD_CALL */
    public static final int METHOD_CALL = 27;
    /** token representing a EXPR */
    public static final int EXPR = 28;
    /** token representing a ARRAY_INIT */
    public static final int ARRAY_INIT = 29;
    /** token representing a IMPORT */
    public static final int IMPORT = 30;
    /** token representing a UNARY_MINUS */
    public static final int UNARY_MINUS = 31;
    /** token representing a UNARY_PLUS */
    public static final int UNARY_PLUS = 32;
    /** token representing a CASE_GROUP */
    public static final int CASE_GROUP = 33;
    /** token representing a ELIST */
    public static final int ELIST = 34;
    /** token representing a FOR_INIT */
    public static final int FOR_INIT = 35;
    /** token representing a FOR_CONDITION */
    public static final int FOR_CONDITION = 36;
    /** token representing a FOR_ITERATOR */
    public static final int FOR_ITERATOR = 37;
    /** token representing a EMPTY_STAT */
    public static final int EMPTY_STAT = 38;
    /** token representing a FINAL */
    public static final int FINAL = 39;
    /** token representing a ABSTRACT */
    public static final int ABSTRACT = 40;
    /** token representing a STRICTFP */
    public static final int STRICTFP = 41;
    /** token representing a SUPER_CTOR_CALL */
    public static final int SUPER_CTOR_CALL = 42;
    /** token representing a CTOR_CALL */
    public static final int CTOR_CALL = 43;
    /** token representing a LITERAL_package */
    public static final int LITERAL_PACKAGE = 44;
    /** token representing a SEMI */
    public static final int SEMI = 45;
    /** token representing a LITERAL_import */
    public static final int LITERAL_IMPORT = 46;
    /** token representing a LBRACK */
    public static final int LBRACK = 47;
    /** token representing a RBRACK */
    public static final int RBRACK = 48;
    /** token representing a LITERAL_void */
    public static final int LITERAL_VOID = 49;
    /** token representing a LITERAL_boolean */
    public static final int LITERAL_BOOLEAN = 50;
    /** token representing a LITERAL_byte */
    public static final int LITERAL_BYTE = 51;
    /** token representing a LITERAL_char */
    public static final int LITERAL_CHAR = 52;
    /** token representing a LITERAL_short */
    public static final int LITERAL_SHORT = 53;
    /** token representing a LITERAL_int */
    public static final int LITERAL_INT = 54;
    /** token representing a LITERAL_float */
    public static final int LITERAL_FLOAT = 55;
    /** token representing a LITERAL_long */
    public static final int LITERAL_LONG = 56;
    /** token representing a LITERAL_double */
    public static final int LITERAL_DOUBLE = 57;
    /** token representing a IDENT */
    public static final int IDENT = 58;
    /** token representing a DOT */
    public static final int DOT = 59;
    /** token representing a STAR */
    public static final int STAR = 60;
    /** token representing a LITERAL_private */
    public static final int LITERAL_PRIVATE = 61;
    /** token representing a LITERAL_public */
    public static final int LITERAL_PUBLIC = 62;
    /** token representing a LITERAL_protected */
    public static final int LITERAL_PROTECTED = 63;
    /** token representing a LITERAL_static */
    public static final int LITERAL_STATIC = 64;
    /** token representing a LITERAL_transient */
    public static final int LITERAL_TRANSIENT = 65;
    /** token representing a LITERAL_native */
    public static final int LITERAL_NATIVE = 66;
    /** token representing a LITERAL_threadsafe */
    public static final int LITERAL_THREADSAFE = 67;
    /** token representing a LITERAL_synchronized */
    public static final int LITERAL_SYNCHRONIZED = 68;
    /** token representing a LITERAL_volatile */
    public static final int LITERAL_VOLATILE = 69;
    /** token representing a LITERAL_class */
    public static final int LITERAL_CLASS = 70;
    /** token representing a LITERAL_extends */
    public static final int LITERAL_EXTENDS = 71;
    /** token representing a LITERAL_interface */
    public static final int LITERAL_INTERFACE = 72;
    /** token representing a LCURLY */
    public static final int LCURLY = 73;
    /** token representing a RCURLY */
    public static final int RCURLY = 74;
    /** token representing a COMMA */
    public static final int COMMA = 75;
    /** token representing a LITERAL_implements */
    public static final int LITERAL_IMPLEMENTS = 76;
    /** token representing a LPAREN */
    public static final int LPAREN = 77;
    /** token representing a RPAREN */
    public static final int RPAREN = 78;
    /** token representing a LITERAL_this */
    public static final int LITERAL_THIS = 79;
    /** token representing a LITERAL_super */
    public static final int LITERAL_SUPER = 80;
    /** token representing a ASSIGN */
    public static final int ASSIGN = 81;
    /** token representing a LITERAL_throws */
    public static final int LITERAL_THROWS = 82;
    /** token representing a COLON */
    public static final int COLON = 83;
    /** token representing a LITERAL_if */
    public static final int LITERAL_IF = 84;
    /** token representing a LITERAL_for */
    public static final int LITERAL_FOR = 85;
    /** token representing a LITERAL_while */
    public static final int LITERAL_WHILE = 86;
    /** token representing a LITERAL_do */
    public static final int LITERAL_DO = 87;
    /** token representing a LITERAL_break */
    public static final int LITERAL_BREAK = 88;
    /** token representing a LITERAL_continue */
    public static final int LITERAL_CONTINUE = 89;
    /** token representing a LITERAL_return */
    public static final int LITERAL_RETURN = 90;
    /** token representing a LITERAL_switch */
    public static final int LITERAL_SWITCH = 91;
    /** token representing a LITERAL_throw */
    public static final int LITERAL_THROW = 92;
    /** token representing a LITERAL_else */
    public static final int LITERAL_ELSE = 93;
    /** token representing a LITERAL_case */
    public static final int LITERAL_CASE = 94;
    /** token representing a LITERAL_default */
    public static final int LITERAL_DEFAULT = 95;
    /** token representing a LITERAL_try */
    public static final int LITERAL_TRY = 96;
    /** token representing a LITERAL_catch */
    public static final int LITERAL_CATCH = 97;
    /** token representing a LITERAL_finally */
    public static final int LITERAL_FINALLY = 98;
    /** token representing a PLUS_ASSIGN */
    public static final int PLUS_ASSIGN = 99;
    /** token representing a MINUS_ASSIGN */
    public static final int MINUS_ASSIGN = 100;
    /** token representing a STAR_ASSIGN */
    public static final int STAR_ASSIGN = 101;
    /** token representing a DIV_ASSIGN */
    public static final int DIV_ASSIGN = 102;
    /** token representing a MOD_ASSIGN */
    public static final int MOD_ASSIGN = 103;
    /** token representing a SR_ASSIGN */
    public static final int SR_ASSIGN = 104;
    /** token representing a BSR_ASSIGN */
    public static final int BSR_ASSIGN = 105;
    /** token representing a SL_ASSIGN */
    public static final int SL_ASSIGN = 106;
    /** token representing a BAND_ASSIGN */
    public static final int BAND_ASSIGN = 107;
    /** token representing a BXOR_ASSIGN */
    public static final int BXOR_ASSIGN = 108;
    /** token representing a BOR_ASSIGN */
    public static final int BOR_ASSIGN = 109;
    /** token representing a QUESTION */
    public static final int QUESTION = 110;
    /** token representing a LOR */
    public static final int LOR = 111;
    /** token representing a LAND */
    public static final int LAND = 112;
    /** token representing a BOR */
    public static final int BOR = 113;
    /** token representing a BXOR */
    public static final int BXOR = 114;
    /** token representing a BAND */
    public static final int BAND = 115;
    /** token representing a NOT_EQUAL */
    public static final int NOT_EQUAL = 116;
    /** token representing a EQUAL */
    public static final int EQUAL = 117;
    /** token representing a LT */
    public static final int LT = 118;
    /** token representing a GT */
    public static final int GT = 119;
    /** token representing a LE */
    public static final int LE = 120;
    /** token representing a GE */
    public static final int GE = 121;
    /** token representing a LITERAL_instanceof */
    public static final int LITERAL_INSTANCEOF = 122;
    /** token representing a SL */
    public static final int SL = 123;
    /** token representing a SR */
    public static final int SR = 124;
    /** token representing a BSR */
    public static final int BSR = 125;
    /** token representing a PLUS */
    public static final int PLUS = 126;
    /** token representing a MINUS */
    public static final int MINUS = 127;
    /** token representing a DIV */
    public static final int DIV = 128;
    /** token representing a MOD */
    public static final int MOD = 129;
    /** token representing a INC */
    public static final int INC = 130;
    /** token representing a DEC */
    public static final int DEC = 131;
    /** token representing a BNOT */
    public static final int BNOT = 132;
    /** token representing a LNOT */
    public static final int LNOT = 133;
    /** token representing a LITERAL_true */
    public static final int LITERAL_TRUE = 134;
    /** token representing a LITERAL_false */
    public static final int LITERAL_FALSE = 135;
    /** token representing a LITERAL_null */
    public static final int LITERAL_NULL = 136;
    /** token representing a LITERAL_new */
    public static final int LITERAL_NEW = 137;
    /** token representing a NUM_INT */
    public static final int NUM_INT = 138;
    /** token representing a CHAR_LITERAL */
    public static final int CHAR_LITERAL = 139;
    /** token representing a STRING_LITERAL */
    public static final int STRING_LITERAL = 140;
    /** token representing a NUM_FLOAT */
    public static final int NUM_FLOAT = 141;
    /** token representing a NUM_LONG */
    public static final int NUM_LONG = 142;
    /** token representing a NUM_DOUBLE */
    public static final int NUM_DOUBLE = 143;
    /** token representing a WS */
    public static final int WS = 144;
    /** token representing a SL_COMMENT */
    public static final int SL_COMMENT = 145;
    /** token representing a ML_COMMENT */
    public static final int ML_COMMENT = 146;
    /** token representing a ESC */
    public static final int ESC = 147;
    /** token representing a HEX_DIGIT */
    public static final int HEX_DIGIT = 148;
    /** token representing a VOCAB */
    public static final int VOCAB = 149;
    /** token representing a EXPONENT */
    public static final int EXPONENT = 150;
    /** token representing a FLOAT_SUFFIX */
    public static final int FLOAT_SUFFIX = 151;
    /** token representing a ASSERT */
    public static final int ASSERT = 152;

    ////////////////////////////////////////////////////////////////////////
    // The interesting code goes here
    ////////////////////////////////////////////////////////////////////////
    
        /** maps from a token name to value */
    private static final Map TOKEN_NAME_TO_VALUE = new HashMap();
    /** maps from a token value to name */
    private static final String[] TOKEN_VALUE_TO_NAME;

    // initialise the constants
    static {
        final Field[] fields = TokenTypes.class.getDeclaredFields();
        String[] tempTokenValueToName = new String[0];
        for (int i = 0; i < fields.length; i++) {
            final Field f = fields[i];

            // Only process the int declarations.
            if (f.getType() != Integer.TYPE) {
                continue;
            }

            final String name = f.getName();
            try {
                // this should NEVER fail (famous last words)
                final Integer value = new Integer(f.getInt(name));
                TOKEN_NAME_TO_VALUE.put(name, value);
                final int tokenValue = value.intValue();
                if (tokenValue > tempTokenValueToName.length - 1) {
                    final String[] temp = new String[tokenValue + 1];
                    System.arraycopy(tempTokenValueToName, 0,
                                     temp, 0, tempTokenValueToName.length);
                    tempTokenValueToName = temp;
                }
                tempTokenValueToName[tokenValue] = name;
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.exit(1);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        TOKEN_VALUE_TO_NAME = tempTokenValueToName;
    }

    /**
     * Returns the name of a token for a given ID.
     * @param aID the ID of the token name to get
     * @return a token name
     */
    public static String getTokenName(int aID)
    {
        if (aID > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException("given id " + aID);
        }
        final String name = TOKEN_VALUE_TO_NAME[aID];
        if (name == null) {
            throw new IllegalArgumentException("given id " + aID);
        }
        return name;
    }

    /**
     * Returns the ID of a token for a given name.
     * @param aName the name of the token ID to get
     * @return a token ID
     */
    public static int getTokenId(String aName)
    {
        final Integer id = (Integer) TOKEN_NAME_TO_VALUE.get(aName);
        if (id == null) {
            throw new IllegalArgumentException("given name " + aName);
        }
        return id.intValue();
    }
}
