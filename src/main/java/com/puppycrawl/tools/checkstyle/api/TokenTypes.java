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

package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaTokenTypes;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree.
 *
 * <p>Implementation detail: This class has been introduced to break
 * the circular dependency between packages.</p>
 *
 * @author Oliver Burn
 * @author <a href="mailto:dobratzp@ele.uri.edu">Peter Dobratz</a>
 */
public final class TokenTypes {
    // The following three types are never part of an AST,
    // left here as a reminder so nobody will read them accidentally

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
    public static final int EOF = GeneratedJavaTokenTypes.EOF;
    /**
     * Modifiers for type, method, and field declarations.  The
     * modifiers element is always present even though it may have no
     * children.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
     * Language Specification, &sect;8</a>
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
     * @see #ANNOTATION
     * @see #LITERAL_DEFAULT
     **/
    public static final int MODIFIERS = GeneratedJavaTokenTypes.MODIFIERS;

    /**
     * An object block.  These are children of class, interface, enum,
     * annotation and enum constant declarations.
     * Also, object blocks are children of the new keyword when defining
     * anonymous inner types.
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
     * @see #ENUM_DEF
     * @see #ENUM_CONSTANT_DEF
     * @see #ANNOTATION_DEF
     **/
    public static final int OBJBLOCK = GeneratedJavaTokenTypes.OBJBLOCK;
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
    public static final int SLIST = GeneratedJavaTokenTypes.SLIST;
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
    public static final int CTOR_DEF = GeneratedJavaTokenTypes.CTOR_DEF;
    /**
     * A method declaration.  The children are modifiers, type parameters,
     * return type, method name, parameter list, an optional throws list, and
     * statement list.  The statement list is omitted if the method
     * declaration appears in an interface declaration.  Method
     * declarations may appear inside object blocks of class
     * declarations, interface declarations, enum declarations,
     * enum constant declarations or anonymous inner-class declarations.
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
     * @see #TYPE_PARAMETERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #LITERAL_THROWS
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int METHOD_DEF = GeneratedJavaTokenTypes.METHOD_DEF;
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
    public static final int VARIABLE_DEF =
        GeneratedJavaTokenTypes.VARIABLE_DEF;

    /**
     * An instance initializer.  Zero or more instance initializers
     * may appear in class and enum definitions.  This token will be a child
     * of the object block of the declaring type.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.6">Java
     * Language Specification&sect;8.6</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int INSTANCE_INIT =
        GeneratedJavaTokenTypes.INSTANCE_INIT;

    /**
     * A static initialization block.  Zero or more static
     * initializers may be children of the object block of a class
     * or enum declaration (interfaces cannot have static initializers).  The
     * first and only child is a statement list.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.7">Java
     * Language Specification, &sect;8.7</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int STATIC_INIT =
        GeneratedJavaTokenTypes.STATIC_INIT;

    /**
     * A type.  This is either a return type of a method or a type of
     * a variable or field.  The first child of this element is the
     * actual type.  This may be a primitive type, an identifier, a
     * dot which is the root of a fully qualified type, or an array of
     * any of these. The second child may be type arguments to the type.
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
     * @see #TYPE_ARGUMENTS
     **/
    public static final int TYPE = GeneratedJavaTokenTypes.TYPE;
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
     *     +--LITERAL_CLASS (class)
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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
     * Language Specification, &sect;8</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     **/
    public static final int CLASS_DEF = GeneratedJavaTokenTypes.CLASS_DEF;
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
     *     +--LITERAL_INTERFACE (interface)
     *     +--IDENT (MyInterface)
     *     +--EXTENDS_CLAUSE
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html">Java
     * Language Specification, &sect;9</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #OBJBLOCK
     **/
    public static final int INTERFACE_DEF =
        GeneratedJavaTokenTypes.INTERFACE_DEF;

    /**
     * The package declaration.  This is optional, but if it is
     * included, then there is only one package declaration per source
     * file and it must be the first non-comment in the file. A package
     * declaration may be annotated in which case the annotations comes
     * before the rest of the declaration (and are the first children).
     *
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
     *     +--ANNOTATIONS
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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.4">Java
     * Language Specification &sect;7.4</a>
     * @see #DOT
     * @see #IDENT
     * @see #SEMI
     * @see #ANNOTATIONS
     * @see FullIdent
     **/
    public static final int PACKAGE_DEF = GeneratedJavaTokenTypes.PACKAGE_DEF;
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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html">Java
     * Language Specification &sect;10</a>
     * @see #TYPE
     * @see #ARRAY_INIT
     **/
    public static final int ARRAY_DECLARATOR =
        GeneratedJavaTokenTypes.ARRAY_DECLARATOR;

