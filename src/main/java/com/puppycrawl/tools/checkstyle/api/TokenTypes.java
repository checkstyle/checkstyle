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

package com.puppycrawl.tools.checkstyle.api;

import org.antlr.v4.runtime.Recognizer;

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLexer;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree.
 *
 * <p>Implementation detail: This class has been introduced to break
 * the circular dependency between packages.</p>
 *
 * @noinspection ClassWithTooManyDependents
 */
public final class TokenTypes {

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
    public static final int EOF = Recognizer.EOF;
    /**
     * Modifiers for type, method, and field declarations.  The
     * modifiers element is always present even though it may have no
     * children.
     * <p>For example:</p>
     * <pre>
     * public int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
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
    public static final int MODIFIERS = JavaLexer.MODIFIERS;

    /**
     * An object block.  These are children of class, interface, enum,
     * annotation and enum constant declarations.
     * Also, object blocks are children of the new keyword when defining
     * anonymous inner types.
     * <p>For example:</p>
     * <pre>
     * class Test {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Test
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
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
    public static final int OBJBLOCK = JavaLexer.OBJBLOCK;
    /**
     * A list of statements.
     *
     * <p>For example:</p>
     * <pre>
     * if (c == 1) {
     *     c = 0;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--EQUAL -&gt; ==
     *  |       |--IDENT -&gt; c
     *  |       `--NUM_INT -&gt; 1
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--EXPR -&gt; EXPR
     *      |   `--ASSIGN -&gt; =
     *      |       |--IDENT -&gt; c
     *      |       `--NUM_INT -&gt; 0
     *      |--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
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
    public static final int SLIST = JavaLexer.SLIST;
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
     * CTOR_DEF -&gt; CTOR_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--IDENT -&gt; SpecialEntry
     *  |--LPAREN -&gt; (
     *  |--PARAMETERS -&gt; PARAMETERS
     *  |   |--PARAMETER_DEF -&gt; PARAMETER_DEF
     *  |   |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |   |--TYPE -&gt; TYPE
     *  |   |   |   `--LITERAL_INT -&gt; int
     *  |   |   `--IDENT -&gt; value
     *  |   |--COMMA -&gt; ,
     *  |   `--PARAMETER_DEF -&gt; PARAMETER_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--IDENT -&gt; String
     *  |       `--IDENT -&gt; text
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--EXPR -&gt; EXPR
     *      |   `--ASSIGN -&gt; =
     *      |       |--DOT -&gt; .
     *      |   |--LITERAL_THIS -&gt; this
     *      |       |   `--IDENT -&gt; value
     *      |       `--IDENT -&gt; value
     *      |--SEMI -&gt; ;
     *      |--EXPR -&gt; EXPR
     *      |   `--ASSIGN -&gt; =
     *      |       |--DOT -&gt; .
     *      |       |   |--LITERAL_THIS -&gt; this
     *      |       |   `--IDENT -&gt; text
     *      |       `--IDENT -&gt; text
     *      |--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #OBJBLOCK
     * @see #CLASS_DEF
     **/
    public static final int CTOR_DEF = JavaLexer.CTOR_DEF;
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
     * --METHOD_DEF -&gt; METHOD_DEF
     *    |--MODIFIERS -&gt; MODIFIERS
     *    |   |--LITERAL_PUBLIC -&gt; public
     *    |   `--LITERAL_STATIC -&gt; static
     *    |--TYPE -&gt; TYPE
     *    |   `--LITERAL_INT -&gt; int
     *    |--IDENT -&gt; square
     *    |--LPAREN -&gt; (
     *    |--PARAMETERS -&gt; PARAMETERS
     *    |   `--PARAMETER_DEF -&gt; PARAMETER_DEF
     *    |       |--MODIFIERS -&gt; MODIFIERS
     *    |       |--TYPE -&gt; TYPE
     *    |       |   `--LITERAL_INT -&gt; int
     *    |       `--IDENT -&gt; x
     *    |--RPAREN -&gt; )
     *    `--SLIST -&gt; {
     *        |--LITERAL_RETURN -&gt; return
     *        |   |--EXPR -&gt; EXPR
     *        |   |   `--STAR -&gt; *
     *        |   |       |--IDENT -&gt; x
     *        |   |       `--IDENT -&gt; x
     *        |   `--SEMI -&gt; ;
     *        `--RCURLY -&gt; }
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
    public static final int METHOD_DEF = JavaLexer.METHOD_DEF;
    /**
     * A field or local variable declaration.  The children are
     * modifiers, type, the identifier name, and an optional
     * assignment statement.
     *
     * <p>For example:</p>
     * <pre>
     * final int PI = 3.14;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--FINAL -&gt; final
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; PI
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--NUM_FLOAT -&gt; 3.14
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #ASSIGN
     **/
    public static final int VARIABLE_DEF =
        JavaLexer.VARIABLE_DEF;

    /**
     * An instance initializer.  Zero or more instance initializers
     * may appear in class and enum definitions.  This token will be a child
     * of the object block of the declaring type.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.6">Java
     * Language Specification&sect;8.6</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int INSTANCE_INIT =
        JavaLexer.INSTANCE_INIT;

    /**
     * A static initialization block.  Zero or more static
     * initializers may be children of the object block of a class
     * or enum declaration (interfaces cannot have static initializers).  The
     * first and only child is a statement list.
     *
     * <p>For Example:</p>
     * <pre>
     * static {
     *   num = 10;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * STATIC_INIT -&gt; STATIC_INIT
     *  `--SLIST -&gt; {
     *      |--EXPR -&gt; EXPR
     *      |   `--ASSIGN -&gt; =
     *      |       |--IDENT -&gt; num
     *      |       `--NUM_INT -&gt; 10
     *      |--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.7">Java
     * Language Specification, &sect;8.7</a>
     * @see #SLIST
     * @see #OBJBLOCK
     **/
    public static final int STATIC_INIT =
        JavaLexer.STATIC_INIT;

    /**
     * A type.  This is either a return type of a method or a type of
     * a variable or field.  The first child of this element is the
     * actual type.  This may be a primitive type, an identifier, a
     * dot which is the root of a fully qualified type, or an array of
     * any of these. The second child may be type arguments to the type.
     *
     * <p>For example:</p>
     * <pre>boolean var = true;</pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_BOOLEAN -&gt; boolean
     * |   |--IDENT -&gt; var
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--LITERAL_TRUE -&gt; true
     * |--SEMI -&gt; ;
     * </pre>
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
    public static final int TYPE = JavaLexer.TYPE;
    /**
     * A class declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public class Test {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Test
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
     * Language Specification, &sect;8</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     **/
    public static final int CLASS_DEF = JavaLexer.CLASS_DEF;
    /**
     * An interface declaration.
     *
     * <p>For example:</p>
     *
     * <pre>
     * public interface MyInterface {
     *
     * }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * INTERFACE_DEF -&gt; INTERFACE_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_INTERFACE -&gt; interface
     * |--IDENT -&gt; MyInterface
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html">Java
     * Language Specification, &sect;9</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #OBJBLOCK
     **/
    public static final int INTERFACE_DEF =
        JavaLexer.INTERFACE_DEF;

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
     * PACKAGE_DEF -&gt; package
     * |--ANNOTATIONS -&gt; ANNOTATIONS
     * |--DOT -&gt; .
     * |   |--DOT -&gt; .
     * |   |   |--DOT -&gt; .
     * |   |   |   |--DOT -&gt; .
     * |   |   |   |   |--IDENT -&gt; com
     * |   |   |   |   `--IDENT -&gt; puppycrawl
     * |   |   |   `--IDENT -&gt; tools
     * |   |   `--IDENT -&gt; checkstyle
     * |   `--IDENT -&gt; api
     * `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.4">Java
     * Language Specification &sect;7.4</a>
     * @see #DOT
     * @see #IDENT
     * @see #SEMI
     * @see #ANNOTATIONS
     * @see FullIdent
     **/
    public static final int PACKAGE_DEF = JavaLexer.PACKAGE_DEF;
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
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   |--LITERAL_INT -&gt; int
     *  |   `--ARRAY_DECLARATOR -&gt; [
     *  |       `--RBRACK -&gt; ]
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * <p>The array declaration may also represent an inline array
     * definition.  In this case, the first child will be either an
     * expression specifying the length of the array or an array
     * initialization block.</p>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html">Java
     * Language Specification &sect;10</a>
     * @see #TYPE
     * @see #ARRAY_INIT
     **/
    public static final int ARRAY_DECLARATOR =
        JavaLexer.ARRAY_DECLARATOR;

    /**
     * An extends clause.  This appear as part of class and interface
     * definitions.  This element appears even if the
     * {@code extends} keyword is not explicitly used.  The child
     * is an optional identifier.
     *
     * <p>For example:</p>
     * <pre>
     * public class Test extends ArrayList {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Test
     * |--EXTENDS_CLAUSE -&gt; extends
     * |   `--IDENT -&gt; ArrayList
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see FullIdent
     **/
    public static final int EXTENDS_CLAUSE =
        JavaLexer.EXTENDS_CLAUSE;

    /**
     * An implements clause.  This always appears in a class or enum
     * declaration, even if there are no implemented interfaces.  The
     * children are a comma separated list of zero or more
     * identifiers.
     *
     * <p>For example:</p>
     * <pre>
     * public class MyClass implements Collection {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; MyClass
     * |--IMPLEMENTS_CLAUSE -&gt; implements
     * |   `--IDENT -&gt; Collection
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #CLASS_DEF
     * @see #ENUM_DEF
     **/
    public static final int IMPLEMENTS_CLAUSE =
        JavaLexer.IMPLEMENTS_CLAUSE;

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
    public static final int PARAMETERS = JavaLexer.PARAMETERS;
    /**
     * A parameter declaration. The last parameter in a list of parameters may
     * be variable length (indicated by the ELLIPSIS child node immediately
     * after the TYPE child).
     * <p>For example</p>
     * <pre>
     *      void foo(int firstParameter, int... secondParameter) {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *     |--MODIFIERS -&gt; MODIFIERS
     *     |--TYPE -&gt; TYPE
     *     |   `--LITERAL_VOID -&gt; void
     *     |--IDENT -&gt; foo
     *     |--LPAREN -&gt; (
     *     |--PARAMETERS -&gt; PARAMETERS
     *     |   |--PARAMETER_DEF -&gt; PARAMETER_DEF
     *     |   |   |--MODIFIERS -&gt; MODIFIERS
     *     |   |   |--TYPE -&gt; TYPE
     *     |   |   |   `--LITERAL_INT -&gt; int
     *     |   |   `--IDENT -&gt; firstParameter
     *     |   |--COMMA -&gt; ,
     *     |   `--PARAMETER_DEF -&gt; PARAMETER_DEF
     *     |       |--MODIFIERS -&gt; MODIFIERS
     *     |       |--TYPE -&gt; TYPE
     *     |       |   `--LITERAL_INT -&gt; int
     *     |       |--ELLIPSIS -&gt; ...
     *     |       `--IDENT -&gt; secondParameter
     *     |--RPAREN -&gt; )
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #ELLIPSIS
     **/
    public static final int PARAMETER_DEF =
        JavaLexer.PARAMETER_DEF;

    /**
     * A labeled statement.
     *
     * <p>For example:</p>
     * <pre>
     * outer:
     * while (i &lt; 10) {
     *     if (i == 5)
     *         continue outer;
     *     i++;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LABELED_STAT -&gt; :
     *  |--IDENT -&gt; outer
     *  `--LITERAL_WHILE -&gt; while
     *      |--LPAREN -&gt; (
     *      |--EXPR -&gt; EXPR
     *      |   `--LT -&gt; &lt;
     *      |       |--IDENT -&gt; i
     *      |       `--NUM_INT -&gt; 10
     *      |--RPAREN -&gt; )
     *      `--SLIST -&gt; {
     *          |--LITERAL_IF -&gt; if
     *          |   |--LPAREN -&gt; (
     *          |   |--EXPR -&gt; EXPR
     *          |   |   `--EQUAL -&gt; ==
     *          |   |       |--IDENT -&gt; i
     *          |   |       `--NUM_INT -&gt; 5
     *          |   |--RPAREN -&gt; )
     *          |   `--LITERAL_CONTINUE -&gt; continue
     *          |       |--IDENT -&gt; outer
     *          |       `--SEMI -&gt; ;
     *          |--EXPR -&gt; EXPR
     *          |   `--POST_INC -&gt; ++
     *          |       `--IDENT -&gt; i
     *          |--SEMI -&gt; ;
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.7">Java
     * Language Specification, &sect;14.7</a>
     * @see #SLIST
     **/
    public static final int LABELED_STAT =
        JavaLexer.LABELED_STAT;

    /**
     * A type-cast.
     *
     * <p>For example:</p>
     * <pre>
     * (String)it.next()
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * `--TYPECAST -&gt; (
     *     |--TYPE -&gt; TYPE
     *     |   `--IDENT -&gt; String
     *     |--RPAREN -&gt; )
     *     `--METHOD_CALL -&gt; (
     *         |--DOT -&gt; .
     *         |   |--IDENT -&gt; it
     *         |   `--IDENT -&gt; next
     *         |--ELIST -&gt; ELIST
     *         `--RPAREN -&gt; )
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">Java
     * Language Specification, &sect;15.16</a>
     * @see #EXPR
     * @see #TYPE
     * @see #TYPE_ARGUMENTS
     * @see #RPAREN
     **/
    public static final int TYPECAST = JavaLexer.TYPECAST;
    /**
     * The array index operator.
     *
     * <p>For example:</p>
     * <pre>
     * arr[0] = 10;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--INDEX_OP -&gt; [
     * |       |   |--IDENT -&gt; arr
     * |       |   |--EXPR -&gt; EXPR
     * |       |   |   `--NUM_INT -&gt; 0
     * |       |   `--RBRACK -&gt; ]
     * |       `--NUM_INT -&gt; 10
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int INDEX_OP = JavaLexer.INDEX_OP;
    /**
     * The {@code ++} (postfix increment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a++;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--POST_INC -&gt; ++
     * |       `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.1">Java
     * Language Specification, &sect;15.14.1</a>
     * @see #EXPR
     * @see #INC
     **/
    public static final int POST_INC = JavaLexer.POST_INC;
    /**
     * The {@code --} (postfix decrement) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a--;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--POST_DEC -&gt; --
     * |       `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.2">Java
     * Language Specification, &sect;15.14.2</a>
     * @see #EXPR
     * @see #DEC
     **/
    public static final int POST_DEC = JavaLexer.POST_DEC;
    /**
     * A method call. A method call may have type arguments however these
     * are attached to the appropriate node in the qualified method name.
     *
     * <p>For example:</p>
     * <pre>
     * Integer.parseInt("123");
     * </pre>
     *
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--METHOD_CALL -&gt; (
     * |       |--DOT -&gt; .
     * |       |   |--IDENT -&gt; Integer
     * |       |   `--IDENT -&gt; parseInt
     * |       |--ELIST -&gt; ELIST
     * |       |   `--EXPR -&gt; EXPR
     * |       |       `--STRING_LITERAL -&gt; "123"
     * |       `--RPAREN -&gt; )
     * |--SEMI -&gt; ;
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
    public static final int METHOD_CALL = JavaLexer.METHOD_CALL;

    /**
     * A reference to a method or constructor without arguments. Part of Java 8 syntax.
     * The token should be used for subscribing for double colon literal.
     * {@link #DOUBLE_COLON} token does not appear in the tree.
     *
     * <p>For example:</p>
     * <pre>
     * Comparator&lt;String&gt; compare = String::compareToIgnoreCase;
     * </pre>
     *
     * <p>parses as:
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   |--IDENT -&gt; Comparator
     * |   |   `--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     * |   |       |--GENERIC_START -&gt; &lt;
     * |   |       |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     * |   |       |   `--IDENT -&gt; String
     * |   |       `--GENERIC_END -&gt; &gt;
     * |   |--IDENT -&gt; compare
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--METHOD_REF -&gt; ::
     * |               |--IDENT -&gt; String
     * |               `--IDENT -&gt; compareToIgnoreCase
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #IDENT
     * @see #DOUBLE_COLON
     */
    public static final int METHOD_REF = JavaLexer.METHOD_REF;
    /**
     * An expression.  Operators with lower precedence appear at a
     * higher level in the tree than operators with higher precedence.
     * Parentheses are siblings to the operator they enclose.
     *
     * <p>For example:</p>
     * <pre>
     * int x = 4 + 2 * (5 % 3) + (1 &lt;&lt; 3) - 4 * 5;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_INT -&gt; int
     * |   |--IDENT -&gt; x
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--MINUS -&gt; -
     * |               |--PLUS -&gt; +
     * |               |   |--PLUS -&gt; +
     * |               |   |   |--NUM_INT -&gt; 4
     * |               |   |   `--STAR -&gt; *
     * |               |   |       |--NUM_INT -&gt; 2
     * |               |   |       |--LPAREN -&gt; (
     * |               |   |       |--MOD -&gt; %
     * |               |   |       |   |--NUM_INT -&gt; 5
     * |               |   |       |   `--NUM_INT -&gt; 3
     * |               |   |       `--RPAREN -&gt; )
     * |               |   |--LPAREN -&gt; (
     * |               |   |--SL -&gt; &lt;&lt;
     * |               |   |   |--NUM_INT -&gt; 1
     * |               |   |   `--NUM_INT -&gt; 3
     * |               |   `--RPAREN -&gt; )
     * |               `--STAR -&gt; *
     * |                   |--NUM_INT -&gt; 4
     * |                   `--NUM_INT -&gt; 5
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #ELIST
     * @see #ASSIGN
     * @see #LPAREN
     * @see #RPAREN
     **/
    public static final int EXPR = JavaLexer.EXPR;
    /**
     * An array initialization.  This may occur as part of an array
     * declaration or inline with {@code new}.
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
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   |--LITERAL_INT -&gt; int
     *  |   `--ARRAY_DECLARATOR -&gt; [
     *  |       `--RBRACK -&gt; ]
     *  |--IDENT -&gt; y
     *  |--ASSIGN -&gt; =
     *  |   `--ARRAY_INIT -&gt; {
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--NUM_INT -&gt; 1
     *  |       |--COMMA -&gt; ,
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--NUM_INT -&gt; 2
     *  |       |--COMMA -&gt; ,
     *  |       `--RCURLY -&gt; }
     *  `--SEMI -&gt; ;
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
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE [2:4]
     *  |   |--LITERAL_INT -&gt; int
     *  |   `--ARRAY_DECLARATOR -&gt; [
     *  |       `--RBRACK -&gt; ]
     *  |--IDENT -&gt; z
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LITERAL_NEW -&gt; new
     *  |           |--LITERAL_INT -&gt; int
     *  |           |--ARRAY_DECLARATOR -&gt; [
     *  |           |   `--RBRACK -&gt; ]
     *  |           `--ARRAY_INIT -&gt; {
     *  |               |--EXPR -&gt; EXPR
     *  |               |   `--NUM_INT -&gt; 1
     *  |               |--COMMA -&gt; ,
     *  |               |--EXPR -&gt; EXPR
     *  |               |   `--NUM_INT -&gt; 2
     *  |               |--COMMA -&gt; ,
     *  |               `--RCURLY -&gt; }
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #ARRAY_DECLARATOR
     * @see #TYPE
     * @see #LITERAL_NEW
     * @see #COMMA
     **/
    public static final int ARRAY_INIT = JavaLexer.ARRAY_INIT;
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
     * IMPORT -&gt; import
     * |--DOT -&gt; .
     * |   |--DOT -&gt; .
     * |   |   |--IDENT -&gt; java
     * |   |   `--IDENT -&gt; io
     * |   `--IDENT -&gt; IOException
     * `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5">Java
     * Language Specification &sect;7.5</a>
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     **/
    public static final int IMPORT = JavaLexer.IMPORT;
    /**
     * The {@code -} (unary minus) operator.
     * <p>For example:</p>
     * <pre>
     * a = -b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--UNARY_MINUS -&gt; -
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.4">Java
     * Language Specification, &sect;15.15.4</a>
     * @see #EXPR
     **/
    public static final int UNARY_MINUS = JavaLexer.UNARY_MINUS;
    /**
     * The {@code +} (unary plus) operator.
     * <p>For example:</p>
     * <pre>
     * a = + b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--UNARY_PLUS -&gt; +
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.3">Java
     * Language Specification, &sect;15.15.3</a>
     * @see #EXPR
     **/
    public static final int UNARY_PLUS = JavaLexer.UNARY_PLUS;
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
     * CASE_GROUP -&gt; CASE_GROUP
     *  |--LITERAL_CASE -&gt; case
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 0
     *  |   `--COLON -&gt; :
     *  |--LITERAL_CASE -&gt; case
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 1
     *  |   `--COLON -&gt; :
     *  |--LITERAL_CASE -&gt; case
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 2
     *  |   `--COLON -&gt; :
     *  `--SLIST -&gt; SLIST
     *      |--EXPR -&gt; EXPR
     *      |   `--ASSIGN -&gt; =
     *      |       |--IDENT -&gt; x
     *      |       `--NUM_INT -&gt; 3
     *      |--SEMI -&gt; ;
     *      `--LITERAL_BREAK -&gt; break
     *          `--SEMI -&gt; ;
     * </pre>
     *
     * @see #LITERAL_CASE
     * @see #LITERAL_DEFAULT
     * @see #LITERAL_SWITCH
     * @see #LITERAL_YIELD
     **/
    public static final int CASE_GROUP = JavaLexer.CASE_GROUP;
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
    public static final int ELIST = JavaLexer.ELIST;
    /**
     * A for loop initializer.  This is a child of
     * {@code LITERAL_FOR}.  The children of this element may be
     * a comma separated list of variable declarations, an expression
     * list, or empty.
     *
     * <p>For example:</p>
     * <pre>
     * for (int i = 0; i &lt; arr.length; i++) {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |   `--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--LITERAL_INT -&gt; int
     *  |       |--IDENT -&gt; i
     *  |       `--ASSIGN -&gt; =
     *  |           `--EXPR -&gt; EXPR
     *  |               `--NUM_INT -&gt; 0
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LT -&gt; &lt;
     *  |           |--IDENT -&gt; i
     *  |           `--DOT -&gt; .
     *  |               |--IDENT -&gt; arr
     *  |               `--IDENT -&gt; length
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |   `--ELIST -&gt; ELIST
     *  |       `--EXPR -&gt; EXPR
     *  |           `--POST_INC -&gt; ++
     *  |               `--IDENT -&gt; i
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_INIT = JavaLexer.FOR_INIT;
    /**
     * A for loop condition.  This is a child of
     * {@code LITERAL_FOR}.  The child of this element is an
     * optional expression.
     *
     * <p>For example:</p>
     * <pre>
     * for (int i = 0; i &lt; arr.length; i++) {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |   `--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--LITERAL_INT -&gt; int
     *  |       |--IDENT -&gt; i
     *  |       `--ASSIGN -&gt; =
     *  |           `--EXPR -&gt; EXPR
     *  |               `--NUM_INT -&gt; 0
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LT -&gt; &lt;
     *  |           |--IDENT -&gt; i
     *  |           `--DOT -&gt; .
     *  |               |--IDENT -&gt; arr
     *  |               `--IDENT -&gt; length
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |   `--ELIST -&gt; ELIST
     *  |       `--EXPR -&gt; EXPR
     *  |           `--POST_INC -&gt; ++
     *  |               `--IDENT -&gt; i
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #EXPR
     * @see #LITERAL_FOR
     **/
    public static final int FOR_CONDITION =
        JavaLexer.FOR_CONDITION;

    /**
     * A for loop iterator.  This is a child of
     * {@code LITERAL_FOR}.  The child of this element is an
     * optional expression list.
     *
     * <p>For example:</p>
     * <pre>
     * for (int i = 0; i &lt; arr.length; i++) {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |   `--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--LITERAL_INT -&gt; int
     *  |       |--IDENT -&gt; i
     *  |       `--ASSIGN -&gt; =
     *  |           `--EXPR -&gt; EXPR
     *  |               `--NUM_INT -&gt; 0
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LT -&gt; &lt;
     *  |           |--IDENT -&gt; i
     *  |           `--DOT -&gt; .
     *  |               |--IDENT -&gt; arr
     *  |               `--IDENT -&gt; length
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |   `--ELIST -&gt; ELIST
     *  |       `--EXPR -&gt; EXPR
     *  |           `--POST_INC -&gt; ++
     *  |               `--IDENT -&gt; i
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_ITERATOR =
        JavaLexer.FOR_ITERATOR;

    /**
     * The empty statement.  This goes in place of an
     * {@code SLIST} for a {@code for} or {@code while}
     * loop body.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.6">Java
     * Language Specification, &sect;14.6</a>
     * @see #LITERAL_FOR
     * @see #LITERAL_WHILE
     **/
    public static final int EMPTY_STAT = JavaLexer.EMPTY_STAT;
    /**
     * The {@code final} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public final int x = 0;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   |--LITERAL_PUBLIC -&gt; public
     *  |   `--FINAL -&gt; final
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--NUM_INT -&gt; 0
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int FINAL = JavaLexer.FINAL;
    /**
     * The {@code abstract} keyword.
     *
     * <p>For example:</p>
     * <pre>
     *  public abstract class MyClass
     *  {
     *  }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * --CLASS_DEF
     *    |--MODIFIERS
     *    |   |--LITERAL_PUBLIC (public)
     *    |   `--ABSTRACT (abstract)
     *    |--LITERAL_CLASS (class)
     *    |--IDENT (MyClass)
     *    `--OBJBLOCK
     *        |--LCURLY ({)
     *        `--RCURLY (})
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int ABSTRACT = JavaLexer.ABSTRACT;
    /**
     * The {@code strictfp} keyword.
     *
     * <p>For example:</p>
     * <pre>public strictfp class Test {}</pre>
     *
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_PUBLIC -&gt; public
     * |   `--STRICTFP -&gt; strictfp
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Test
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int STRICTFP = JavaLexer.STRICTFP;
    /**
     * A super constructor call.
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #CTOR_CALL
     **/
    public static final int SUPER_CTOR_CALL =
        JavaLexer.SUPER_CTOR_CALL;

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
    public static final int CTOR_CALL = JavaLexer.CTOR_CALL;

    /**
     * The statement terminator ({@code ;}).  Depending on the
     * context, this make occur as a sibling, a child, or not at all.
     *
     * <p>For example:</p>
     * <pre>
     * for(;;);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |--RPAREN -&gt; )
     *  `--EMPTY_STAT -&gt; ;
     * </pre>
     *
     * @see #PACKAGE_DEF
     * @see #IMPORT
     * @see #SLIST
     * @see #ARRAY_INIT
     * @see #LITERAL_FOR
     **/
    public static final int SEMI = JavaLexer.SEMI;

    /**
     * The {@code ]} symbol.
     *
     * <p>For example:</p>
     * <pre>
     * int a[];
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   |--LITERAL_INT -&gt; int
     *  |   `--ARRAY_DECLARATOR -&gt; [
     *  |       `--RBRACK -&gt; ]
     *  |--IDENT -&gt; a
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #INDEX_OP
     * @see #ARRAY_DECLARATOR
     **/
    public static final int RBRACK = JavaLexer.RBRACK;
    /**
     * The {@code void} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * {@code void literal_void(){}}
     * </pre>
     * <p>'void' parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_VOID -&gt; void
     *  |--IDENT -&gt; literal_void
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_VOID =
        JavaLexer.LITERAL_void;

    /**
     * The {@code boolean} keyword.
     *
     * @see #TYPE
     **/
    public static final int LITERAL_BOOLEAN =
        JavaLexer.LITERAL_boolean;

    /**
     * The {@code byte} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public byte x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_BYTE -&gt; byte
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_BYTE =
        JavaLexer.LITERAL_byte;

    /**
     * The {@code char} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * char a = 'A';
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_CHAR -&gt; char
     *  |--IDENT -&gt; a
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--CHAR_LITERAL -&gt; 'A'
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_CHAR =
        JavaLexer.LITERAL_char;

    /**
     * The {@code short} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public short x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_SHORT -&gt; short
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_SHORT =
        JavaLexer.LITERAL_short;

    /**
     * The {@code int} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_INT = JavaLexer.LITERAL_int;
    /**
     * The {@code float} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public float x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_FLOAT -&gt; float
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_FLOAT =
        JavaLexer.LITERAL_float;

    /**
     * The {@code long} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public long x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_LONG -&gt; long
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_LONG =
        JavaLexer.LITERAL_long;

    /**
     * The {@code double} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public double x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_DOUBLE -&gt; double
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #TYPE
     **/
    public static final int LITERAL_DOUBLE =
        JavaLexer.LITERAL_double;

    /**
     * An identifier.  These can be names of types, subpackages,
     * fields, methods, parameters, and local variables.
     **/
    public static final int IDENT = JavaLexer.IDENT;
    /**
     * The <code>&#46;</code> (dot) operator.
     *
     * <p>For example:</p>
     * <pre>
     * return person.name;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * --LITERAL_RETURN -&gt; return
     *    |--EXPR -&gt; EXPR
     *    |   `--DOT -&gt; .
     *    |       |--IDENT -&gt; person
     *    |       `--IDENT -&gt; name
     *    `--SEMI -&gt; ;
     * </pre>
     *
     * @see FullIdent
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int DOT = JavaLexer.DOT;
    /**
     * The {@code *} (multiplication or wildcard) operator.
     *
     * <p>For example:</p>
     * <pre>
     * f = m * a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; f
     * |       `--STAR -&gt; *
     * |           |--IDENT -&gt; m
     * |           `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5.2">Java
     * Language Specification, &sect;7.5.2</a>
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.1">Java
     * Language Specification, &sect;15.17.1</a>
     * @see #EXPR
     * @see #IMPORT
     **/
    public static final int STAR = JavaLexer.STAR;
    /**
     * The {@code private} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * private int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PRIVATE -&gt; private
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PRIVATE =
        JavaLexer.LITERAL_private;

    /**
     * The {@code public} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PUBLIC =
        JavaLexer.LITERAL_public;

    /**
     * The {@code protected} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * protected int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PROTECTED -&gt; protected
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_PROTECTED =
        JavaLexer.LITERAL_protected;

    /**
     * The {@code static} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * public static int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   |--LITERAL_PUBLIC -&gt; public
     *  |   `--LITERAL_STATIC -&gt; static
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; x
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_STATIC =
        JavaLexer.LITERAL_static;

    /**
     * The {@code transient} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * transient int a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_TRANSIENT -&gt; transient
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; a
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_TRANSIENT =
        JavaLexer.LITERAL_transient;

    /**
     * The {@code native} keyword.
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_NATIVE =
        JavaLexer.LITERAL_native;

    /**
     * The {@code synchronized} keyword.  This may be used as a
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
     * |--LITERAL_SYNCHRONIZED -&gt; synchronized
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--LITERAL_THIS -&gt; this
     * |   |--RPAREN -&gt; )
     * |   `--SLIST -&gt; {
     * |       |--EXPR -&gt; EXPR
     * |       |   `--POST_INC -&gt; ++
     * |       |       `--IDENT -&gt; x
     * |       |--SEMI -&gt; ;
     * |       `--RCURLY -&gt; }
     * `--RCURLY -&gt; }
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
        JavaLexer.LITERAL_synchronized;

    /**
     * The {@code volatile} keyword. This may be used as a
     * modifier of a field.
     * <p>For example:</p>
     * <pre>
     * private volatile int x;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_PRIVATE -&gt; private
     * |   `--LITERAL_VOLATILE -&gt; volatile
     * |--TYPE -&gt; TYPE
     * |   `--LITERAL_INT -&gt; int
     * |--IDENT -&gt; x
     * `--SEMI -&gt; ;
     * </pre>
     *
     * @see #MODIFIERS
     **/
    public static final int LITERAL_VOLATILE =
        JavaLexer.LITERAL_volatile;

    /**
     * The {@code class} keyword.  This element appears both
     * as part of a class declaration, and inline to reference a
     * class object.
     *
     * <p>For example:</p>
     * <pre>
     * class Test {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Test
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * <p>For example:</p>
     * <pre> int.class
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * EXPR -&gt; EXPR
     *  `--DOT -&gt; .
     *      |--LITERAL_INT -&gt; int
     *      `--LITERAL_CLASS -&gt; class
     * </pre>
     *
     * @see #DOT
     * @see #IDENT
     * @see #CLASS_DEF
     * @see FullIdent
     **/
    public static final int LITERAL_CLASS =
        JavaLexer.LITERAL_class;

    /**
     * The {@code interface} keyword. This token appears in
     * interface definition.
     *
     * <p>For example:</p>
     *
     * <pre>
     * public interface MyInterface {
     *
     * }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * INTERFACE_DEF -&gt; INTERFACE_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_INTERFACE -&gt; interface
     * |--IDENT -&gt; MyInterface
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see #INTERFACE_DEF
     **/
    public static final int LITERAL_INTERFACE =
        JavaLexer.LITERAL_interface;

    /**
     * A left curly brace (<code>{</code>).
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int LCURLY = JavaLexer.LCURLY;
    /**
     * A right curly brace (<code>}</code>).
     *
     * <p>For example:</p>
     * <pre>
     * {@code
     * void foo(){}
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_VOID -&gt; void
     *  |--IDENT -&gt; foo
     *  |--LPAREN -&gt; (
     *  |--PARAMETERS -&gt; PARAMETERS
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     **/
    public static final int RCURLY = JavaLexer.RCURLY;

    /**
     * The {@code ,} (comma) operator.
     *
     * <p>For example:</p>
     * <pre>
     * int a, b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_INT -&gt; int
     * |   `--IDENT -&gt; a
     * |--COMMA -&gt; ,
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_INT -&gt; int
     * |   `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #ARRAY_INIT
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #LITERAL_THROWS
     * @see #IMPLEMENTS_CLAUSE
     **/
    public static final int COMMA = JavaLexer.COMMA;

    /**
     * A left parenthesis ({@code (}).
     *
     * <p>For example:</p>
     * <pre>
     * Integer val = new Integer();
     * while (false) {
     *     val += (-3);
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     *  |--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |--TYPE -&gt; TYPE
     *  |   |   `--IDENT -&gt; Integer
     *  |   |--IDENT -&gt; val
     *  |   `--ASSIGN -&gt; =
     *  |       `--EXPR -&gt; EXPR
     *  |           `--LITERAL_NEW -&gt; new
     *  |               |--IDENT -&gt; Integer
     *  |               |--LPAREN -&gt; (
     *  |               |--ELIST -&gt; ELIST
     *  |               `--RPAREN -&gt; )
     *  |--SEMI -&gt; ;
     *  |--LITERAL_WHILE -&gt; while
     *  |   |--LPAREN -&gt; (
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--LITERAL_FALSE -&gt; false
     *  |   |--RPAREN -&gt; )
     *  |   `--SLIST -&gt; {
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--PLUS_ASSIGN -&gt; +=
     *  |       |       |--IDENT -&gt; val
     *  |       |       |--LPAREN -&gt; (
     *  |       |       |--UNARY_MINUS -&gt; -
     *  |       |       |   `--NUM_INT -&gt; 3
     *  |       |       `--RPAREN -&gt; )
     *  |       |--SEMI -&gt; ;
     *  |       `--RCURLY -&gt; }
     * </pre>
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     **/
    public static final int LPAREN = JavaLexer.LPAREN;
    /**
     * A right parenthesis ({@code )}).
     *
     * <p>For example:</p>
     * <pre>
     * void check() {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_VOID -&gt; void
     *  |--IDENT -&gt; check
     *  |--LPAREN -&gt; (
     *  |--PARAMETERS -&gt; PARAMETERS
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #METHOD_CALL
     * @see #TYPECAST
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     **/
    public static final int RPAREN = JavaLexer.RPAREN;
    /**
     * The {@code this} keyword use to refer the current object.
     * This can also be used to call the constructor.
     *
     * <p>For example:</p>
     * <pre>
     * this.name = name;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * EXPR -&gt; EXPR
     *  `--ASSIGN -&gt; =
     *      |--DOT -&gt; .
     *      |   |--LITERAL_THIS -&gt; this
     *      |   `--IDENT -&gt; name
     *      `--IDENT -&gt; name
     * SEMI -&gt; ;
     * </pre>
     * <p>Also consider:</p>
     * <pre>
     * this(1, "NULL");
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CTOR_CALL -&gt; this
     *  |--LPAREN -&gt; (
     *  |--ELIST -&gt; ELIST
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 1
     *  |   |--COMMA -&gt; ,
     *  |   `--EXPR -&gt; EXPR
     *  |       `--STRING_LITERAL -&gt; "NULL"
     *  |--RPAREN -&gt; )
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     * @see #CTOR_CALL
     **/
    public static final int LITERAL_THIS =
        JavaLexer.LITERAL_this;

    /**
     * The {@code super} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * super.toString()；
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--METHOD_CALL -&gt; (
     * |       |--DOT -&gt; .
     * |       |  |--LITERAL_SUPER -&gt; super
     * |       |  `--IDENT -&gt; toString
     * |       |--ELIST -&gt; ELIST
     * |       `--RPAREN -&gt; )
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     * @see #SUPER_CTOR_CALL
     **/
    public static final int LITERAL_SUPER =
        JavaLexer.LITERAL_super;

    /**
     * The {@code =} (assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.1">Java
     * Language Specification, &sect;15.26.1</a>
     * @see #EXPR
     **/
    public static final int ASSIGN = JavaLexer.ASSIGN;
    /**
     * The {@code throws} keyword.  The children are a number of
     * one or more identifiers separated by commas.
     *
     * <p>For example:</p>
     * <pre>
     * void test() throws FileNotFoundException, EOFException {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_VOID -&gt; void
     *  |--IDENT -&gt; test
     *  |--LPAREN -&gt; (
     *  |--PARAMETERS -&gt; PARAMETERS
     *  |--RPAREN -&gt; )
     *  |--LITERAL_THROWS -&gt; throws
     *  |   |--IDENT -&gt; FileNotFoundException
     *  |   |--COMMA -&gt; ,
     *  |   `--IDENT -&gt; EOFException
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.4">Java
     * Language Specification, &sect;8.4.4</a>
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     * @see FullIdent
     **/
    public static final int LITERAL_THROWS =
        JavaLexer.LITERAL_throws;

    /**
     * The {@code :} (colon) operator.  This will appear as part
     * of the conditional operator ({@code ? :}).
     *
     * @see #QUESTION
     * @see #LABELED_STAT
     * @see #CASE_GROUP
     **/
    public static final int COLON = JavaLexer.COLON;

    /**
     * The {@code ::} (double colon) separator.
     * It is part of Java 8 syntax that is used for method reference.
     * The token does not appear in tree, {@link #METHOD_REF} should be used instead.
     *
     * @see #METHOD_REF
     */
    public static final int DOUBLE_COLON = JavaLexer.DOUBLE_COLON;
    /**
     * The {@code if} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * if (optimistic)
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
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--IDENT -&gt; optimistic
     *  |--RPAREN -&gt; )
     *  |--SLIST -&gt; {
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--ASSIGN -&gt; =
     *  |   |       |--IDENT -&gt; message
     *  |   |       `--STRING_LITERAL -&gt; "half full"
     *  |   |--SEMI -&gt; ;
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_ELSE -&gt; else
     *      `--SLIST -&gt; {
     *          |--EXPR -&gt; EXPR
     *          |   `--ASSIGN -&gt; =
     *          |       |--IDENT -&gt; message
     *          |       `--STRING_LITERAL -&gt; "half empty"
     *          |--SEMI -&gt; ;
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #SLIST
     * @see #EMPTY_STAT
     * @see #LITERAL_ELSE
     **/
    public static final int LITERAL_IF = JavaLexer.LITERAL_if;
    /**
     * The {@code for} keyword.  The children are {@code (},
     * an initializer, a condition, an iterator, a {@code )} and
     * either a statement list, a single expression, or an empty
     * statement.
     *
     * <p>For example:</p>
     * <pre>
     * for (int i = 0; i &lt; arr.length; i++) {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |   `--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--LITERAL_INT -&gt; int
     *  |       |--IDENT -&gt; i
     *  |       `--ASSIGN -&gt; =
     *  |           `--EXPR -&gt; EXPR
     *  |               `--NUM_INT -&gt; 0
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LT -&gt; &lt;
     *  |           |--IDENT -&gt; i
     *  |           `--DOT -&gt; .
     *  |               |--IDENT -&gt; arr
     *  |               `--IDENT -&gt; length
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |   `--ELIST -&gt; ELIST
     *  |       `--EXPR -&gt; EXPR
     *  |           `--POST_INC -&gt; ++
     *  |               `--IDENT -&gt; i
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
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
    public static final int LITERAL_FOR = JavaLexer.LITERAL_for;
    /**
     * The {@code while} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * while (i &lt; 5) {
     *     i++;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_WHILE -&gt; while
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--LT -&gt; &lt;
     *  |       |--IDENT -&gt; i
     *  |       `--NUM_INT -&gt; 5
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--EXPR -&gt; EXPR
     *      |   `--POST_INC -&gt; ++
     *      |       `--IDENT -&gt; i
     *      |--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     **/
    public static final int LITERAL_WHILE =
        JavaLexer.LITERAL_while;

    /**
     * The {@code do} keyword.  Note the the while token does not
     * appear as part of the do-while construct.
     *
     * <p>For example:</p>
     * <pre>
     * do {
     *   x = rand.nextInt();
     * } while (x &lt; 5);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_DO -&gt; do
     *  |--SLIST -&gt; {
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--ASSIGN -&gt; =
     *  |   |       |--IDENT -&gt; x
     *  |   |       `--METHOD_CALL -&gt; (
     *  |   |           |--DOT -&gt; .
     *  |   |           |   |--IDENT -&gt; rand
     *  |   |           |   `--IDENT -&gt; nextInt
     *  |   |           |--ELIST -&gt; ELIST
     *  |   |           `--RPAREN -&gt; )
     *  |   |--SEMI -&gt; ;
     *  |   `--RCURLY -&gt; }
     *  |--DO_WHILE -&gt; while
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--LT -&gt; &lt;
     *  |       |--IDENT -&gt; x
     *  |       `--NUM_INT -&gt; 5
     *  |--RPAREN -&gt; )
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LPAREN
     * @see #RPAREN
     * @see #SEMI
     **/
    public static final int LITERAL_DO = JavaLexer.LITERAL_do;
    /**
     * Literal {@code while} in do-while loop.
     *
     * <p>For example:</p>
     * <pre>
     * do {
     *
     * } while (a &gt; 0);
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * --LITERAL_DO -&gt; do
     *    |--SLIST -&gt; {
     *    |   `--RCURLY -&gt; }
     *    |--DO_WHILE -&gt; while
     *    |--LPAREN -&gt; (
     *    |--EXPR -&gt; EXPR
     *    |   `--GT -&gt; &gt;
     *    |       |--IDENT -&gt; a
     *    |       `--NUM_INT -&gt; 0
     *    |--RPAREN -&gt; )
     *    `--SEMI -&gt; ;
     * </pre>
     *
     * @see #LITERAL_DO
     */
    public static final int DO_WHILE = JavaLexer.DO_WHILE;
    /**
     * The {@code break} keyword.  The first child is an optional
     * identifier and the last child is a semicolon.
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_BREAK =
        JavaLexer.LITERAL_break;

    /**
     * The {@code continue} keyword.  The first child is an
     * optional identifier and the last child is a semicolon.
     *
     * <p>For example:</p>
     * <pre>
     * for (;;) {
     *     continue;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_INIT -&gt; FOR_INIT
     *  |--SEMI -&gt; ;
     *  |--FOR_CONDITION -&gt; FOR_CONDITION
     *  |--SEMI -&gt; ;
     *  |--FOR_ITERATOR -&gt; FOR_ITERATOR
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--LITERAL_CONTINUE -&gt; continue
     *      |   `--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_CONTINUE =
        JavaLexer.LITERAL_continue;

    /**
     * The {@code return} keyword.  The first child is an
     * optional expression for the return value.  The last child is a
     * semi colon.
     *
     * <p>For example:</p>
     * <pre>
     * public int foo(int i) {
     *     return i+1;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * METHOD_DEF -&gt; METHOD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_INT -&gt; int
     *  |--IDENT -&gt; foo
     *  |--LPAREN -&gt; (
     *  |--PARAMETERS -&gt; PARAMETERS
     *  |   `--PARAMETER_DEF -&gt; PARAMETER_DEF
     *  |       |--MODIFIERS -&gt; MODIFIERS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--LITERAL_INT -&gt; int
     *  |       `--IDENT -&gt; i
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--LITERAL_RETURN -&gt; return
     *      |   |--EXPR -&gt; EXPR
     *      |   |   `--PLUS -&gt; +
     *      |   |       |--IDENT -&gt; i
     *      |   |       `--NUM_INT -&gt; 1
     *      |   `--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #EXPR
     * @see #SEMI
     * @see #SLIST
     **/
    public static final int LITERAL_RETURN =
        JavaLexer.LITERAL_return;

    /**
     * The {@code switch} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * switch (type) {
     *      case 0:
     *          background = Color.red;
     *          break;
     *      case 1:
     *          background = Color.blue;
     *          break;
     *      default:
     *          background = Color.green;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_SWITCH -&gt; switch
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--IDENT -&gt; type
     *  |--RPAREN -&gt; )
     *  |--LCURLY -&gt; {
     *  |--CASE_GROUP -&gt; CASE_GROUP
     *  |   |--LITERAL_CASE -&gt; case
     *  |   |   |--EXPR -&gt; EXPR
     *  |   |   |   `--NUM_INT -&gt; 0
     *  |   |   `--COLON -&gt; :
     *  |   `--SLIST -&gt; SLIST
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--ASSIGN -&gt; =
     *  |       |       |--IDENT -&gt; background
     *  |       |       `--DOT -&gt; .
     *  |       |           |--IDENT -&gt; Color
     *  |       |           `--IDENT -&gt; red
     *  |       |--SEMI -&gt; ;
     *  |       `--LITERAL_BREAK -&gt; break
     *  |           `--SEMI -&gt; ;
     *  |--CASE_GROUP -&gt; CASE_GROUP
     *  |   |--LITERAL_CASE -&gt; case
     *  |   |   |--EXPR -&gt; EXPR
     *  |   |   |   `--NUM_INT -&gt; 1
     *  |   |   `--COLON -&gt; :
     *  |   `--SLIST -&gt; SLIST
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--ASSIGN -&gt; =
     *  |       |       |--IDENT -&gt; background
     *  |       |       `--DOT -&gt; .
     *  |       |           |--IDENT -&gt; Color
     *  |       |           `--IDENT -&gt; blue
     *  |       |--SEMI -&gt; ;
     *  |       `--LITERAL_BREAK -&gt; break
     *  |           `--SEMI -&gt; ;
     *  |--CASE_GROUP -&gt; CASE_GROUP
     *  |   |--LITERAL_DEFAULT -&gt; default
     *  |   |   `--COLON -&gt; :
     *  |   `--SLIST -&gt; SLIST
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--ASSIGN -&gt; =
     *  |       |       |--IDENT -&gt; background
     *  |       |       `--DOT -&gt; .
     *  |       |           |--IDENT -&gt; Color
     *  |       |           `--IDENT -&gt; green
     *  |       `--SEMI -&gt; ;
     *  `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.10">Java
     * Language Specification, &sect;14.10</a>
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #LCURLY
     * @see #CASE_GROUP
     * @see #RCURLY
     * @see #SLIST
     * @see #SWITCH_RULE
     **/
    public static final int LITERAL_SWITCH =
        JavaLexer.LITERAL_switch;

    /**
     * The {@code throw} keyword.  The first child is an
     * expression that evaluates to a {@code Throwable} instance.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.17">Java
     * Language Specification, &sect;14.17</a>
     * @see #SLIST
     * @see #EXPR
     **/
    public static final int LITERAL_THROW =
        JavaLexer.LITERAL_throw;

    /**
     * The {@code else} keyword.  This appears as a child of an
     * {@code if} statement.
     *
     * <p>For example:</p>
     * <pre>
     * if (flag) {
     *
     * } else {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--IDENT -&gt; flag
     *  |--RPAREN -&gt; )
     *  |--SLIST -&gt; {
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_ELSE -&gt; else
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LITERAL_IF
     **/
    public static final int LITERAL_ELSE =
        JavaLexer.LITERAL_else;

    /**
     * The {@code case} keyword.  The first child is a constant
     * expression that evaluates to an integer.
     *
     * @see #CASE_GROUP
     * @see #EXPR
     **/
    public static final int LITERAL_CASE =
        JavaLexer.LITERAL_case;

    /**
     * The {@code default} keyword.  This element has no
     * children.
     *
     * <p>For example:</p>
     * <pre>
     * switch (type) {
     *   case 1:
     *     x = 1;
     *     break;
     *   default:
     *     x = 3;
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_SWITCH -&gt; switch
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--IDENT -&gt; type
     *  |--RPAREN -&gt; )
     *  |--LCURLY -&gt; {
     *  |--CASE_GROUP -&gt; CASE_GROUP
     *  |   |--LITERAL_CASE -&gt; case
     *  |   |   |--EXPR -&gt; EXPR
     *  |   |   |   `--NUM_INT -&gt; 1
     *  |   |   `--COLON -&gt; :
     *  |   `--SLIST -&gt; SLIST
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--ASSIGN -&gt; =
     *  |       |       |--IDENT -&gt; x
     *  |       |       `--NUM_INT -&gt; 1
     *  |       |   |       |--SEMI -&gt; ;
     *  |       `--LITERAL_BREAK -&gt; break
     *  |           `--SEMI -&gt; ;
     *  |--CASE_GROUP -&gt; CASE_GROUP
     *  |   |--LITERAL_DEFAULT -&gt; default
     *  |   |   `--COLON -&gt; :
     *  |   `--SLIST -&gt; SLIST
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--ASSIGN -&gt; =
     *  |       |       |--IDENT -&gt; x
     *  |       |       `--NUM_INT -&gt; 3
     *  |       `--SEMI -&gt; ;
     *  `--RCURLY -&gt; }
     * </pre>
     *
     * @see #CASE_GROUP
     * @see #MODIFIERS
     * @see #SWITCH_RULE
     **/
    public static final int LITERAL_DEFAULT =
        JavaLexer.LITERAL_default;

    /**
     * The {@code try} keyword.  The children are a statement
     * list, zero or more catch blocks and then an optional finally
     * block.
     *
     * <p>For example:</p>
     * <pre>
     * try { } finally {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--SLIST -&gt; {
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_FINALLY -&gt; finally
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.19">Java
     * Language Specification, &sect;14.19</a>
     * @see #SLIST
     * @see #LITERAL_CATCH
     * @see #LITERAL_FINALLY
     **/
    public static final int LITERAL_TRY = JavaLexer.LITERAL_try;

    /**
     * The Java 7 try-with-resources construct.
     *
     * <p>For example:</p>
     * <pre>
     * try (Foo foo = new Foo(); Bar bar = new Bar()) {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--RESOURCE_SPECIFICATION -&gt; RESOURCE_SPECIFICATION
     *  |   |--LPAREN -&gt; (
     *  |   |--RESOURCES -&gt; RESOURCES
     *  |   |   |--RESOURCE -&gt; RESOURCE
     *  |   |   |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |   |   |--TYPE -&gt; TYPE
     *  |   |   |   |   `--IDENT -&gt; Foo
     *  |   |   |   |--IDENT -&gt; foo
     *  |   |   |   `--ASSIGN -&gt; =
     *  |   |   |       `--EXPR -&gt; EXPR
     *  |   |   |           `--LITERAL_NEW -&gt; new
     *  |   |   |               |--IDENT -&gt; Foo
     *  |   |   |               |--LPAREN -&gt; (
     *  |   |   |               |--ELIST -&gt; ELIST
     *  |   |   |               `--RPAREN -&gt; )
     *  |   |   |--SEMI -&gt; ;
     *  |   |   `--RESOURCE -&gt; RESOURCE
     *  |   |       |--MODIFIERS -&gt; MODIFIERS
     *  |   |       |--TYPE -&gt; TYPE
     *  |   |       |   `--IDENT -&gt; Bar
     *  |   |       |--IDENT -&gt; bar
     *  |   |       `--ASSIGN -&gt; =
     *  |   |           `--EXPR -&gt; EXPR
     *  |   |               `--LITERAL_NEW -&gt; new
     *  |   |                   |--IDENT -&gt; Bar
     *  |   |                   |--LPAREN -&gt; (
     *  |   |                   |--ELIST -&gt; ELIST
     *  |   |                   `--RPAREN -&gt; )
     *  |   `--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * <p>Also consider:</p>
     * <pre>
     * try (BufferedReader br = new BufferedReader(new FileReader(path))) {
     * }
     * </pre>
     * <p>which parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--RESOURCE_SPECIFICATION -&gt; RESOURCE_SPECIFICATION
     *  |   |--LPAREN -&gt; (
     *  |   |--RESOURCES -&gt; RESOURCES
     *  |   |   `--RESOURCE -&gt; RESOURCE
     *  |   |       |--MODIFIERS -&gt; MODIFIERS
     *  |   |       |--TYPE -&gt; TYPE
     *  |   |       |   `--IDENT -&gt; BufferedReader
     *  |   |       |--IDENT -&gt; br
     *  |   |       `--ASSIGN -&gt; =
     *  |   |           `--EXPR -&gt; EXPR
     *  |   |               `--LITERAL_NEW -&gt; new
     *  |   |                   |--IDENT -&gt; BufferedReader
     *  |   |                   |--LPAREN -&gt; (
     *  |   |                   |--ELIST -&gt; ELIST
     *  |   |                   |   `--EXPR -&gt; EXPR
     *  |   |                   |       `--LITERAL_NEW -&gt; new
     *  |   |                   |           |--IDENT -&gt; FileReader
     *  |   |                   |           |--LPAREN -&gt; (
     *  |   |                   |           |--ELIST -&gt; ELIST
     *  |   |                   |           |   `--EXPR -&gt; EXPR
     *  |   |                   |           |       `--IDENT -&gt; path
     *  |   |                   |           `--RPAREN -&gt; )
     *  |   |                   `--RPAREN -&gt; )
     *  |   `--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
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
        JavaLexer.RESOURCE_SPECIFICATION;

    /**
     * A list of resources in the Java 7 try-with-resources construct.
     * This is a child of RESOURCE_SPECIFICATION.
     *
     * <p>For example:</p>
     * <pre>
     *     try (FileReader fr = new FileReader("config.xml")) {
     *     } finally {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--RESOURCE_SPECIFICATION -&gt; RESOURCE_SPECIFICATION
     *  |   |--LPAREN -&gt; (
     *  |   |--RESOURCES -&gt; RESOURCES
     *  |   |   `--RESOURCE -&gt; RESOURCE
     *  |   |       |--MODIFIERS -&gt; MODIFIERS
     *  |   |       |--TYPE -&gt; TYPE
     *  |   |       |   `--IDENT -&gt; FileReader
     *  |   |       |--IDENT -&gt; fr
     *  |   |       `--ASSIGN -&gt; =
     *  |   |           `--EXPR -&gt; EXPR
     *  |   |               `--LITERAL_NEW -&gt; new
     *  |   |                   |--IDENT -&gt; FileReader
     *  |   |                   |--LPAREN -&gt; (
     *  |   |                   |--ELIST -&gt; ELIST
     *  |   |                   |   `--EXPR -&gt; EXPR
     *  |   |                   |       `--STRING_LITERAL -&gt; "config.xml"
     *  |   |                   `--RPAREN -&gt; )
     *  |   `--RPAREN -&gt; )
     *  |--SLIST -&gt; {
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_FINALLY -&gt; finally
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #RESOURCE_SPECIFICATION
     **/
    public static final int RESOURCES =
        JavaLexer.RESOURCES;

    /**
     * A resource in the Java 7 try-with-resources construct.
     * This is a child of RESOURCES.
     *
     * <p>For example:</p>
     * <pre>
     * try (Foo foo = new Foo(); Bar bar = new Bar()) { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--RESOURCE_SPECIFICATION -&gt; RESOURCE_SPECIFICATION
     *  |   |--LPAREN -&gt; (
     *  |   |--RESOURCES -&gt; RESOURCES
     *  |   |   |--RESOURCE -&gt; RESOURCE
     *  |   |   |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |   |   |--TYPE -&gt; TYPE
     *  |   |   |   |   `--IDENT -&gt; Foo
     *  |   |   |   |--IDENT -&gt; foo
     *  |   |   |   `--ASSIGN -&gt; =
     *  |   |   |       `--EXPR -&gt; EXPR
     *  |   |   |           `--LITERAL_NEW -&gt; new
     *  |   |   |               |--IDENT -&gt; Foo
     *  |   |   |               |--LPAREN -&gt; (
     *  |   |   |               |--ELIST -&gt; ELIST
     *  |   |   |               `--RPAREN -&gt; )
     *  |   |   |--SEMI -&gt; ;
     *  |   |   `--RESOURCE -&gt; RESOURCE
     *  |   |       |--MODIFIERS -&gt; MODIFIERS
     *  |   |       |--TYPE -&gt; TYPE
     *  |   |       |   `--IDENT -&gt; Bar
     *  |   |       |--IDENT -&gt; bar
     *  |   |       `--ASSIGN -&gt; =
     *  |   |           `--EXPR -&gt; EXPR
     *  |   |               `--LITERAL_NEW -&gt; new
     *  |   |                   |--IDENT -&gt; Bar
     *  |   |                   |--LPAREN -&gt; (
     *  |   |                   |--ELIST -&gt; ELIST
     *  |   |                   `--RPAREN -&gt; )
     *  |   `--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #RESOURCES
     * @see #RESOURCE_SPECIFICATION
     **/
    public static final int RESOURCE =
        JavaLexer.RESOURCE;

    /**
     * The {@code catch} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * try {
     *     FileReader fr = new FileReader("Test.txt");
     * } catch (FileNotFoundException e) {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--SLIST -&gt; {
     *  |   |--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |   |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |   |--TYPE -&gt; TYPE
     *  |   |   |   `--IDENT -&gt; FileReader
     *  |   |   |--IDENT -&gt; fr
     *  |   |   `--ASSIGN -&gt; =
     *  |   |       `--EXPR -&gt; EXPR
     *  |   |           `--LITERAL_NEW -&gt; new
     *  |   |               |--IDENT -&gt; FileReader
     *  |   |               |--LPAREN -&gt; (
     *  |   |               |--ELIST -&gt; ELIST
     *  |   |               |   `--EXPR -&gt; EXPR
     *  |   |               |       `--STRING_LITERAL -&gt; "Test.txt"
     *  |   |               `--RPAREN -&gt; )
     *  |   |--SEMI -&gt; ;
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_CATCH -&gt; catch
     *      |--LPAREN -&gt; (
     *      |--PARAMETER_DEF -&gt; PARAMETER_DEF
     *      |   |--MODIFIERS -&gt; MODIFIERS
     *      |   |--TYPE -&gt; TYPE
     *      |   |   `--IDENT -&gt; FileNotFoundException
     *      |   `--IDENT -&gt; e
     *      |--RPAREN -&gt; )
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #LPAREN
     * @see #PARAMETER_DEF
     * @see #RPAREN
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_CATCH =
        JavaLexer.LITERAL_catch;

    /**
     * The {@code finally} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * try {} finally {}
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_TRY -&gt; try
     *  |--SLIST -&gt; {
     *  |   `--RCURLY -&gt; }
     *  `--LITERAL_FINALLY -&gt; finally
     *      `--SLIST -&gt; {
     *          `--RCURLY -&gt; }
     * </pre>
     *
     * @see #SLIST
     * @see #LITERAL_TRY
     **/
    public static final int LITERAL_FINALLY =
        JavaLexer.LITERAL_finally;

    /**
     * The {@code +=} (addition assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a += b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--PLUS_ASSIGN -&gt; +=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int PLUS_ASSIGN = JavaLexer.PLUS_ASSIGN;
    /**
     * The {@code -=} (subtraction assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a -= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--MINUS_ASSIGN -&gt; -=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MINUS_ASSIGN =
        JavaLexer.MINUS_ASSIGN;

    /**
     * The {@code *=} (multiplication assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a *= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--STAR_ASSIGN -&gt; *=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int STAR_ASSIGN = JavaLexer.STAR_ASSIGN;
    /**
     * The {@code /=} (division assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a /= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--DIV_ASSIGN -&gt; /=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int DIV_ASSIGN = JavaLexer.DIV_ASSIGN;
    /**
     * The {@code %=} (remainder assignment) operator.
     * <p>For example:</p>
     * <pre>a %= 2;</pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--MOD_ASSIGN -&gt; %=
     * |       |--IDENT -&gt; a
     * |       `--NUM_INT -&gt; 2
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int MOD_ASSIGN = JavaLexer.MOD_ASSIGN;
    /**
     * The {@code >>=} (signed right shift assignment)
     * operator.
     *
     * <p>For example:</p>
     * <pre>
     * a &gt;&gt;= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--SR_ASSIGN -&gt; &gt;&gt;=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SR_ASSIGN = JavaLexer.SR_ASSIGN;
    /**
     * The {@code >>>=} (unsigned right shift assignment)
     * operator.
     *
     * <p>For example:</p>
     * <pre>
     * a &gt;&gt;&gt;= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--BSR_ASSIGN -&gt; &gt;&gt;&gt;=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BSR_ASSIGN = JavaLexer.BSR_ASSIGN;
    /**
     * The {@code <<=} (left shift assignment) operator.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int SL_ASSIGN = JavaLexer.SL_ASSIGN;
    /**
     * The {@code &=} (bitwise AND assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a &amp;= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--BAND_ASSIGN -&gt; &amp;=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BAND_ASSIGN = JavaLexer.BAND_ASSIGN;
    /**
     * The {@code ^=} (bitwise exclusive OR assignment) operator.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BXOR_ASSIGN = JavaLexer.BXOR_ASSIGN;
    /**
     * The {@code |=} (bitwise OR assignment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a |= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--BOR_ASSIGN -&gt; |=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     * Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     **/
    public static final int BOR_ASSIGN = JavaLexer.BOR_ASSIGN;
    /**
     * The <code>&#63;</code> (conditional) operator.  Technically,
     * the colon is also part of this operator, but it appears as a
     * separate token.
     *
     * <p>For example:</p>
     * <pre>
     * String variable=(quantity==1)?"true":"false";
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--IDENT -&gt; String
     * |   |--IDENT -&gt; variable
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--QUESTION -&gt; ?
     * |               |--LPAREN -&gt; (
     * |               |--EQUAL -&gt; ==
     * |               |   |--IDENT -&gt; quantity
     * |               |   `--NUM_INT -&gt; 1
     * |               |--RPAREN -&gt; )
     * |               |--STRING_LITERAL -&gt; "true"
     * |               |--COLON -&gt; :
     * |               `--STRING_LITERAL -&gt; "false"
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.25">Java
     * Language Specification, &sect;15.25</a>
     * @see #EXPR
     * @see #COLON
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int QUESTION = JavaLexer.QUESTION;
    /**
     * The {@code ||} (conditional OR) operator.
     *
     * <p>For example:</p>
     * <pre>
     * if (a || b) {
     * }
     * </pre>
     * <p>
     * parses as:
     * </p>
     * <pre>
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--LOR -&gt; ||
     *  |       |--IDENT -&gt; a
     *  |       `--IDENT -&gt; b
     *  |--RPAREN -&gt; )
     *  |--SLIST -&gt; {
     *  |   |--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">Java
     * Language Specification, &sect;15.24</a>
     * @see #EXPR
     **/
    public static final int LOR = JavaLexer.LOR;
    /**
     * The {@code &&} (conditional AND) operator.
     *
     *
     * <p>For example:</p>
     * <pre>
     * if (a &amp;&amp; b) {
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--LAND -&gt; &amp;&amp;
     *  |       |--IDENT -&gt; a
     *  |       `--IDENT -&gt; b
     *  |--RPAREN -&gt; )
     *  |--SLIST -&gt; {
     *  |   |--RCURLY -&gt; }
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">Java
     * Language Specification, &sect;15.23</a>
     * @see #EXPR
     **/
    public static final int LAND = JavaLexer.LAND;
    /**
     * The {@code |} (bitwise OR) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = a | b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--BOR -&gt; |
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BOR = JavaLexer.BOR;
    /**
     * The {@code ^} (bitwise exclusive OR) operator.
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BXOR = JavaLexer.BXOR;
    /**
     * The {@code &} (bitwise AND) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a &amp; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--BAND -&gt; &amp;
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     * Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     **/
    public static final int BAND = JavaLexer.BAND;
    /**
     * The <code>&#33;=</code> (not equal) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a != b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--NOT_EQUAL -&gt; !=
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * `--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int NOT_EQUAL = JavaLexer.NOT_EQUAL;
    /**
     * The {@code ==} (equal) operator.
     *
     * <p>For example:</p>
     * <pre>
     * return a == b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--EQUAL -&gt; ==
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * `--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int EQUAL = JavaLexer.EQUAL;
    /**
     * The {@code <} (less than) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a &lt; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--LT -&gt; &lt;
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int LT = JavaLexer.LT;
    /**
     * The {@code >} (greater than) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a &gt; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--BAND -&gt; &gt;
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int GT = JavaLexer.GT;
    /**
     * The {@code <=} (less than or equal) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a &lt;= b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--LE -&gt; &lt;=
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXPR
     **/
    public static final int LE = JavaLexer.LE;
    /**
     * The {@code >=} (greater than or equal) operator.
     *
     * @see #EXPR
     **/
    public static final int GE = JavaLexer.GE;
    /**
     * The {@code instanceof} operator.  The first child is an
     * object reference or something that evaluates to an object
     * reference.  The second child is a reference type.
     * <p>For example:</p>
     * <pre>
     * boolean isBuilder = text instanceof StringBuilder;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_BOOLEAN -&gt; boolean
     * |   |--IDENT -&gt; isBuilder
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--LITERAL_INSTANCEOF -&gt; instanceof
     * |               |--IDENT -&gt; text
     * |               `--TYPE -&gt; TYPE
     * |                   `--IDENT -&gt; StringBuilder
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.20.2">Java
     * Language Specification, &sect;15.20.2</a>
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #IDENT
     * @see #DOT
     * @see #TYPE
     * @see FullIdent
     **/
    public static final int LITERAL_INSTANCEOF =
        JavaLexer.LITERAL_instanceof;

    /**
     * The {@code <<} (shift left) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = a &lt;&lt; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--SR -&gt; &lt;&lt;
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SL = JavaLexer.SL;
    /**
     * The {@code >>} (signed shift right) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = a &gt;&gt; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--SR -&gt; &gt;&gt;
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int SR = JavaLexer.SR;
    /**
     * The {@code >>>} (unsigned shift right) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a &gt;&gt;&gt; b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--BSR -&gt; &gt;&gt;&gt;
     * |       |--IDENT -&gt; a
     * |       `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     * Language Specification, &sect;15.19</a>
     * @see #EXPR
     **/
    public static final int BSR = JavaLexer.BSR;
    /**
     * The {@code +} (addition) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a + b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--PLUS -&gt; +
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int PLUS = JavaLexer.PLUS;
    /**
     * The {@code -} (subtraction) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a - b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--MINUS -&gt; -
     * |           |--IDENT -&gt; a
     * |           `--IDENT -&gt; b
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     * Language Specification, &sect;15.18</a>
     * @see #EXPR
     **/
    public static final int MINUS = JavaLexer.MINUS;
    /**
     * The {@code /} (division) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = 4 / 2;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--DIV -&gt; /
     * |           |--NUM_INT -&gt; 4
     * |           `--NUM_INT -&gt; 2
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.2">Java
     * Language Specification, &sect;15.17.2</a>
     * @see #EXPR
     **/
    public static final int DIV = JavaLexer.DIV;
    /**
     * The {@code %} (remainder) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = a % b;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * EXPR -&gt; EXPR
     *  `--ASSIGN -&gt; =
     *      |--IDENT -&gt; c
     *      `--MOD -&gt; %
     *          |--IDENT -&gt; a
     *          `--IDENT -&gt; b
     * SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.3">Java
     * Language Specification, &sect;15.17.3</a>
     * @see #EXPR
     **/
    public static final int MOD = JavaLexer.MOD;
    /**
     * The {@code ++} (prefix increment) operator.
     *
     * <p>For example:</p>
     * <pre>
     * ++a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--INC -&gt; ++
     * |       `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.1">Java
     * Language Specification, &sect;15.15.1</a>
     * @see #EXPR
     * @see #POST_INC
     **/
    public static final int INC = JavaLexer.INC;
    /**
     * The {@code --} (prefix decrement) operator.
     *
     * <p>For example:</p>
     * <pre>
     * --a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--DEC -&gt; --
     * |       `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.2">Java
     * Language Specification, &sect;15.15.2</a>
     * @see #EXPR
     * @see #POST_DEC
     **/
    public static final int DEC = JavaLexer.DEC;
    /**
     * The {@code ~} (bitwise complement) operator.
     *
     * <p>For example:</p>
     * <pre>
     * a = ~ a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--BNOT -&gt; ~
     * |           `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.5">Java
     * Language Specification, &sect;15.15.5</a>
     * @see #EXPR
     **/
    public static final int BNOT = JavaLexer.BNOT;
    /**
     * The <code>&#33;</code> (logical complement) operator.
     *
     * <p>For example:</p>
     * <pre>
     * c = &#33; a;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; c
     * |       `--LNOT -&gt; &#33;
     * |           `--IDENT -&gt; a
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.6">Java
     * Language Specification, &sect;15.15.6</a>
     * @see #EXPR
     * @noinspection HtmlTagCanBeJavadocTag
     **/
    public static final int LNOT = JavaLexer.LNOT;
    /**
     * The {@code true} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * boolean a = true;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--LITERAL_BOOLEAN -&gt; boolean
     * |   |--IDENT -&gt; a
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--LITERAL_TRUE -&gt; true
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_FALSE
     **/
    public static final int LITERAL_TRUE =
        JavaLexer.LITERAL_true;

    /**
     * The {@code false} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * boolean a = false;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--LITERAL_BOOLEAN -&gt; boolean
     *  |--IDENT -&gt; a
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LITERAL_FALSE -&gt; false
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     * Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_TRUE
     **/
    public static final int LITERAL_FALSE =
        JavaLexer.LITERAL_false;

    /**
     * The {@code null} keyword.
     *
     * <p>For example:</p>
     * <pre>
     * String s = null;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--IDENT -&gt; String
     *  |--IDENT -&gt; s
     *  |--ASSIGN -&gt; =
     *  |   `--EXPR -&gt; EXPR
     *  |       `--LITERAL_NULL -&gt; null
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.7">Java
     * Language Specification, &sect;3.10.7</a>
     * @see #EXPR
     **/
    public static final int LITERAL_NULL =
        JavaLexer.LITERAL_null;

    /**
     * The {@code new} keyword.  This element is used to define
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
     * LITERAL_NEW -&gt; new
     *  |--IDENT -&gt; ArrayList
     *  |--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     *  |   |--GENERIC_START -&gt; &lt;
     *  |   `--GENERIC_END -&gt; &gt;
     *  |--LPAREN -&gt; (
     *  |--ELIST -&gt; ELIST
     *  |   `--EXPR -&gt; EXPR
     *  |       `--NUM_INT -&gt; 50
     *  `--RPAREN -&gt; )
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
    public static final int LITERAL_NEW = JavaLexer.LITERAL_new;
    /**
     * An integer literal.  These may be specified in decimal,
     * hexadecimal, or octal form.
     *
     * <p>For example:</p>
     * <pre>
     * a = 3;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--NUM_INT -&gt; 3
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_LONG
     **/
    public static final int NUM_INT = JavaLexer.NUM_INT;
    /**
     * A character literal.  This is a (possibly escaped) character
     * enclosed in single quotes.
     *
     * <p>For example:</p>
     * <pre>
     * return 'a';
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * --LITERAL_RETURN -&gt; return
     *    |--EXPR -&gt; EXPR
     *    |   `--CHAR_LITERAL -&gt; 'a'
     *    `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.4">Java
     * Language Specification, &sect;3.10.4</a>
     * @see #EXPR
     **/
    public static final int CHAR_LITERAL =
        JavaLexer.CHAR_LITERAL;

    /**
     * A string literal.  This is a sequence of (possibly escaped)
     * characters enclosed in double quotes.
     * <p>For example:</p>
     * <pre>String str = "StringLiteral";</pre>
     *
     * <p>parses as:</p>
     * <pre>
     *  |--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |--TYPE -&gt; TYPE
     *  |   |   `--IDENT -&gt; String
     *  |   |--IDENT -&gt; str
     *  |   `--ASSIGN -&gt; =
     *  |       `--EXPR -&gt; EXPR
     *  |           `--STRING_LITERAL -&gt; "StringLiteral"
     *  |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.5">Java
     * Language Specification, &sect;3.10.5</a>
     * @see #EXPR
     **/
    public static final int STRING_LITERAL =
        JavaLexer.STRING_LITERAL;

    /**
     * A single precision floating point literal.  This is a floating
     * point number with an {@code F} or {@code f} suffix.
     *
     * <p>For example:</p>
     * <pre>
     * a = 3.14f;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--NUM_FLOAT -&gt; 3.14f
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_DOUBLE
     **/
    public static final int NUM_FLOAT = JavaLexer.NUM_FLOAT;
    /**
     * A long integer literal.  These are almost the same as integer
     * literals, but they have an {@code L} or {@code l}
     * (ell) suffix.
     *
     * <p>For example:</p>
     * <pre>
     * a = 3l;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--NUM_LONG -&gt; 3l
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     * Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_INT
     **/
    public static final int NUM_LONG = JavaLexer.NUM_LONG;
    /**
     * A double precision floating point literal.  This is a floating
     * point number with an optional {@code D} or {@code d}
     * suffix.
     *
     * <p>For example:</p>
     * <pre>
     * a = 3.14d;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--EXPR -&gt; EXPR
     * |   `--ASSIGN -&gt; =
     * |       |--IDENT -&gt; a
     * |       `--NUM_DOUBLE -&gt; 3.14d
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a
     * href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     * Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_FLOAT
     **/
    public static final int NUM_DOUBLE = JavaLexer.NUM_DOUBLE;

    /**
     * The {@code assert} keyword.  This is only for Java 1.4 and
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
    public static final int LITERAL_ASSERT = JavaLexer.ASSERT;

    /**
     * A static import declaration.  Static import declarations are optional,
     * but must appear after the package declaration and before the type
     * declaration.
     *
     * <p>For example:</p>
     * <pre>
     * import static java.io.IOException;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * STATIC_IMPORT -&gt; import
     * |--LITERAL_STATIC -&gt; static
     * |--DOT -&gt; .
     * |   |--DOT -&gt; .
     * |   |   |--IDENT -&gt; java
     * |   |   `--IDENT -&gt; io
     * |   `--IDENT -&gt; IOException
     * `--SEMI -&gt; ;
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
        JavaLexer.STATIC_IMPORT;

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
        JavaLexer.ENUM_DEF;

    /**
     * The {@code enum} keyword.  This element appears
     * as part of an enum declaration.
     **/
    public static final int ENUM =
        JavaLexer.ENUM;

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
        JavaLexer.ENUM_CONSTANT_DEF;

    /**
     * A for-each clause.  This is a child of
     * {@code LITERAL_FOR}.  The children of this element may be
     * a parameter definition, the colon literal and an expression.
     *
     * <p>For example:</p>
     * <pre>
     * for (int value : values) {
     *     doSmth();
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_FOR -&gt; for
     *  |--LPAREN -&gt; (
     *  |--FOR_EACH_CLAUSE -&gt; FOR_EACH_CLAUSE
     *  |   |--VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |   |   |--MODIFIERS -&gt; MODIFIERS
     *  |   |   |--TYPE -&gt; TYPE
     *  |   |   |   `--LITERAL_INT -&gt; int
     *  |   |   `--IDENT -&gt; value
     *  |   |--COLON -&gt; :
     *  |   `--EXPR -&gt; EXPR
     *  |       `--IDENT -&gt; values
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      |--EXPR -&gt; EXPR
     *      |   `--METHOD_CALL -&gt; (
     *      |       |--IDENT -&gt; doSmth
     *      |       |--ELIST -&gt; ELIST
     *      |       `--RPAREN -&gt; )
     *      |--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     **/
    public static final int FOR_EACH_CLAUSE =
        JavaLexer.FOR_EACH_CLAUSE;

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
     * ANNOTATION_DEF -&gt; ANNOTATION_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--AT -&gt; @
     *  |--LITERAL_INTERFACE -&gt; interface
     *  |--IDENT -&gt; MyAnnotation
     *  `--OBJBLOCK -&gt; OBJBLOCK
     *      |--LCURLY -&gt; {
     *      |--ANNOTATION_FIELD_DEF -&gt; ANNOTATION_FIELD_DEF
     *      |   |--MODIFIERS -&gt; MODIFIERS
     *      |   |--TYPE -&gt; TYPE
     *      |   |   `--LITERAL_INT -&gt; int
     *      |   |--IDENT -&gt; someValue
     *      |   |--LPAREN -&gt; (
     *      |   |--RPAREN -&gt; )
     *      |   `--SEMI -&gt; ;
     *      `--RCURLY -&gt; }
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
        JavaLexer.ANNOTATION_DEF;

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
     * ANNOTATION_FIELD_DEF -&gt; ANNOTATION_FIELD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   `--IDENT -&gt; String
     *  |--IDENT -&gt; someField
     *  |--LPAREN -&gt; (
     *  |--RPAREN -&gt; )
     *  |--LITERAL_DEFAULT -&gt; default
     *  |   `--EXPR -&gt; EXPR
     *  |       `--STRING_LITERAL -&gt; "Hello world"
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #MODIFIERS
     * @see #TYPE
     * @see #LITERAL_DEFAULT
     */
    public static final int ANNOTATION_FIELD_DEF =
        JavaLexer.ANNOTATION_FIELD_DEF;

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
        JavaLexer.ANNOTATIONS;

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
        JavaLexer.ANNOTATION;

    /**
     * An initialization of an annotation member with a value.
     * Its children are the name of the member, the assignment literal
     * and the (compile-time constant conditional expression) value.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     */
    public static final int ANNOTATION_MEMBER_VALUE_PAIR =
        JavaLexer.ANNOTATION_MEMBER_VALUE_PAIR;

    /**
     * An annotation array member initialization.
     * Initializers can not be nested.
     * An initializer may be present as a default to an annotation
     * member, as the single default value to an annotation
     * (e.g. @Annotation({1,2})) or as the value of an annotation
     * member value pair.
     *
     * <p>For example:</p>
     * <pre>
     * &#64;Annotation({1, 2})
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * ANNOTATION -&gt; ANNOTATION
     *  |--AT -&gt; &#64;
     *  |--IDENT -&gt; Annotation
     *  |--LPAREN -&gt; (
     *  |--ANNOTATION_ARRAY_INIT -&gt; {
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 1
     *  |   |--COMMA -&gt; ,
     *  |   |--EXPR -&gt; EXPR
     *  |   |   `--NUM_INT -&gt; 2
     *  |   `--RCURLY -&gt; }
     *  `--RPAREN -&gt; )
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     * @see #ANNOTATION_MEMBER_VALUE_PAIR
     */
    public static final int ANNOTATION_ARRAY_INIT =
        JavaLexer.ANNOTATION_ARRAY_INIT;

