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
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree.
 *
 * <p>Implementation detail: This class has been introduced to break
 * the circular dependency between packages.<p>
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author <a href="mailto:dobratzp@ele.uri.edu">Peter Dobratz</a>
 * @version 1.0
 */
public class TokenTypes
{
    /** prevent instantiation */
    private TokenTypes()
    {
    }

    // The following three types are never part of an AST,
    // left here as a reminder so nobody will readd them accidentally

    /* * token representing a NULL_TREE_LOOKAHEAD */
    // public static final int NULL_TREE_LOOKAHEAD = 3;
    /* * token representing a BLOCK */
    // public static final int BLOCK = 4;
    /* * token representing a VOCAB */
    // public static final int VOCAB = 149;

    // These are the types that can actually occur in an AST
    // it makes sense to register Checks for these types

    /**
     * The end of file token.  This is the root node for the source
     * file.  It's children are an optional package definition, zero
     * or more import statements, and one or more class or interface
     * definitions.
     *
     * @see #PACKAGE_DEF
     * @see #IMPORT
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     **/
    public static final int EOF = 1;
    /**
     * Modifiers for type, method, and field declarations.  The
     * modifiers element is always present even though it may have no
     * children.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html">Java
     * Language Specification, Chapter 8</a>
     * @see #LITERAL_PUBLIC
     * @see #LITERAL_PROTECTED
     * @see #LITERAL_PRIVATE
     * @see #ABSTRACT
     * @see #LITERAL_STATIC
     * @see #FINAL
     * @see #LITERAL_TRANSIENT
     * @see #LITERAL_VOLATILE
     * @see #LITERAL_SYNCHRONIZED
     * @see #LITERAL_NATIVE
     * @see #STRICTFP
     **/
    public static final int MODIFIERS = 5;
    /**
     * An object block.  These are children of class and interface
     * declarations.  Also, object blocks are children of the new
     * keyword when defining anonymous inner classes.
     *
     * @see #LCURLY
     * @see #INSTANCE_INIT
     * @see #STATIC_INIT
     * @see #CLASS_DEF
     * @see #CTOR_DEF
     * @see #METHOD_DEF
     * @see #VARIABLE_DEF
     * @see #RCURLY
     * @see #INTERFACE_DEF
     * @see #LITERAL_NEW
     **/
    public static final int OBJBLOCK = 6;
    /**
     * A list of statements.
     *
     * @see #RCURLY
     * @see #EXPR
     * @see #LABELED_STAT
     * @see #LITERAL_THROWS
     * @see #LITERAL_RETURN
     * @see #SEMI
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     * @see #LITERAL_FOR
     * @see #LITERAL_WHILE
     * @see #LITERAL_IF
     * @see #LITERAL_ELSE
     * @see #CASE_GROUP
     **/
    public static final int SLIST = 7;
    /**
     * A constructor declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public SpecialEntry(int value, String text)
     * {
     *   this.value = value;
     *   this.text = text;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--CTOR_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--IDENT (SpecialEntry)
     *     +--LPAREN (()
     *     +--PARAMETERS
     *         |
     *         +--PARAMETER_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--LITERAL_INT (int)
     *             +--IDENT (value)
     *         +--COMMA (,)
     *         +--PARAMETER_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--IDENT (String)
     *             +--IDENT (text)
     *     +--RPAREN ())
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--DOT (.)
     *                     |
     *                     +--LITERAL_THIS (this)
     *                     +--IDENT (value)
     *                 +--IDENT (value)
     *         +--SEMI (;)
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--DOT (.)
     *                     |
     *                     +--LITERAL_THIS (this)
     *                     +--IDENT (text)
     *                 +--IDENT (text)
     *         +--SEMI (;)
     *         +--RCURLY (})
     * </pre>
     *
     * @see #OBJBLOCK
     * @see #CLASS_DEF
     **/
    public static final int CTOR_DEF = 8;
    /**
     * A method declaration.  The children are modifiers, return type,
     * method name, parameter list, an optional throws list, and
     * statement list.  The statement list is omitted if the method
     * declaration appears in an interface declaration.  Method
     * declarations may appear inside object blocks of class
     * declarations, interface declarations, or anonymous inner-class
     * declarations.
     *
     * <p>For example:</p>
     * 
     * <pre>
     *  public static int square(int x)
     *  {
     *    return x*x;
     *  }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--METHOD_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *         +--LITERAL_STATIC (static)
     *     +--TYPE
     *         |
     *         +--LITERAL_INT (int)
     *     +--IDENT (square)
     *     +--PARAMETERS
     *         |
     *         +--PARAMETER_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--LITERAL_INT (int)
     *             +--IDENT (x)
     *     +--SLIST ({)
     *         |
     *         +--LITERAL_RETURN (return)
     *             |
     *             +--EXPR
     *                 |
     *                 +--STAR (*)
     *                     |
     *                     +--IDENT (x)
     *                     +--IDENT (x)
     *             +--SEMI (;)
     *         +--RCURLY (})
     * </pre>
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #LITERAL_THROWS
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int METHOD_DEF = 9;
    /**
     * A field or local variable declaration.  The children are
     * modifiers, type, the identifier name, and an optional
     * assignment statement.
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #ASSIGN
     **/
    public static final int VARIABLE_DEF = 10;
    /**
     * An instance initializer.  Zero or more instance initializers
     * may appear in class definitions.  This token will be a child of
     * the object block in either a normal or anonymous inner class.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#246032">Java
     * Language Specification&sect;8.6</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int INSTANCE_INIT = 11;
    /**
     * A static initialization block.  Zero or more static
     * initializers may be children of the object block of a class
     * declaration (interfaces cannot have static initializers).  The
     * first and only child is a statement list.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#39245">Java
     * Language Specification, &sect;8.7</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int STATIC_INIT = 12;
    /**
     * A type.  This is either a return type of a method or a type of
     * a variable or field.  The first child of this element is the
     * actual type.  This may be a primitive type, an identifier, a
     * dot which is the root of a fully qualified type, or an array of
     * any of these.
     *
     * @see #VARIABLE_DEF
     * @see #METHOD_DEF
     * @see #PARAMETER_DEF
     * @see #IDENT
     * @see #DOT
     * @see #LITERAL_VOID
     * @see #LITERAL_BOOLEAN
     * @see #LITERAL_BYTE
     * @see #LITERAL_CHAR
     * @see #LITERAL_SHORT
     * @see #LITERAL_INT
     * @see #LITERAL_FLOAT
     * @see #LITERAL_LONG
     * @see #LITERAL_DOUBLE
     * @see #ARRAY_DECLARATOR
     **/
    public static final int TYPE = 13;
    /**
     * A class declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public class MyClass
     *   implements Serializable
     * {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--CLASS_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--IDENT (MyClass)
     *     +--EXTENDS_CLAUSE
     *     +--IMPLEMENTS_CLAUSE
     *         |
     *         +--IDENT (Serializable)
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html">Java
     * Language Specification, Chapter 8</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     **/
    public static final int CLASS_DEF = 14;
    /**
     * An interface declaration.
     * 
     * <p>For example:</p>
     *
     * <pre>
     *   public interface MyInterface
     *   {
     *   }
     *
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--INTERFACE_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--IDENT (MyInterface)
     *     +--EXTENDS_CLAUSE
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/interfaces.doc.html">Java
     * Language Specification, Chapter 9</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #OBJBLOCK
     **/
    public static final int INTERFACE_DEF = 15;
    /**
     * The package declaration.  This is optional, but if it is
     * included, then there is only one package declaration per source
     * file and it must be the first non-comment in the file.
     * <p>For example:</p>
     *
     * <pre>
     *   package com.puppycrawl.tools.checkstyle.api;
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--PACKAGE_DEF (package)
     *     |
     *     +--DOT (.)
     *         |
     *         +--DOT (.)
     *             |
     *             +--DOT (.)
     *                 |
     *                 +--DOT (.)
     *                     |
     *                     +--IDENT (com)
     *                     +--IDENT (puppycrawl)
     *                 +--IDENT (tools)
     *             +--IDENT (checkstyle)
     *         +--IDENT (api)
     *     +--SEMI (;)
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/packages.doc.html#26619">Java
     * Language Specification &sect;7.4</a>
     * @see #DOT
     * @see #IDENT
     * @see #SEMI
     * @see FullIdent
     **/
    public static final int PACKAGE_DEF = 16;
    /**
     * An array declaration.
     *
     * <p>If the array declaration represents a type, then the type of
     * the array elements is the first child.  Multidimensional arrays
     * may be regarded as arrays of arrays.  In other words, the first
     * child of the array declaration is another array
     * declaration.</p>
     *
     * <p>For example:</p>
     * <pre>
     *   int[] x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--VARIABLE_DEF
     *     |
     *     +--MODIFIERS
     *     +--TYPE
     *         |
     *         +--ARRAY_DECLARATOR ([)
     *             |
     *             +--LITERAL_INT (int)
     *     +--IDENT (x)
     * +--SEMI (;)
     * </pre>
     *
     * <p>The array declaration may also represent an inline array
     * definition.  In this case, the first child will be either an
     * expression specifying the length of the array or an array
     * initialization block.</p>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/arrays.doc.html">Java
     * Language Specification Chapter 10</a>
     * @see #TYPE
     * @see #ARRAY_INIT
     **/
    public static final int ARRAY_DECLARATOR = 17;
    /**
     * An extends clause.  This appear as part of class and interface
     * definitions.  This element appears even if the
     * <code>extends</code> keyword is not explicitly used.  The child
     * is an optional identifier.
     *
     * <p>For example:</p>
     * <pre>
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--EXTENDS_CLAUSE
     *     |
     *     +--DOT (.)
     *         |
     *         +--DOT (.)
     *             |
     *             +--IDENT (java)
     *             +--IDENT (util)
     *         +--IDENT (LinkedList)
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see FullIdent
     **/
    public static final int EXTENDS_CLAUSE = 18;
    /**
     * An implements clause.  This always appears in a class
     * declaration, even if there are no implemented interfaces.  The
     * children are a comma separated list of zero or more
     * identifiers.
     *
     * <p>For example:</p>
     * <pre>
     * implements Serializable, Comparable
     * <pre>
     * <p>parses as:</p>
     * <pre>
     * +--IMPLEMENTS_CLAUSE
     *     |
     *     +--IDENT (Serializable)
     *     +--COMMA (,)
     *     +--IDENT (Comparable)
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #CLASS_DEF
     **/
    public static final int IMPLEMENTS_CLAUSE = 19;
    /**
     * A list of parameters to a method or constructor.  The children
     * are zero or more parameter declarations separated by commas.
     *
     * <p>For example</p>
     * <pre>
     * int start, int end
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--PARAMETERS
     *     |
     *     +--PARAMETER_DEF
     *         |
     *         +--MODIFIERS
     *         +--TYPE
     *             |
     *             +--LITERAL_INT (int)
     *         +--IDENT (start)
     *     +--COMMA (,)
     *     +--PARAMETER_DEF
     *         |
     *         +--MODIFIERS
     *         +--TYPE
     *             |
     *             +--LITERAL_INT (int)
     *         +--IDENT (end)
     * </pre>
     *
     * @see #PARAMETER_DEF
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     **/
    public static final int PARAMETERS = 20;
    /**
     * A parameter declaration.
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     **/
    public static final int PARAMETER_DEF = 21;
    /**
     * A labeled statement.
     *
     * <p>For example:</p>
     * <pre>
     * outside: ;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LABELED_STAT (:)
     *     |
     *     +--IDENT (outside)
     *     +--EMPTY_STAT (;)
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/statements.doc.html#78993">Java
     * Language Specification, &sect;14.7</a>
     * @see #SLIST
     **/
    public static final int LABELED_STAT = 22;
    /**
     * A type-cast.
     *
     * <p>For example:</p>
     * <pre>
     * (String)it.next()
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--TYPECAST (()
     *     |
     *     +--TYPE
     *         |
     *         +--IDENT (String)
     *     +--RPAREN ())
     *     +--METHOD_CALL (()
     *         |
     *         +--DOT (.)
     *             |
     *             +--IDENT (it)
     *             +--IDENT (next)
     *         +--ELIST
     *         +--RPAREN ())
     * </pre>
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#238146">Java
     * Language Specification, &sect;15.16</a>
     * @see #EXPR
     * @see #TYPE
     * @see #RPAREN
     **/
    public static final int TYPECAST = 23;
    /**
     * The array index operator.
     *
     * <p>For example:</p>
     * <pre>
     * ar[2] = 5;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--EXPR
     *     |
     *     +--ASSIGN (=)
     *         |
     *         +--INDEX_OP ([)
     *             |
     *             +--IDENT (ar)
     *             +--EXPR
     *                 |
     *                 +--NUM_INT (2)
     *         +--NUM_INT (5)
     * +--SEMI (;)
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int INDEX_OP = 24;
    /**
     * The <code>++</code> (postfix increment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#39438">Java
     * Language Specification, &sect;15.14.1</a>
     * @see #EXPR
     * @see #INC
     **/
    public static final int POST_INC = 25;
    /**
     * The <code>--</code> (postfix decrement) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#4987">Java
     * Language Specification, &sect;15.14.2</a>
     * @see #EXPR
     * @see #DEC
     **/
    public static final int POST_DEC = 26;
    /**
     * A method call.
     *
     * <p>For example:</p>
     * <pre>
     * Math.random()
     * </pre>
     * <p>parses as:
     * <pre>
     * +--METHOD_CALL (()
     *     |
     *     +--DOT (.)
     *         |
     *         +--IDENT (Math)
     *         +--IDENT (random)
     *     +--ELIST
     *     +--RPAREN ())
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #ELIST
     * @see #RPAREN
     * @see FullIdent
     **/
    public static final int METHOD_CALL = 27;
    /**
     * An expression.  Operators with lower precedence appear at a
     * higher level in the tree than operators with higher precedence.
     * Parentheses are siblings to the operator they enclose.
     *
     * <p>For example:</p>
     * <pre>
     * x = 4 + 3 * 5 + (30 + 26) / 4 + 5 % 4 + (1&lt;&lt;3);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--EXPR
     *     |
     *     +--ASSIGN (=)
     *         |
     *         +--IDENT (x)
     *         +--PLUS (+)
     *             |
     *             +--PLUS (+)
     *                 |
     *                 +--PLUS (+)
     *                     |
     *                     +--PLUS (+)
     *                         |
     *                         +--NUM_INT (4)
     *                         +--STAR (*)
     *                             |
     *                             +--NUM_INT (3)
     *                             +--NUM_INT (5)
     *                     +--DIV (/)
     *                         |
     *                         +--LPAREN (()
     *                         +--PLUS (+)
     *                             |
     *                             +--NUM_INT (30)
     *                             +--NUM_INT (26)
     *                         +--RPAREN ())
     *                         +--NUM_INT (4)
     *                 +--MOD (%)
     *                     |
     *                     +--NUM_INT (5)
     *                     +--NUM_INT (4)
     *             +--LPAREN (()
     *             +--SL (&lt;&lt;)
     *                 |
     *                 +--NUM_INT (1)
     *                 +--NUM_INT (3)
     *             +--RPAREN ())
     * +--SEMI (;)
     * </pre>
     *
     * @see #ELIST
     * @see #ASSIGN
     * @see #LPAREN
     * @see #RPAREN
     **/
    public static final int EXPR = 28;
    /**
     * An array initialization.  This may occur as part of an array
     * declaration or inline with <code>new</code>.
     *
     * <p>For example:</p>
     * <pre>
     *   int[] y =
     *     {
     *       1,
     *       2,
     *     };
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--VARIABLE_DEF
     *     |
     *     +--MODIFIERS
     *     +--TYPE
     *         |
     *         +--ARRAY_DECLARATOR ([)
     *             |
     *             +--LITERAL_INT (int)
     *     +--IDENT (y)
     *     +--ASSIGN (=)
     *         |
     *         +--ARRAY_INIT ({)
     *             |
     *             +--EXPR
     *                 |
     *                 +--NUM_INT (1)
     *             +--COMMA (,)
     *             +--EXPR
     *                 |
     *                 +--NUM_INT (2)
     *             +--COMMA (,)
     *             +--RCURLY (})
     * +--SEMI (;)
     * </pre>
     *
     * <p>Also consider:</p>
     * <pre>
     *   int[] z = new int[]
     *     {
     *       1,
     *       2,
     *     };
     * </pre>
     * <p>which parses as:</p>
     * <pre>
     * +--VARIABLE_DEF
     *     |
     *     +--MODIFIERS
     *     +--TYPE
     *         |
     *         +--ARRAY_DECLARATOR ([)
     *             |
     *             +--LITERAL_INT (int)
     *     +--IDENT (z)
     *     +--ASSIGN (=)
     *         |
     *         +--EXPR
     *             |
     *             +--LITERAL_NEW (new)
     *                 |
     *                 +--LITERAL_INT (int)
     *                 +--ARRAY_DECLARATOR ([)
     *                 +--ARRAY_INIT ({)
     *                     |
     *                     +--EXPR
     *                         |
     *                         +--NUM_INT (1)
     *                     +--COMMA (,)
     *                     +--EXPR
     *                         |
     *                         +--NUM_INT (2)
     *                     +--COMMA (,)
     *                     +--RCURLY (})
     * </pre>
     *
     * @see #ARRAY_DECLARATOR
     * @see #TYPE
     * @see #LITERAL_NEW
     * @see #COMMA
     **/
    public static final int ARRAY_INIT = 29;
    /**
     * An import declaration.  Import declarations are option, but
     * must appear after the package declaration and before the type
     * declaration.
     *
     * <p>For example:</p>
     *
     * <pre>
     *   import java.io.IOException;
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--IMPORT (import)
     *     |
     *     +--DOT (.)
     *         |
     *         +--DOT (.)
     *             |
     *             +--IDENT (java)
     *             +--IDENT (io)
     *         +--IDENT (IOException)
     *     +--SEMI (;)
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/packages.doc.html#70209">Java
     * Language Specification &sect;7.5</a>
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     **/
    public static final int IMPORT = 30;
    /**
     * The <code>+</code> (unary plus) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#24924">Java
     * Language Specification, &sect;15.15.3</a>
     * @see #EXPR
     **/
    public static final int UNARY_MINUS = 31;
    /**
     * The <code>-</code> (unary minus) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#236345">Java
     * Language Specification, &sect;15.15.4</a>
     * @see #EXPR
     **/
    public static final int UNARY_PLUS = 32;
    /**
     * A group of case clauses.  Case clauses with no associated
     * statements are grouped together into a case group.  The last
     * child is a statement list containing the statements to execute
     * upon a match.
     *
     * <p>For example:</p>
     * <pre>
     * case 0:
     * case 1:
     * case 2:
     *   x = 3;
     *   break;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--CASE_GROUP
     *     |
     *     +--LITERAL_CASE (case)
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (0)
     *     +--LITERAL_CASE (case)
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (1)
     *     +--LITERAL_CASE (case)
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (2)
     *     +--SLIST
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (x)
     *                 +--NUM_INT (3)
     *         +--SEMI (;)
     *         +--LITERAL_BREAK (break)
     *             |
     *             +--SEMI (;)
     * </pre>
     *
     * @see #LITERAL_CASE
     * @see #LITERAL_DEFAULT
     * @see #LITERAL_SWITCH
     **/
    public static final int CASE_GROUP = 33;
    /**
     * An expression list.  The children are a comma separated list of
     * expressions.
     *
     * @see #LITERAL_NEW
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #CTOR_CALL
     * @see #SUPER_CTOR_CALL
     **/
    public static final int ELIST = 34;
    /**
     * A for loop initializer.  This is a child of
     * <code>LITERAL_FOR</code>.  The children of this element may be
     * a comma separated list of variable declarations, an expression
     * list, or empty.
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_INIT = 35;
    /**
     * A for loop condition.  This is a child of
     * <code>LITERAL_FOR</code>.  The child of this element is an
     * optional expression.
     *
     * @see #EXPR
     * @see #LITERAL_FOR
     **/
    public static final int FOR_CONDITION = 36;
    /**
     * A for loop iterator.  This is a child of
     * <code>LITERAL_FOR</code>.  The child of this element is an
     * optional expression list.
     *
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_ITERATOR = 37;
    /**
     * The empty statement.  This goes in place of an
     * <code>SLIST</code> for a <code>for</code> or <code>while</code>
     * loop body.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/statements.doc.html#5970">Java
     * Language Specification, &sect;14.6</a>
     * @see #LITERAL_FOR
     * @see #LITERAL_WHILE
     **/
    public static final int EMPTY_STAT = 38;
    /**
     * The <code>final</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int FINAL = 39;
    /**
     * The <code>abstract</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int ABSTRACT = 40;
    /**
     * The <code>strictfp</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int STRICTFP = 41;
    /**
     * A super constructor call.
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #CTOR_CALL
     **/
    public static final int SUPER_CTOR_CALL = 42;
    /**
     * A constructor call.
     *
     * <p>For example:</p>
     * <pre>
     * this(1);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--CTOR_CALL (()
     *     |
     *     +--ELIST
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (1)
     *     +--RPAREN ())
     *     +--SEMI (;)
     * </pre>
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #SUPER_CTOR_CALL
     **/
    public static final int CTOR_CALL = 43;
    /**
     * This token does not appear in the tree.
     *
     * @see #PACKAGE_DEF
     **/
    public static final int LITERAL_PACKAGE = 44;
    /**
     * The statement terminator (<code>;</code>).  Depending on the
     * context, this make occur as a sibling, a child, or not at all.
     *
     * @see #PACKAGE_DEF
     * @see #IMPORT
     * @see #SLIST
     * @see #ARRAY_INIT
     * @see #LITERAL_FOR
     **/
    public static final int SEMI = 45;
    /**
     * This token does not appear in the tree.
     *
     * @see #IMPORT
     **/
    public static final int LITERAL_IMPORT = 46;
    /**
     * This token does not appear in the tree.
     *
     * @see #INDEX_OP
     * @see #ARRAY_DECLARATOR
     **/
    public static final int LBRACK = 47;
    /**
     * This token does not appear in the tree.
     *
     * @see #INDEX_OP
     * @see #ARRAY_DECLARATOR
     **/
    public static final int RBRACK = 48;
    /**
     * The <code>void</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_VOID = 49;
    /**
     * The <code>boolean</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_BOOLEAN = 50;
    /**
     * The <code>byte</code> keyword.
     * 
     * @see #TYPE
     **/
    public static final int LITERAL_BYTE = 51;
    /**
     * The <code>char</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_CHAR = 52;
    /**
     * The <code>short</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_SHORT = 53;
    /**
     * The <code>int</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_INT = 54;
    /**
     * The <code>float</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_FLOAT = 55;
    /**
     * The <code>long</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_LONG = 56;
    /**
     * The <code>double</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_DOUBLE = 57;
    /**
     * An identifier.  These can be names of types, subpackages,
     * fields, methods, parameters, and local variables.
     **/
    public static final int IDENT = 58;
    /**
     * The <code>&#46;</code> (dot) operator.
     *
     * @see FullIdent
     **/
    public static final int DOT = 59;
    /**
     * The <code>*</code> (multiplication or wildcard) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/packages.doc.html#26725">Java
     * Language Specification, &sect;7.5.2</a>
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5036">Java
     * Language Specification, &sect;15.17.1</a>
     * @see #EXPR
     * @see #IMPORT
     **/
    public static final int STAR = 60;
    /**
     * The <code>private</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PRIVATE = 61;
    /**
     * The <code>public</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PUBLIC = 62;
    /**
     * The <code>protected</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PROTECTED = 63;
    /**
     * The <code>static</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_STATIC = 64;
    /**
     * The <code>transient</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_TRANSIENT = 65;
    /**
     * The <code>native</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_NATIVE = 66;
    /**
     * The <code>synchronized</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_SYNCHRONIZED = 67;
    /**
     * The <code>volatile</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_VOLATILE = 68;
    /**
     * The <code>class</code> keyword.  This element does not appear
     * as part of a class declaration, but only inline to reference a
     * class object.
     *
     * <p>For example:</p>
     *
     * <pre>
     * int.class
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--EXPR
     *     |
     *     +--DOT (.)
     *         |
     *         +--LITERAL_INT (int)
     *         +--LITERAL_CLASS (class)
     * </pre>
     *
     * @see #DOT
     * @see #IDENT
     * @see #CLASS_DEF
     * @see FullIdent
     **/
    public static final int LITERAL_CLASS = 69;
    /**
     * This token does not appear in the tree.
     *
     * @see #EXTENDS_CLAUSE
     **/
    public static final int LITERAL_EXTENDS = 70;
    /**
     * This token does not appear in the tree.
     *
     * @see #INTERFACE_DEF
     **/
    public static final int LITERAL_INTERFACE = 71;
    /**
     * A left (curly) brace (<code>{</code>).
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int LCURLY = 72;
    /**
     * A right (curly) brace (<code>}</code>).
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int RCURLY = 73;
    /**
     * The <code>,</code> (comma) operator.
     *
     * @see #ARRAY_INIT
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #LITERAL_THROWS
     * @see #IMPLEMENTS_CLAUSE
     **/
    public static final int COMMA = 74;
    /**
     * This token does not appear in the tree.
     *
     * @see #IMPLEMENTS_CLAUSE
     **/
    public static final int LITERAL_IMPLEMENTS = 75;
    /**
     * A left parenthesis (<code>(</code>).
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     **/
    public static final int LPAREN = 76;
    /**
     * A right parenthesis (<code>)</code>).
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #METHOD_CALL
     * @see #TYPECAST
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     **/
    public static final int RPAREN = 77;
    /**
     * The <code>this</code> keyword.
     *
     * @see #EXPR
     * @see #CTOR_CALL
     **/
    public static final int LITERAL_THIS = 78;
    /**
     * The <code>super</code> keyword.
     *
     * @see #EXPR
     * @see #SUPER_CTOR_CALL
     **/
    public static final int LITERAL_SUPER = 79;
    /**
     * The <code>=</code> (assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5295">Java
     * Language Specification, &sect;15.26.1</a>
     * @see #EXPR
     **/
    public static final int ASSIGN = 80;
    /**
     * The <code>throws</code> keyword.  The children are a number of
     * one or more identifiers separated by commas.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#78323">Java
     * Language Specification, &sect;8.4.4</a>
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     * @see FullIdent
     **/
    public static final int LITERAL_THROWS = 81;
    /**
     * The <code>:</code> (colon) operator.  This will appear as part
     * of the conditional operator (<code>? :</code>).
     *
     * @see #QUESTION
     * @see #LABELED_STAT
     * @see #CASE_GROUP
     **/
    public static final int COLON = 82;
    /**
     * The <code>if</code> keyword.
     *
     * <p>For example:</p>
     * <pre>
     * if(optimistic)
     * {
     *   message = "half full";
     * }
     * else
     * {
     *   message = "half empty";
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_IF (if)
     *     |
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--IDENT (optimistic)
     *     +--RPAREN ())
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (message)
     *                 +--STRING_LITERAL ("half full")
     *         +--SEMI (;)
     *         +--RCURLY (})
     *     +--LITERAL_ELSE (else)
     *         |
     *         +--SLIST ({)
     *             |
     *             +--EXPR
     *                 |
     *                 +--ASSIGN (=)
     *                     |
     *                     +--IDENT (message)
     *                     +--STRING_LITERAL ("half empty")
     *             +--SEMI (;)
     *             +--RCURLY (})
     * </pre>
     *
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #SLIST
     * @see #EMPTY_STAT
     * @see #LITERAL_ELSE
     **/
    public static final int LITERAL_IF = 83;
    /**
     * The <code>for</code> keyword.  The children are <code>(</code>,
     * an initializer, a condition, an iterator, a <code>)</code> and
     * either a statement list, a single expression, or an empty
     * statement.
     *
     * <p>For example:</p>
     * <pre>
     * for(int i = 0, n = myArray.length; i &lt; n; i++)
     * {
     * }
     * </pre>
     *
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_FOR (for)
     *     |
     *     +--LPAREN (()
     *     +--FOR_INIT
     *         |
     *         +--VARIABLE_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--LITERAL_INT (int)
     *             +--IDENT (i)
     *             +--ASSIGN (=)
     *                 |
     *                 +--EXPR
     *                     |
     *                     +--NUM_INT (0)
     *         +--COMMA (,)
     *         +--VARIABLE_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--LITERAL_INT (int)
     *             +--IDENT (n)
     *             +--ASSIGN (=)
     *                 |
     *                 +--EXPR
     *                     |
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (myArray)
     *                         +--IDENT (length)
     *     +--SEMI (;)
     *     +--FOR_CONDITION
     *         |
     *         +--EXPR
     *             |
     *             +--LT (&lt;)
     *                 |
     *                 +--IDENT (i)
     *                 +--IDENT (n)
     *     +--SEMI (;)
     *     +--FOR_ITERATOR
     *         |
     *         +--ELIST
     *             |
     *             +--EXPR
     *                 |
     *                 +--POST_INC (++)
     *                     |
     *                     +--IDENT (i)
     *     +--RPAREN ())
     *     +--SLIST ({)
     *         |
     *         +--RCURLY (})
     * </pre>
     *
     * @see #LPAREN
     * @see #FOR_INIT
     * @see #SEMI
     * @see #FOR_CONDITION
     * @see #FOR_ITERATOR
     * @see #RPAREN
     * @see #SLIST
     * @see #EMPTY_STAT
     * @see #EXPR
     **/
    public static final int LITERAL_FOR = 84;
    /**
     * The <code>while</code> keyword.
     *
     * <p>For example:</p>
     * <pre>
     * while(line != null)
     * {
     *   process(line);
     *   line = in.readLine();
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_WHILE (while)
     *     |
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--NOT_EQUAL (!=)
     *             |
     *             +--IDENT (line)
     *             +--LITERAL_NULL (null)
     *     +--RPAREN ())
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--METHOD_CALL (()
     *                 |
     *                 +--IDENT (process)
     *                 +--ELIST
     *                     |
     *                     +--EXPR
     *                         |
     *                         +--IDENT (line)
     *                 +--RPAREN ())
     *         +--SEMI (;)
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (line)
     *                 +--METHOD_CALL (()
     *                     |
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (in)
     *                         +--IDENT (readLine)
     *                     +--ELIST
     *                     +--RPAREN ())
     *         +--SEMI (;)
     *         +--RCURLY (})
     * </pre>
     **/
    public static final int LITERAL_WHILE = 85;
    /**
     * The <code>do</code> keyword.  Note the the while token does not
     * appear as part of the do-while construct.
     *
     * <p>For example:</p>
     * <pre>
     * do
     * {
     *   x = rand.nextInt(10);
     * }
     * while(x < 5);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_DO (do)
     *     |
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (x)
     *                 +--METHOD_CALL (()
     *                     |
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (rand)
     *                         +--IDENT (nextInt)
     *                     +--ELIST
     *                         |
     *                         +--EXPR
     *                             |
     *                             +--NUM_INT (10)
     *                     +--RPAREN ())
     *         +--SEMI (;)
     *         +--RCURLY (})
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--LT (<)
     *             |
     *             +--IDENT (x)
     *             +--NUM_INT (5)
     *     +--RPAREN ())
     *     +--SEMI (;)
     * </pre>
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LPAREN
     * @see #RPAREN
     * @see #SEMI
     **/
    public static final int LITERAL_DO = 86;
    /**
     * The <code>break</code> keyword.  The first child is an optional
     * identifier and the last child is a semicolon.
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_BREAK = 87;
    /**
     * The <code>continue</code> keyword.  The first child is an
     * optional identifier and the last child is a semicolon.
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_CONTINUE = 88;
    /**
     * The <code>return</code> keyword.  The first child is an
     * optional expression for the return value.  The last child is a
     * semi colon.
     *
     * @see #EXPR
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_RETURN = 89;
    /**
     * The <code>switch</code> keyword.
     *
     * <p>For example:</p>
     * <pre>
     * switch(type)
     * {
     *   case 0:
     *     background = Color.blue;
     *     break;
     *   case 1:
     *     background = Color.red;
     *     break;
     *   default:
     *     background = Color.green;
     *     break;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_SWITCH (switch)
     *     |
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--IDENT (type)
     *     +--RPAREN ())
     *     +--LCURLY ({)
     *     +--CASE_GROUP
     *         |
     *         +--LITERAL_CASE (case)
     *             |
     *             +--EXPR
     *                 |
     *                 +--NUM_INT (0)
     *         +--SLIST
     *             |
     *             +--EXPR
     *                 |
     *                 +--ASSIGN (=)
     *                     |
     *                     +--IDENT (background)
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (Color)
     *                         +--IDENT (blue)
     *             +--SEMI (;)
     *             +--LITERAL_BREAK (break)
     *                 |
     *                 +--SEMI (;)
     *     +--CASE_GROUP
     *         |
     *         +--LITERAL_CASE (case)
     *             |
     *             +--EXPR
     *                 |
     *                 +--NUM_INT (1)
     *         +--SLIST
     *             |
     *             +--EXPR
     *                 |
     *                 +--ASSIGN (=)
     *                     |
     *                     +--IDENT (background)
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (Color)
     *                         +--IDENT (red)
     *             +--SEMI (;)
     *             +--LITERAL_BREAK (break)
     *                 |
     *                 +--SEMI (;)
     *     +--CASE_GROUP
     *         |
     *         +--LITERAL_DEFAULT (default)
     *         +--SLIST
     *             |
     *             +--EXPR
     *                 |
     *                 +--ASSIGN (=)
     *                     |
     *                     +--IDENT (background)
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (Color)
     *                         +--IDENT (green)
     *             +--SEMI (;)
     *             +--LITERAL_BREAK (break)
     *                 |
     *                 +--SEMI (;)
     *     +--RCURLY (})
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/statements.doc.html#35518">Java
     * Language Specification, &sect;14.10</a>
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #LCURLY
     * @see #CASE_GROUP
     * @see #RCURLY
     * @see #SLIST
     **/
    public static final int LITERAL_SWITCH = 90;
    /**
     * The <code>throw</code> keyword.  The first child is an
     * expression that evaluates to a <code>Throwable</code> instance.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/statements.doc.html#237350">Java
     * Language Specification, &sect;14.17</a>
     * @see #SLIST
     * @see #EXPR
     **/
    public static final int LITERAL_THROW = 91;
    /**
     * The <code>else</code> keyword.  This appears as a child of an
     * <code>if</code> statement.
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LITERAL_IF
     **/
    public static final int LITERAL_ELSE = 92;
    /**
     * The <code>case</code> keyword.  The first child is a constant
     * expression that evaluates to a integer.
     *
     * @see #CASE_GROUP
     * @see #EXPR
     **/
    public static final int LITERAL_CASE = 93;
    /**
     * The <code>default</code> keyword.  This element has no
     * children.
     *
     * @see #CASE_GROUP
     **/
    public static final int LITERAL_DEFAULT = 94;
    /**
     * The <code>try</code> keyword.  The children are a statement
     * list, zero or more catch blocks and then an optional finally
     * block.
     *
     * <p>For example:</p>
     * <pre>
     * try
     * {
     *   FileReader in = new FileReader("abc.txt");
     * }
     * catch(IOException ioe)
     * {
     * }
     * finally
     * {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_TRY (try)
     *     |
     *     +--SLIST ({)
     *         |
     *         +--VARIABLE_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--IDENT (FileReader)
     *             +--IDENT (in)
     *             +--ASSIGN (=)
     *                 |
     *                 +--EXPR
     *                     |
     *                     +--LITERAL_NEW (new)
     *                         |
     *                         +--IDENT (FileReader)
     *                         +--LPAREN (()
     *                         +--ELIST
     *                             |
     *                             +--EXPR
     *                                 |
     *                                 +--STRING_LITERAL ("abc.txt")
     *                         +--RPAREN ())
     *         +--SEMI (;)
     *         +--RCURLY (})
     *     +--LITERAL_CATCH (catch)
     *         |
     *         +--LPAREN (()
     *         +--PARAMETER_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--IDENT (IOException)
     *             +--IDENT (ioe)
     *         +--RPAREN ())
     *         +--SLIST ({)
     *             |
     *             +--RCURLY (})
     *     +--LITERAL_FINALLY (finally)
     *         |
     *         +--SLIST ({)
     *             |
     *             +--RCURLY (})
     * +--RCURLY (})
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/statements.doc.html#79311">Java
     * Language Specification, &sect;14.19</a>
     * @see #SLIST
     * @see #LITERAL_CATCH
     * @see #LITERAL_FINALLY
     **/
    public static final int LITERAL_TRY = 95;
    /**
     * The <code>catch</code> keyword.
     *
     * @see #LPAREN
     * @see #PARAMETER_DEF
     * @see #RPAREN
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_CATCH = 96;
    /**
     * The <code>finally</code> keyword.
     *
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_FINALLY = 97;
    /**
     * The <code>+=</code> (addition assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int PLUS_ASSIGN = 98;
    /**
     * The <code>-=</code> (subtraction assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MINUS_ASSIGN = 99;
    /**
     * The <code>*=</code> (multiplication assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int STAR_ASSIGN = 100;
    /**
     * The <code>/=</code> (division assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int DIV_ASSIGN = 101;
    /**
     * The <code>%=</code> (remainder assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MOD_ASSIGN = 102;
    /**
     * The <code>&gt;&gt;=</code> (signed right shift assignment)
     * operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SR_ASSIGN = 103;
    /**
     * The <code>&gt;&gt;&gt;=</code> (unsigned right shift assignment)
     * operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BSR_ASSIGN = 104;
    /**
     * The <code>&lt;&lt;=</code> (left shift assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SL_ASSIGN = 105;
    /**
     * The <code>&amp;=</code> (bitwise AND assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BAND_ASSIGN = 106;
    /**
     * The <code>^=</code> (bitwise exclusive OR assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BXOR_ASSIGN = 107;
    /**
     * The <code>|=</code> (bitwise OR assignment) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5304">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BOR_ASSIGN = 108;
    /**
     * The <code>&#63;</code> (conditional) operator.  Technically,
     * the colon is also part of this operator, but it appears as a
     * separate token.
     *
     * <p>For example:</p>
     * <pre>
     * (quantity == 1) ? "": "s"
     * </pre>
     * <p>
     * <p>parses as:</p>
     * </p>
     * <pre>
     * +--QUESTION (?)
     *     |
     *     +--LPAREN (()
     *     +--EQUAL (==)
     *         |
     *         +--IDENT (quantity)
     *         +--NUM_INT (1)
     *     +--RPAREN ())
     *     +--STRING_LITERAL ("")
     *     +--COLON (:)
     *     +--STRING_LITERAL ("s")
     * </pre>
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#290293">Java
     * Language Specification, &sect;15.25</a>
     * @see #EXPR
     * @see #COLON
     **/
    public static final int QUESTION = 109;
    /**
     * The <code>||</code> (conditional OR) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#54532">Java
     * Language Specification, &sect;15.24</a>
     * @see #EXPR
     **/
    public static final int LOR = 110;
    /**
     * The <code>&&</code> (conditional AND) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5247">Java
     * Language Specification, &sect;15.23</a>
     * @see #EXPR
     **/
    public static final int LAND = 111;
    /**
     * The <code>|</code> (bitwise OR) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5233">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BOR = 112;
    /**
     * The <code>^</code> (bitwise exclusive OR) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5233">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BXOR = 113;
    /**
     * The <code>&</code> (bitwise AND) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5233">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BAND = 114;
    /**
     * The <code>&#33;=</code> (not equal) operator.
     *
     * @see #EXPR
     **/
    public static final int NOT_EQUAL = 115;
    /**
     * The <code>==</code> (equal) operator.
     *
     * @see #EXPR
     **/
    public static final int EQUAL = 116;
    /**
     * The <code>&lt;</code> (less than) operator.
     *
     * @see #EXPR
     **/
    public static final int LT = 117;
    /**
     * The <code>&gt;</code> (greater than) operator.
     *
     * @see #EXPR
     **/
    public static final int GT = 118;
    /**
     * The <code>&lt;=</code> (less than or equal) operator.
     *
     * @see #EXPR
     **/
    public static final int LE = 119;
    /**
     * The <code>&gt;=</code> (greater than or equal) operator.
     *
     * @see #EXPR
     **/
    public static final int GE = 120;
    /**
     * The <code>instanceof</code> operator.  The first child is an
     * object reference or something that evaluates to an object
     * reference.  The second child is a reference type.
     * 
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#80289">Java
     * Language Specification, &sect;15.20.2</a>
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #IDENT
     * @see #DOT
     * @see #TYPE
     * @see FullIdent
     **/
    public static final int LITERAL_INSTANCEOF = 121;
    /**
     * The <code>&lt;&lt;</code> (shift left) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5121">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SL = 122;
    /**
     * The <code>&gt;&gt;</code> (signed shift right) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5121">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SR = 123;
    /**
     * The <code>&gt;&gt;&gt;</code> (unsigned shift right) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5121">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int BSR = 124;
    /**
     * The <code>+</code> (addition) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#15746">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int PLUS = 125;
    /**
     * The <code>-</code> (subtraction) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#15746">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int MINUS = 126;
    /**
     * The <code>/</code> (division) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5047">Java
     * Language Specification, &sect;15.17.2</a>
     * @see #EXPR
     **/
    public static final int DIV = 127;
    /**
     * The <code>%</code> (remainder) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#24956">Java
     * Language Specification, &sect;15.17.3</a>
     * @see #EXPR
     **/
    public static final int MOD = 128;
    /**
     * The <code>++</code> (prefix increment) operator.
     *
     * @see <a target="top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#39547">Java
     * Language Specification, &sect;15.15.1</a>
     * @see #EXPR
     * @see #POST_INC
     **/
    public static final int INC = 129;
    /**
     * The <code>--</code> (prefix decrement) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#239136">Java
     * Language Specification, &sect;15.15.2</a>
     * @see #EXPR
     * @see #POST_DEC
     **/
    public static final int DEC = 130;
    /**
     * The <code>~</code> (bitwise complement) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#5017>Java
     * Language Specification, &sect;15.15.5</a>
     * @see #EXPR
     **/
    public static final int BNOT = 131;
    /**
     * The <code>&#33;</code> (logical complement) operator.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#13350">Java
     * Language Specification, &sect;15.15.6</a>
     * @see #EXPR
     **/
    public static final int LNOT = 132;
    /**
     * The <code>true</code> keyword.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#49652">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_FALSE
     **/
    public static final int LITERAL_TRUE = 133;
    /**
     * The <code>false</code> keyword.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#49652">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_TRUE
     **/
    public static final int LITERAL_FALSE = 134;
    /**
     * The <code>null</code> keyword.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#230717">Java
     * Language Specification, &sect;3.10.7</a>
     * @see #EXPR
     **/
    public static final int LITERAL_NULL = 135;
    /**
     * The <code>new</code> keyword.  This element is used to define
     * new instances of objects, new arrays, and new anonymous inner
     * classes.
     *
     * <p>For example:</p>
     *
     * <pre>
     * new ArrayList(50)
     * </pre>
     * 
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_NEW (new)
     *     |
     *     +--IDENT (ArrayList)
     *     +--LPAREN (()
     *     +--ELIST
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (50)
     *     +--RPAREN ())
     * </pre>
     *
     * <p>For example:</p>
     * <pre>
     * new float[]
     *   {
     *     3.0f,
     *     4.0f
     *   };
     * </pre>
     *
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_NEW (new)
     *     |
     *     +--LITERAL_FLOAT (float)
     *     +--ARRAY_DECLARATOR ([)
     *     +--ARRAY_INIT ({)
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_FLOAT (3.0f)
     *         +--COMMA (,)
     *         +--EXPR
     *             |
     *             +--NUM_FLOAT (4.0f)
     *         +--RCURLY (})
     * </pre>
     *
     * <p>For example:</p>
     * <pre>
     * new FilenameFilter()
     * {
     *   public boolean accept(File dir, String name)
     *   {
     *     return name.endsWith(".java");
     *   }
     * }
     * </pre>
     *
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_NEW (new)
     *     |
     *     +--IDENT (FilenameFilter)
     *     +--LPAREN (()
     *     +--ELIST
     *     +--RPAREN ())
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--METHOD_DEF
     *             |
     *             +--MODIFIERS
     *                 |
     *                 +--LITERAL_PUBLIC (public)
     *             +--TYPE
     *                 |
     *                 +--LITERAL_BOOLEAN (boolean)
     *             +--IDENT (accept)
     *             +--PARAMETERS
     *                 |
     *                 +--PARAMETER_DEF
     *                     |
     *                     +--MODIFIERS
     *                     +--TYPE
     *                         |
     *                         +--IDENT (File)
     *                     +--IDENT (dir)
     *                 +--COMMA (,)
     *                 +--PARAMETER_DEF
     *                     |
     *                     +--MODIFIERS
     *                     +--TYPE
     *                         |
     *                         +--IDENT (String)
     *                     +--IDENT (name)
     *             +--SLIST ({)
     *                 |
     *                 +--LITERAL_RETURN (return)
     *                     |
     *                     +--EXPR
     *                         |
     *                         +--METHOD_CALL (()
     *                             |
     *                             +--DOT (.)
     *                                 |
     *                                 +--IDENT (name)
     *                                 +--IDENT (endsWith)
     *                             +--ELIST
     *                                 |
     *                                 +--EXPR
     *                                     |
     *                                     +--STRING_LITERAL (".java")
     *                             +--RPAREN ())
     *                     +--SEMI (;)
     *                 +--RCURLY (})
     *         +--RCURLY (})
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #LPAREN
     * @see #ELIST
     * @see #RPAREN
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see FullIdent
     **/
    public static final int LITERAL_NEW = 136;
    /**
     * An integer literal.  These may be specified in decimal,
     * hexadecimal, or octal form.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#48282">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_LONG
     **/
    public static final int NUM_INT = 137;
    /**
     * A character literal.  This is a (possibly escaped) character
     * enclosed in single quotes.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#100960">Java
     * Language Specification, &sect;3.10.4</a>
     * @see #EXPR
     **/
    public static final int CHAR_LITERAL = 138;
    /**
     * A string literal.  This is a sequence of (possibly escaped)
     * characters enclosed in double quotes.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#101084">Java
     * Language Specification, &sect;3.10.5</a>
     * @see #EXPR
     **/
    public static final int STRING_LITERAL = 139;
    /**
     * A single precision floating point literal.  This is a floating
     * point number with an <code>F</code> or <code>f</code> suffix.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#230798">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_DOUBLE
     **/
    public static final int NUM_FLOAT = 140;
    /**
     * A long integer literal.  These are almost the same as integer
     * literals, but they have an <code>L</code> or <code>l</code>
     * (ell) suffix.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#48282">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_INT
     **/
    public static final int NUM_LONG = 141;
    /**
     * A double precision floating point literal.  This is a floating
     * point number with an optional <code>D</code> or <code>d</code>
     * suffix.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#230798">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_FLOAT
     **/
    public static final int NUM_DOUBLE = 142;
    /**
     * This token does not appear in the tree.
     * 
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#95710">Java
     * Language Specification, &sect;3.6</a>
     * @see FileContents
     **/
    public static final int WS = 143;
    /**
     * This token does not appear in the tree.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#48125">Java
     * Language Specification, &sect;3.7</a>
     * @see FileContents
     **/
    public static final int SL_COMMENT = 144;
    /**
     * This token does not appear in the tree.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#48125">Java
     * Language Specification, &sect;3.7</a>
     * @see FileContents
     **/
    public static final int ML_COMMENT = 145;
    /**
     * This token does not appear in the tree.
     *
     * @see <a target="_top"
     * href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#101089">Java
     * Language Specification, &sect;3.10.6</a>
     * @see #CHAR_LITERAL
     * @see #STRING_LITERAL
     **/
    public static final int ESC = 146;
    /**
     * This token does not appear in the tree.
     *
     * @see #NUM_INT
     * @see #NUM_LONG
     **/
    public static final int HEX_DIGIT = 147;
    /**
     * This token does not appear in the tree.
     *
     * @see #NUM_FLOAT
     * @see #NUM_DOUBLE
     **/
    public static final int EXPONENT = 149;
    /**
     * This token does not appear in the tree.
     *
     * @see #NUM_FLOAT
     * @see #NUM_DOUBLE
     **/
    public static final int FLOAT_SUFFIX = 150;
    /**
     * The <code>assert</code> keyword.  This is only for Java 1.4 and
     * later.
     *
     * <p>For example:</p>
     * <pre>
     * assert(x==4);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_ASSERT (assert)
     *     |
     *     +--EXPR
     *         |
     *         +--LPAREN (()
     *         +--EQUAL (==)
     *             |
     *             +--IDENT (x)
     *             +--NUM_INT (4)
     *         +--RPAREN ())
     *     +--SEMI (;)
     * </pre>
     **/
    public static final int LITERAL_ASSERT = 151;

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