    /**
     * An extends clause.  This appear as part of class and interface
     * definitions.  This element appears even if the
     * <code>extends</code> keyword is not explicitly used.  The child
     * is an optional identifier.
     *
     * <p>For example:</p>
     *
     * <pre>
     * extends java.util.LinkedList
     * </pre>
     *
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
    public static final int EXTENDS_CLAUSE =
        GeneratedJavaTokenTypes.EXTENDS_CLAUSE;

    /**
     * An implements clause.  This always appears in a class or enum
     * declaration, even if there are no implemented interfaces.  The
     * children are a comma separated list of zero or more
     * identifiers.
     *
     * <p>For example:</p>
     * <pre>
     * implements Serializable, Comparable
     * </pre>
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
     * @see #ENUM_DEF
     **/
    public static final int IMPLEMENTS_CLAUSE =
        GeneratedJavaTokenTypes.IMPLEMENTS_CLAUSE;

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
    public static final int PARAMETERS = GeneratedJavaTokenTypes.PARAMETERS;
    /**
     * A parameter declaration. The last parameter in a list of parameters may
     * be variable length (indicated by the ELLIPSIS child node immediately
     * after the TYPE child).
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #ELLIPSIS
     **/
    public static final int PARAMETER_DEF =
        GeneratedJavaTokenTypes.PARAMETER_DEF;

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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.7">Java
     * Language Specification, &sect;14.7</a>
     * @see #SLIST
     **/
    public static final int LABELED_STAT =
        GeneratedJavaTokenTypes.LABELED_STAT;

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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">Java
     * Language Specification, &sect;15.16</a>
     * @see #EXPR
     * @see #TYPE
     * @see #TYPE_ARGUMENTS
     * @see #RPAREN
     **/
    public static final int TYPECAST = GeneratedJavaTokenTypes.TYPECAST;
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
    public static final int INDEX_OP = GeneratedJavaTokenTypes.INDEX_OP;
    /**
     * The <code>++</code> (postfix increment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.1">Java
     * Language Specification, &sect;15.14.1</a>
     * @see #EXPR
     * @see #INC
     **/
    public static final int POST_INC = GeneratedJavaTokenTypes.POST_INC;
    /**
     * The <code>--</code> (postfix decrement) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.2">Java
     * Language Specification, &sect;15.14.2</a>
     * @see #EXPR
     * @see #DEC
     **/
    public static final int POST_DEC = GeneratedJavaTokenTypes.POST_DEC;
    /**
     * A method call. A method call may have type arguments however these
     * are attached to the appropriate node in the qualified method name.
     *
     * <p>For example:</p>
     * <pre>
     * Math.random()
     * </pre>
     *
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
     *
     * @see #IDENT
     * @see #TYPE_ARGUMENTS
     * @see #DOT
     * @see #ELIST
     * @see #RPAREN
     * @see FullIdent
     **/
    public static final int METHOD_CALL = GeneratedJavaTokenTypes.METHOD_CALL;

    /**
     * Part of Java 8 syntax. Method or constructor call without arguments.
     * @see #DOUBLE_COLON
     */
    public static final int METHOD_REF = GeneratedJavaTokenTypes.METHOD_REF;
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
    public static final int EXPR = GeneratedJavaTokenTypes.EXPR;
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
    public static final int ARRAY_INIT = GeneratedJavaTokenTypes.ARRAY_INIT;
    /**
     * An import declaration.  Import declarations are option, but
     * must appear after the package declaration and before the first type
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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5">Java
     * Language Specification &sect;7.5</a>
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     **/
    public static final int IMPORT = GeneratedJavaTokenTypes.IMPORT;
    /**
     * The <code>-</code> (unary minus) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.4">Java
     * Language Specification, &sect;15.15.4</a>
     * @see #EXPR
     **/
    public static final int UNARY_MINUS = GeneratedJavaTokenTypes.UNARY_MINUS;
    /**
     * The <code>+</code> (unary plus) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.3">Java
     * Language Specification, &sect;15.15.3</a>
     * @see #EXPR
     **/
    public static final int UNARY_PLUS = GeneratedJavaTokenTypes.UNARY_PLUS;
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
    public static final int CASE_GROUP = GeneratedJavaTokenTypes.CASE_GROUP;
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
    public static final int ELIST = GeneratedJavaTokenTypes.ELIST;
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
    public static final int FOR_INIT = GeneratedJavaTokenTypes.FOR_INIT;
    /**
     * A for loop condition.  This is a child of
     * <code>LITERAL_FOR</code>.  The child of this element is an
     * optional expression.
     *
     * @see #EXPR
     * @see #LITERAL_FOR
     **/
    public static final int FOR_CONDITION =
        GeneratedJavaTokenTypes.FOR_CONDITION;

    /**
     * A for loop iterator.  This is a child of
     * <code>LITERAL_FOR</code>.  The child of this element is an
     * optional expression list.
     *
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_ITERATOR =
        GeneratedJavaTokenTypes.FOR_ITERATOR;

    /**
     * The empty statement.  This goes in place of an
     * <code>SLIST</code> for a <code>for</code> or <code>while</code>
     * loop body.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.6">Java
     * Language Specification, &sect;14.6</a>
     * @see #LITERAL_FOR
     * @see #LITERAL_WHILE
     **/
    public static final int EMPTY_STAT = GeneratedJavaTokenTypes.EMPTY_STAT;
    /**
     * The <code>final</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int FINAL = GeneratedJavaTokenTypes.FINAL;
    /**
     * The <code>abstract</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int ABSTRACT = GeneratedJavaTokenTypes.ABSTRACT;
    /**
     * The <code>strictfp</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int STRICTFP = GeneratedJavaTokenTypes.STRICTFP;
    /**
     * A super constructor call.
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #CTOR_CALL
     **/
    public static final int SUPER_CTOR_CALL =
        GeneratedJavaTokenTypes.SUPER_CTOR_CALL;