    /**
     * A list of type parameters to a class, interface or
     * method definition. Children are LT, at least one
     * TYPE_PARAMETER, zero or more of: a COMMAs followed by a single
     * TYPE_PARAMETER and a final GT.
     *
     * <p>For example:</p>
     *
     * <pre>
     * public class MyClass&lt;A, B&gt; {
     *
     * }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; MyClass
     * |--TYPE_PARAMETERS -&gt; TYPE_PARAMETERS
     * |   |--GENERIC_START -&gt; &lt;
     * |   |--TYPE_PARAMETER -&gt; TYPE_PARAMETER
     * |   |   `--IDENT -&gt; A
     * |   |--COMMA -&gt; ,
     * |   |--TYPE_PARAMETER -&gt; TYPE_PARAMETER
     * |   |   `--IDENT -&gt; B
     * |   `--GENERIC_END -&gt; &gt;
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
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
        JavaLexer.TYPE_PARAMETERS;

    /**
     * A type parameter to a class, interface or method definition.
     * Children are the type name and an optional TYPE_UPPER_BOUNDS.
     *
     * <p>For example:</p>
     *
     * <pre>
     * public class MyClass &lt;A extends Collection&gt; {
     *
     * }
     * </pre>
     *
     * <p>parses as:</p>
     *
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; MyClass
     * |--TYPE_PARAMETERS -&gt; TYPE_PARAMETERS
     * |   |--GENERIC_START -&gt; &lt;
     * |   |--TYPE_PARAMETER -&gt; TYPE_PARAMETER
     * |   |   |--IDENT -&gt; A
     * |   |   `--TYPE_UPPER_BOUNDS -&gt; extends
     * |   |       `--IDENT -&gt; Collection
     * |   `--GENERIC_END -&gt; &gt;
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #IDENT
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     */
    public static final int TYPE_PARAMETER =
        JavaLexer.TYPE_PARAMETER;

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
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--TYPE -&gt; TYPE
     *  |   |--IDENT -&gt; Collection
     *  |   `--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     *  |       |--GENERIC_START -&gt; &lt;
     *  |       |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     *  |       |   `--WILDCARD_TYPE -&gt; ?
     *  |       `--GENERIC_END -&gt; &gt;
     *  |--IDENT -&gt; a
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #GENERIC_START
     * @see #GENERIC_END
     * @see #TYPE_ARGUMENT
     * @see #COMMA
     */
    public static final int TYPE_ARGUMENTS =
        JavaLexer.TYPE_ARGUMENTS;

    /**
     * A type arguments to a type reference or a method/ctor invocation.
     * Children are either: type name or wildcard type with possible type
     * upper or lower bounds.
     * <p>For example:</p>
     * <pre>List&lt;? super List&gt; list;</pre>
     * <p>parses as:</p>
     * <pre>
     * VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   |--IDENT -&gt; List
     *  |   `--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     *  |       |--GENERIC_START -&gt; &lt;
     *  |       |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     *  |       |   |--WILDCARD_TYPE -&gt; ?
     *  |       |   `--TYPE_LOWER_BOUNDS -&gt; super
     *  |       |       `--IDENT -&gt; List
     *  |       `--GENERIC_END -&gt; &gt;
     *  |--IDENT -&gt; list
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int TYPE_ARGUMENT =
        JavaLexer.TYPE_ARGUMENT;

    /**
     * The type that refers to all types. This node has no children.
     * <p> For example: </p>
     * <pre>
     *
     * List&lt;?&gt; list;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   |--IDENT -&gt; List
     * |   |   |`--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     * |   |        |--GENERIC_START -&gt; &lt;
     * |   |        |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     * |   |        |  `--WILDCARD_TYPE -&gt; ?
     * |   |        `--GENERIC_END -&gt; &gt;
     * |   `--IDENT -&gt; list
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_ARGUMENT
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int WILDCARD_TYPE =
        JavaLexer.WILDCARD_TYPE;

    /**
     * An upper bounds on a wildcard type argument or type parameter.
     * This node has one child - the type that is being used for
     * the bounding.
     * <p>For example:</p>
     * <pre>List&lt;? extends Number&gt; list;</pre>
     *
     * <p>parses as:</p>
     * <pre>
     * --VARIABLE_DEF -&gt; VARIABLE_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |--TYPE -&gt; TYPE
     *  |   |--IDENT -&gt; List
     *  |   `--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     *  |       |--GENERIC_START -&gt; &lt;
     *  |       |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     *  |       |   |--WILDCARD_TYPE -&gt; ?
     *  |       |   `--TYPE_UPPER_BOUNDS -&gt; extends
     *  |       |       `--IDENT -&gt; Number
     *  |       `--GENERIC_END -&gt; &gt;
     *  |--IDENT -&gt; list
     *  `--SEMI -&gt; ;
     *  </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_PARAMETER
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_UPPER_BOUNDS =
        JavaLexer.TYPE_UPPER_BOUNDS;