    /**
     * A constructor call.
     *
     * <p>For example:</p>
     * <pre>
     * this(1);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--CTOR_CALL (this)
     *     |
     *     +--LPAREN (()
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
    public static final int CTOR_CALL = GeneratedJavaTokenTypes.CTOR_CALL;

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
    public static final int SEMI = GeneratedJavaTokenTypes.SEMI;

    /**
     * The <code>]</code> symbol.
     *
     * @see #INDEX_OP
     * @see #ARRAY_DECLARATOR
     **/
    public static final int RBRACK = GeneratedJavaTokenTypes.RBRACK;
    /**
     * The <code>void</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_VOID =
        GeneratedJavaTokenTypes.LITERAL_void;

    /**
     * The <code>boolean</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_BOOLEAN =
        GeneratedJavaTokenTypes.LITERAL_boolean;

    /**
     * The <code>byte</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_BYTE =
        GeneratedJavaTokenTypes.LITERAL_byte;

    /**
     * The <code>char</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_CHAR =
        GeneratedJavaTokenTypes.LITERAL_char;

    /**
     * The <code>short</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_SHORT =
        GeneratedJavaTokenTypes.LITERAL_short;

    /**
     * The <code>int</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_INT = GeneratedJavaTokenTypes.LITERAL_int;
    /**
     * The <code>float</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_FLOAT =
        GeneratedJavaTokenTypes.LITERAL_float;

    /**
     * The <code>long</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_LONG =
        GeneratedJavaTokenTypes.LITERAL_long;

    /**
     * The <code>double</code> keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_DOUBLE =
        GeneratedJavaTokenTypes.LITERAL_double;

    /**
     * An identifier.  These can be names of types, subpackages,
     * fields, methods, parameters, and local variables.
     **/
    public static final int IDENT = GeneratedJavaTokenTypes.IDENT;
    /**
     * The <code>&#46;</code> (dot) operator.
     *
     * @see FullIdent
     **/
    public static final int DOT = GeneratedJavaTokenTypes.DOT;
    /**
     * The <code>*</code> (multiplication or wildcard) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5.2">Java
     * Language Specification, &sect;7.5.2</a>
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.1">Java
     * Language Specification, &sect;15.17.1</a>
     * @see #EXPR
     * @see #IMPORT
     **/
    public static final int STAR = GeneratedJavaTokenTypes.STAR;
    /**
     * The <code>private</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PRIVATE =
        GeneratedJavaTokenTypes.LITERAL_private;

    /**
     * The <code>public</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PUBLIC =
        GeneratedJavaTokenTypes.LITERAL_public;

    /**
     * The <code>protected</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PROTECTED =
        GeneratedJavaTokenTypes.LITERAL_protected;

    /**
     * The <code>static</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_STATIC =
        GeneratedJavaTokenTypes.LITERAL_static;

    /**
     * The <code>transient</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_TRANSIENT =
        GeneratedJavaTokenTypes.LITERAL_transient;

    /**
     * The <code>native</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_NATIVE =
        GeneratedJavaTokenTypes.LITERAL_native;

    /**
     * The <code>synchronized</code> keyword.  This may be used as a
     * modifier of a method or in the definition of a synchronized
     * block.
     *
     * <p>For example:</p>
     *
     * <pre>
     * synchronized(this)
     * {
     *   x++;
     * }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--LITERAL_SYNCHRONIZED (synchronized)
     *     |
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--LITERAL_THIS (this)
     *     +--RPAREN ())
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--POST_INC (++)
     *                 |
     *                 +--IDENT (x)
     *         +--SEMI (;)
     *         +--RCURLY (})
     * +--RCURLY (})
     * </pre>
     *
     * @see #MODIFIERS
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #SLIST
     * @see #RCURLY
     **/
    public static final int LITERAL_SYNCHRONIZED =
        GeneratedJavaTokenTypes.LITERAL_synchronized;

    /**
     * The <code>volatile</code> keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_VOLATILE =
        GeneratedJavaTokenTypes.LITERAL_volatile;

    /**
     * The <code>class</code> keyword.  This element appears both
     * as part of a class declaration, and inline to reference a
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
    public static final int LITERAL_CLASS =
        GeneratedJavaTokenTypes.LITERAL_class;

    /**
     * The <code>interface</code> keyword. This token appears in
     * interface definition.
     *
     * @see #INTERFACE_DEF
     **/
    public static final int LITERAL_INTERFACE =
        GeneratedJavaTokenTypes.LITERAL_interface;

    /**
     * A left (curly) brace (<code>{</code>).
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int LCURLY = GeneratedJavaTokenTypes.LCURLY;
    /**
     * A right (curly) brace (<code>}</code>).
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int RCURLY = GeneratedJavaTokenTypes.RCURLY;
    /**
     * The <code>,</code> (comma) operator.
     *
     * @see #ARRAY_INIT
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #LITERAL_THROWS
     * @see #IMPLEMENTS_CLAUSE
     **/
    public static final int COMMA = GeneratedJavaTokenTypes.COMMA;

    /**
     * A left parenthesis (<code>(</code>).
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     **/
    public static final int LPAREN = GeneratedJavaTokenTypes.LPAREN;
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
    public static final int RPAREN = GeneratedJavaTokenTypes.RPAREN;
    /**
     * The <code>this</code> keyword.
     *
     * @see #EXPR
     * @see #CTOR_CALL
     **/
    public static final int LITERAL_THIS =
        GeneratedJavaTokenTypes.LITERAL_this;

    /**
     * The <code>super</code> keyword.
     *
     * @see #EXPR
     * @see #SUPER_CTOR_CALL
     **/
    public static final int LITERAL_SUPER =
        GeneratedJavaTokenTypes.LITERAL_super;

    /**
     * The <code>=</code> (assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.1">Java
     * Language Specification, &sect;15.26.1</a>
     * @see #EXPR
     **/
    public static final int ASSIGN = GeneratedJavaTokenTypes.ASSIGN;
    /**
     * The <code>throws</code> keyword.  The children are a number of
     * one or more identifiers separated by commas.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.4">Java
     * Language Specification, &sect;8.4.4</a>
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     * @see FullIdent
     **/
    public static final int LITERAL_THROWS =
        GeneratedJavaTokenTypes.LITERAL_throws;

    /**
     * The <code>:</code> (colon) operator.  This will appear as part
     * of the conditional operator (<code>? :</code>).
     *
     * @see #QUESTION
     * @see #LABELED_STAT
     * @see #CASE_GROUP
     **/
    public static final int COLON = GeneratedJavaTokenTypes.COLON;

    /**
     * The <code>::</code> (double colon) operator.
     * It is part of Java 8 syntax that is used for method reference.
     * @see #METHOD_REF
     */
    public static final int DOUBLE_COLON = GeneratedJavaTokenTypes.DOUBLE_COLON;
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
    public static final int LITERAL_IF = GeneratedJavaTokenTypes.LITERAL_if;
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
    public static final int LITERAL_FOR = GeneratedJavaTokenTypes.LITERAL_for;
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
    public static final int LITERAL_WHILE =
        GeneratedJavaTokenTypes.LITERAL_while;

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
     * while(x &lt; 5);
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
     *         +--LT (&lt;)
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
    public static final int LITERAL_DO = GeneratedJavaTokenTypes.LITERAL_do;
    /**
     * Literal <code>while</code> in do-while loop.
     * @see #LITERAL_DO
     */
    public static final int DO_WHILE = GeneratedJavaTokenTypes.DO_WHILE;
    /**
     * The <code>break</code> keyword.  The first child is an optional
     * identifier and the last child is a semicolon.
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_BREAK =
        GeneratedJavaTokenTypes.LITERAL_break;

    /**
     * The <code>continue</code> keyword.  The first child is an
     * optional identifier and the last child is a semicolon.
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_CONTINUE =
        GeneratedJavaTokenTypes.LITERAL_continue;

    /**
     * The <code>return</code> keyword.  The first child is an
     * optional expression for the return value.  The last child is a
     * semi colon.
     *
     * @see #EXPR
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_RETURN =
        GeneratedJavaTokenTypes.LITERAL_return;

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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.10">Java
     * Language Specification, &sect;14.10</a>
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #LCURLY
     * @see #CASE_GROUP
     * @see #RCURLY
     * @see #SLIST
     **/
    public static final int LITERAL_SWITCH =
        GeneratedJavaTokenTypes.LITERAL_switch;

    /**
     * The <code>throw</code> keyword.  The first child is an
     * expression that evaluates to a <code>Throwable</code> instance.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.17">Java
     * Language Specification, &sect;14.17</a>
     * @see #SLIST
     * @see #EXPR
     **/
    public static final int LITERAL_THROW =
        GeneratedJavaTokenTypes.LITERAL_throw;

    /**
     * The <code>else</code> keyword.  This appears as a child of an
     * <code>if</code> statement.
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LITERAL_IF
     **/
    public static final int LITERAL_ELSE =
        GeneratedJavaTokenTypes.LITERAL_else;

    /**
     * The <code>case</code> keyword.  The first child is a constant
     * expression that evaluates to a integer.
     *
     * @see #CASE_GROUP
     * @see #EXPR
     **/
    public static final int LITERAL_CASE =
        GeneratedJavaTokenTypes.LITERAL_case;

    /**
     * The <code>default</code> keyword.  This element has no
     * children.
     *
     * @see #CASE_GROUP
     * @see #MODIFIERS
     **/
    public static final int LITERAL_DEFAULT =
        GeneratedJavaTokenTypes.LITERAL_default;

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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.19">Java
     * Language Specification, &sect;14.19</a>
     * @see #SLIST
     * @see #LITERAL_CATCH
     * @see #LITERAL_FINALLY
     **/
    public static final int LITERAL_TRY = GeneratedJavaTokenTypes.LITERAL_try;

    /**
     * Java 7 try-with-resources construct.
     *
     * <p>For example:</p>
     * <pre>
     * try (Foo foo = new Foo(); Bar bar = new Bar()) { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--LITERAL_TRY (try)
     *     |
     *     +--RESOURCE_SPECIFICATION
     *         |
     *         +--LPAREN (()
     *         +--RESOURCES
     *             |
     *             +--RESOURCE
     *                 |
     *                 +--MODIFIERS
     *                 +--TYPE
     *                     |
     *                     +--IDENT (Foo)
     *                 +--IDENT (foo)
     *                 +--ASSIGN (=)
     *                 +--EXPR
     *                    |
     *                    +--LITERAL_NEW (new)
     *                       |
     *                       +--IDENT (Foo)
     *                       +--LPAREN (()
     *                       +--ELIST
     *                       +--RPAREN ())
     *             +--SEMI (;)
     *             +--RESOURCE
     *                 |
     *                 +--MODIFIERS
     *                 +--TYPE
     *                     |
     *                     +--IDENT (Bar)
     *                 +--IDENT (bar)
     *                 +--ASSIGN (=)
     *                 +--EXPR
     *                    |
     *                    +--LITERAL_NEW (new)
     *                       |
     *                       +--IDENT (Bar)
     *                       +--LPAREN (()
     *                       +--ELIST
     *                       +--RPAREN ())
     *         +--RPAREN ())
     *     +--SLIST ({)
     *         +--RCURLY (})
     * </pre>
     *
     * @see #LPAREN
     * @see #RESOURCES
     * @see #RESOURCE
     * @see #SEMI
     * @see #RPAREN
     * @see #LITERAL_TRY
     **/
    public static final int RESOURCE_SPECIFICATION =
        GeneratedJavaTokenTypes.RESOURCE_SPECIFICATION;

    /**
     * Java 7 try-with-resources construct.
     *
     * @see #RESOURCE_SPECIFICATION
     **/
    public static final int RESOURCES =
        GeneratedJavaTokenTypes.RESOURCES;

    /**
     * Java 7 try-with-resources construct.
     *
     * @see #RESOURCE_SPECIFICATION
     **/
    public static final int RESOURCE =
        GeneratedJavaTokenTypes.RESOURCE;