    /**
     * A lower bounds on a wildcard type argument. This node has one child
     *  - the type that is being used for the bounding.
     *
     *  <p>For example:</p>
     *  <pre>List&lt;? super Integer&gt; list;</pre>
     *
     *  <p>parses as:</p>
     *  <pre>
     *  --VARIABLE_DEF -&gt; VARIABLE_DEF
     *     |--MODIFIERS -&gt; MODIFIERS
     *     |--TYPE -&gt; TYPE
     *     |   |--IDENT -&gt; List
     *     |   `--TYPE_ARGUMENTS -&gt; TYPE_ARGUMENTS
     *     |       |--GENERIC_START -&gt; &lt;
     *     |       |--TYPE_ARGUMENT -&gt; TYPE_ARGUMENT
     *     |       |   |--WILDCARD_TYPE -&gt; ?
     *     |       |   `--TYPE_LOWER_BOUNDS -&gt; super
     *     |       |       `--IDENT -&gt; Integer
     *     |       `--GENERIC_END -&gt; &gt;
     *     |--IDENT -&gt; list
     *     `--SEMI -&gt; ;
     *  </pre>
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=14">
     * JSR14</a>
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_LOWER_BOUNDS =
        JavaLexer.TYPE_LOWER_BOUNDS;

    /**
     * An {@code @} symbol - signifying an annotation instance or the prefix
     * to the interface literal signifying the definition of an annotation
     * declaration.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     */
    public static final int AT = JavaLexer.AT;

    /**
     * A triple dot for variable-length parameters. This token only ever occurs
     * in a parameter declaration immediately after the type of the parameter.
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     * JSR201</a>
     */
    public static final int ELLIPSIS = JavaLexer.ELLIPSIS;

    /**
     * The {@code &} symbol when used to extend a generic upper or lower bounds constrain
     * or a type cast expression with an additional interface.
     *
     * <p>Generic type bounds extension:
     * {@code class Comparable<T extends Serializable & CharSequence>}</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Comparable
     * |--TYPE_PARAMETERS -&gt; TYPE_PARAMETERS
     *     |--GENERIC_START -&gt; &lt;
     *     |--TYPE_PARAMETER -&gt; TYPE_PARAMETER
     *     |   |--IDENT -&gt; T
     *     |   `--TYPE_UPPER_BOUNDS -&gt; extends
     *     |       |--IDENT -&gt; Serializable
     *     |       |--TYPE_EXTENSION_AND -&gt; &#38;
     *     |       `--IDENT -&gt; CharSequence
     *     `--GENERIC_END -&gt; &gt;
     * </pre>
     *
     * <p>Type cast extension:
     * {@code return (Serializable & CharSequence) null;}</p>
     * <pre>
     * --LITERAL_RETURN -&gt; return
     *    |--EXPR -&gt; EXPR
     *    |   `--TYPECAST -&gt; (
     *    |       |--TYPE -&gt; TYPE
     *    |       |   `--IDENT -&gt; Serializable
     *    |       |--TYPE_EXTENSION_AND -&gt; &#38;
     *    |       |--TYPE -&gt; TYPE
     *    |       |   `--IDENT -&gt; CharSequence
     *    |       |--RPAREN -&gt; )
     *    |       `--LITERAL_NULL -&gt; null
     *    `--SEMI -&gt; ;
     * </pre>
     *
     * @see #EXTENDS_CLAUSE
     * @see #TYPECAST
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.4">
     * Java Language Specification, &sect;4.4</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">
     * Java Language Specification, &sect;15.16</a>
     */
    public static final int TYPE_EXTENSION_AND =
        JavaLexer.TYPE_EXTENSION_AND;