    /**
     * The <code>catch</code> keyword.
     *
     * @see #LPAREN
     * @see #PARAMETER_DEF
     * @see #RPAREN
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_CATCH =
        GeneratedJavaTokenTypes.LITERAL_catch;

    /**
     * The <code>finally</code> keyword.
     *
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_FINALLY =
        GeneratedJavaTokenTypes.LITERAL_finally;

    /**
     * The <code>+=</code> (addition assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int PLUS_ASSIGN = GeneratedJavaTokenTypes.PLUS_ASSIGN;
    /**
     * The <code>-=</code> (subtraction assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MINUS_ASSIGN =
        GeneratedJavaTokenTypes.MINUS_ASSIGN;

    /**
     * The <code>*=</code> (multiplication assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int STAR_ASSIGN = GeneratedJavaTokenTypes.STAR_ASSIGN;
    /**
     * The <code>/=</code> (division assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int DIV_ASSIGN = GeneratedJavaTokenTypes.DIV_ASSIGN;
    /**
     * The <code>%=</code> (remainder assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MOD_ASSIGN = GeneratedJavaTokenTypes.MOD_ASSIGN;
    /**
     * The <code>&gt;&gt;=</code> (signed right shift assignment)
     * operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SR_ASSIGN = GeneratedJavaTokenTypes.SR_ASSIGN;
    /**
     * The <code>&gt;&gt;&gt;=</code> (unsigned right shift assignment)
     * operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BSR_ASSIGN = GeneratedJavaTokenTypes.BSR_ASSIGN;
    /**
     * The <code>&lt;&lt;=</code> (left shift assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SL_ASSIGN = GeneratedJavaTokenTypes.SL_ASSIGN;
    /**
     * The <code>&amp;=</code> (bitwise AND assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BAND_ASSIGN = GeneratedJavaTokenTypes.BAND_ASSIGN;
    /**
     * The <code>^=</code> (bitwise exclusive OR assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BXOR_ASSIGN = GeneratedJavaTokenTypes.BXOR_ASSIGN;
    /**
     * The <code>|=</code> (bitwise OR assignment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BOR_ASSIGN = GeneratedJavaTokenTypes.BOR_ASSIGN;
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
     * parses as:
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
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.25">Java
     * Language Specification, &sect;15.25</a>
     * @see #EXPR
     * @see #COLON
     **/
    public static final int QUESTION = GeneratedJavaTokenTypes.QUESTION;
    /**
     * The <code>||</code> (conditional OR) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">Java
     * Language Specification, &sect;15.24</a>
     * @see #EXPR
     **/
    public static final int LOR = GeneratedJavaTokenTypes.LOR;
    /**
     * The <code>&amp;&amp;</code> (conditional AND) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">Java
     * Language Specification, &sect;15.23</a>
     * @see #EXPR
     **/
    public static final int LAND = GeneratedJavaTokenTypes.LAND;
    /**
     * The <code>|</code> (bitwise OR) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BOR = GeneratedJavaTokenTypes.BOR;
    /**
     * The <code>^</code> (bitwise exclusive OR) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BXOR = GeneratedJavaTokenTypes.BXOR;
    /**
     * The <code>&amp;</code> (bitwise AND) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BAND = GeneratedJavaTokenTypes.BAND;
    /**
     * The <code>&#33;=</code> (not equal) operator.
     *
     * @see #EXPR
     **/
    public static final int NOT_EQUAL = GeneratedJavaTokenTypes.NOT_EQUAL;
    /**
     * The <code>==</code> (equal) operator.
     *
     * @see #EXPR
     **/
    public static final int EQUAL = GeneratedJavaTokenTypes.EQUAL;
    /**
     * The <code>&lt;</code> (less than) operator.
     *
     * @see #EXPR
     **/
    public static final int LT = GeneratedJavaTokenTypes.LT;
    /**
     * The <code>&gt;</code> (greater than) operator.
     *
     * @see #EXPR
     **/
    public static final int GT = GeneratedJavaTokenTypes.GT;
    /**
     * The <code>&lt;=</code> (less than or equal) operator.
     *
     * @see #EXPR
     **/
    public static final int LE = GeneratedJavaTokenTypes.LE;
    /**
     * The <code>&gt;=</code> (greater than or equal) operator.
     *
     * @see #EXPR
     **/
    public static final int GE = GeneratedJavaTokenTypes.GE;
    /**
     * The <code>instanceof</code> operator.  The first child is an
     * object reference or something that evaluates to an object
     * reference.  The second child is a reference type.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.20.2">Java
     * Language Specification, &sect;15.20.2</a>
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #IDENT
     * @see #DOT
     * @see #TYPE
     * @see FullIdent
     **/
    public static final int LITERAL_INSTANCEOF =
        GeneratedJavaTokenTypes.LITERAL_instanceof;

    /**
     * The <code>&lt;&lt;</code> (shift left) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SL = GeneratedJavaTokenTypes.SL;
    /**
     * The <code>&gt;&gt;</code> (signed shift right) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SR = GeneratedJavaTokenTypes.SR;
    /**
     * The <code>&gt;&gt;&gt;</code> (unsigned shift right) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int BSR = GeneratedJavaTokenTypes.BSR;
    /**
     * The <code>+</code> (addition) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int PLUS = GeneratedJavaTokenTypes.PLUS;
    /**
     * The <code>-</code> (subtraction) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int MINUS = GeneratedJavaTokenTypes.MINUS;
    /**
     * The <code>/</code> (division) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.2">Java
     * Language Specification, &sect;15.17.2</a>
     * @see #EXPR
     **/
    public static final int DIV = GeneratedJavaTokenTypes.DIV;
    /**
     * The <code>%</code> (remainder) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.3">Java
     * Language Specification, &sect;15.17.3</a>
     * @see #EXPR
     **/
    public static final int MOD = GeneratedJavaTokenTypes.MOD;
    /**
     * The <code>++</code> (prefix increment) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.1">Java
     * Language Specification, &sect;15.15.1</a>
     * @see #EXPR
     * @see #POST_INC
     **/
    public static final int INC = GeneratedJavaTokenTypes.INC;
    /**
     * The <code>--</code> (prefix decrement) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.2">Java
     * Language Specification, &sect;15.15.2</a>
     * @see #EXPR
     * @see #POST_DEC
     **/
    public static final int DEC = GeneratedJavaTokenTypes.DEC;
    /**
     * The <code>~</code> (bitwise complement) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.5">Java
     * Language Specification, &sect;15.15.5</a>
     * @see #EXPR
     **/
    public static final int BNOT = GeneratedJavaTokenTypes.BNOT;
    /**
     * The <code>&#33;</code> (logical complement) operator.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.6">Java
     * Language Specification, &sect;15.15.6</a>
     * @see #EXPR
     **/
    public static final int LNOT = GeneratedJavaTokenTypes.LNOT;
    /**
     * The <code>true</code> keyword.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_FALSE
     **/
    public static final int LITERAL_TRUE =
        GeneratedJavaTokenTypes.LITERAL_true;

    /**
     * The <code>false</code> keyword.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_TRUE
     **/
    public static final int LITERAL_FALSE =
        GeneratedJavaTokenTypes.LITERAL_false;

    /**
     * The <code>null</code> keyword.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.7">Java
     * Language Specification, &sect;3.10.7</a>
     * @see #EXPR
     **/
    public static final int LITERAL_NULL =
        GeneratedJavaTokenTypes.LITERAL_null;

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
    public static final int LITERAL_NEW = GeneratedJavaTokenTypes.LITERAL_new;
    /**
     * An integer literal.  These may be specified in decimal,
     * hexadecimal, or octal form.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_LONG
     **/
    public static final int NUM_INT = GeneratedJavaTokenTypes.NUM_INT;
    /**
     * A character literal.  This is a (possibly escaped) character
     * enclosed in single quotes.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.4">Java
     * Language Specification, &sect;3.10.4</a>
     * @see #EXPR
     **/
    public static final int CHAR_LITERAL =
        GeneratedJavaTokenTypes.CHAR_LITERAL;

    /**
     * A string literal.  This is a sequence of (possibly escaped)
     * characters enclosed in double quotes.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.5">Java
     * Language Specification, &sect;3.10.5</a>
     * @see #EXPR
     **/
    public static final int STRING_LITERAL =
        GeneratedJavaTokenTypes.STRING_LITERAL;

    /**
     * A single precision floating point literal.  This is a floating
     * point number with an <code>F</code> or <code>f</code> suffix.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_DOUBLE
     **/
    public static final int NUM_FLOAT = GeneratedJavaTokenTypes.NUM_FLOAT;
    /**
     * A long integer literal.  These are almost the same as integer
     * literals, but they have an <code>L</code> or <code>l</code>
     * (ell) suffix.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_INT
     **/
    public static final int NUM_LONG = GeneratedJavaTokenTypes.NUM_LONG;
    /**
     * A double precision floating point literal.  This is a floating
     * point number with an optional <code>D</code> or <code>d</code>
     * suffix.
     *
     * @see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_FLOAT
     **/
    public static final int NUM_DOUBLE = GeneratedJavaTokenTypes.NUM_DOUBLE;

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
    public static final int LITERAL_ASSERT = GeneratedJavaTokenTypes.ASSERT;

    /**
     * A static import declaration.  Static import declarations are optional,
     * but must appear after the package declaration and before the type
     * declaration.
     *
     * <p>For example:</p>
     *
     * <pre>
     *   import static java.io.IOException;
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--STATIC_IMPORT (import)
     *     |
     *     +--LITERAL_STATIC
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
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #LITERAL_STATIC
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     **/
    public static final int STATIC_IMPORT =
        GeneratedJavaTokenTypes.STATIC_IMPORT;

    /**
     * An enum declaration. Its notable children are
     * enum constant declarations followed by
     * any construct that may be expected in a class body.
     *
     * <p>For example:</p>
     * <pre>
     * public enum MyEnum
     *   implements Serializable
     * {
     *     FIRST_CONSTANT,
     *     SECOND_CONSTANT;
     *
     *     public void someMethod()
     *     {
     *     }
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--ENUM_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--ENUM (enum)
     *     +--IDENT (MyEnum)
     *     +--EXTENDS_CLAUSE
     *     +--IMPLEMENTS_CLAUSE
     *         |
     *         +--IDENT (Serializable)
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--ENUM_CONSTANT_DEF
     *             |
     *             +--IDENT (FIRST_CONSTANT)
     *         +--COMMA (,)
     *         +--ENUM_CONSTANT_DEF
     *             |
     *             +--IDENT (SECOND_CONSTANT)
     *         +--SEMI (;)
     *         +--METHOD_DEF
     *             |
     *             +--MODIFIERS
     *                 |
     *                 +--LITERAL_PUBLIC (public)
     *             +--TYPE
     *                 |
     *                 +--LITERAL_void (void)
     *             +--IDENT (someMethod)
     *             +--LPAREN (()
     *             +--PARAMETERS
     *             +--RPAREN ())
     *             +--SLIST ({)
     *                 |
     *                 +--RCURLY (})
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #MODIFIERS
     * @see #ENUM
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     * @see #ENUM_CONSTANT_DEF
     **/
    public static final int ENUM_DEF =
        GeneratedJavaTokenTypes.ENUM_DEF;

    /**
     * The <code>enum</code> keyword.  This element appears
     * as part of an enum declaration.
     **/
    public static final int ENUM =
        GeneratedJavaTokenTypes.ENUM;

    /**
     * An enum constant declaration. Its notable children are annotations,
     * arguments and object block akin to an anonymous
     * inner class' body.
     *
     * <p>For example:</p>
     * <pre>
     * SOME_CONSTANT(1)
     * {
     *     public void someMethodOverriddenFromMainBody()
     *     {
     *     }
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--ENUM_CONSTANT_DEF
     *     |
     *     +--ANNOTATIONS
     *     +--IDENT (SOME_CONSTANT)
     *     +--LPAREN (()
     *     +--ELIST
     *         |
     *         +--EXPR
     *             |
     *             +--NUM_INT (1)
     *     +--RPAREN ())
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         |
     *         +--METHOD_DEF
     *             |
     *             +--MODIFIERS
     *                 |
     *                 +--LITERAL_PUBLIC (public)
     *             +--TYPE
     *                 |
     *                 +--LITERAL_void (void)
     *             +--IDENT (someMethodOverriddenFromMainBody)
     *             +--LPAREN (()
     *             +--PARAMETERS
     *             +--RPAREN ())
     *             +--SLIST ({)
     *                 |
     *                 +--RCURLY (})
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATIONS
     * @see #MODIFIERS
     * @see #IDENT
     * @see #ELIST
     * @see #OBJBLOCK
     **/
    public static final int ENUM_CONSTANT_DEF =
        GeneratedJavaTokenTypes.ENUM_CONSTANT_DEF;