    /**
     * A {@code <} symbol signifying the start of type arguments or type parameters.
     */
    public static final int GENERIC_START =
        JavaLexer.GENERIC_START;

    /**
     * A {@code >} symbol signifying the end of type arguments or type parameters.
     */
    public static final int GENERIC_END = JavaLexer.GENERIC_END;

    /**
     * Special lambda symbol {@code ->}.
     */
    public static final int LAMBDA = JavaLexer.LAMBDA;

    /**
     * Beginning of single line comment: '//'.
     *
     * <pre>
     * +--SINGLE_LINE_COMMENT
     *         |
     *         +--COMMENT_CONTENT
     * </pre>
     *
     * <p>For example:</p>
     * <pre>
     * // Comment content
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * SINGLE_LINE_COMMENT -&gt; //
     *  `--COMMENT_CONTENT -&gt;  Comment Content\n
     * </pre>
     */
    public static final int SINGLE_LINE_COMMENT =
            JavaLexer.SINGLE_LINE_COMMENT;

    /**
     * Beginning of block comment: '/*'.
     * <p>For example:</p>
     * <pre>
     * /&#42; Comment content
     * &#42;/
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * --BLOCK_COMMENT_BEGIN -&gt; /&#42;
     *    |--COMMENT_CONTENT -&gt;  Comment content\r\n
     *    `--BLOCK_COMMENT_END -&gt; &#42;/
     * </pre>
     */
    public static final int BLOCK_COMMENT_BEGIN =
            JavaLexer.BLOCK_COMMENT_BEGIN;

    /**
     * End of block comment: '&#42;/'.
     *
     * <pre>
     * +--BLOCK_COMMENT_BEGIN
     *         |
     *         +--COMMENT_CONTENT
     *         +--BLOCK_COMMENT_END
     * </pre>
     */
    public static final int BLOCK_COMMENT_END =
            JavaLexer.BLOCK_COMMENT_END;

    /**
     * Text of single-line or block comment.
     *
     * <pre>
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
            JavaLexer.COMMENT_CONTENT;

    /**
     * A pattern variable definition; when conditionally matched,
     * this variable is assigned with the defined type.
     *
     * <p>For example:</p>
     * <pre>
     * if (obj instanceof String str) { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_IF -&gt; if
     *  |--LPAREN -&gt; (
     *  |--EXPR -&gt; EXPR
     *  |   `--LITERAL_INSTANCEOF -&gt; instanceof
     *  |       |--IDENT -&gt; obj
     *  |       `--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     *  |           |--TYPE -&gt; TYPE
     *  |           |   `--IDENT -&gt; String
     *  |           `--IDENT -&gt; str
     *  |--RPAREN -&gt; )
     *  `--SLIST -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @see #LITERAL_INSTANCEOF
     * @since 8.35
     */
    public static final int PATTERN_VARIABLE_DEF =
            JavaLexer.PATTERN_VARIABLE_DEF;