    /**
     * A for-each clause.  This is a child of
     * <code>LITERAL_FOR</code>.  The children of this element may be
     * a parameter definition, the colon literal and an expression.
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_EACH_CLAUSE =
        GeneratedJavaTokenTypes.FOR_EACH_CLAUSE;

    /**
     * An annotation declaration. The notable children are the name of the
     * annotation type, annotation field declarations and (constant) fields.
     *
     * <p>For example:</p>
     * <pre>
     * public @interface MyAnnotation
     * {
     *     int someValue();
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * +--ANNOTATION_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--AT (@)
     *     +--LITERAL_INTERFACE (interface)
     *     +--IDENT (MyAnnotation)
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *         +--ANNOTATION_FIELD_DEF
     *             |
     *             +--MODIFIERS
     *             +--TYPE
     *                 |
     *                 +--LITERAL_INT (int)
     *             +--IDENT (someValue)
     *             +--LPAREN (()
     *             +--RPAREN ())
     *             +--SEMI (;)
     *         +--RCURLY (})
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #MODIFIERS
     * @see #LITERAL_INTERFACE
     * @see #IDENT
     * @see #OBJBLOCK
     * @see #ANNOTATION_FIELD_DEF
     **/
    public static final int ANNOTATION_DEF =
        GeneratedJavaTokenTypes.ANNOTATION_DEF;

    /**
     * An annotation field declaration.  The notable children are modifiers,
     * field type, field name and an optional default value (a conditional
     * compile-time constant expression). Default values may also by
     * annotations.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     String someField() default "Hello world";
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--ANNOTATION_FIELD_DEF
     *     |
     *     +--MODIFIERS
     *     +--TYPE
     *         |
     *         +--IDENT (String)
     *     +--IDENT (someField)
     *     +--LPAREN (()
     *     +--RPAREN ())
     *     +--LITERAL_DEFAULT (default)
     *     +--STRING_LITERAL ("Hello world")
     *     +--SEMI (;)
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #MODIFIERS
     * @see #TYPE
     * @see #LITERAL_DEFAULT
     */
    public static final int ANNOTATION_FIELD_DEF =
        GeneratedJavaTokenTypes.ANNOTATION_FIELD_DEF;

    // note: &#064; is the html escape for '@',
    // used here to avoid confusing the javadoc tool
    /**
     * A collection of annotations on a package or enum constant.
     * A collections of annotations will only occur on these nodes
     * as all other nodes that may be qualified with an annotation can
     * be qualified with any other modifier and hence these annotations
     * would be contained in a {@link #MODIFIERS} node.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     &#064;MyAnnotation package blah;
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--PACKAGE_DEF (package)
     *     |
     *     +--ANNOTATIONS
     *         |
     *         +--ANNOTATION
     *             |
     *             +--AT (&#064;)
     *             +--IDENT (MyAnnotation)
     *     +--IDENT (blah)
     *     +--SEMI (;)
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATION
     * @see #AT
     * @see #IDENT
     */
    public static final int ANNOTATIONS =
        GeneratedJavaTokenTypes.ANNOTATIONS;

    // note: &#064; is the html escape for '@',
    // used here to avoid confusing the javadoc tool
    /**
     * An annotation of a package, type, field, parameter or variable.
     * An annotation may occur anywhere modifiers occur (it is a
     * type of modifier) and may also occur prior to a package definition.
     * The notable children are: The annotation name and either a single
     * default annotation value or a sequence of name value pairs.
     * Annotation values may also be annotations themselves.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     &#064;MyAnnotation(someField1 = "Hello",
     *                    someField2 = &#064;SomeOtherAnnotation)
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--ANNOTATION
     *     |
     *     +--AT (&#064;)
     *     +--IDENT (MyAnnotation)
     *     +--LPAREN (()
     *     +--ANNOTATION_MEMBER_VALUE_PAIR
     *         |
     *         +--IDENT (someField1)
     *         +--ASSIGN (=)
     *         +--ANNOTATION
     *             |
     *             +--AT (&#064;)
     *             +--IDENT (SomeOtherAnnotation)
     *     +--ANNOTATION_MEMBER_VALUE_PAIR
     *         |
     *         +--IDENT (someField2)
     *         +--ASSIGN (=)
     *         +--STRING_LITERAL ("Hello")
     *     +--RPAREN ())
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #ANNOTATION_MEMBER_VALUE_PAIR
     */
    public static final int ANNOTATION =
        GeneratedJavaTokenTypes.ANNOTATION;

    /**
     * An initialisation of an annotation member with a value.
     * Its children are the name of the member, the assignment literal
     * and the (compile-time constant conditional expression) value.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     */
    public static final int ANNOTATION_MEMBER_VALUE_PAIR =
        GeneratedJavaTokenTypes.ANNOTATION_MEMBER_VALUE_PAIR;

    /**
     * An annotation array member initialisation.
     * Initializers can not be nested.
     * Am initializer may be present as a default to a annotation
     * member, as the single default value to an annotation
     * (e.g. @Annotation({1,2})) or as the value of an annotation
     * member value pair.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     { 1, 2 }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--ANNOTATION_ARRAY_INIT ({)
     *     |
     *     +--NUM_INT (1)
     *     +--COMMA (,)
     *     +--NUM_INT (2)
     *     +--RCURLY (})
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     * @see #ANNOTATION_MEMBER_VALUE_PAIR
     */
    public static final int ANNOTATION_ARRAY_INIT =
        GeneratedJavaTokenTypes.ANNOTATION_ARRAY_INIT;

    /**
     * A list of type parameters to a class, interface or
     * method definition. Children are LT, at least one
     * TYPE_PARAMETER, zero or more of: a COMMAs followed by a single
     * TYPE_PARAMETER and a final GT.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     public class Blah&lt;A, B&gt;
     *     {
     *     }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--CLASS_DEF ({)
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--LITERAL_CLASS (class)
     *     +--IDENT (Blah)
     *     +--TYPE_PARAMETERS
     *         |
     *         +--GENERIC_START (&lt;)
     *         +--TYPE_PARAMETER
     *             |
     *             +--IDENT (A)
     *         +--COMMA (,)
     *         +--TYPE_PARAMETER
     *             |
     *             +--IDENT (B)
     *         +--GENERIC_END (&gt;)
     *     +--OBJBLOCK
     *         |
     *         +--LCURLY ({)
     *     +--NUM_INT (1)
     *     +--COMMA (,)
     *     +--NUM_INT (2)
     *     +--RCURLY (})
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #GENERIC_START
     * @see #GENERIC_END
     * @see #TYPE_PARAMETER
     * @see #COMMA
     */
    public static final int TYPE_PARAMETERS =
        GeneratedJavaTokenTypes.TYPE_PARAMETERS;