    /**
     * The {@code record} keyword.  This element appears
     * as part of a record declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public record MyRecord () {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * RECORD_DEF -&gt; RECORD_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_RECORD -&gt; record
     * |--IDENT -&gt; MyRecord
     * |--LPAREN -&gt; (
     * |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     * |--RPAREN -&gt; )
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @since 8.35
     **/
    public static final int LITERAL_RECORD =
            JavaLexer.LITERAL_record;

    /**
     * A declaration of a record specifies a name, a header, and a body.
     * The header lists the components of the record, which are the variables
     * that make up its state.
     *
     * <p>For example:</p>
     * <pre>
     * public record MyRecord () {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * RECORD_DEF -&gt; RECORD_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_RECORD -&gt; record
     * |--IDENT -&gt; MyRecord
     * |--LPAREN -&gt; (
     * |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     * |--RPAREN -&gt; )
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @since 8.35
     */
    public static final int RECORD_DEF =
            JavaLexer.RECORD_DEF;

    /**
     * Record components are a (possibly empty) list containing the components of a record, which
     * are the variables that make up its state.
     *
     * <p>For example:</p>
     * <pre>
     * public record myRecord (Comp x, Comp y) { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * RECORD_DEF -&gt; RECORD_DEF
     *  |--MODIFIERS -&gt; MODIFIERS
     *  |   `--LITERAL_PUBLIC -&gt; public
     *  |--LITERAL_RECORD -&gt; record
     *  |--IDENT -&gt; myRecord
     *  |--LPAREN -&gt; (
     *  |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     *  |   |--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     *  |   |   |--ANNOTATIONS -&gt; ANNOTATIONS
     *  |   |   |--TYPE -&gt; TYPE
     *  |   |   |   `--IDENT -&gt; Comp
     *  |   |   `--IDENT -&gt; x
     *  |   |--COMMA -&gt; ,
     *  |   `--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     *  |       |--ANNOTATIONS -&gt; ANNOTATIONS
     *  |       |--TYPE -&gt; TYPE
     *  |       |   `--IDENT -&gt; Comp
     *  |       `--IDENT -&gt; y
     *  |--RPAREN -&gt; )
     *  `--OBJBLOCK -&gt; OBJBLOCK
     *      |--LCURLY -&gt; {
     *      `--RCURLY -&gt; }
     * </pre>
     *
     * @since 8.36
     */
    public static final int RECORD_COMPONENTS =
            JavaLexer.RECORD_COMPONENTS;

    /**
     * A record component is a variable that comprises the state of a record.  Record components
     * have annotations (possibly), a type definition, and an identifier.  They can also be of
     * variable arity ('...').
     *
     * <p>For example:</p>
     * <pre>
     * public record MyRecord(Comp x, Comp... comps) {
     *
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * RECORD_DEF -&gt; RECORD_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_PUBLIC -&gt; public
     * |--LITERAL_RECORD -&gt; record
     * |--IDENT -&gt; MyRecord
     * |--LPAREN -&gt; (
     * |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     * |   |--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     * |   |   |--ANNOTATIONS -&gt; ANNOTATIONS
     * |   |   |--TYPE -&gt; TYPE
     * |   |   |   `--IDENT -&gt; Comp
     * |   |   `--IDENT -&gt; x
     * |   |--COMMA -&gt; ,
     * |   `--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     * |       |--ANNOTATIONS -&gt; ANNOTATIONS
     * |       |--TYPE -&gt; TYPE
     * |       |   `--IDENT -&gt; Comp
     * |       |--ELLIPSIS -&gt; ...
     * |       `--IDENT -&gt; comps
     * |--RPAREN -&gt; )
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @since 8.36
     */
    public static final int RECORD_COMPONENT_DEF =
            JavaLexer.RECORD_COMPONENT_DEF;

    /**
     * A compact canonical constructor eliminates the list of formal parameters; they are
     * declared implicitly.
     *
     * <p>For example:</p>
     * <pre>
     * public record myRecord () {
     *     public myRecord{}
     * }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * RECORD_DEF
     * |--MODIFIERS
     * |   `--LITERAL_PUBLIC (public)
     * |--LITERAL_RECORD (record)
     * |--IDENT (myRecord)
     * |--LPAREN (()
     * |--RECORD_COMPONENTS
     * |--RPAREN ())
     * `--OBJBLOCK
     *     |--LCURLY ({)
     *     |--COMPACT_CTOR_DEF
     *     |   |--MODIFIERS
     *     |   |   `--LITERAL_PUBLIC (public)
     *     |   |--IDENT (myRecord)
     *     |   `--SLIST ({)
     *     |       `--RCURLY (})
     *     `--RCURLY (})
     * </pre>
     *
     * @since 8.36
     */
    public static final int COMPACT_CTOR_DEF =
            JavaLexer.COMPACT_CTOR_DEF;

    /**
     * Beginning of a Java 14 Text Block literal,
     * delimited by three double quotes.
     *
     * <p>For example:</p>
     * <pre>
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--IDENT -&gt; String
     * |   |--IDENT -&gt; hello
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--TEXT_BLOCK_LITERAL_BEGIN -&gt; """
     * |               |--TEXT_BLOCK_CONTENT -&gt; \r\n                 Hello, world!\r\n
     * |               `--TEXT_BLOCK_LITERAL_END -&gt; """
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_LITERAL_BEGIN =
            JavaLexer.TEXT_BLOCK_LITERAL_BEGIN;

    /**
     * Content of a Java 14 text block. This is a
     * sequence of characters, possibly escaped with '\'. Actual line terminators
     * are represented by '\n'.
     *
     * <p>For example:</p>
     * <pre>
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF -&gt; VARIABLE_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--TYPE -&gt; TYPE
     * |   |   `--IDENT -&gt; String
     * |   |--IDENT -&gt; hello
     * |   `--ASSIGN -&gt; =
     * |       `--EXPR -&gt; EXPR
     * |           `--TEXT_BLOCK_LITERAL_BEGIN -&gt; """
     * |               |--TEXT_BLOCK_CONTENT -&gt; \n                Hello, world!\n
     * |               `--TEXT_BLOCK_LITERAL_END -&gt; """
     * |--SEMI -&gt; ;
     * </pre>
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_CONTENT =
            JavaLexer.TEXT_BLOCK_CONTENT;

    /**
     * End of a Java 14 text block literal, delimited by three
     * double quotes.
     *
     * <p>For example:</p>
     * <pre>
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF
     * |   |--MODIFIERS
     * |   |--TYPE
     * |   |   `--IDENT (String)
     * |   |--IDENT (hello)
     * |   |--ASSIGN (=)
     * |   |   `--EXPR
     * |   |       `--TEXT_BLOCK_LITERAL_BEGIN (""")
     * |   |           |--TEXT_BLOCK_CONTENT (\n                Hello, world!\n                    )
     * |   |           `--TEXT_BLOCK_LITERAL_END (""")
     * |   `--SEMI (;)
     * </pre>
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_LITERAL_END =
            JavaLexer.TEXT_BLOCK_LITERAL_END;

    /**
     * The {@code yield} keyword.  This element appears
     * as part of a yield statement.
     *
     * <p>For example:</p>
     * <pre>
     * int yield = 0; // not a keyword here
     * return switch (mode) {
     *    case "a", "b":
     *        yield 1;
     *    default:
     *        yield - 1;
     * };
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * |--VARIABLE_DEF
     * |   |--MODIFIERS
     * |   |--TYPE
     * |   |   `--LITERAL_INT (int)
     * |   |--IDENT (yield)
     * |   `--ASSIGN (=)
     * |       `--EXPR
     * |           `--NUM_INT (0)
     * |--SEMI (;)
     * |--LITERAL_RETURN (return)
     * |   |--EXPR
     * |   |   `--LITERAL_SWITCH (switch)
     * |   |       |--LPAREN (()
     * |   |       |--EXPR
     * |   |       |   `--IDENT (mode)
     * |   |       |--RPAREN ())
     * |   |       |--LCURLY ({)
     * |   |       |--CASE_GROUP
     * |   |       |   |--LITERAL_CASE (case)
     * |   |       |   |   |--EXPR
     * |   |       |   |   |   `--STRING_LITERAL ("a")
     * |   |       |   |   |--COMMA (,)
     * |   |       |   |   |--EXPR
     * |   |       |   |   |   `--STRING_LITERAL ("b")
     * |   |       |   |   `--COLON (:)
     * |   |       |   `--SLIST
     * |   |       |       `--LITERAL_YIELD (yield)
     * |   |       |           |--EXPR
     * |   |       |           |   `--NUM_INT (1)
     * |   |       |           `--SEMI (;)
     * |   |       |--CASE_GROUP
     * |   |       |   |--LITERAL_DEFAULT (default)
     * |   |       |   |   `--COLON (:)
     * |   |       |   `--SLIST
     * |   |       |       `--LITERAL_YIELD (yield)
     * |   |       |           |--EXPR
     * |   |       |           |   `--UNARY_MINUS (-)
     * |   |       |           |       `--NUM_INT (1)
     * |   |       |           `--SEMI (;)
     * |   |       `--RCURLY (})
     * |   `--SEMI (;)
     * </pre>
     *
     *
     * @see #LITERAL_SWITCH
     * @see #CASE_GROUP
     * @see #SLIST
     * @see #SWITCH_RULE
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se13/preview/switch-expressions.html">
     * Java Language Specification, &sect;14.21</a>
     *
     * @since 8.36
     */
    public static final int LITERAL_YIELD =
            JavaLexer.LITERAL_yield;

    /**
     * Switch Expressions.
     *
     * <p>For example:</p>
     * <pre>
     * return switch (day) {
     *     case SAT, SUN -&gt; "Weekend";
     *     default -&gt; "Working day";
     * };
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * LITERAL_RETURN -&gt; return
     *  |--EXPR -&gt; EXPR
     *  |   `--LITERAL_SWITCH -&gt; switch
     *  |       |--LPAREN -&gt; (
     *  |       |--EXPR -&gt; EXPR
     *  |       |   `--IDENT -&gt; day
     *  |       |--RPAREN -&gt; )
     *  |       |--LCURLY -&gt; {
     *  |       |--SWITCH_RULE -&gt; SWITCH_RULE
     *  |       |   |--LITERAL_CASE -&gt; case
     *  |       |   |   |--EXPR -&gt; EXPR
     *  |       |   |   |   `--IDENT -&gt; SAT
     *  |       |   |   |--COMMA -&gt; ,
     *  |       |   |   `--EXPR -&gt; EXPR
     *  |       |   |       `--IDENT -&gt; SUN
     *  |       |   |--LAMBDA -&gt; -&gt;
     *  |       |   |--EXPR -&gt; EXPR
     *  |       |   |   `--STRING_LITERAL -&gt; "Weekend"
     *  |       |   `--SEMI -&gt; ;
     *  |       |--SWITCH_RULE -&gt; SWITCH_RULE
     *  |       |   |--LITERAL_DEFAULT -&gt; default
     *  |       |   |--LAMBDA -&gt; -&gt;
     *  |       |   |--EXPR -&gt; EXPR
     *  |       |   |   `--STRING_LITERAL -&gt; "Working day"
     *  |       |   `--SEMI -&gt; ;
     *  |       `--RCURLY -&gt; }
     *  `--SEMI -&gt; ;
     * </pre>
     *
     * @see #LITERAL_CASE
     * @see #LITERAL_DEFAULT
     * @see #LITERAL_SWITCH
     * @see #LITERAL_YIELD
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se13/preview/switch-expressions.html">
     * Java Language Specification, &sect;14.21</a>
     *
     * @since 8.36
     */
    public static final int SWITCH_RULE =
            JavaLexer.SWITCH_RULE;

    /**
     * The {@code non-sealed} keyword.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * <pre>
     * non-sealed class Square extends Rectangle { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   `--LITERAL_NON_SEALED -&gt; non-sealed
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Square
     * |--EXTENDS_CLAUSE -&gt; extends
     * |   `--IDENT -&gt; Rectangle
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     * Java Language Specification, &sect;8.1.1.2</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_NON_SEALED =
        JavaLexer.LITERAL_non_sealed;

    /**
     * The {@code sealed} restricted identifier.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_PUBLIC -&gt; public
     * |   `--LITERAL_SEALED -&gt; sealed
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Shape
     * |--PERMITS_CLAUSE -&gt; permits
     * |   |--IDENT -&gt; Circle
     * |   |--COMMA -&gt; ,
     * |   |--IDENT -&gt; Square
     * |   |--COMMA -&gt; ,
     * |   `--IDENT -&gt; Rectangle
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     * Java Language Specification, &sect;8.1.1.2</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_SEALED =
        JavaLexer.LITERAL_sealed;

    /**
     * The {@code permits} restricted identifier.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * <pre>
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_PUBLIC -&gt; public
     * |   `--LITERAL_SEALED -&gt; sealed
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Shape
     * |--PERMITS_CLAUSE -&gt; permits
     * |   |--IDENT -&gt; Circle
     * |   |--COMMA -&gt; ,
     * |   |--IDENT -&gt; Square
     * |   |--COMMA -&gt; ,
     * |   `--IDENT -&gt; Rectangle
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     * Java Language Specification, &sect;9.1.4</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_PERMITS =
        JavaLexer.LITERAL_permits;

    /**
     * A permits clause.  A permits clause's children are a comma separated list of one or
     * more identifiers.
     *
     * <p>For example:</p>
     * <pre>
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * </pre>
     * <p>parses as:</p>
     * <pre>
     * CLASS_DEF -&gt; CLASS_DEF
     * |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_PUBLIC -&gt; public
     * |   `--LITERAL_SEALED -&gt; sealed
     * |--LITERAL_CLASS -&gt; class
     * |--IDENT -&gt; Shape
     * |--PERMITS_CLAUSE -&gt; permits
     * |   |--IDENT -&gt; Circle
     * |   |--COMMA -&gt; ,
     * |   |--IDENT -&gt; Square
     * |   |--COMMA -&gt; ,
     * |   `--IDENT -&gt; Rectangle
     * `--OBJBLOCK -&gt; OBJBLOCK
     *     |--LCURLY -&gt; {
     *     `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     * Java Language Specification, &sect;9.1.4</a>
     * @see #MODIFIERS
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see #COMMA
     * @see #IDENT
     *
     * @since 8.42
     */
    public static final int PERMITS_CLAUSE =
        JavaLexer.PERMITS_CLAUSE;

    /** Prevent instantiation. */
    private TokenTypes() {
    }

}