    /**
     * A type parameter to a class, interface or method definition.
     * Children are the type name and an optional TYPE_UPPER_BOUNDS.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     A extends Collection
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--TYPE_PARAMETER
     *     |
     *     +--IDENT (A)
     *     +--TYPE_UPPER_BOUNDS
     *         |
     *         +--IDENT (Collection)
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #IDENT
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     */
    public static final int TYPE_PARAMETER =
        GeneratedJavaTokenTypes.TYPE_PARAMETER;

    /**
     * A list of type arguments to a type reference or
     * a method/ctor invocation. Children are GENERIC_START, at least one
     * TYPE_ARGUMENT, zero or more of a COMMAs followed by a single
     * TYPE_ARGUMENT, and a final GENERIC_END.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     public Collection&lt;?&gt; a;
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--VARIABLE_DEF
     *     |
     *     +--MODIFIERS
     *         |
     *         +--LITERAL_PUBLIC (public)
     *     +--TYPE
     *         |
     *         +--IDENT (Collection)
     *             |
     *             +--TYPE_ARGUMENTS
     *                 |
     *                 +--GENERIC_START (&lt;)
     *                 +--TYPE_ARGUMENT
     *                     |
     *                     +--WILDCARD_TYPE (?)
     *                 +--GENERIC_END (&gt;)
     *     +--IDENT (a)
     *     +--SEMI (;)
     * </pre>
     *
     * @see #GENERIC_START
     * @see #GENERIC_END
     * @see #TYPE_ARGUMENT
     * @see #COMMA
     */
    public static final int TYPE_ARGUMENTS =
        GeneratedJavaTokenTypes.TYPE_ARGUMENTS;

    /**
     * A type arguments to a type reference or a method/ctor invocation.
     * Children are either: type name or wildcard type with possible type
     * upper or lower bounds.
     *
     * <p>For example:</p>
     *
     * <pre>
     *     ? super List
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * +--TYPE_ARGUMENT
     *     |
     *     +--WILDCARD_TYPE (?)
     *     +--TYPE_LOWER_BOUNDS
     *         |
     *         +--IDENT (List)
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int TYPE_ARGUMENT =
        GeneratedJavaTokenTypes.TYPE_ARGUMENT;

    /**
     * The type that refers to all types. This node has no children.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_ARGUMENT
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int WILDCARD_TYPE =
        GeneratedJavaTokenTypes.WILDCARD_TYPE;

    /**
     * An upper bounds on a wildcard type argument or type parameter.
     * This node has one child - the type that is being used for
     * the bounding.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_PARAMETER
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_UPPER_BOUNDS =
        GeneratedJavaTokenTypes.TYPE_UPPER_BOUNDS;

    /**
     * A lower bounds on a wildcard type argument. This node has one child
     *  - the type that is being used for the bounding.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_LOWER_BOUNDS =
        GeneratedJavaTokenTypes.TYPE_LOWER_BOUNDS;

    /**
     * An 'at' symbol - signifying an annotation instance or the prefix
     * to the interface literal signifying the definition of an annotation
     * declaration.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     */
    public static final int AT = GeneratedJavaTokenTypes.AT;

    /**
     * A triple dot for variable-length parameters. This token only ever occurs
     * in a parameter declaration immediately after the type of the parameter.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     */
    public static final int ELLIPSIS = GeneratedJavaTokenTypes.ELLIPSIS;

    /**
     * '&amp;' symbol when used in a generic upper or lower bounds constrain
     * e.g. {@code Comparable&lt;<? extends Serializable, CharSequence>}.
     */
    public static final int TYPE_EXTENSION_AND =
        GeneratedJavaTokenTypes.TYPE_EXTENSION_AND;

    /**
     * '&lt;' symbol signifying the start of type arguments or type
     * parameters.
     */
    public static final int GENERIC_START =
        GeneratedJavaTokenTypes.GENERIC_START;

    /**
     * '&gt;' symbol signifying the end of type arguments or type parameters.
     */
    public static final int GENERIC_END = GeneratedJavaTokenTypes.GENERIC_END;

    /**
     * Special lambda symbol '-&gt;'.
     */
    public static final int LAMBDA = GeneratedJavaTokenTypes.LAMBDA;

    /**
     * Beginning of single line comment: '//'.
     *
     * <pre>
     * +--SINGLE_LINE_COMMENT
     *         |
     *         +--COMMENT_CONTENT
     * </pre>
     */
    public static final int SINGLE_LINE_COMMENT =
            GeneratedJavaTokenTypes.SINGLE_LINE_COMMENT;

    /**
     * Beginning of block comment: '/*'.
     *
     * <pre>
     * +--BLOCK_COMMENT_BEGIN
     *         |
     *         +--COMMENT_CONTENT
     *         +--BLOCK_COMMENT_END
     * </pre>
     */
    public static final int BLOCK_COMMENT_BEGIN =
            GeneratedJavaTokenTypes.BLOCK_COMMENT_BEGIN;

    /**
     * End of block comment: '* /'.
     *
     * <pre>
     * +--BLOCK_COMMENT_BEGIN
     *         |
     *         +--COMMENT_CONTENT
     *         +--BLOCK_COMMENT_END
     * </pre>
     */
    public static final int BLOCK_COMMENT_END =
            GeneratedJavaTokenTypes.BLOCK_COMMENT_END;

    /**
     * Text of single-line or block comment.
     *
     *<pre>
     * +--SINGLE_LINE_COMMENT
     *         |
     *         +--COMMENT_CONTENT
     * </pre>
     *
     * <pre>
     * +--BLOCK_COMMENT_BEGIN
     *         |
     *         +--COMMENT_CONTENT
     *         +--BLOCK_COMMENT_END
     * </pre>
     */
    public static final int COMMENT_CONTENT =
            GeneratedJavaTokenTypes.COMMENT_CONTENT;

    /** Prevent instantiation. */
    private TokenTypes() {
    }

}
