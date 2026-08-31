///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree.
 *
 * <p>Implementation detail: This class has been introduced to break
 * the circular dependency between packages.</p>
 *
 * @noinspection ClassWithTooManyDependents
 * @noinspectionreason ClassWithTooManyDependents - this class is a core part of our API
 */
public final class TokenTypes {

    /**
     * This is the root node for the source file.  It's children
     * are an optional package definition, zero or more import statements,
     * and zero or more type declarations.
     *
     * <p>For example:</p>
     * {@snippet :
     * import java.util.List;
     *
     * class MyClass{}
     * interface MyInterface{}
     * ;
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * COMPILATION_UNIT -> COMPILATION_UNIT
     * |--IMPORT -> import
     * |   |--DOT -> .
     * |   |   |--DOT -> .
     * |   |   |   |--IDENT -> java
     * |   |   |   `--IDENT -> util
     * |   |   `--IDENT -> List
     * |   `--SEMI -> ;
     * |--CLASS_DEF -> CLASS_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_CLASS -> class
     * |   |--IDENT -> MyClass
     * |   `--OBJBLOCK -> OBJBLOCK
     * |       |--LCURLY -> {
     * |       `--RCURLY -> }
     * |--INTERFACE_DEF -> INTERFACE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_INTERFACE -> interface
     * |   |--IDENT -> MyInterface
     * |   `--OBJBLOCK -> OBJBLOCK
     * |       |--LCURLY -> {
     * |       `--RCURLY -> }
     * `--SEMI -> ;
     * }
     *
     * @see #PACKAGE_DEF
     * @see #IMPORT
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see #RECORD_DEF
     * @see #ANNOTATION_DEF
     * @see #ENUM_DEF
     */
    public static final int COMPILATION_UNIT = JavaLanguageLexer.COMPILATION_UNIT;

    /**
     * The root of an AST for a JEP 512 (JDK 25) compact source file. This node
     * replaces {@link #COMPILATION_UNIT} as the root when the source file declares
     * any top-level method or field. It represents a {@code CompactCompilationUnit},
     * which is one of the three mutually exclusive compilation-unit
     * forms ({@code OrdinaryCompilationUnit}, {@code CompactCompilationUnit},
     * {@code ModularCompilationUnit}). Import declarations and top-level members
     * appear as direct children of this node. Compact source files cannot declare
     * a package.
     *
     * <p>For example:</p>
     * {@snippet :
     * import java.util.List;
     *
     * int counter = 3;
     * void main() {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * COMPACT_COMPILATION_UNIT -> COMPACT_COMPILATION_UNIT
     * |--IMPORT -> import
     * |   |--DOT -> .
     * |   |   |--DOT -> .
     * |   |   |   |--IDENT -> java
     * |   |   |   `--IDENT -> util
     * |   |   `--IDENT -> List
     * |   `--SEMI -> ;
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_INT -> int
     * |   |--IDENT -> counter
     * |   |--ASSIGN -> =
     * |   |   `--EXPR -> EXPR
     * |   |       `--NUM_INT -> 3
     * |   `--SEMI -> ;
     * `--METHOD_DEF -> METHOD_DEF
     *     |--MODIFIERS -> MODIFIERS
     *     |--TYPE -> TYPE
     *     |   `--LITERAL_VOID -> void
     *     |--IDENT -> main
     *     |--LPAREN -> (
     *     |--PARAMETERS -> PARAMETERS
     *     |--RPAREN -> )
     *     `--SLIST -> {
     *         `--RCURLY -> }
     * }
     *
     * @see <a href="https://openjdk.org/jeps/512">JEP 512: Compact Source Files
     *     and Instance Main Methods</a>
     * @see #COMPILATION_UNIT
     */
    public static final int COMPACT_COMPILATION_UNIT =
            JavaLanguageLexer.COMPACT_COMPILATION_UNIT;

    /**
     * Modifiers for type, method, and field declarations.  The
     * modifiers element is always present even though it may have no
     * children.
     *
     * <p>For example:</p>
     * {@snippet :
     * public int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
     *     Language Specification, &sect;8</a>
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
     */
    public static final int MODIFIERS = JavaLanguageLexer.MODIFIERS;

    /**
     * An object block.  These are children of class, interface, enum,
     * annotation and enum constant declarations.
     * Also, object blocks are children of the new keyword when defining
     * anonymous inner types.
     *
     * <p>For example:</p>
     * {@snippet :
     * class Test {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Test
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
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
     */
    public static final int OBJBLOCK = JavaLanguageLexer.OBJBLOCK;
    /**
     * A list of statements.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (c == 1) {
     *     c = 0;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--EQUAL -> ==
     *  |       |--IDENT -> c
     *  |       `--NUM_INT -> 1
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--EXPR -> EXPR
     *      |   `--ASSIGN -> =
     *      |       |--IDENT -> c
     *      |       `--NUM_INT -> 0
     *      |--SEMI -> ;
     *      `--RCURLY -> }
     * }
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
     */
    public static final int SLIST = JavaLanguageLexer.SLIST;
    /**
     * A constructor declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public SpecialEntry(int value, String text)
     * {
     *   this.value = value;
     *   this.text = text;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CTOR_DEF -> CTOR_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--IDENT -> SpecialEntry
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |   |--PARAMETER_DEF -> PARAMETER_DEF
     *  |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--LITERAL_INT -> int
     *  |   |   `--IDENT -> value
     *  |   |--COMMA -> ,
     *  |   `--PARAMETER_DEF -> PARAMETER_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--IDENT -> String
     *  |       `--IDENT -> text
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--EXPR -> EXPR
     *      |   `--ASSIGN -> =
     *      |       |--DOT -> .
     *      |   |--LITERAL_THIS -> this
     *      |       |   `--IDENT -> value
     *      |       `--IDENT -> value
     *      |--SEMI -> ;
     *      |--EXPR -> EXPR
     *      |   `--ASSIGN -> =
     *      |       |--DOT -> .
     *      |       |   |--LITERAL_THIS -> this
     *      |       |   `--IDENT -> text
     *      |       `--IDENT -> text
     *      |--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see #OBJBLOCK
     * @see #CLASS_DEF
     */
    public static final int CTOR_DEF = JavaLanguageLexer.CTOR_DEF;
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
     * {@snippet :
     *  public static int square(int x)
     *  {
     *    return x*x;
     *  }
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * --METHOD_DEF -> METHOD_DEF
     *    |--MODIFIERS -> MODIFIERS
     *    |   |--LITERAL_PUBLIC -> public
     *    |   `--LITERAL_STATIC -> static
     *    |--TYPE -> TYPE
     *    |   `--LITERAL_INT -> int
     *    |--IDENT -> square
     *    |--LPAREN -> (
     *    |--PARAMETERS -> PARAMETERS
     *    |   `--PARAMETER_DEF -> PARAMETER_DEF
     *    |       |--MODIFIERS -> MODIFIERS
     *    |       |--TYPE -> TYPE
     *    |       |   `--LITERAL_INT -> int
     *    |       `--IDENT -> x
     *    |--RPAREN -> )
     *    `--SLIST -> {
     *        |--LITERAL_RETURN -> return
     *        |   |--EXPR -> EXPR
     *        |   |   `--STAR -> *
     *        |   |       |--IDENT -> x
     *        |   |       `--IDENT -> x
     *        |   `--SEMI -> ;
     *        `--RCURLY -> }
     * }
     *
     * @see #MODIFIERS
     * @see #TYPE_PARAMETERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #LITERAL_THROWS
     * @see #SLIST
     * @see #OBJBLOCK
     */
    public static final int METHOD_DEF = JavaLanguageLexer.METHOD_DEF;

    /**
     * A field or local variable declaration.  The children are
     * modifiers, type, the identifier name, and an optional
     * assignment statement.
     *
     * <p>For example:</p>
     * {@snippet :
     * final double PI = 3.14;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--FINAL -> final
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_DOUBLE -> double
     *  |--IDENT -> PI
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--NUM_FLOAT -> 3.14
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #ASSIGN
     */
    public static final int VARIABLE_DEF =
        JavaLanguageLexer.VARIABLE_DEF;

    /**
     * An instance initializer.  Zero or more instance initializers
     * may appear in class and enum definitions.  This token will be a child
     * of the object block of the declaring type.
     *
     * <p>For example:</p>
     * {@snippet :
     * public class MyClass {
     *     private int foo;
     *     {foo = 10;}
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--LITERAL_CLASS -> class
     *  |--IDENT -> MyClass
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      |--VARIABLE_DEF -> VARIABLE_DEF
     *      |   |--MODIFIERS -> MODIFIERS
     *      |   |   `--LITERAL_PRIVATE -> private
     *      |   |--TYPE -> TYPE
     *      |   |   `--LITERAL_INT -> int
     *      |   |--IDENT -> foo
     *      |   `--SEMI -> ;
     *      |--INSTANCE_INIT -> INSTANCE_INIT
     *      |   `--SLIST -> {
     *      |       |--EXPR -> EXPR
     *      |       |   `--ASSIGN -> =
     *      |       |       |--IDENT -> foo
     *      |       |       `--NUM_INT -> 10
     *      |       |--SEMI -> ;
     *      |       `--RCURLY -> }
     *      `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.6">Java
     *     Language Specification&sect;8.6</a>
     * @see #SLIST
     * @see #OBJBLOCK
     */
    public static final int INSTANCE_INIT =
        JavaLanguageLexer.INSTANCE_INIT;

    /**
     * A static initialization block.  Zero or more static
     * initializers may be children of the object block of a class
     * or enum declaration (interfaces cannot have static initializers).  The
     * first and only child is a statement list.
     *
     * <p>For Example:</p>
     * {@snippet :
     * static {
     *   num = 10;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * STATIC_INIT -> STATIC_INIT
     *  `--SLIST -> {
     *      |--EXPR -> EXPR
     *      |   `--ASSIGN -> =
     *      |       |--IDENT -> num
     *      |       `--NUM_INT -> 10
     *      |--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.7">Java
     *     Language Specification, &sect;8.7</a>
     * @see #SLIST
     * @see #OBJBLOCK
     */
    public static final int STATIC_INIT =
        JavaLanguageLexer.STATIC_INIT;

    /**
     * A type.  This is either a return type of a method or a type of
     * a variable or field.  The first child of this element is the
     * actual type.  This may be a primitive type, an identifier, a
     * dot which is the root of a fully qualified type, or an array of
     * any of these. The second child may be type arguments to the type.
     *
     * <p>
     * For example:
     * {@code boolean var = true;}
     * </p>
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_BOOLEAN -> boolean
     * |   |--IDENT -> var
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--LITERAL_TRUE -> true
     * |--SEMI -> ;
     * }
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
     */
    public static final int TYPE = JavaLanguageLexer.TYPE;
    /**
     * A class declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public class Test {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Test
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html">Java
     *     Language Specification, &sect;8</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     */
    public static final int CLASS_DEF = JavaLanguageLexer.CLASS_DEF;
    /**
     * An interface declaration.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * public interface MyInterface {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * INTERFACE_DEF -> INTERFACE_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_INTERFACE -> interface
     * |--IDENT -> MyInterface
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html">Java
     *     Language Specification, &sect;9</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #OBJBLOCK
     */
    public static final int INTERFACE_DEF =
        JavaLanguageLexer.INTERFACE_DEF;

    /**
     * The package declaration.  This is optional, but if it is
     * included, then there is only one package declaration per source
     * file and it must be the first non-comment in the file. A package
     * declaration may be annotated in which case the annotations comes
     * before the rest of the declaration (and are the first children).
     *
     * <p>For example:</p>
     *
     * {@snippet :
     *   package com.puppycrawl.tools.checkstyle.api;
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * PACKAGE_DEF -> package
     * |--ANNOTATIONS -> ANNOTATIONS
     * |--DOT -> .
     * |   |--DOT -> .
     * |   |   |--DOT -> .
     * |   |   |   |--DOT -> .
     * |   |   |   |   |--IDENT -> com
     * |   |   |   |   `--IDENT -> puppycrawl
     * |   |   |   `--IDENT -> tools
     * |   |   `--IDENT -> checkstyle
     * |   `--IDENT -> api
     * `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.4">Java
     *     Language Specification &sect;7.4</a>
     * @see #DOT
     * @see #IDENT
     * @see #SEMI
     * @see #ANNOTATIONS
     * @see FullIdent
     */
    public static final int PACKAGE_DEF = JavaLanguageLexer.PACKAGE_DEF;
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
     * {@snippet :
     *   int[] x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--LITERAL_INT -> int
     *  |   `--ARRAY_DECLARATOR -> [
     *  |       `--RBRACK -> ]
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * <p>The array declaration may also represent an inline array
     * definition.  In this case, the first child will be either an
     * expression specifying the length of the array or an array
     * initialization block.</p>
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-10.html">Java
     *     Language Specification &sect;10</a>
     * @see #TYPE
     * @see #ARRAY_INIT
     */
    public static final int ARRAY_DECLARATOR =
        JavaLanguageLexer.ARRAY_DECLARATOR;

    /**
     * An extends clause.  This appears as part of class and interface
     * definitions.  This element appears even if the
     * {@code extends} keyword is not explicitly used.  The child
     * is an optional identifier.
     *
     * <p>For example:</p>
     * {@snippet :
     * public class Test extends ArrayList {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Test
     * |--EXTENDS_CLAUSE -> extends
     * |   `--IDENT -> ArrayList
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see #IDENT
     * @see #DOT
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see FullIdent
     */
    public static final int EXTENDS_CLAUSE =
        JavaLanguageLexer.EXTENDS_CLAUSE;

    /**
     * An implements clause.  This always appears in a class or enum
     * declaration, even if there are no implemented interfaces.  The
     * children are a comma separated list of zero or more
     * identifiers.
     *
     * <p>For example:</p>
     * {@snippet :
     * public class MyClass implements Collection {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_CLASS -> class
     * |--IDENT -> MyClass
     * |--IMPLEMENTS_CLAUSE -> implements
     * |   `--IDENT -> Collection
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #CLASS_DEF
     * @see #ENUM_DEF
     */
    public static final int IMPLEMENTS_CLAUSE =
        JavaLanguageLexer.IMPLEMENTS_CLAUSE;

    /**
     * A list of parameters to a method or constructor.  The children
     * are zero or more parameter declarations separated by commas.
     *
     * <p>For example</p>
     * {@snippet :
     * int start, int end
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * PARAMETERS -> PARAMETERS
     *  |--PARAMETER_DEF -> PARAMETER_DEF
     *  |   |--MODIFIERS -> MODIFIERS
     *  |   |--TYPE -> TYPE
     *  |   |   `--LITERAL_INT -> int
     *  |   `--IDENT -> start
     *  |--COMMA -> ,
     *  `--PARAMETER_DEF -> PARAMETER_DEF
     *      |--MODIFIERS -> MODIFIERS
     *      |--TYPE -> TYPE
     *      |   `--LITERAL_INT -> int
     *      `--IDENT -> end
     * }
     *
     * @see #PARAMETER_DEF
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     */
    public static final int PARAMETERS = JavaLanguageLexer.PARAMETERS;
    /**
     * A parameter declaration. The last parameter in a list of parameters may
     * be variable length (indicated by the ELLIPSIS child node immediately
     * after the TYPE child).
     *
     * <p>For example</p>
     * {@snippet :
     *      void foo(SomeType SomeType.this, int firstParameter, int... secondParameter) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> foo
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |   |--PARAMETER_DEF -> PARAMETER_DEF
     *  |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--IDENT -> SomeType
     *  |   |   `--DOT -> .
     *  |   |       |--IDENT -> SomeType
     *  |   |       `--LITERAL_THIS -> this
     *  |   |--COMMA -> ,
     *  |   |--PARAMETER_DEF -> PARAMETER_DEF
     *  |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--LITERAL_INT -> int
     *  |   |   `--IDENT -> firstParameter
     *  |   |--COMMA -> ,
     *  |   `--PARAMETER_DEF -> PARAMETER_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       |--ELLIPSIS -> ...
     *  |       `--IDENT -> secondParameter
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     *
     * }
     *
     * @see #MODIFIERS
     * @see #TYPE
     * @see #IDENT
     * @see #PARAMETERS
     * @see #ELLIPSIS
     */
    public static final int PARAMETER_DEF =
        JavaLanguageLexer.PARAMETER_DEF;

    /**
     * A labeled statement.
     *
     * <p>For example:</p>
     * {@snippet :
     * outer:
     * while (i < 10) {
     *     if (i == 5)
     *         continue outer;
     *     i++;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LABELED_STAT -> :
     *  |--IDENT -> outer
     *  `--LITERAL_WHILE -> while
     *      |--LPAREN -> (
     *      |--EXPR -> EXPR
     *      |   `--LT -> <
     *      |       |--IDENT -> i
     *      |       `--NUM_INT -> 10
     *      |--RPAREN -> )
     *      `--SLIST -> {
     *          |--LITERAL_IF -> if
     *          |   |--LPAREN -> (
     *          |   |--EXPR -> EXPR
     *          |   |   `--EQUAL -> ==
     *          |   |       |--IDENT -> i
     *          |   |       `--NUM_INT -> 5
     *          |   |--RPAREN -> )
     *          |   `--LITERAL_CONTINUE -> continue
     *          |       |--IDENT -> outer
     *          |       `--SEMI -> ;
     *          |--EXPR -> EXPR
     *          |   `--POST_INC -> ++
     *          |       `--IDENT -> i
     *          |--SEMI -> ;
     *          `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.7">Java
     *     Language Specification, &sect;14.7</a>
     * @see #SLIST
     */
    public static final int LABELED_STAT =
        JavaLanguageLexer.LABELED_STAT;

    /**
     * A type-cast.
     *
     * <p>For example:</p>
     * {@snippet :
     * (String)it.next()
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * `--TYPECAST -> (
     *     |--TYPE -> TYPE
     *     |   `--IDENT -> String
     *     |--RPAREN -> )
     *     `--METHOD_CALL -> (
     *         |--DOT -> .
     *         |   |--IDENT -> it
     *         |   `--IDENT -> next
     *         |--ELIST -> ELIST
     *         `--RPAREN -> )
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">Java
     *     Language Specification, &sect;15.16</a>
     * @see #EXPR
     * @see #TYPE
     * @see #TYPE_ARGUMENTS
     * @see #RPAREN
     */
    public static final int TYPECAST = JavaLanguageLexer.TYPECAST;
    /**
     * The array index operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * arr[0] = 10;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--INDEX_OP -> [
     * |       |   |--IDENT -> arr
     * |       |   |--EXPR -> EXPR
     * |       |   |   `--NUM_INT -> 0
     * |       |   `--RBRACK -> ]
     * |       `--NUM_INT -> 10
     * |--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int INDEX_OP = JavaLanguageLexer.INDEX_OP;
    /**
     * The {@code ++} (postfix increment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a++;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--POST_INC -> ++
     * |       `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.1">Java
     *     Language Specification, &sect;15.14.1</a>
     * @see #EXPR
     * @see #INC
     */
    public static final int POST_INC = JavaLanguageLexer.POST_INC;
    /**
     * The {@code --} (postfix decrement) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a--;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--POST_DEC -> --
     * |       `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.14.2">Java
     *     Language Specification, &sect;15.14.2</a>
     * @see #EXPR
     * @see #DEC
     */
    public static final int POST_DEC = JavaLanguageLexer.POST_DEC;
    /**
     * A method call. A method call may have type arguments however these
     * are attached to the appropriate node in the qualified method name.
     *
     * <p>For example:</p>
     * {@snippet :
     * Integer.parseInt("123");
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--METHOD_CALL -> (
     * |       |--DOT -> .
     * |       |   |--IDENT -> Integer
     * |       |   `--IDENT -> parseInt
     * |       |--ELIST -> ELIST
     * |       |   `--EXPR -> EXPR
     * |       |       `--STRING_LITERAL -> "123"
     * |       `--RPAREN -> )
     * |--SEMI -> ;
     * }
     *
     *
     * @see #IDENT
     * @see #TYPE_ARGUMENTS
     * @see #DOT
     * @see #ELIST
     * @see #RPAREN
     * @see FullIdent
     */
    public static final int METHOD_CALL = JavaLanguageLexer.METHOD_CALL;

    /**
     * A reference to a method or constructor without arguments. Part of Java 8 syntax.
     * The token should be used for subscribing for double colon literal.
     * {@link #DOUBLE_COLON} token does not appear in the tree.
     *
     * <p>For example:</p>
     * {@snippet :
     * Comparator<String> compare = String::compareToIgnoreCase;
     * }
     *
     * <p>parses as:
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   |--IDENT -> Comparator
     * |   |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     * |   |       |--GENERIC_START -> <
     * |   |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     * |   |       |   `--IDENT -> String
     * |   |       `--GENERIC_END -> >
     * |   |--IDENT -> compare
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--METHOD_REF -> ::
     * |               |--IDENT -> String
     * |               `--IDENT -> compareToIgnoreCase
     * |--SEMI -> ;
     * }
     *
     * @see #IDENT
     * @see #DOUBLE_COLON
     */
    public static final int METHOD_REF = JavaLanguageLexer.METHOD_REF;
    /**
     * An expression.  Operators with lower precedence appear at a
     * higher level in the tree than operators with higher precedence.
     * Parentheses are siblings to the operator they enclose.
     *
     * <p>For example:</p>
     * {@snippet :
     * int x = 4 + 2 * (5 % 3) + (1 << 3) - 4 * 5;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_INT -> int
     * |   |--IDENT -> x
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--MINUS -> -
     * |               |--PLUS -> +
     * |               |   |--PLUS -> +
     * |               |   |   |--NUM_INT -> 4
     * |               |   |   `--STAR -> *
     * |               |   |       |--NUM_INT -> 2
     * |               |   |       |--LPAREN -> (
     * |               |   |       |--MOD -> %
     * |               |   |       |   |--NUM_INT -> 5
     * |               |   |       |   `--NUM_INT -> 3
     * |               |   |       `--RPAREN -> )
     * |               |   |--LPAREN -> (
     * |               |   |--SL -> <<
     * |               |   |   |--NUM_INT -> 1
     * |               |   |   `--NUM_INT -> 3
     * |               |   `--RPAREN -> )
     * |               `--STAR -> *
     * |                   |--NUM_INT -> 4
     * |                   `--NUM_INT -> 5
     * |--SEMI -> ;
     * }
     *
     * @see #ELIST
     * @see #ASSIGN
     * @see #LPAREN
     * @see #RPAREN
     */
    public static final int EXPR = JavaLanguageLexer.EXPR;
    /**
     * An array initialization.  This may occur as part of an array
     * declaration or inline with {@code new}.
     *
     * <p>For example:</p>
     * {@snippet :
     *   int[] y =
     *     {
     *       1,
     *       2,
     *     };
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--LITERAL_INT -> int
     *  |   `--ARRAY_DECLARATOR -> [
     *  |       `--RBRACK -> ]
     *  |--IDENT -> y
     *  |--ASSIGN -> =
     *  |   `--ARRAY_INIT -> {
     *  |       |--EXPR -> EXPR
     *  |       |   `--NUM_INT -> 1
     *  |       |--COMMA -> ,
     *  |       |--EXPR -> EXPR
     *  |       |   `--NUM_INT -> 2
     *  |       |--COMMA -> ,
     *  |       `--RCURLY -> }
     *  `--SEMI -> ;
     * }
     *
     * <p>Also consider:</p>
     * {@snippet :
     *   int[] z = new int[]
     *     {
     *       1,
     *       2,
     *     };
     * }
     *
     * <p>which parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE [2:4]
     *  |   |--LITERAL_INT -> int
     *  |   `--ARRAY_DECLARATOR -> [
     *  |       `--RBRACK -> ]
     *  |--IDENT -> z
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_NEW -> new
     *  |           |--LITERAL_INT -> int
     *  |           |--ARRAY_DECLARATOR -> [
     *  |           |   `--RBRACK -> ]
     *  |           `--ARRAY_INIT -> {
     *  |               |--EXPR -> EXPR
     *  |               |   `--NUM_INT -> 1
     *  |               |--COMMA -> ,
     *  |               |--EXPR -> EXPR
     *  |               |   `--NUM_INT -> 2
     *  |               |--COMMA -> ,
     *  |               `--RCURLY -> }
     *  `--SEMI -> ;
     * }
     *
     * @see #ARRAY_DECLARATOR
     * @see #TYPE
     * @see #LITERAL_NEW
     * @see #COMMA
     */
    public static final int ARRAY_INIT = JavaLanguageLexer.ARRAY_INIT;
    /**
     * An import declaration.  Import declarations are option, but
     * must appear after the package declaration and before the first type
     * declaration.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     *   import java.io.IOException;
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * IMPORT -> import
     * |--DOT -> .
     * |   |--DOT -> .
     * |   |   |--IDENT -> java
     * |   |   `--IDENT -> io
     * |   `--IDENT -> IOException
     * `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5">Java
     *     Language Specification &sect;7.5</a>
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     */
    public static final int IMPORT = JavaLanguageLexer.IMPORT;
    /**
     * The {@code -} (unary minus) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = -b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--UNARY_MINUS -> -
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.4">Java
     *     Language Specification, &sect;15.15.4</a>
     * @see #EXPR
     */
    public static final int UNARY_MINUS = JavaLanguageLexer.UNARY_MINUS;
    /**
     * The {@code +} (unary plus) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = + b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--UNARY_PLUS -> +
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.3">Java
     *     Language Specification, &sect;15.15.3</a>
     * @see #EXPR
     */
    public static final int UNARY_PLUS = JavaLanguageLexer.UNARY_PLUS;
    /**
     * A group of case clauses.  Case clauses with no associated
     * statements are grouped together into a case group.  The last
     * child is a statement list containing the statements to execute
     * upon a match.
     *
     * <p>For example:</p>
     * {@snippet :
     * case 0:
     * case 1:
     * case 2:
     *   x = 3;
     *   break;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CASE_GROUP -> CASE_GROUP
     *  |--LITERAL_CASE -> case
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 0
     *  |   `--COLON -> :
     *  |--LITERAL_CASE -> case
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 1
     *  |   `--COLON -> :
     *  |--LITERAL_CASE -> case
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 2
     *  |   `--COLON -> :
     *  `--SLIST -> SLIST
     *      |--EXPR -> EXPR
     *      |   `--ASSIGN -> =
     *      |       |--IDENT -> x
     *      |       `--NUM_INT -> 3
     *      |--SEMI -> ;
     *      `--LITERAL_BREAK -> break
     *          `--SEMI -> ;
     * }
     *
     * @see #LITERAL_CASE
     * @see #LITERAL_DEFAULT
     * @see #LITERAL_SWITCH
     * @see #LITERAL_YIELD
     */
    public static final int CASE_GROUP = JavaLanguageLexer.CASE_GROUP;
    /**
     * An expression list.  The children are a comma separated list of
     * expressions.
     *
     * <p>For example:</p>
     * {@snippet :
     * new ArrayList(50);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--LITERAL_NEW -> new
     * |       |--IDENT -> ArrayList
     * |       |--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     * |       |   |--GENERIC_START -> <
     * |       |   `--GENERIC_END -> >
     * |       |--LPAREN -> (
     * |       |--ELIST -> ELIST
     * |       |   `--EXPR -> EXPR
     * |       |       `--NUM_INT -> 50
     * |       `--RPAREN -> )
     * |--SEMI -> ;
     * }
     *
     * @see #LITERAL_NEW
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #CTOR_CALL
     * @see #SUPER_CTOR_CALL
     */
    public static final int ELIST = JavaLanguageLexer.ELIST;
    /**
     * A for loop initializer.  This is a child of
     * {@code LITERAL_FOR}.  The children of this element may be
     * a comma separated list of variable declarations, an expression
     * list, or empty.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (int i = 0; i < arr.length; i++) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |   `--VARIABLE_DEF -> VARIABLE_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       |--IDENT -> i
     *  |       `--ASSIGN -> =
     *  |           `--EXPR -> EXPR
     *  |               `--NUM_INT -> 0
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |   `--EXPR -> EXPR
     *  |       `--LT -> <
     *  |           |--IDENT -> i
     *  |           `--DOT -> .
     *  |               |--IDENT -> arr
     *  |               `--IDENT -> length
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |   `--ELIST -> ELIST
     *  |       `--EXPR -> EXPR
     *  |           `--POST_INC -> ++
     *  |               `--IDENT -> i
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     */
    public static final int FOR_INIT = JavaLanguageLexer.FOR_INIT;
    /**
     * A for loop condition.  This is a child of
     * {@code LITERAL_FOR}.  The child of this element is an
     * optional expression.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (int i = 0; i < arr.length; i++) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |   `--VARIABLE_DEF -> VARIABLE_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       |--IDENT -> i
     *  |       `--ASSIGN -> =
     *  |           `--EXPR -> EXPR
     *  |               `--NUM_INT -> 0
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |   `--EXPR -> EXPR
     *  |       `--LT -> <
     *  |           |--IDENT -> i
     *  |           `--DOT -> .
     *  |               |--IDENT -> arr
     *  |               `--IDENT -> length
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |   `--ELIST -> ELIST
     *  |       `--EXPR -> EXPR
     *  |           `--POST_INC -> ++
     *  |               `--IDENT -> i
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #EXPR
     * @see #LITERAL_FOR
     */
    public static final int FOR_CONDITION =
        JavaLanguageLexer.FOR_CONDITION;

    /**
     * A for loop iterator.  This is a child of
     * {@code LITERAL_FOR}.  The child of this element is an
     * optional expression list.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (int i = 0; i < arr.length; i++) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |   `--VARIABLE_DEF -> VARIABLE_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       |--IDENT -> i
     *  |       `--ASSIGN -> =
     *  |           `--EXPR -> EXPR
     *  |               `--NUM_INT -> 0
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |   `--EXPR -> EXPR
     *  |       `--LT -> <
     *  |           |--IDENT -> i
     *  |           `--DOT -> .
     *  |               |--IDENT -> arr
     *  |               `--IDENT -> length
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |   `--ELIST -> ELIST
     *  |       `--EXPR -> EXPR
     *  |           `--POST_INC -> ++
     *  |               `--IDENT -> i
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #ELIST
     * @see #LITERAL_FOR
     */
    public static final int FOR_ITERATOR =
        JavaLanguageLexer.FOR_ITERATOR;

    /**
     * The empty statement.  This goes in place of an
     * {@code SLIST} for a {@code for} or {@code while}
     * loop body.
     *
     * <p>For example:</p>
     * {@snippet :
     * while(true);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_WHILE -> while
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LITERAL_TRUE -> true
     *  |--RPAREN -> )
     *  `--EMPTY_STAT -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.6">Java
     *     Language Specification, &sect;14.6</a>
     * @see #LITERAL_FOR
     * @see #LITERAL_WHILE
     */
    public static final int EMPTY_STAT = JavaLanguageLexer.EMPTY_STAT;
    /**
     * The {@code final} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public final int x = 0;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   |--LITERAL_PUBLIC -> public
     *  |   `--FINAL -> final
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--NUM_INT -> 0
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int FINAL = JavaLanguageLexer.FINAL;
    /**
     * The {@code abstract} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     *  public abstract class MyClass
     *  {
     *  }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * --CLASS_DEF
     *    |--MODIFIERS
     *    |   |--LITERAL_PUBLIC (public)
     *    |   `--ABSTRACT (abstract)
     *    |--LITERAL_CLASS (class)
     *    |--IDENT (MyClass)
     *    `--OBJBLOCK
     *        |--LCURLY ({)
     *        `--RCURLY (})
     * }
     *
     * @see #MODIFIERS
     */
    public static final int ABSTRACT = JavaLanguageLexer.ABSTRACT;
    /**
     * The {@code strictfp} keyword.
     *
     * <p>For example: {@code public strictfp class Test {}}</p>
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_PUBLIC -> public
     * |   `--STRICTFP -> strictfp
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Test
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see #MODIFIERS
     */
    public static final int STRICTFP = JavaLanguageLexer.STRICTFP;
    /**
     * A super constructor call.
     *
     * <p>For example:</p>
     * {@snippet :
     * super(1);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * SUPER_CTOR_CALL -> super
     *  |--LPAREN -> (
     *  |--ELIST -> ELIST
     *  |   `--EXPR -> EXPR
     *  |       `--NUM_INT -> 1
     *  |--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #CTOR_CALL
     */
    public static final int SUPER_CTOR_CALL =
        JavaLanguageLexer.SUPER_CTOR_CALL;

    /**
     * A constructor call.
     *
     * <p>For example:</p>
     * {@snippet :
     * this(1);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CTOR_CALL -> this
     *  |--LPAREN -> (
     *  |--ELIST -> ELIST
     *  |   `--EXPR -> EXPR
     *  |       `--NUM_INT -> 1
     *  |--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * @see #ELIST
     * @see #RPAREN
     * @see #SEMI
     * @see #SUPER_CTOR_CALL
     */
    public static final int CTOR_CALL = JavaLanguageLexer.CTOR_CALL;

    /**
     * The statement terminator ({@code ;}).  Depending on the
     * context, this make occur as a sibling, a child, or not at all.
     *
     * <p>For example:</p>
     * {@snippet :
     * for(;;);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |--RPAREN -> )
     *  `--EMPTY_STAT -> ;
     * }
     *
     * @see #PACKAGE_DEF
     * @see #IMPORT
     * @see #SLIST
     * @see #ARRAY_INIT
     * @see #LITERAL_FOR
     */
    public static final int SEMI = JavaLanguageLexer.SEMI;

    /**
     * The {@code ]} symbol.
     *
     * <p>For example:</p>
     * {@snippet :
     * int a[];
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--LITERAL_INT -> int
     *  |   `--ARRAY_DECLARATOR -> [
     *  |       `--RBRACK -> ]
     *  |--IDENT -> a
     *  `--SEMI -> ;
     * }
     *
     * @see #INDEX_OP
     * @see #ARRAY_DECLARATOR
     */
    public static final int RBRACK = JavaLanguageLexer.RBRACK;
    /**
     * The {@code void} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * void LITERAL_VOID(){}
     * }
     *
     * <p>'void' parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> LITERAL_VOID
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_VOID =
        JavaLanguageLexer.LITERAL_VOID;

    /**
     * The {@code boolean} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public boolean flag;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_BOOLEAN -> boolean
     *  |--IDENT -> flag
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_BOOLEAN =
        JavaLanguageLexer.LITERAL_BOOLEAN;

    /**
     * The {@code byte} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public byte x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_BYTE -> byte
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_BYTE =
        JavaLanguageLexer.LITERAL_BYTE;

    /**
     * The {@code char} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * char a = 'A';
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_CHAR -> char
     *  |--IDENT -> a
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--CHAR_LITERAL -> 'A'
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_CHAR =
        JavaLanguageLexer.LITERAL_CHAR;

    /**
     * The {@code short} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public short x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_SHORT -> short
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_SHORT =
        JavaLanguageLexer.LITERAL_SHORT;

    /**
     * The {@code int} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_INT = JavaLanguageLexer.LITERAL_INT;
    /**
     * The {@code float} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public float x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_FLOAT -> float
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_FLOAT =
        JavaLanguageLexer.LITERAL_FLOAT;

    /**
     * The {@code long} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public long x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_LONG -> long
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_LONG =
        JavaLanguageLexer.LITERAL_LONG;

    /**
     * The {@code double} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public double x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_DOUBLE -> double
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #TYPE
     */
    public static final int LITERAL_DOUBLE =
        JavaLanguageLexer.LITERAL_DOUBLE;

    /**
     * An identifier.  These can be names of types, subpackages,
     * fields, methods, parameters, and local variables.
     *
     * <p>For example:</p>
     * {@snippet :
     * int a = 10;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> a
     *  |   `--ASSIGN -> =
     *  |       `--EXPR -> EXPR
     *  |           `--NUM_INT -> 10
     *  `--SEMI -> ;
     * }
     *
     */
    public static final int IDENT = JavaLanguageLexer.IDENT;
    /**
     * The {@code .} (dot) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * return person.name;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * --LITERAL_RETURN -> return
     *    |--EXPR -> EXPR
     *    |   `--DOT -> .
     *    |       |--IDENT -> person
     *    |       `--IDENT -> name
     *    `--SEMI -> ;
     * }
     *
     * @see FullIdent
     */
    public static final int DOT = JavaLanguageLexer.DOT;
    /**
     * The {@code *} (multiplication or wildcard) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * f = m * a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> f
     * |       `--STAR -> *
     * |           |--IDENT -> m
     * |           `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.5.2">Java
     *     Language Specification, &sect;7.5.2</a>
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.1">Java
     *     Language Specification, &sect;15.17.1</a>
     * @see #EXPR
     * @see #IMPORT
     */
    public static final int STAR = JavaLanguageLexer.STAR;
    /**
     * The {@code private} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * private int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PRIVATE -> private
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_PRIVATE =
        JavaLanguageLexer.LITERAL_PRIVATE;

    /**
     * The {@code public} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_PUBLIC =
        JavaLanguageLexer.LITERAL_PUBLIC;

    /**
     * The {@code protected} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * protected int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PROTECTED -> protected
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_PROTECTED =
        JavaLanguageLexer.LITERAL_PROTECTED;

    /**
     * The {@code static} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * public static int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   |--LITERAL_PUBLIC -> public
     *  |   `--LITERAL_STATIC -> static
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> x
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_STATIC =
        JavaLanguageLexer.LITERAL_STATIC;

    /**
     * The {@code transient} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * transient int a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_TRANSIENT -> transient
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> a
     *  `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_TRANSIENT =
        JavaLanguageLexer.LITERAL_TRANSIENT;

    /**
     * The {@code native} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * native void foo(){}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_NATIVE -> native
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> foo
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_NATIVE =
        JavaLanguageLexer.LITERAL_NATIVE;

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
     */
    public static final int LITERAL_SYNCHRONIZED =
        JavaLanguageLexer.LITERAL_SYNCHRONIZED;

    /**
     * The {@code volatile} keyword. This may be used as a
     * modifier of a field.
     *
     * <p>For example:</p>
     * {@snippet :
     * private volatile int x;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_PRIVATE -> private
     * |   `--LITERAL_VOLATILE -> volatile
     * |--TYPE -> TYPE
     * |   `--LITERAL_INT -> int
     * |--IDENT -> x
     * `--SEMI -> ;
     * }
     *
     * @see #MODIFIERS
     */
    public static final int LITERAL_VOLATILE =
        JavaLanguageLexer.LITERAL_VOLATILE;

    /**
     * The {@code class} keyword.  This element appears both
     * as part of a class declaration, and inline to reference a
     * class object.
     *
     * <p>For example:</p>
     * {@snippet :
     * class Test {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Test
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * <p>For example:</p>
     * {@snippet :
     * int.class
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * EXPR -> EXPR
     *  `--DOT -> .
     *      |--LITERAL_INT -> int
     *      `--LITERAL_CLASS -> class
     * }
     *
     * @see #DOT
     * @see #IDENT
     * @see #CLASS_DEF
     * @see FullIdent
     */
    public static final int LITERAL_CLASS =
        JavaLanguageLexer.LITERAL_CLASS;

    /**
     * The {@code interface} keyword. This token appears in
     * interface definition.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * public interface MyInterface {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * INTERFACE_DEF -> INTERFACE_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_INTERFACE -> interface
     * |--IDENT -> MyInterface
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see #INTERFACE_DEF
     */
    public static final int LITERAL_INTERFACE =
        JavaLanguageLexer.LITERAL_INTERFACE;

    /**
     * A left curly brace (<code>{</code>).
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * class App {
     *   int num;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |--LITERAL_CLASS -> class
     * |--IDENT -> App
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     |--VARIABLE_DEF -> VARIABLE_DEF
     *     |   |--MODIFIERS -> MODIFIERS
     *     |   |--TYPE -> TYPE
     *     |   |   `--LITERAL_INT -> int
     *     |   |--IDENT -> num
     *     |   `--SEMI -> ;
     *     `--RCURLY -> }
     * }
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     */
    public static final int LCURLY = JavaLanguageLexer.LCURLY;
    /**
     * A right curly brace (<code>}</code>).
     *
     * <p>For example:</p>
     * {@snippet :
     * void foo(){}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> foo
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see #SLIST
     */
    public static final int RCURLY = JavaLanguageLexer.RCURLY;

    /**
     * The {@code ,} (comma) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * int a, b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_INT -> int
     * |   `--IDENT -> a
     * |--COMMA -> ,
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_INT -> int
     * |   `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see #ARRAY_INIT
     * @see #FOR_INIT
     * @see #FOR_ITERATOR
     * @see #LITERAL_THROWS
     * @see #IMPLEMENTS_CLAUSE
     */
    public static final int COMMA = JavaLanguageLexer.COMMA;

    /**
     * A left parenthesis ({@code (}).
     *
     * <p>For example:</p>
     * {@snippet :
     * Integer val = new Integer();
     * while (false) {
     *     val += (-3);
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     *  |--VARIABLE_DEF -> VARIABLE_DEF
     *  |   |--MODIFIERS -> MODIFIERS
     *  |   |--TYPE -> TYPE
     *  |   |   `--IDENT -> Integer
     *  |   |--IDENT -> val
     *  |   `--ASSIGN -> =
     *  |       `--EXPR -> EXPR
     *  |           `--LITERAL_NEW -> new
     *  |               |--IDENT -> Integer
     *  |               |--LPAREN -> (
     *  |               |--ELIST -> ELIST
     *  |               `--RPAREN -> )
     *  |--SEMI -> ;
     *  |--LITERAL_WHILE -> while
     *  |   |--LPAREN -> (
     *  |   |--EXPR -> EXPR
     *  |   |   `--LITERAL_FALSE -> false
     *  |   |--RPAREN -> )
     *  |   `--SLIST -> {
     *  |       |--EXPR -> EXPR
     *  |       |   `--PLUS_ASSIGN -> +=
     *  |       |       |--IDENT -> val
     *  |       |       |--LPAREN -> (
     *  |       |       |--UNARY_MINUS -> -
     *  |       |       |   `--NUM_INT -> 3
     *  |       |       `--RPAREN -> )
     *  |       |--SEMI -> ;
     *  |       `--RCURLY -> }
     * }
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     */
    public static final int LPAREN = JavaLanguageLexer.LPAREN;
    /**
     * A right parenthesis ({@code )}).
     *
     * <p>For example:</p>
     * {@snippet :
     * void check() {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> check
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #LITERAL_FOR
     * @see #LITERAL_NEW
     * @see #METHOD_CALL
     * @see #TYPECAST
     * @see #EXPR
     * @see #LITERAL_SWITCH
     * @see #LITERAL_CATCH
     */
    public static final int RPAREN = JavaLanguageLexer.RPAREN;
    /**
     * The {@code this} keyword use to refer the current object.
     * This can also be used to call the constructor.
     *
     * <p>For example:</p>
     * {@snippet :
     * this.name = name;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * EXPR -> EXPR
     *  `--ASSIGN -> =
     *      |--DOT -> .
     *      |   |--LITERAL_THIS -> this
     *      |   `--IDENT -> name
     *      `--IDENT -> name
     * SEMI -> ;
     * }
     *
     * <p>Also consider:</p>
     * {@snippet :
     * this(1, "NULL");
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CTOR_CALL -> this
     *  |--LPAREN -> (
     *  |--ELIST -> ELIST
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 1
     *  |   |--COMMA -> ,
     *  |   `--EXPR -> EXPR
     *  |       `--STRING_LITERAL -> "NULL"
     *  |--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * @see #EXPR
     * @see #CTOR_CALL
     */
    public static final int LITERAL_THIS =
        JavaLanguageLexer.LITERAL_THIS;

    /**
     * The {@code super} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * super.toString()；
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--METHOD_CALL -> (
     * |       |--DOT -> .
     * |       |  |--LITERAL_SUPER -> super
     * |       |  `--IDENT -> toString
     * |       |--ELIST -> ELIST
     * |       `--RPAREN -> )
     * |--SEMI -> ;
     * }
     *
     * @see #EXPR
     * @see #SUPER_CTOR_CALL
     */
    public static final int LITERAL_SUPER =
        JavaLanguageLexer.LITERAL_SUPER;

    /**
     * The {@code =} (assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.1">Java
     *     Language Specification, &sect;15.26.1</a>
     * @see #EXPR
     */
    public static final int ASSIGN = JavaLanguageLexer.ASSIGN;
    /**
     * The {@code throws} keyword.  The children are a number of
     * one or more identifiers separated by commas.
     *
     * <p>For example:</p>
     * {@snippet :
     * void test() throws FileNotFoundException, EOFException {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_VOID -> void
     *  |--IDENT -> test
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |--RPAREN -> )
     *  |--LITERAL_THROWS -> throws
     *  |   |--IDENT -> FileNotFoundException
     *  |   |--COMMA -> ,
     *  |   `--IDENT -> EOFException
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.4">Java
     *     Language Specification, &sect;8.4.4</a>
     * @see #IDENT
     * @see #DOT
     * @see #COMMA
     * @see #METHOD_DEF
     * @see #CTOR_DEF
     * @see FullIdent
     */
    public static final int LITERAL_THROWS =
        JavaLanguageLexer.LITERAL_THROWS;

    /**
     * The {@code :} (colon) operator.  This will appear as part
     * of the conditional operator ({@code ? :}).
     *
     * <p>For example:</p>
     * {@snippet :
     * num = isValid ? 1 : 0;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> num
     * |       `--QUESTION -> ?
     * |           |--IDENT -> isValid
     * |           |--NUM_INT -> 1
     * |           |--COLON -> :
     * |           `--NUM_INT -> 0
     * |--SEMI -> ;
     * }
     *
     * @see #QUESTION
     * @see #LABELED_STAT
     * @see #CASE_GROUP
     */
    public static final int COLON = JavaLanguageLexer.COLON;

    /**
     * The {@code ::} (double colon) separator.
     * It is part of Java 8 syntax that is used for method reference.
     * The token does not appear in tree, {@link #METHOD_REF} should be used instead.
     *
     * <p>For example:</p>
     * {@snippet :
     * Function<Double, Double> square = MyClass::square;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> Function
     *  |   |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |   |       |--GENERIC_START -> <
     *  |   |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |   |       |   `--IDENT -> Double
     *  |   |       |--COMMA -> ,
     *  |   |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |   |       |   `--IDENT -> Double
     *  |   |       `--GENERIC_END -> >
     *  |   |--IDENT -> square
     *  |   |--ASSIGN -> =
     *  |   |   `--EXPR -> EXPR
     *  |   |       `--METHOD_REF -> ::
     *  |   |           |--IDENT -> MyClass
     *  |   |           `--IDENT -> square
     *  |   `--SEMI -> ;
     * }
     *
     * @see #METHOD_REF
     */
    public static final int DOUBLE_COLON = JavaLanguageLexer.DOUBLE_COLON;
    /**
     * The {@code if} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (optimistic)
     * {
     *   message = "half full";
     * }
     * else
     * {
     *   message = "half empty";
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--IDENT -> optimistic
     *  |--RPAREN -> )
     *  |--SLIST -> {
     *  |   |--EXPR -> EXPR
     *  |   |   `--ASSIGN -> =
     *  |   |       |--IDENT -> message
     *  |   |       `--STRING_LITERAL -> "half full"
     *  |   |--SEMI -> ;
     *  |   `--RCURLY -> }
     *  `--LITERAL_ELSE -> else
     *      `--SLIST -> {
     *          |--EXPR -> EXPR
     *          |   `--ASSIGN -> =
     *          |       |--IDENT -> message
     *          |       `--STRING_LITERAL -> "half empty"
     *          |--SEMI -> ;
     *          `--RCURLY -> }
     * }
     *
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #SLIST
     * @see #EMPTY_STAT
     * @see #LITERAL_ELSE
     */
    public static final int LITERAL_IF = JavaLanguageLexer.LITERAL_IF;
    /**
     * The {@code for} keyword.  The children are {@code (},
     * an initializer, a condition, an iterator, a {@code )} and
     * either a statement list, a single expression, or an empty
     * statement.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (int i = 0; i < arr.length; i++) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |   `--VARIABLE_DEF -> VARIABLE_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       |--IDENT -> i
     *  |       `--ASSIGN -> =
     *  |           `--EXPR -> EXPR
     *  |               `--NUM_INT -> 0
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |   `--EXPR -> EXPR
     *  |       `--LT -> <
     *  |           |--IDENT -> i
     *  |           `--DOT -> .
     *  |               |--IDENT -> arr
     *  |               `--IDENT -> length
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |   `--ELIST -> ELIST
     *  |       `--EXPR -> EXPR
     *  |           `--POST_INC -> ++
     *  |               `--IDENT -> i
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
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
     */
    public static final int LITERAL_FOR = JavaLanguageLexer.LITERAL_FOR;
    /**
     * The {@code while} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * while (i < 5) {
     *     i++;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_WHILE -> while
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LT -> <
     *  |       |--IDENT -> i
     *  |       `--NUM_INT -> 5
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--EXPR -> EXPR
     *      |   `--POST_INC -> ++
     *      |       `--IDENT -> i
     *      |--SEMI -> ;
     *      `--RCURLY -> }
     * }
     */
    public static final int LITERAL_WHILE =
        JavaLanguageLexer.LITERAL_WHILE;

    /**
     * The {@code do} keyword.  Note that the while token does not
     * appear as part of the do-while construct.
     *
     * <p>For example:</p>
     * {@snippet :
     * do {
     *   x = rand.nextInt();
     * } while (x < 5);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_DO -> do
     *  |--SLIST -> {
     *  |   |--EXPR -> EXPR
     *  |   |   `--ASSIGN -> =
     *  |   |       |--IDENT -> x
     *  |   |       `--METHOD_CALL -> (
     *  |   |           |--DOT -> .
     *  |   |           |   |--IDENT -> rand
     *  |   |           |   `--IDENT -> nextInt
     *  |   |           |--ELIST -> ELIST
     *  |   |           `--RPAREN -> )
     *  |   |--SEMI -> ;
     *  |   `--RCURLY -> }
     *  |--DO_WHILE -> while
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LT -> <
     *  |       |--IDENT -> x
     *  |       `--NUM_INT -> 5
     *  |--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LPAREN
     * @see #RPAREN
     * @see #SEMI
     */
    public static final int LITERAL_DO = JavaLanguageLexer.LITERAL_DO;
    /**
     * Literal {@code while} in do-while loop.
     *
     * <p>For example:</p>
     * {@snippet :
     * do {
     *
     * } while (a > 0);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * --LITERAL_DO -> do
     *    |--SLIST -> {
     *    |   `--RCURLY -> }
     *    |--DO_WHILE -> while
     *    |--LPAREN -> (
     *    |--EXPR -> EXPR
     *    |   `--GT -> >
     *    |       |--IDENT -> a
     *    |       `--NUM_INT -> 0
     *    |--RPAREN -> )
     *    `--SEMI -> ;
     * }
     *
     * @see #LITERAL_DO
     */
    public static final int DO_WHILE = JavaLanguageLexer.DO_WHILE;
    /**
     * The {@code break} keyword.  The first child is an optional
     * identifier and the last child is a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (;;) {
     *     break;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--LITERAL_BREAK -> break
     *      |   `--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     */
    public static final int LITERAL_BREAK =
        JavaLanguageLexer.LITERAL_BREAK;

    /**
     * The {@code continue} keyword.  The first child is an
     * optional identifier and the last child is a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (;;) {
     *     continue;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_INIT -> FOR_INIT
     *  |--SEMI -> ;
     *  |--FOR_CONDITION -> FOR_CONDITION
     *  |--SEMI -> ;
     *  |--FOR_ITERATOR -> FOR_ITERATOR
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--LITERAL_CONTINUE -> continue
     *      |   `--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see #IDENT
     * @see #SEMI
     * @see #SLIST
     */
    public static final int LITERAL_CONTINUE =
        JavaLanguageLexer.LITERAL_CONTINUE;

    /**
     * The {@code return} keyword.  The first child is an
     * optional expression for the return value.  The last child is a
     * semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * public int foo(int i) {
     *     return i+1;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_INT -> int
     *  |--IDENT -> foo
     *  |--LPAREN -> (
     *  |--PARAMETERS -> PARAMETERS
     *  |   `--PARAMETER_DEF -> PARAMETER_DEF
     *  |       |--MODIFIERS -> MODIFIERS
     *  |       |--TYPE -> TYPE
     *  |       |   `--LITERAL_INT -> int
     *  |       `--IDENT -> i
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--LITERAL_RETURN -> return
     *      |   |--EXPR -> EXPR
     *      |   |   `--PLUS -> +
     *      |   |       |--IDENT -> i
     *      |   |       `--NUM_INT -> 1
     *      |   `--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see #EXPR
     * @see #SEMI
     * @see #SLIST
     */
    public static final int LITERAL_RETURN =
        JavaLanguageLexer.LITERAL_RETURN;

    /**
     * The {@code switch} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
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
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_SWITCH -> switch
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--IDENT -> type
     *  |--RPAREN -> )
     *  |--LCURLY -> {
     *  |--CASE_GROUP -> CASE_GROUP
     *  |   |--LITERAL_CASE -> case
     *  |   |   |--EXPR -> EXPR
     *  |   |   |   `--NUM_INT -> 0
     *  |   |   `--COLON -> :
     *  |   `--SLIST -> SLIST
     *  |       |--EXPR -> EXPR
     *  |       |   `--ASSIGN -> =
     *  |       |       |--IDENT -> background
     *  |       |       `--DOT -> .
     *  |       |           |--IDENT -> Color
     *  |       |           `--IDENT -> red
     *  |       |--SEMI -> ;
     *  |       `--LITERAL_BREAK -> break
     *  |           `--SEMI -> ;
     *  |--CASE_GROUP -> CASE_GROUP
     *  |   |--LITERAL_CASE -> case
     *  |   |   |--EXPR -> EXPR
     *  |   |   |   `--NUM_INT -> 1
     *  |   |   `--COLON -> :
     *  |   `--SLIST -> SLIST
     *  |       |--EXPR -> EXPR
     *  |       |   `--ASSIGN -> =
     *  |       |       |--IDENT -> background
     *  |       |       `--DOT -> .
     *  |       |           |--IDENT -> Color
     *  |       |           `--IDENT -> blue
     *  |       |--SEMI -> ;
     *  |       `--LITERAL_BREAK -> break
     *  |           `--SEMI -> ;
     *  |--CASE_GROUP -> CASE_GROUP
     *  |   |--LITERAL_DEFAULT -> default
     *  |   |   `--COLON -> :
     *  |   `--SLIST -> SLIST
     *  |       |--EXPR -> EXPR
     *  |       |   `--ASSIGN -> =
     *  |       |       |--IDENT -> background
     *  |       |       `--DOT -> .
     *  |       |           |--IDENT -> Color
     *  |       |           `--IDENT -> green
     *  |       `--SEMI -> ;
     *  `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.10">Java
     *     Language Specification, &sect;14.10</a>
     * @see #LPAREN
     * @see #EXPR
     * @see #RPAREN
     * @see #LCURLY
     * @see #CASE_GROUP
     * @see #RCURLY
     * @see #SLIST
     * @see #SWITCH_RULE
     */
    public static final int LITERAL_SWITCH =
        JavaLanguageLexer.LITERAL_SWITCH;

    /**
     * The {@code throw} keyword.  The first child is an
     * expression that evaluates to a {@code Throwable} instance.
     *
     * <p>For example:</p>
     * {@snippet :
     * throw new ArithmeticException("An exception occurred.");
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_THROW -> throw
     *  |--EXPR -> EXPR
     *  |   `--LITERAL_NEW -> new
     *  |       |--IDENT -> ArithmeticException
     *  |       |--LPAREN -> (
     *  |       |--ELIST -> ELIST
     *  |       |   `--EXPR -> EXPR
     *  |       |       `--STRING_LITERAL -> "An exception occurred."
     *  |       `--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.17">Java
     *     Language Specification, &sect;14.17</a>
     * @see #SLIST
     * @see #EXPR
     */
    public static final int LITERAL_THROW =
        JavaLanguageLexer.LITERAL_THROW;

    /**
     * The {@code else} keyword.  This appears as a child of an
     * {@code if} statement.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (flag) {
     *
     * } else {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--IDENT -> flag
     *  |--RPAREN -> )
     *  |--SLIST -> {
     *  |   `--RCURLY -> }
     *  `--LITERAL_ELSE -> else
     *      `--SLIST -> {
     *          `--RCURLY -> }
     * }
     *
     * @see #SLIST
     * @see #EXPR
     * @see #EMPTY_STAT
     * @see #LITERAL_IF
     */
    public static final int LITERAL_ELSE =
        JavaLanguageLexer.LITERAL_ELSE;

    /**
     * The {@code case} keyword.  The first child is a constant
     * expression that evaluates to an integer.
     *
     * <p>For example:</p>
     * {@snippet :
     * switch(num){
     *    case 0:
     *      num = 1;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     *
     * CASE_GROUP -> CASE_GROUP
     *    |--LITERAL_CASE -> cas
     *    |   |--EXPR -> EXPR
     *    |   |   `--NUM_INT -> 0
     *    |   `--COLON -> :
     *    `--SLIST -> SLIST
     *         |--EXPR -> EXPR
     *         |   `--ASSIGN -> =
     *         |       |--IDENT -> num
     *         |       `--NUM_INT -> 1
     *         `--SEMI -> ;
     * }
     *
     * <p>For example:</p>
     * {@snippet :
     * switch(num){
     *    case 1 -> num = -1
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * SWITCH_RULE -> SWITCH_RULE
     *   |--LITERAL_CASE -> case
     *   |   `--EXPR -> EXPR
     *   |       `--NUM_INT -> 1
     *   |--LAMBDA -> ->
     *   |--EXPR -> EXPR
     *   |   `--ASSIGN -> =
     *   |       |--IDENT -> num
     *   |       `--UNARY_MINUS -> -
     *   |           `--NUM_INT -> 1
     *   `--SEMI -> ;
     * }
     *
     * @see #CASE_GROUP
     * @see #EXPR
     */
    public static final int LITERAL_CASE =
        JavaLanguageLexer.LITERAL_CASE;

    /**
     * The {@code default} keyword.  This element has no
     * children.
     *
     * <p>For example:</p>
     * {@snippet :
     * switch (type) {
     *   case 1:
     *     x = 1;
     *     break;
     *   default:
     *     x = 3;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_SWITCH -> switch
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--IDENT -> type
     *  |--RPAREN -> )
     *  |--LCURLY -> {
     *  |--CASE_GROUP -> CASE_GROUP
     *  |   |--LITERAL_CASE -> case
     *  |   |   |--EXPR -> EXPR
     *  |   |   |   `--NUM_INT -> 1
     *  |   |   `--COLON -> :
     *  |   `--SLIST -> SLIST
     *  |       |--EXPR -> EXPR
     *  |       |   `--ASSIGN -> =
     *  |       |       |--IDENT -> x
     *  |       |       `--NUM_INT -> 1
     *  |       |   |       |--SEMI -> ;
     *  |       `--LITERAL_BREAK -> break
     *  |           `--SEMI -> ;
     *  |--CASE_GROUP -> CASE_GROUP
     *  |   |--LITERAL_DEFAULT -> default
     *  |   |   `--COLON -> :
     *  |   `--SLIST -> SLIST
     *  |       |--EXPR -> EXPR
     *  |       |   `--ASSIGN -> =
     *  |       |       |--IDENT -> x
     *  |       |       `--NUM_INT -> 3
     *  |       `--SEMI -> ;
     *  `--RCURLY -> }
     * }
     *
     * @see #CASE_GROUP
     * @see #MODIFIERS
     * @see #SWITCH_RULE
     */
    public static final int LITERAL_DEFAULT =
        JavaLanguageLexer.LITERAL_DEFAULT;

    /**
     * The {@code try} keyword.  The children are a statement
     * list, zero or more catch blocks and then an optional finally
     * block.
     *
     * <p>For example:</p>
     * {@snippet :
     * try { } finally {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--SLIST -> {
     *  |   `--RCURLY -> }
     *  `--LITERAL_FINALLY -> finally
     *      `--SLIST -> {
     *          `--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.19">Java
     *     Language Specification, &sect;14.19</a>
     * @see #SLIST
     * @see #LITERAL_CATCH
     * @see #LITERAL_FINALLY
     */
    public static final int LITERAL_TRY = JavaLanguageLexer.LITERAL_TRY;

    /**
     * The Java 7 try-with-resources construct.
     *
     * <p>For example:</p>
     * {@snippet :
     * try (Foo foo = new Foo(); Bar bar = new Bar()) {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--RESOURCE_SPECIFICATION -> RESOURCE_SPECIFICATION
     *  |   |--LPAREN -> (
     *  |   |--RESOURCES -> RESOURCES
     *  |   |   |--RESOURCE -> RESOURCE
     *  |   |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |   |--TYPE -> TYPE
     *  |   |   |   |   `--IDENT -> Foo
     *  |   |   |   |--IDENT -> foo
     *  |   |   |   `--ASSIGN -> =
     *  |   |   |       `--EXPR -> EXPR
     *  |   |   |           `--LITERAL_NEW -> new
     *  |   |   |               |--IDENT -> Foo
     *  |   |   |               |--LPAREN -> (
     *  |   |   |               |--ELIST -> ELIST
     *  |   |   |               `--RPAREN -> )
     *  |   |   |--SEMI -> ;
     *  |   |   `--RESOURCE -> RESOURCE
     *  |   |       |--MODIFIERS -> MODIFIERS
     *  |   |       |--TYPE -> TYPE
     *  |   |       |   `--IDENT -> Bar
     *  |   |       |--IDENT -> bar
     *  |   |       `--ASSIGN -> =
     *  |   |           `--EXPR -> EXPR
     *  |   |               `--LITERAL_NEW -> new
     *  |   |                   |--IDENT -> Bar
     *  |   |                   |--LPAREN -> (
     *  |   |                   |--ELIST -> ELIST
     *  |   |                   `--RPAREN -> )
     *  |   `--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * <p>Also consider:</p>
     * {@snippet :
     * try (BufferedReader br = new BufferedReader(new FileReader(path))) {
     * }
     * }
     *
     * <p>which parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--RESOURCE_SPECIFICATION -> RESOURCE_SPECIFICATION
     *  |   |--LPAREN -> (
     *  |   |--RESOURCES -> RESOURCES
     *  |   |   `--RESOURCE -> RESOURCE
     *  |   |       |--MODIFIERS -> MODIFIERS
     *  |   |       |--TYPE -> TYPE
     *  |   |       |   `--IDENT -> BufferedReader
     *  |   |       |--IDENT -> br
     *  |   |       `--ASSIGN -> =
     *  |   |           `--EXPR -> EXPR
     *  |   |               `--LITERAL_NEW -> new
     *  |   |                   |--IDENT -> BufferedReader
     *  |   |                   |--LPAREN -> (
     *  |   |                   |--ELIST -> ELIST
     *  |   |                   |   `--EXPR -> EXPR
     *  |   |                   |       `--LITERAL_NEW -> new
     *  |   |                   |           |--IDENT -> FileReader
     *  |   |                   |           |--LPAREN -> (
     *  |   |                   |           |--ELIST -> ELIST
     *  |   |                   |           |   `--EXPR -> EXPR
     *  |   |                   |           |       `--IDENT -> path
     *  |   |                   |           `--RPAREN -> )
     *  |   |                   `--RPAREN -> )
     *  |   `--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #LPAREN
     * @see #RESOURCES
     * @see #RESOURCE
     * @see #SEMI
     * @see #RPAREN
     * @see #LITERAL_TRY
     */
    public static final int RESOURCE_SPECIFICATION =
        JavaLanguageLexer.RESOURCE_SPECIFICATION;

    /**
     * A list of resources in the Java 7 try-with-resources construct.
     * This is a child of RESOURCE_SPECIFICATION.
     *
     * <p>For example:</p>
     * {@snippet :
     *     try (FileReader fr = new FileReader("config.xml")) {
     *     } finally {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--RESOURCE_SPECIFICATION -> RESOURCE_SPECIFICATION
     *  |   |--LPAREN -> (
     *  |   |--RESOURCES -> RESOURCES
     *  |   |   `--RESOURCE -> RESOURCE
     *  |   |       |--MODIFIERS -> MODIFIERS
     *  |   |       |--TYPE -> TYPE
     *  |   |       |   `--IDENT -> FileReader
     *  |   |       |--IDENT -> fr
     *  |   |       `--ASSIGN -> =
     *  |   |           `--EXPR -> EXPR
     *  |   |               `--LITERAL_NEW -> new
     *  |   |                   |--IDENT -> FileReader
     *  |   |                   |--LPAREN -> (
     *  |   |                   |--ELIST -> ELIST
     *  |   |                   |   `--EXPR -> EXPR
     *  |   |                   |       `--STRING_LITERAL -> "config.xml"
     *  |   |                   `--RPAREN -> )
     *  |   `--RPAREN -> )
     *  |--SLIST -> {
     *  |   `--RCURLY -> }
     *  `--LITERAL_FINALLY -> finally
     *      `--SLIST -> {
     *          `--RCURLY -> }
     * }
     *
     * @see #RESOURCE_SPECIFICATION
     */
    public static final int RESOURCES =
        JavaLanguageLexer.RESOURCES;

    /**
     * A resource in the Java 7 try-with-resources construct.
     * This is a child of RESOURCES.
     *
     * <p>For example:</p>
     * {@snippet :
     * try (Foo foo = new Foo(); Bar bar = new Bar()) { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--RESOURCE_SPECIFICATION -> RESOURCE_SPECIFICATION
     *  |   |--LPAREN -> (
     *  |   |--RESOURCES -> RESOURCES
     *  |   |   |--RESOURCE -> RESOURCE
     *  |   |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |   |--TYPE -> TYPE
     *  |   |   |   |   `--IDENT -> Foo
     *  |   |   |   |--IDENT -> foo
     *  |   |   |   `--ASSIGN -> =
     *  |   |   |       `--EXPR -> EXPR
     *  |   |   |           `--LITERAL_NEW -> new
     *  |   |   |               |--IDENT -> Foo
     *  |   |   |               |--LPAREN -> (
     *  |   |   |               |--ELIST -> ELIST
     *  |   |   |               `--RPAREN -> )
     *  |   |   |--SEMI -> ;
     *  |   |   `--RESOURCE -> RESOURCE
     *  |   |       |--MODIFIERS -> MODIFIERS
     *  |   |       |--TYPE -> TYPE
     *  |   |       |   `--IDENT -> Bar
     *  |   |       |--IDENT -> bar
     *  |   |       `--ASSIGN -> =
     *  |   |           `--EXPR -> EXPR
     *  |   |               `--LITERAL_NEW -> new
     *  |   |                   |--IDENT -> Bar
     *  |   |                   |--LPAREN -> (
     *  |   |                   |--ELIST -> ELIST
     *  |   |                   `--RPAREN -> )
     *  |   `--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #RESOURCES
     * @see #RESOURCE_SPECIFICATION
     */
    public static final int RESOURCE =
        JavaLanguageLexer.RESOURCE;

    /**
     * The {@code catch} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * try {
     *     FileReader fr = new FileReader("Test.txt");
     * } catch (FileNotFoundException e) {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--SLIST -> {
     *  |   |--VARIABLE_DEF -> VARIABLE_DEF
     *  |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--IDENT -> FileReader
     *  |   |   |--IDENT -> fr
     *  |   |   `--ASSIGN -> =
     *  |   |       `--EXPR -> EXPR
     *  |   |           `--LITERAL_NEW -> new
     *  |   |               |--IDENT -> FileReader
     *  |   |               |--LPAREN -> (
     *  |   |               |--ELIST -> ELIST
     *  |   |               |   `--EXPR -> EXPR
     *  |   |               |       `--STRING_LITERAL -> "Test.txt"
     *  |   |               `--RPAREN -> )
     *  |   |--SEMI -> ;
     *  |   `--RCURLY -> }
     *  `--LITERAL_CATCH -> catch
     *      |--LPAREN -> (
     *      |--PARAMETER_DEF -> PARAMETER_DEF
     *      |   |--MODIFIERS -> MODIFIERS
     *      |   |--TYPE -> TYPE
     *      |   |   `--IDENT -> FileNotFoundException
     *      |   `--IDENT -> e
     *      |--RPAREN -> )
     *      `--SLIST -> {
     *          `--RCURLY -> }
     * }
     *
     * @see #LPAREN
     * @see #PARAMETER_DEF
     * @see #RPAREN
     * @see #SLIST
     * @see #LITERAL_TRY
     */
    public static final int LITERAL_CATCH =
        JavaLanguageLexer.LITERAL_CATCH;

    /**
     * The {@code finally} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * try {} finally {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_TRY -> try
     *  |--SLIST -> {
     *  |   `--RCURLY -> }
     *  `--LITERAL_FINALLY -> finally
     *      `--SLIST -> {
     *          `--RCURLY -> }
     * }
     *
     * @see #SLIST
     * @see #LITERAL_TRY
     */
    public static final int LITERAL_FINALLY =
        JavaLanguageLexer.LITERAL_FINALLY;

    /**
     * The {@code +=} (addition assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a += b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--PLUS_ASSIGN -> +=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int PLUS_ASSIGN = JavaLanguageLexer.PLUS_ASSIGN;
    /**
     * The {@code -=} (subtraction assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a -= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--MINUS_ASSIGN -> -=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int MINUS_ASSIGN =
        JavaLanguageLexer.MINUS_ASSIGN;

    /**
     * The {@code *=} (multiplication assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a *= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--STAR_ASSIGN -> *=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int STAR_ASSIGN = JavaLanguageLexer.STAR_ASSIGN;
    /**
     * The {@code /=} (division assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a /= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--DIV_ASSIGN -> /=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int DIV_ASSIGN = JavaLanguageLexer.DIV_ASSIGN;
    /**
     * The {@code %=} (remainder assignment) operator.
     *
     * <p>For example: {@code a %= 2;}</p>
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--MOD_ASSIGN -> %=
     * |       |--IDENT -> a
     * |       `--NUM_INT -> 2
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int MOD_ASSIGN = JavaLanguageLexer.MOD_ASSIGN;
    /**
     * The {@code >>=} (signed right shift assignment)
     * operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a >>= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--SR_ASSIGN -> >>=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int SR_ASSIGN = JavaLanguageLexer.SR_ASSIGN;
    /**
     * The {@code >>>=} (unsigned right shift assignment)
     * operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a >>>= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--BSR_ASSIGN -> >>>=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int BSR_ASSIGN = JavaLanguageLexer.BSR_ASSIGN;
    /**
     * The {@code <<=} (left shift assignment) operator.
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int SL_ASSIGN = JavaLanguageLexer.SL_ASSIGN;
    /**
     * The {@code &=} (bitwise AND assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a &= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--BAND_ASSIGN -> &=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int BAND_ASSIGN = JavaLanguageLexer.BAND_ASSIGN;
    /**
     * The {@code ^=} (bitwise exclusive OR assignment) operator.
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int BXOR_ASSIGN = JavaLanguageLexer.BXOR_ASSIGN;
    /**
     * The {@code |=} (bitwise OR assignment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a |= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--BOR_ASSIGN -> |=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.26.2">Java
     *     Language Specification, &sect;15.26.2</a>
     * @see #EXPR
     */
    public static final int BOR_ASSIGN = JavaLanguageLexer.BOR_ASSIGN;
    /**
     * The {@code ?} (conditional) operator.  Technically,
     * the colon is also part of this operator, but it appears as a
     * separate token.
     *
     * <p>For example:</p>
     * {@snippet :
     * String variable=(quantity==1)?"true":"false";
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--IDENT -> String
     * |   |--IDENT -> variable
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--QUESTION -> ?
     * |               |--LPAREN -> (
     * |               |--EQUAL -> ==
     * |               |   |--IDENT -> quantity
     * |               |   `--NUM_INT -> 1
     * |               |--RPAREN -> )
     * |               |--STRING_LITERAL -> "true"
     * |               |--COLON -> :
     * |               `--STRING_LITERAL -> "false"
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.25">Java
     *     Language Specification, &sect;15.25</a>
     * @see #EXPR
     * @see #COLON
     */
    public static final int QUESTION = JavaLanguageLexer.QUESTION;
    /**
     * The {@code ||} (conditional OR) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (a || b) {
     * }
     * }
     *
     * <p>
     * parses as:
     * </p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LOR -> ||
     *  |       |--IDENT -> a
     *  |       `--IDENT -> b
     *  |--RPAREN -> )
     *  |--SLIST -> {
     *  |   |--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">Java
     *     Language Specification, &sect;15.24</a>
     * @see #EXPR
     */
    public static final int LOR = JavaLanguageLexer.LOR;
    /**
     * The {@code &&} (conditional AND) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (a && b) {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LAND -> &&
     *  |       |--IDENT -> a
     *  |       `--IDENT -> b
     *  |--RPAREN -> )
     *  |--SLIST -> {
     *  |   |--RCURLY -> }
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">Java
     *     Language Specification, &sect;15.23</a>
     * @see #EXPR
     */
    public static final int LAND = JavaLanguageLexer.LAND;
    /**
     * The {@code |} (bitwise OR) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = a | b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--BOR -> |
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     *     Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     */
    public static final int BOR = JavaLanguageLexer.BOR;
    /**
     * The {@code ^} (bitwise exclusive OR) operator.
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     *     Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     */
    public static final int BXOR = JavaLanguageLexer.BXOR;
    /**
     * The {@code &} (bitwise AND) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a & b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--BAND -> &
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.22.1">Java
     *     Language Specification, &sect;15.22.1</a>
     * @see #EXPR
     */
    public static final int BAND = JavaLanguageLexer.BAND;
    /**
     * The {@code !=} (not equal) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a != b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--NOT_EQUAL -> !=
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * `--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int NOT_EQUAL = JavaLanguageLexer.NOT_EQUAL;
    /**
     * The {@code ==} (equal) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * return a == b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--EQUAL -> ==
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * `--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int EQUAL = JavaLanguageLexer.EQUAL;
    /**
     * The {@code <} (less than) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a < b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--LT -> <
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int LT = JavaLanguageLexer.LT;
    /**
     * The {@code >} (greater than) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a > b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--BAND -> >
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int GT = JavaLanguageLexer.GT;
    /**
     * The {@code <=} (less than or equal) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a <= b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--LE -> <=
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see #EXPR
     */
    public static final int LE = JavaLanguageLexer.LE;
    /**
     * The {@code >=} (greater than or equal) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     *   boolean b = a >= 3;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_BOOLEAN -> boolean
     *  |--IDENT -> b
     *  `--ASSIGN -> =
     *      `--EXPR -> EXPR
     *          `--GE -> >=
     *              |--IDENT -> a
     *              `--NUM_INT -> 3
     * }
     *
     * @see #EXPR
     */
    public static final int GE = JavaLanguageLexer.GE;
    /**
     * The {@code instanceof} operator.  The first child is an
     * object reference or something that evaluates to an object
     * reference.  The second child is a reference type or pattern.
     *
     * <p>For example:</p>
     * {@snippet :
     * boolean isBuilderReferenceType = text instanceof StringBuilder; // reference type
     * boolean isBuilderPatternWithPattern =
     *         text instanceof StringBuilder s; // type pattern, no `PATTERN_DEF`
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_BOOLEAN -> boolean
     * |   |--IDENT -> isBuilderReferenceType
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--LITERAL_INSTANCEOF -> instanceof
     * |               |--IDENT -> text
     * |               `--TYPE -> TYPE
     * |                   `--IDENT -> StringBuilder
     * |--SEMI -> ;
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_BOOLEAN -> boolean
     * |   |--IDENT -> isBuilderPatternWithPattern
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--LITERAL_INSTANCEOF -> instanceof
     * |               |--IDENT -> text
     * |               `--PATTERN_VARIABLE_DEF -> PATTERN_VARIABLE_DEF
     * |                   |--MODIFIERS -> MODIFIERS
     * |                   |--TYPE -> TYPE
     * |                   |   `--IDENT -> StringBuilder
     * |                   `--IDENT -> s
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.20.2">Java
     *     Language Specification, &sect;15.20.2</a>
     * @see #EXPR
     * @see #METHOD_CALL
     * @see #IDENT
     * @see #DOT
     * @see #TYPE
     * @see #PATTERN_VARIABLE_DEF
     * @see FullIdent
     */
    public static final int LITERAL_INSTANCEOF =
        JavaLanguageLexer.LITERAL_INSTANCEOF;

    /**
     * The {@code <<} (shift left) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = a << b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--SR -> <<
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     *     Language Specification, &sect;15.19</a>
     * @see #EXPR
     */
    public static final int SL = JavaLanguageLexer.SL;
    /**
     * The {@code >>} (signed shift right) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = a >> b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--SR -> >>
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     *     Language Specification, &sect;15.19</a>
     * @see #EXPR
     */
    public static final int SR = JavaLanguageLexer.SR;
    /**
     * The {@code >>>} (unsigned shift right) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a >>> b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--BSR -> >>>
     * |       |--IDENT -> a
     * |       `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19">Java
     *     Language Specification, &sect;15.19</a>
     * @see #EXPR
     */
    public static final int BSR = JavaLanguageLexer.BSR;
    /**
     * The {@code +} (addition) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a + b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--PLUS -> +
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     *     Language Specification, &sect;15.18</a>
     * @see #EXPR
     */
    public static final int PLUS = JavaLanguageLexer.PLUS;
    /**
     * The {@code -} (subtraction) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a - b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--MINUS -> -
     * |           |--IDENT -> a
     * |           `--IDENT -> b
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18">Java
     *     Language Specification, &sect;15.18</a>
     * @see #EXPR
     */
    public static final int MINUS = JavaLanguageLexer.MINUS;
    /**
     * The {@code /} (division) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = 4 / 2;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--DIV -> /
     * |           |--NUM_INT -> 4
     * |           `--NUM_INT -> 2
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.2">Java
     *     Language Specification, &sect;15.17.2</a>
     * @see #EXPR
     */
    public static final int DIV = JavaLanguageLexer.DIV;
    /**
     * The {@code %} (remainder) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = a % b;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * EXPR -> EXPR
     *  `--ASSIGN -> =
     *      |--IDENT -> c
     *      `--MOD -> %
     *          |--IDENT -> a
     *          `--IDENT -> b
     * SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.17.3">Java
     *     Language Specification, &sect;15.17.3</a>
     * @see #EXPR
     */
    public static final int MOD = JavaLanguageLexer.MOD;
    /**
     * The {@code ++} (prefix increment) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * ++a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--INC -> ++
     * |       `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.1">Java
     *     Language Specification, &sect;15.15.1</a>
     * @see #EXPR
     * @see #POST_INC
     */
    public static final int INC = JavaLanguageLexer.INC;
    /**
     * The {@code --} (prefix decrement) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * --a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--DEC -> --
     * |       `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.2">Java
     *     Language Specification, &sect;15.15.2</a>
     * @see #EXPR
     * @see #POST_DEC
     */
    public static final int DEC = JavaLanguageLexer.DEC;
    /**
     * The {@code ~} (bitwise complement) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = ~ a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--BNOT -> ~
     * |           `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.5">Java
     *     Language Specification, &sect;15.15.5</a>
     * @see #EXPR
     */
    public static final int BNOT = JavaLanguageLexer.BNOT;
    /**
     * The {@code !} (logical complement) operator.
     *
     * <p>For example:</p>
     * {@snippet :
     * c = ! a;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> c
     * |       `--LNOT -> !
     * |           `--IDENT -> a
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.6">Java
     *     Language Specification, &sect;15.15.6</a>
     * @see #EXPR
     */
    public static final int LNOT = JavaLanguageLexer.LNOT;
    /**
     * The {@code true} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * boolean a = true;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_BOOLEAN -> boolean
     * |   |--IDENT -> a
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--LITERAL_TRUE -> true
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     *     Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_FALSE
     */
    public static final int LITERAL_TRUE =
        JavaLanguageLexer.LITERAL_TRUE;

    /**
     * The {@code false} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * boolean a = false;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--LITERAL_BOOLEAN -> boolean
     *  |--IDENT -> a
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_FALSE -> false
     *  `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.3">Java
     *     Language Specification, &sect;3.10.3</a>
     * @see #EXPR
     * @see #LITERAL_TRUE
     */
    public static final int LITERAL_FALSE =
        JavaLanguageLexer.LITERAL_FALSE;

    /**
     * The {@code null} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * String s = null;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--IDENT -> String
     *  |--IDENT -> s
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_NULL -> null
     *  `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.7">Java
     *     Language Specification, &sect;3.10.7</a>
     * @see #EXPR
     */
    public static final int LITERAL_NULL =
        JavaLanguageLexer.LITERAL_NULL;

    /**
     * The {@code new} keyword.  This element is used to define
     * new instances of objects, new arrays, and new anonymous inner
     * classes.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * List<String> l = new ArrayList<String>();
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> List
     *  |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |       |--GENERIC_START -> <
     *  |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |       |   `--IDENT -> String
     *  |       `--GENERIC_END -> >
     *  |--IDENT -> l
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_NEW -> new
     *  |           |--IDENT -> ArrayList
     *  |           |--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |           |   |--GENERIC_START -> <
     *  |           |   |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |           |   |   `--IDENT -> String
     *  |           |   `--GENERIC_END -> >
     *  |           |--LPAREN -> (
     *  |           |--ELIST -> ELIST
     *  |           `--RPAREN -> )
     *  `--SEMI -> ;
     * }
     *
     * <p>For example:</p>
     * {@snippet :
     * String[] strings = new String[3];
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> String
     *  |   `--ARRAY_DECLARATOR -> [
     *  |       `--RBRACK -> ]
     *  |--IDENT -> strings
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_NEW -> new
     *  |           |--IDENT -> String
     *  |           `--ARRAY_DECLARATOR -> [
     *  |               |--EXPR -> EXPR
     *  |               |   `--NUM_INT -> 3
     *  |               `--RBRACK -> ]
     *  `--SEMI -> ;
     * }
     *
     * <p>For example:</p>
     * {@snippet :
     * Supplier<Integer> s = new Supplier<>() {
     *     @Override
     *     public Integer get() {
     *         return 42;
     *     }
     * };
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> Supplier
     *  |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |       |--GENERIC_START -> <
     *  |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |       |   `--IDENT -> Integer
     *  |       `--GENERIC_END -> >
     *  |--IDENT -> s
     *  |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--LITERAL_NEW -> new
     *  |           |--IDENT -> Supplier
     *  |           |--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |           |   |--GENERIC_START -> <
     *  |           |   `--GENERIC_END -> >
     *  |           |--LPAREN -> (
     *  |           |--ELIST -> ELIST
     *  |           |--RPAREN -> )
     *  |           `--OBJBLOCK -> OBJBLOCK
     *  |               |--LCURLY -> {
     *  |               |--METHOD_DEF -> METHOD_DEF
     *  |               |   |--MODIFIERS -> MODIFIERS
     *  |               |   |   |--ANNOTATION -> ANNOTATION
     *  |               |   |   |   |--AT -> @
     *  |               |   |   |   `--IDENT -> Override
     *  |               |   |   `--LITERAL_PUBLIC -> public
     *  |               |   |--TYPE -> TYPE
     *  |               |   |   `--IDENT -> Integer
     *  |               |   |--IDENT -> get
     *  |               |   |--LPAREN -> (
     *  |               |   |--PARAMETERS -> PARAMETERS
     *  |               |   |--RPAREN -> )
     *  |               |   `--SLIST -> {
     *  |               |       |--LITERAL_RETURN -> return
     *  |               |       |   |--EXPR -> EXPR
     *  |               |       |   |   `--NUM_INT -> 42
     *  |               |       |   `--SEMI -> ;
     *  |               |       `--RCURLY -> }
     *  |               `--RCURLY -> }
     *  `--SEMI -> ;
     * }
     *
     * @see #IDENT
     * @see #DOT
     * @see #LPAREN
     * @see #ELIST
     * @see #RPAREN
     * @see #OBJBLOCK
     * @see #ARRAY_INIT
     * @see FullIdent
     */
    public static final int LITERAL_NEW = JavaLanguageLexer.LITERAL_NEW;
    /**
     * An integer literal.  These may be specified in decimal,
     * hexadecimal, or octal form.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = 3;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--NUM_INT -> 3
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     *     Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_LONG
     */
    public static final int NUM_INT = JavaLanguageLexer.NUM_INT;
    /**
     * A character literal.  This is a (possibly escaped) character
     * enclosed in single quotes.
     *
     * <p>For example:</p>
     * {@snippet :
     * return 'a';
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * --LITERAL_RETURN -> return
     *    |--EXPR -> EXPR
     *    |   `--CHAR_LITERAL -> 'a'
     *    `--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.4">Java
     *     Language Specification, &sect;3.10.4</a>
     * @see #EXPR
     */
    public static final int CHAR_LITERAL =
        JavaLanguageLexer.CHAR_LITERAL;

    /**
     * A string literal.  This is a sequence of (possibly escaped)
     * characters enclosed in double quotes.
     *
     * <p>For example: {@code String str = "StringLiteral";}</p>
     *
     * <p>parses as:</p>
     * {@snippet :
     *  |--VARIABLE_DEF -> VARIABLE_DEF
     *  |   |--MODIFIERS -> MODIFIERS
     *  |   |--TYPE -> TYPE
     *  |   |   `--IDENT -> String
     *  |   |--IDENT -> str
     *  |   `--ASSIGN -> =
     *  |       `--EXPR -> EXPR
     *  |           `--STRING_LITERAL -> "StringLiteral"
     *  |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.5">Java
     *     Language Specification, &sect;3.10.5</a>
     * @see #EXPR
     */
    public static final int STRING_LITERAL =
        JavaLanguageLexer.STRING_LITERAL;

    /**
     * A single precision floating point literal.  This is a floating
     * point number with an {@code F} or {@code f} suffix.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = 3.14f;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--NUM_FLOAT -> 3.14f
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     *     Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_DOUBLE
     */
    public static final int NUM_FLOAT = JavaLanguageLexer.NUM_FLOAT;
    /**
     * A long integer literal.  These are almost the same as integer
     * literals, but they have an {@code L} or {@code l}
     * (ell) suffix.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = 3l;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--NUM_LONG -> 3l
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">Java
     *     Language Specification, &sect;3.10.1</a>
     * @see #EXPR
     * @see #NUM_INT
     */
    public static final int NUM_LONG = JavaLanguageLexer.NUM_LONG;
    /**
     * A double precision floating point literal.  This is a floating
     * point number with an optional {@code D} or {@code d}
     * suffix.
     *
     * <p>For example:</p>
     * {@snippet :
     * a = 3.14d;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--EXPR -> EXPR
     * |   `--ASSIGN -> =
     * |       |--IDENT -> a
     * |       `--NUM_DOUBLE -> 3.14d
     * |--SEMI -> ;
     * }
     *
     * @see <a
     *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.2">Java
     *     Language Specification, &sect;3.10.2</a>
     * @see #EXPR
     * @see #NUM_FLOAT
     */
    public static final int NUM_DOUBLE = JavaLanguageLexer.NUM_DOUBLE;

    /**
     * The {@code assert} keyword.  This is only for Java 1.4 and
     * later.
     *
     * <p>For example:</p>
     * {@snippet :
     * assert(x==4);
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_ASSERT -> assert
     *  |--EXPR -> EXPR
     *  |   |--LPAREN -> (
     *  |   |--EQUAL -> ==
     *  |   |   |--IDENT -> x
     *  |   |   `--NUM_INT -> 4
     *  |   `--RPAREN -> )
     *  `--SEMI -> ;
     * }
     */
    public static final int LITERAL_ASSERT = JavaLanguageLexer.ASSERT;

    /**
     * A static import declaration.  Static import declarations are optional,
     * but must appear after the package declaration and before the type
     * declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * import static java.io.IOException;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * STATIC_IMPORT -> import
     * |--LITERAL_STATIC -> static
     * |--DOT -> .
     * |   |--DOT -> .
     * |   |   |--IDENT -> java
     * |   |   `--IDENT -> io
     * |   `--IDENT -> IOException
     * `--SEMI -> ;
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #LITERAL_STATIC
     * @see #DOT
     * @see #IDENT
     * @see #STAR
     * @see #SEMI
     * @see FullIdent
     */
    public static final int STATIC_IMPORT =
        JavaLanguageLexer.STATIC_IMPORT;

    /**
     * An enum declaration. Its notable children are
     * enum constant declarations followed by
     * any construct that may be expected in a class body.
     *
     * <p>For example:</p>
     * {@snippet :
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
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ENUM_DEF -> ENUM_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--ENUM -> enum
     *  |--IDENT -> MyEnum
     *  |--IMPLEMENTS_CLAUSE -> implements
     *  |   `--IDENT -> Serializable
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      |--ENUM_CONSTANT_DEF -> ENUM_CONSTANT_DEF
     *      |   |--ANNOTATIONS -> ANNOTATIONS
     *      |   `--IDENT -> FIRST_CONSTANT
     *      |--COMMA -> ,
     *      |--ENUM_CONSTANT_DEF -> ENUM_CONSTANT_DEF
     *      |   |--ANNOTATIONS -> ANNOTATIONS
     *      |   `--IDENT -> SECOND_CONSTANT
     *      |--SEMI -> ;
     *      |--METHOD_DEF -> METHOD_DEF
     *      |   |--MODIFIERS -> MODIFIERS
     *      |   |   `--LITERAL_PUBLIC -> public
     *      |   |--TYPE -> TYPE
     *      |   |   `--LITERAL_VOID -> void
     *      |   |--IDENT -> someMethod
     *      |   |--LPAREN -> (
     *      |   |--PARAMETERS -> PARAMETERS
     *      |   |--RPAREN -> )
     *      |   `--SLIST -> {
     *      |       `--RCURLY -> }
     *      `--RCURLY -> }
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #MODIFIERS
     * @see #ENUM
     * @see #IDENT
     * @see #EXTENDS_CLAUSE
     * @see #IMPLEMENTS_CLAUSE
     * @see #OBJBLOCK
     * @see #LITERAL_NEW
     * @see #ENUM_CONSTANT_DEF
     */
    public static final int ENUM_DEF =
        JavaLanguageLexer.ENUM_DEF;

    /**
     * The {@code enum} keyword.  This element appears
     * as part of an enum declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public enum Count {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ENUM_DEF -> ENUM_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |  `--LITERAL_PUBLIC -> public
     *  |--ENUM -> enum
     *  |--IDENT -> Count
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      `--RCURLY -> }
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">JSR201</a>
     * @see #MODIFIERS
     * @see #ENUM_DEF
     * @see #IDENT
     * @see #OBJBLOCK
     */
    public static final int ENUM =
        JavaLanguageLexer.ENUM;

    /**
     * An enum constant declaration. Its notable children are annotations,
     * arguments and object block akin to an anonymous
     * inner class' body.
     *
     * <p>For example:</p>
     * {@snippet :
     * SOME_CONSTANT(1)
     * {
     *     public void someMethodOverriddenFromMainBody()
     *     {
     *     }
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ENUM_CONSTANT_DEF -> ENUM_CONSTANT_DEF
     *   |   |--ANNOTATIONS -> ANNOTATIONS
     *   |   |--IDENT -> SOME_CONSTANT
     *   |   |--LPAREN -> (
     *   |   |--ELIST -> ELIST
     *   |   |   `--EXPR -> EXPR
     *   |   |       `--NUM_INT -> 1
     *   |   |--RPAREN -> )
     *   |   `--OBJBLOCK -> OBJBLOCK
     *   |       |--LCURLY -> {
     *   |       |--METHOD_DEF -> METHOD_DEF
     *   |       |   |--MODIFIERS -> MODIFIERS
     *   |       |   |   `--LITERAL_PUBLIC -> public
     *   |       |   |--TYPE -> TYPE
     *   |       |   |   `--LITERAL_VOID -> void
     *   |       |   |--IDENT -> someMethodOverriddenFromMainBody
     *   |       |   |--LPAREN -> (
     *   |       |   |--PARAMETERS -> PARAMETERS
     *   |       |   |--RPAREN -> )
     *   |       |   `--SLIST -> {
     *   |       |       `--RCURLY -> }
     *   |       `--RCURLY -> }
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #ANNOTATIONS
     * @see #MODIFIERS
     * @see #IDENT
     * @see #ELIST
     * @see #OBJBLOCK
     */
    public static final int ENUM_CONSTANT_DEF =
        JavaLanguageLexer.ENUM_CONSTANT_DEF;

    /**
     * A for-each clause.  This is a child of
     * {@code LITERAL_FOR}.  The children of this element may be
     * a parameter definition, the colon literal and an expression.
     *
     * <p>For example:</p>
     * {@snippet :
     * for (int value : values) {
     *     doSomething();
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_FOR -> for
     *  |--LPAREN -> (
     *  |--FOR_EACH_CLAUSE -> FOR_EACH_CLAUSE
     *  |   |--VARIABLE_DEF -> VARIABLE_DEF
     *  |   |   |--MODIFIERS -> MODIFIERS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--LITERAL_INT -> int
     *  |   |   `--IDENT -> value
     *  |   |--COLON -> :
     *  |   `--EXPR -> EXPR
     *  |       `--IDENT -> values
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      |--EXPR -> EXPR
     *      |   `--METHOD_CALL -> (
     *      |       |--IDENT -> doSomething
     *      |       |--ELIST -> ELIST
     *      |       `--RPAREN -> )
     *      |--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see #VARIABLE_DEF
     * @see #ELIST
     * @see #LITERAL_FOR
     */
    public static final int FOR_EACH_CLAUSE =
        JavaLanguageLexer.FOR_EACH_CLAUSE;

    /**
     * An annotation declaration. The notable children are the name of the
     * annotation type, annotation field declarations and (constant) fields.
     *
     * <p>For example:</p>
     * {@snippet :
     * public @interface MyAnnotation
     * {
     *     int someValue();
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ANNOTATION_DEF -> ANNOTATION_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--AT -> @
     *  |--LITERAL_INTERFACE -> interface
     *  |--IDENT -> MyAnnotation
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      |--ANNOTATION_FIELD_DEF -> ANNOTATION_FIELD_DEF
     *      |   |--MODIFIERS -> MODIFIERS
     *      |   |--TYPE -> TYPE
     *      |   |   `--LITERAL_INT -> int
     *      |   |--IDENT -> someValue
     *      |   |--LPAREN -> (
     *      |   |--RPAREN -> )
     *      |   `--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #MODIFIERS
     * @see #LITERAL_INTERFACE
     * @see #IDENT
     * @see #OBJBLOCK
     * @see #ANNOTATION_FIELD_DEF
     */
    public static final int ANNOTATION_DEF =
        JavaLanguageLexer.ANNOTATION_DEF;

    /**
     * An annotation field declaration.  The notable children are modifiers,
     * field type, field name and an optional default value (a conditional
     * compile-time constant expression). Default values may also be
     * annotations.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     *     String someField() default "Hello world";
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * ANNOTATION_FIELD_DEF -> ANNOTATION_FIELD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   `--IDENT -> String
     *  |--IDENT -> someField
     *  |--LPAREN -> (
     *  |--RPAREN -> )
     *  |--LITERAL_DEFAULT -> default
     *  |   `--EXPR -> EXPR
     *  |       `--STRING_LITERAL -> "Hello world"
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #MODIFIERS
     * @see #TYPE
     * @see #LITERAL_DEFAULT
     */
    public static final int ANNOTATION_FIELD_DEF =
        JavaLanguageLexer.ANNOTATION_FIELD_DEF;

    /**
     * A collection of annotations on a package or enum constant.
     * A collections of annotations will only occur on these nodes
     * as all other nodes that may be qualified with an annotation can
     * be qualified with any other modifier and hence these annotations
     * would be contained in a {@link #MODIFIERS} node.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     *     @MyAnnotation package blah;
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * PACKAGE_DEF -> package
     *  |--ANNOTATIONS -> ANNOTATIONS
     *  |   `--ANNOTATION -> ANNOTATION
     *  |       |--AT -> @
     *  |       `--IDENT -> MyAnnotation
     *  |--IDENT -> blah
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #ANNOTATION
     * @see #AT
     * @see #IDENT
     */
    public static final int ANNOTATIONS =
        JavaLanguageLexer.ANNOTATIONS;

    /**
     * An annotation of a package, type, field, parameter or variable.
     * An annotation may occur anywhere modifiers occur (it is a
     * type of modifier) and may also occur prior to a package definition.
     * The notable children are: The annotation name and either a single
     * default annotation value or a sequence of name value pairs.
     * Annotation values may also be annotations themselves.
     *
     * <p>For example:</p>
     * {@snippet :
     *     @MyAnnotation(someField1 = "Hello",
     *                    someField2 = @SomeOtherAnnotation)
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ANNOTATION -> ANNOTATION
     *  |--AT -> @
     *  |--IDENT -> MyAnnotation
     *  |--LPAREN -> (
     *  |--ANNOTATION_MEMBER_VALUE_PAIR -> ANNOTATION_MEMBER_VALUE_PAIR
     *  |   |--IDENT -> someField1
     *  |   |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--STRING_LITERAL -> "Hello"
     *  |--COMMA -> ,
     *  |--ANNOTATION_MEMBER_VALUE_PAIR -> ANNOTATION_MEMBER_VALUE_PAIR
     *  |   |--IDENT -> someField2
     *  |   |--ASSIGN -> =
     *  |   `--ANNOTATION -> ANNOTATION
     *  |       |--AT -> @
     *  |       `--IDENT -> SomeOtherAnnotation
     *  `--RPAREN -> )
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #MODIFIERS
     * @see #IDENT
     * @see #ANNOTATION_MEMBER_VALUE_PAIR
     */
    public static final int ANNOTATION =
        JavaLanguageLexer.ANNOTATION;

    /**
     * An initialization of an annotation member with a value.
     * Its children are the name of the member, the assignment literal
     * and the (compile-time constant conditional expression) value.
     *
     * <p>For example:</p>
     * {@snippet :
     * @Annotation(
     *     value="123"
     * )
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ANNOTATION -> ANNOTATION
     *  |--AT -> @
     *  |--IDENT -> Annotation
     *  |--LPAREN -> (
     *  |--ANNOTATION_MEMBER_VALUE_PAIR -> ANNOTATION_MEMBER_VALUE_PAIR
     *  |   |--IDENT -> value
     *  |   |--ASSIGN -> =
     *  |   `--EXPR -> EXPR
     *  |       `--STRING_LITERAL -> "123"
     *  `--RPAREN -> )
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     */
    public static final int ANNOTATION_MEMBER_VALUE_PAIR =
        JavaLanguageLexer.ANNOTATION_MEMBER_VALUE_PAIR;

    /**
     * An annotation array member initialization.
     * Initializers can not be nested.
     * An initializer may be present as a default to an annotation
     * member, as the single default value to an annotation
     * (e.g. @Annotation({1,2})) or as the value of an annotation
     * member value pair.
     *
     * <p>For example:</p>
     * {@snippet :
     * @Annotation({1, 2})
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * ANNOTATION -> ANNOTATION
     *  |--AT -> @
     *  |--IDENT -> Annotation
     *  |--LPAREN -> (
     *  |--ANNOTATION_ARRAY_INIT -> {
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 1
     *  |   |--COMMA -> ,
     *  |   |--EXPR -> EXPR
     *  |   |   `--NUM_INT -> 2
     *  |   `--RCURLY -> }
     *  `--RPAREN -> )
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     * @see #ANNOTATION
     * @see #IDENT
     * @see #ANNOTATION_MEMBER_VALUE_PAIR
     */
    public static final int ANNOTATION_ARRAY_INIT =
        JavaLanguageLexer.ANNOTATION_ARRAY_INIT;

    /**
     * A list of type parameters to a class, interface or
     * method definition. Children are LT, at least one
     * TYPE_PARAMETER, zero or more of: a COMMAs followed by a single
     * TYPE_PARAMETER and a final GT.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * public class MyClass<A, B> {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_CLASS -> class
     * |--IDENT -> MyClass
     * |--TYPE_PARAMETERS -> TYPE_PARAMETERS
     * |   |--GENERIC_START -> <
     * |   |--TYPE_PARAMETER -> TYPE_PARAMETER
     * |   |   `--IDENT -> A
     * |   |--COMMA -> ,
     * |   |--TYPE_PARAMETER -> TYPE_PARAMETER
     * |   |   `--IDENT -> B
     * |   `--GENERIC_END -> >
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #GENERIC_START
     * @see #GENERIC_END
     * @see #TYPE_PARAMETER
     * @see #COMMA
     */
    public static final int TYPE_PARAMETERS =
        JavaLanguageLexer.TYPE_PARAMETERS;

    /**
     * A type parameter to a class, interface or method definition.
     * Children are the type name and an optional TYPE_UPPER_BOUNDS.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     * public class MyClass <A extends Collection> {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_CLASS -> class
     * |--IDENT -> MyClass
     * |--TYPE_PARAMETERS -> TYPE_PARAMETERS
     * |   |--GENERIC_START -> <
     * |   |--TYPE_PARAMETER -> TYPE_PARAMETER
     * |   |   |--IDENT -> A
     * |   |   `--TYPE_UPPER_BOUNDS -> extends
     * |   |       `--IDENT -> Collection
     * |   `--GENERIC_END -> >
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #IDENT
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     */
    public static final int TYPE_PARAMETER =
        JavaLanguageLexer.TYPE_PARAMETER;

    /**
     * A list of type arguments to a type reference or
     * a method/ctor invocation. Children are GENERIC_START, at least one
     * TYPE_ARGUMENT, zero or more of a COMMAs followed by a single
     * TYPE_ARGUMENT, and a final GENERIC_END.
     *
     * <p>For example:</p>
     *
     * {@snippet :
     *     public Collection<?> a;
     * }
     *
     * <p>parses as:</p>
     *
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> Collection
     *  |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |       |--GENERIC_START -> <
     *  |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |       |   `--WILDCARD_TYPE -> ?
     *  |       `--GENERIC_END -> >
     *  |--IDENT -> a
     *  `--SEMI -> ;
     * }
     *
     * @see #GENERIC_START
     * @see #GENERIC_END
     * @see #TYPE_ARGUMENT
     * @see #COMMA
     */
    public static final int TYPE_ARGUMENTS =
        JavaLanguageLexer.TYPE_ARGUMENTS;

    /**
     * A type arguments to a type reference or a method/ctor invocation.
     * Children are either: type name or wildcard type with possible type
     * upper or lower bounds.
     *
     * <p>For example: {@code List< ? super List> list }</p>
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> List
     *  |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |       |--GENERIC_START -> <
     *  |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |       |   |--WILDCARD_TYPE -> ?
     *  |       |   `--TYPE_LOWER_BOUNDS -> super
     *  |       |       `--IDENT -> List
     *  |       `--GENERIC_END -> >
     *  |--IDENT -> list
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #WILDCARD_TYPE
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int TYPE_ARGUMENT =
        JavaLanguageLexer.TYPE_ARGUMENT;

    /**
     * The type that refers to all types. This node has no children.
     *
     * <p>For example: </p>
     * {@snippet :
     *
     * List<?> list;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   |--IDENT -> List
     * |   |   |`--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     * |   |        |--GENERIC_START -> <
     * |   |        |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     * |   |        |  `--WILDCARD_TYPE -> ?
     * |   |        `--GENERIC_END -> >
     * |   `--IDENT -> list
     * |--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #TYPE_ARGUMENT
     * @see #TYPE_UPPER_BOUNDS
     * @see #TYPE_LOWER_BOUNDS
     */
    public static final int WILDCARD_TYPE =
        JavaLanguageLexer.WILDCARD_TYPE;

    /**
     * An upper bounds on a wildcard type argument or type parameter.
     * This node has one child - the type that is being used for
     * the bounding.
     *
     * <p>For example: {@code List< ? extends Number> list;}</p>
     *
     * <p>parses as:</p>
     * {@snippet :
     * --VARIABLE_DEF -> VARIABLE_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--TYPE -> TYPE
     *  |   |--IDENT -> List
     *  |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *  |       |--GENERIC_START -> <
     *  |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *  |       |   |--WILDCARD_TYPE -> ?
     *  |       |   `--TYPE_UPPER_BOUNDS -> extends
     *  |       |       `--IDENT -> Number
     *  |       `--GENERIC_END -> >
     *  |--IDENT -> list
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #TYPE_PARAMETER
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_UPPER_BOUNDS =
        JavaLanguageLexer.TYPE_UPPER_BOUNDS;

    /**
     * A lower bounds on a wildcard type argument. This node has one child
     *  - the type that is being used for the bounding.
     *
     *  <p>For example: {@code List< ? super Integer> list;}</p>
     *
     *  <p>parses as:</p>
     *  {@snippet :
     *  --VARIABLE_DEF -> VARIABLE_DEF
     *     |--MODIFIERS -> MODIFIERS
     *     |--TYPE -> TYPE
     *     |   |--IDENT -> List
     *     |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *     |       |--GENERIC_START -> <
     *     |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *     |       |   |--WILDCARD_TYPE -> ?
     *     |       |   `--TYPE_LOWER_BOUNDS -> super
     *     |       |       `--IDENT -> Integer
     *     |       `--GENERIC_END -> >
     *     |--IDENT -> list
     *     `--SEMI -> ;
     *  }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.1.2">
     *     Generic Classes and Type Parameters</a>
     * @see #TYPE_ARGUMENT
     * @see #WILDCARD_TYPE
     */
    public static final int TYPE_LOWER_BOUNDS =
        JavaLanguageLexer.TYPE_LOWER_BOUNDS;

    /**
     * An {@code @} symbol - signifying an annotation instance or the prefix
     * to the interface literal signifying the definition of an annotation
     * declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * @Deprecated
     * private int value;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * VARIABLE_DEF -> VARIABLE_DEF
     * |--MODIFIERS -> MODIFIERS
     * |  |--ANNOTATION -> ANNOTATION
     * |  |  |--AT -> @
     * |  |  `--IDENT -> Deprecated
     * |  `--LITERAL_PRIVATE -> private
     * |--TYPE -> TYPE
     * |  `--LITERAL_INT -> int
     * |--IDENT -> value
     * `--SEMI -> ;
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     */
    public static final int AT = JavaLanguageLexer.AT;

    /**
     * A triple dot for variable-length parameters. This token only ever occurs
     * in a parameter declaration immediately after the type of the parameter.
     *
     * <p>For example:</p>
     * {@snippet :
     *  public void myShape(int... dimension) {
     *
     *  }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_DEF -> METHOD_DEF
     *   |--MODIFIERS -> MODIFIERS
     *   |   `--LITERAL_PUBLIC -> public
     *   |--TYPE -> TYPE
     *   |   `--LITERAL_VOID -> void
     *   |--IDENT -> myShape
     *   |--LPAREN -> (
     *   |--PARAMETERS -> PARAMETERS
     *   |   `--PARAMETER_DEF -> PARAMETER_DEF
     *   |       |--MODIFIERS -> MODIFIERS
     *   |       |--TYPE -> TYPE
     *   |       |   `--LITERAL_INT -> int
     *   |       |--ELLIPSIS -> ...
     *   |       `--IDENT -> dimension
     *   |--RPAREN -> )
     *   `--SLIST -> {
     *       `--RCURLY -> }
     * }
     *
     * @see <a href="https://www.jcp.org/en/jsr/detail?id=201">
     *     JSR201</a>
     */
    public static final int ELLIPSIS = JavaLanguageLexer.ELLIPSIS;

    /**
     * The {@code &} symbol when used to extend a generic upper or lower bounds constrain
     * or a type cast expression with an additional interface.
     *
     * <p>Generic type bounds extension:
     * {@code class Comparable<T extends Serializable & CharSequence>}</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Comparable
     * |--TYPE_PARAMETERS -> TYPE_PARAMETERS
     *     |--GENERIC_START -> <
     *     |--TYPE_PARAMETER -> TYPE_PARAMETER
     *     |   |--IDENT -> T
     *     |   `--TYPE_UPPER_BOUNDS -> extends
     *     |       |--IDENT -> Serializable
     *     |       |--TYPE_EXTENSION_AND -> &
     *     |       `--IDENT -> CharSequence
     *     `--GENERIC_END -> >
     * }
     *
     * <p>Type cast extension:
     * {@code return (Serializable & CharSequence) null;}</p>
     * {@snippet :
     * --LITERAL_RETURN -> return
     *    |--EXPR -> EXPR
     *    |   `--TYPECAST -> (
     *    |       |--TYPE -> TYPE
     *    |       |   `--IDENT -> Serializable
     *    |       |--TYPE_EXTENSION_AND -> &
     *    |       |--TYPE -> TYPE
     *    |       |   `--IDENT -> CharSequence
     *    |       |--RPAREN -> )
     *    |       `--LITERAL_NULL -> null
     *    `--SEMI -> ;
     * }
     *
     * @see #EXTENDS_CLAUSE
     * @see #TYPECAST
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.4">
     *     Java Language Specification, &sect;4.4</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.16">
     *     Java Language Specification, &sect;15.16</a>
     */
    public static final int TYPE_EXTENSION_AND =
        JavaLanguageLexer.TYPE_EXTENSION_AND;

    /**
     * A {@code <} symbol signifying the start of type arguments or type parameters.
     *
     * <p>For example:</p>
     * {@snippet :
     * class Test<T> {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--LITERAL_CLASS -> class
     *  |--IDENT -> Test
     *  |--TYPE_PARAMETERS -> TYPE_PARAMETERS
     *  |   |--GENERIC_START -> <
     *  |   |--TYPE_PARAMETER -> TYPE_PARAMETER
     *  |   |   `--IDENT -> T
     *  |   `--GENERIC_END -> >
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #MODIFIERS
     * @see #IDENT
     * @see #OBJBLOCK
     * @see #TYPE_PARAMETERS
     * @see #GENERIC_END
     */
    public static final int GENERIC_START =
        JavaLanguageLexer.GENERIC_START;

    /**
     * A {@code >} symbol signifying the end of type arguments or type parameters.
     *
     * <p>For example:</p>
     * {@snippet :
     * class Test<T> {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |--LITERAL_CLASS -> class
     *  |--IDENT -> Test
     *  |--TYPE_PARAMETERS -> TYPE_PARAMETERS
     *  |   |--GENERIC_START -> <
     *  |   |--TYPE_PARAMETER -> TYPE_PARAMETER
     *  |   |   `--IDENT -> T
     *  |   `--GENERIC_END -> >
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #MODIFIERS
     * @see #IDENT
     * @see #OBJBLOCK
     * @see #TYPE_PARAMETERS
     * @see #GENERIC_START
     */
    public static final int GENERIC_END = JavaLanguageLexer.GENERIC_END;

    /**
     * Special lambda symbol {@code ->}.
     *
     * <p>For example:</p>
     * {@snippet :
     * numbers.forEach((n) -> System.out.println(n));
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * METHOD_CALL -> (
     *  |--DOT -> .
     *  |   |--IDENT -> numbers
     *  |   `--IDENT -> forEach
     *  |--ELIST -> ELIST
     *  |   `--LAMBDA -> ->
     *  |       |--LPAREN -> (
     *  |       |--PARAMETERS -> PARAMETERS
     *  |       |   `--PARAMETER_DEF -> PARAMETER_DEF
     *  |       |       |--MODIFIERS -> MODIFIERS
     *  |       |       |--TYPE -> TYPE
     *  |       |       `--IDENT -> n
     *  |       |--RPAREN -> )
     *  |       `--EXPR -> EXPR
     *  |           `--METHOD_CALL -> (
     *  |               |--DOT -> .
     *  |               |   |--DOT -> .
     *  |               |   |   |--IDENT -> System
     *  |               |   |   `--IDENT -> out
     *  |               |   `--IDENT -> println
     *  |               |--ELIST -> ELIST
     *  |               |   `--EXPR -> EXPR
     *  |               |       `--IDENT -> n
     *  |               `--RPAREN -> )
     *  `--RPAREN -> )
     * }
     *
     */
    public static final int LAMBDA = JavaLanguageLexer.LAMBDA;

    /**
     * Beginning of single-line comment: '//'.
     *
     * {@snippet :
     * SINGLE_LINE_COMMENT -> //
     *  `--COMMENT_CONTENT -> \r\n
     * }
     *
     * <p>For example:</p>
     * {@snippet :
     * // Comment content
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * SINGLE_LINE_COMMENT -> //
     *  `--COMMENT_CONTENT ->  Comment Content\n
     * }
     */
    public static final int SINGLE_LINE_COMMENT =
            JavaLanguageLexer.SINGLE_LINE_COMMENT;

    /**
     * Beginning of block comment: '/*'.
     *
     * <p>For example:</p>
     * <pre>{@code
     * /* Comment content
     *  * /
     * }</pre>
     *
     * <p>parses as:</p>
     * {@snippet :
     * --BLOCK_COMMENT_BEGIN -> /*
     *    |--COMMENT_CONTENT ->  Comment content\r\n
     *    `--BLOCK_COMMENT_END -> *
     * }
     */
    public static final int BLOCK_COMMENT_BEGIN =
            JavaLanguageLexer.BLOCK_COMMENT_BEGIN;

    /**
     * End of block comment: '* /'.
     *
     * <p>For example:</p>
     * <pre>{@code
     * /*comment
     *  * /
     * }</pre>
     *
     * <p>parses as:</p>
     * {@snippet :
     * BLOCK_COMMENT_BEGIN -> /*
     *  |--COMMENT_CONTENT -> comment
     *  `--BLOCK_COMMENT_END -> *
     * }
     *
     */
    public static final int BLOCK_COMMENT_END =
            JavaLanguageLexer.BLOCK_COMMENT_END;

    /**
     * Text of single-line or block comment.
     *
     * <p>For example:</p>
     * <pre>{@code
     * //this is single-line comment
     *
     * /*
     * this is multiline comment
     *  * /
     * }</pre>
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--SINGLE_LINE_COMMENT -> //
     * |   `--COMMENT_CONTENT -> this is single-line comment\n
     * |--BLOCK_COMMENT_BEGIN -> /*
     * |   |--COMMENT_CONTENT -> \n\t\t\tthis is multiline comment\n\t\t
     * |   `--BLOCK_COMMENT_END -> *
     * }
     *
     */
    public static final int COMMENT_CONTENT =
            JavaLanguageLexer.COMMENT_CONTENT;

    /**
     * A pattern variable definition; when conditionally matched,
     * this variable is assigned with the defined type.
     *
     * <p>For example:</p>
     * {@snippet :
     * if (obj instanceof String str) { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LITERAL_INSTANCEOF -> instanceof
     *  |       |--IDENT -> obj
     *  |       `--PATTERN_VARIABLE_DEF -> PATTERN_VARIABLE_DEF
     *  |           |--TYPE -> TYPE
     *  |           |   `--IDENT -> String
     *  |           `--IDENT -> str
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #LITERAL_INSTANCEOF
     * @since 8.35
     */
    public static final int PATTERN_VARIABLE_DEF =
            JavaLanguageLexer.PATTERN_VARIABLE_DEF;

    /**
     * The {@code record} keyword.  This element appears
     * as part of a record declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public record MyRecord () {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * RECORD_DEF -> RECORD_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_RECORD -> record
     * |--IDENT -> MyRecord
     * |--LPAREN -> (
     * |--RECORD_COMPONENTS -> RECORD_COMPONENTS
     * |--RPAREN -> )
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @since 8.35
     */
    public static final int LITERAL_RECORD =
            JavaLanguageLexer.LITERAL_RECORD;

    /**
     * A declaration of a record specifies a name, a header, and a body.
     * The header lists the components of the record, which are the variables
     * that make up its state.
     *
     * <p>For example:</p>
     * {@snippet :
     * public record MyRecord () {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * RECORD_DEF -> RECORD_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_RECORD -> record
     * |--IDENT -> MyRecord
     * |--LPAREN -> (
     * |--RECORD_COMPONENTS -> RECORD_COMPONENTS
     * |--RPAREN -> )
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @since 8.35
     */
    public static final int RECORD_DEF =
            JavaLanguageLexer.RECORD_DEF;

    /**
     * Record components are a (possibly empty) list containing the components of a record, which
     * are the variables that make up its state.
     *
     * <p>For example:</p>
     * {@snippet :
     * public record myRecord (Comp x, Comp y) { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * RECORD_DEF -> RECORD_DEF
     *  |--MODIFIERS -> MODIFIERS
     *  |   `--LITERAL_PUBLIC -> public
     *  |--LITERAL_RECORD -> record
     *  |--IDENT -> myRecord
     *  |--LPAREN -> (
     *  |--RECORD_COMPONENTS -> RECORD_COMPONENTS
     *  |   |--RECORD_COMPONENT_DEF -> RECORD_COMPONENT_DEF
     *  |   |   |--ANNOTATIONS -> ANNOTATIONS
     *  |   |   |--TYPE -> TYPE
     *  |   |   |   `--IDENT -> Comp
     *  |   |   `--IDENT -> x
     *  |   |--COMMA -> ,
     *  |   `--RECORD_COMPONENT_DEF -> RECORD_COMPONENT_DEF
     *  |       |--ANNOTATIONS -> ANNOTATIONS
     *  |       |--TYPE -> TYPE
     *  |       |   `--IDENT -> Comp
     *  |       `--IDENT -> y
     *  |--RPAREN -> )
     *  `--OBJBLOCK -> OBJBLOCK
     *      |--LCURLY -> {
     *      `--RCURLY -> }
     * }
     *
     * @since 8.36
     */
    public static final int RECORD_COMPONENTS =
            JavaLanguageLexer.RECORD_COMPONENTS;

    /**
     * A record component is a variable that comprises the state of a record.  Record components
     * have annotations (possibly), a type definition, and an identifier.  They can also be of
     * variable arity ('...').
     *
     * <p>For example:</p>
     * {@snippet :
     * public record MyRecord(Comp x, Comp... comps) {
     *
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * RECORD_DEF -> RECORD_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_PUBLIC -> public
     * |--LITERAL_RECORD -> record
     * |--IDENT -> MyRecord
     * |--LPAREN -> (
     * |--RECORD_COMPONENTS -> RECORD_COMPONENTS
     * |   |--RECORD_COMPONENT_DEF -> RECORD_COMPONENT_DEF
     * |   |   |--ANNOTATIONS -> ANNOTATIONS
     * |   |   |--TYPE -> TYPE
     * |   |   |   `--IDENT -> Comp
     * |   |   `--IDENT -> x
     * |   |--COMMA -> ,
     * |   `--RECORD_COMPONENT_DEF -> RECORD_COMPONENT_DEF
     * |       |--ANNOTATIONS -> ANNOTATIONS
     * |       |--TYPE -> TYPE
     * |       |   `--IDENT -> Comp
     * |       |--ELLIPSIS -> ...
     * |       `--IDENT -> comps
     * |--RPAREN -> )
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @since 8.36
     */
    public static final int RECORD_COMPONENT_DEF =
            JavaLanguageLexer.RECORD_COMPONENT_DEF;

    /**
     * A compact canonical constructor eliminates the list of formal parameters; they are
     * declared implicitly.
     *
     * <p>For example:</p>
     * {@snippet :
     * public record myRecord () {
     *     public myRecord{}
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
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
     * }
     *
     * @since 8.36
     */
    public static final int COMPACT_CTOR_DEF =
            JavaLanguageLexer.COMPACT_CTOR_DEF;

    /**
     * Text blocks are a new feature added to to Java SE 15 and later
     * that will make writing multi-line strings much easier and cleaner.
     * Beginning of a Java 15 Text Block literal,
     * delimited by three double quotes.
     *
     * <p>For example:</p>
     * {@snippet :
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--IDENT -> String
     * |   |--IDENT -> hello
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--TEXT_BLOCK_LITERAL_BEGIN -> """
     * |               |--TEXT_BLOCK_CONTENT -> \n                Hello, world!\n
     * |               `--TEXT_BLOCK_LITERAL_END -> """
     * `--SEMI -> ;
     * }
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_LITERAL_BEGIN =
            JavaLanguageLexer.TEXT_BLOCK_LITERAL_BEGIN;

    /**
     * Content of a Java 15 text block. This is a
     * sequence of characters, possibly escaped with '\'. Actual line terminators
     * are represented by '\n'.
     *
     * <p>For example:</p>
     * {@snippet :
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--IDENT -> String
     * |   |--IDENT -> hello
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--TEXT_BLOCK_LITERAL_BEGIN -> """
     * |               |--TEXT_BLOCK_CONTENT -> \n                Hello, world!\n
     * |               `--TEXT_BLOCK_LITERAL_END -> """
     * `--SEMI -> ;
     * }
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_CONTENT =
            JavaLanguageLexer.TEXT_BLOCK_CONTENT;

    /**
     * End of a Java 15 text block literal, delimited by three
     * double quotes.
     *
     * <p>For example:</p>
     * {@snippet :
     *         String hello = """
     *                 Hello, world!
     *                 """;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--IDENT -> String
     * |   |--IDENT -> hello
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--TEXT_BLOCK_LITERAL_BEGIN -> """
     * |               |--TEXT_BLOCK_CONTENT -> \n                Hello, world!\n
     * |               `--TEXT_BLOCK_LITERAL_END -> """
     * `--SEMI -> ;
     * }
     *
     * @since 8.36
     */
    public static final int TEXT_BLOCK_LITERAL_END =
            JavaLanguageLexer.TEXT_BLOCK_LITERAL_END;

    /**
     * The {@code yield} keyword.  This element appears
     * as part of a yield statement.
     *
     * <p>For example:</p>
     * {@snippet :
     * int yield = 0; // not a keyword here
     * return switch (mode) {
     *    case "a", "b":
     *        yield 1;
     *    default:
     *        yield - 1;
     * };
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * |--VARIABLE_DEF -> VARIABLE_DEF
     * |   |--MODIFIERS -> MODIFIERS
     * |   |--TYPE -> TYPE
     * |   |   `--LITERAL_INT -> int
     * |   |--IDENT -> yield
     * |   `--ASSIGN -> =
     * |       `--EXPR -> EXPR
     * |           `--NUM_INT -> 0
     * |--SEMI -> ;
     * |--LITERAL_RETURN -> return
     * |   |--EXPR -> EXPR
     * |   |   `--LITERAL_SWITCH -> switch
     * |   |       |--LPAREN -> (
     * |   |       |--EXPR -> EXPR
     * |   |       |   `--IDENT -> mode
     * |   |       |--RPAREN -> )
     * |   |       |--LCURLY -> {
     * |   |       |--CASE_GROUP -> CASE_GROUP
     * |   |       |   |--LITERAL_CASE -> case
     * |   |       |   |   |--EXPR -> EXPR
     * |   |       |   |   |   `--STRING_LITERAL -> "a"
     * |   |       |   |   |--COMMA -> ,
     * |   |       |   |   |--EXPR -> EXPR
     * |   |       |   |   |   `--STRING_LITERAL -> "b"
     * |   |       |   |   `--COLON -> :
     * |   |       |   `--SLIST -> SLIST
     * |   |       |       `--LITERAL_YIELD -> yield
     * |   |       |           |--EXPR -> EXPR
     * |   |       |           |   `--NUM_INT -> 1
     * |   |       |           `--SEMI -> ;
     * |   |       |--CASE_GROUP -> CASE_GROUP
     * |   |       |   |--LITERAL_DEFAULT -> default
     * |   |       |   |   `--COLON -> :
     * |   |       |   `--SLIST -> SLIST
     * |   |       |       `--LITERAL_YIELD -> yield
     * |   |       |           |--EXPR -> EXPR
     * |   |       |           |   `--UNARY_MINUS -> -
     * |   |       |           |       `--NUM_INT -> 1
     * |   |       |           `--SEMI -> ;
     * |   |       `--RCURLY -> }
     * |   `--SEMI -> ;
     * }
     *
     *
     * @see #LITERAL_SWITCH
     * @see #CASE_GROUP
     * @see #SLIST
     * @see #SWITCH_RULE
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se13/preview/switch-expressions.html">
     *     Java Language Specification, &sect;14.21</a>
     *
     * @since 8.36
     */
    public static final int LITERAL_YIELD =
            JavaLanguageLexer.LITERAL_YIELD;

    /**
     * Switch Expressions.
     *
     * <p>For example:</p>
     * {@snippet :
     * return switch (day) {
     *     case SAT, SUN -> "Weekend";
     *     default -> "Working day";
     * };
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_RETURN -> return
     *  |--EXPR -> EXPR
     *  |   `--LITERAL_SWITCH -> switch
     *  |       |--LPAREN -> (
     *  |       |--EXPR -> EXPR
     *  |       |   `--IDENT -> day
     *  |       |--RPAREN -> )
     *  |       |--LCURLY -> {
     *  |       |--SWITCH_RULE -> SWITCH_RULE
     *  |       |   |--LITERAL_CASE -> case
     *  |       |   |   |--EXPR -> EXPR
     *  |       |   |   |   `--IDENT -> SAT
     *  |       |   |   |--COMMA -> ,
     *  |       |   |   `--EXPR -> EXPR
     *  |       |   |       `--IDENT -> SUN
     *  |       |   |--LAMBDA -> ->
     *  |       |   |--EXPR -> EXPR
     *  |       |   |   `--STRING_LITERAL -> "Weekend"
     *  |       |   `--SEMI -> ;
     *  |       |--SWITCH_RULE -> SWITCH_RULE
     *  |       |   |--LITERAL_DEFAULT -> default
     *  |       |   |--LAMBDA -> ->
     *  |       |   |--EXPR -> EXPR
     *  |       |   |   `--STRING_LITERAL -> "Working day"
     *  |       |   `--SEMI -> ;
     *  |       `--RCURLY -> }
     *  `--SEMI -> ;
     * }
     *
     * @see #LITERAL_CASE
     * @see #LITERAL_DEFAULT
     * @see #LITERAL_SWITCH
     * @see #LITERAL_YIELD
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se13/preview/switch-expressions.html">
     *     Java Language Specification, &sect;14.21</a>
     *
     * @since 8.36
     */
    public static final int SWITCH_RULE =
            JavaLanguageLexer.SWITCH_RULE;

    /**
     * The {@code non-sealed} keyword.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * non-sealed class Square extends Rectangle { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   `--LITERAL_NON_SEALED -> non-sealed
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Square
     * |--EXTENDS_CLAUSE -> extends
     * |   `--IDENT -> Rectangle
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     *     Java Language Specification, &sect;8.1.1.2</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_NON_SEALED =
        JavaLanguageLexer.LITERAL_NON_SEALED;

    /**
     * The {@code sealed} restricted identifier.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_PUBLIC -> public
     * |   `--LITERAL_SEALED -> sealed
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Shape
     * |--PERMITS_CLAUSE -> permits
     * |   |--IDENT -> Circle
     * |   |--COMMA -> ,
     * |   |--IDENT -> Square
     * |   |--COMMA -> ,
     * |   `--IDENT -> Rectangle
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     *     Java Language Specification, &sect;8.1.1.2</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_SEALED =
        JavaLanguageLexer.LITERAL_SEALED;

    /**
     * The {@code permits} restricted identifier.  This element appears
     * as part of a class or interface declaration.
     *
     * <p>For example:</p>
     * {@snippet :
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_PUBLIC -> public
     * |   `--LITERAL_SEALED -> sealed
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Shape
     * |--PERMITS_CLAUSE -> permits
     * |   |--IDENT -> Circle
     * |   |--COMMA -> ,
     * |   |--IDENT -> Square
     * |   |--COMMA -> ,
     * |   `--IDENT -> Rectangle
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     *     Java Language Specification, &sect;9.1.4</a>
     * @see #MODIFIERS
     *
     * @since 8.42
     */
    public static final int LITERAL_PERMITS =
        JavaLanguageLexer.LITERAL_PERMITS;

    /**
     * A permits clause.  A permits clause's children are a comma separated list of one or
     * more identifiers.
     *
     * <p>For example:</p>
     * {@snippet :
     * public sealed class Shape permits Circle, Square, Rectangle { }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * CLASS_DEF -> CLASS_DEF
     * |--MODIFIERS -> MODIFIERS
     * |   |--LITERAL_PUBLIC -> public
     * |   `--LITERAL_SEALED -> sealed
     * |--LITERAL_CLASS -> class
     * |--IDENT -> Shape
     * |--PERMITS_CLAUSE -> permits
     * |   |--IDENT -> Circle
     * |   |--COMMA -> ,
     * |   |--IDENT -> Square
     * |   |--COMMA -> ,
     * |   `--IDENT -> Rectangle
     * `--OBJBLOCK -> OBJBLOCK
     *     |--LCURLY -> {
     *     `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/15/docs/specs/sealed-classes-jls.html">
     *     Java Language Specification, &sect;9.1.4</a>
     * @see #MODIFIERS
     * @see #CLASS_DEF
     * @see #INTERFACE_DEF
     * @see #COMMA
     * @see #IDENT
     *
     * @since 8.42
     */
    public static final int PERMITS_CLAUSE =
        JavaLanguageLexer.PERMITS_CLAUSE;

    /**
     * A pattern definition, excluding simple type pattern (pattern variable)
     * definition such as {@code if (o instanceof Integer i){}}. Pattern definitions
     * appear as operands of statements and expressions.
     *
     * <p>For example:</p>
     * <pre>
     * switch(o) {
     *     case String s when s.length() &gt; 4: // guarded pattern, `PATTERN_DEF`
     *         break;
     *     case String s: // type pattern, no `PATTERN_DEF`
     *         break;
     * }
     * </pre>
     *
     * <p>parses as:</p>
     * <pre>
     * LITERAL_SWITCH -&gt; switch
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--IDENT -&gt; o
     * |   |--RPAREN -&gt; )
     * |   |--LCURLY -&gt; {
     * |   |--CASE_GROUP -&gt; CASE_GROUP
     * |   |   |--LITERAL_CASE -&gt; case
     * |   |   |   |--PATTERN_DEF -&gt; PATTERN_DEF
     * |   |   |   |   `--LITERAL_WHEN -&gt; when
     * |   |   |   |       |--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |   |   |       |   |--MODIFIERS -&gt; MODIFIERS
     * |   |   |   |       |   |--TYPE -&gt; TYPE
     * |   |   |   |       |   |   `--IDENT -&gt; String
     * |   |   |   |       |   `--IDENT -&gt; s
     * |   |   |   |       `--GT -&gt; &gt;
     * |   |   |   |           |--METHOD_CALL -&gt; (
     * |   |   |   |           |   |--DOT -&gt; .
     * |   |   |   |           |   |   |--IDENT -&gt; s
     * |   |   |   |           |   |   `--IDENT -&gt; length
     * |   |   |   |           |   |--ELIST -&gt; ELIST
     * |   |   |   |           |   `--RPAREN -&gt; )
     * |   |   |   |           `--NUM_INT -&gt; 4
     * |   |   |   `--COLON -&gt; :
     * |   |   `--SLIST -&gt; SLIST
     * |   |       `--LITERAL_BREAK -&gt; break
     * |   |           `--SEMI -&gt; ;
     * |   |--CASE_GROUP -&gt; CASE_GROUP
     * |   |   |--LITERAL_CASE -&gt; case
     * |   |   |   |--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |   |   |   |--MODIFIERS -&gt; MODIFIERS
     * |   |   |   |   |--TYPE -&gt; TYPE
     * |   |   |   |   |   `--IDENT -&gt; String
     * |   |   |   |   `--IDENT -&gt; s
     * |   |   |   `--COLON -&gt; :
     * |   |   `--SLIST -&gt; SLIST
     * |   |       `--LITERAL_BREAK -&gt; break
     * |   |           `--SEMI -&gt; ;
     * |   `--RCURLY -&gt; }
     * `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30">
     *     Java Language Specification, &sect;14.30</a>
     * @see #LITERAL_SWITCH
     * @see #PATTERN_VARIABLE_DEF
     * @see #LITERAL_INSTANCEOF
     *
     * @since 9.3
     */
    public static final int PATTERN_DEF =
        JavaLanguageLexer.PATTERN_DEF;

    /**
     * A {@code when} clause. Appears as part of a switch label in a guarded pattern definition.
     *
     * <p>For example:</p>
     * {@snippet :
     * return switch (o) {
     *     case Integer i when i >= 0 -> i;
     *     default -> 2;
     * };
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_RETURN -> return
     *  `--EXPR -> EXPR
     *      `--LITERAL_SWITCH -> switch
     *          |--LPAREN -> (
     *          |--EXPR -> EXPR
     *          |   `--IDENT -> o
     *          |--RPAREN -> )
     *          |--LCURLY -> {
     *          |--SWITCH_RULE -> SWITCH_RULE
     *          |   |--LITERAL_CASE -> case
     *          |   |   `--PATTERN_DEF -> PATTERN_DEF
     *          |   |       `--LITERAL_WHEN -> when
     *          |   |           |--PATTERN_VARIABLE_DEF -> PATTERN_VARIABLE_DEF
     *          |   |           |   |--MODIFIERS -> MODIFIERS
     *          |   |           |   |--TYPE -> TYPE
     *          |   |           |   |   `--IDENT -> Integer
     *          |   |           |   `--IDENT -> i
     *          |   |           `--GE -> >=
     *          |   |               |--IDENT -> i
     *          |   |               `--NUM_INT -> 0
     *          |   |--LAMBDA -> ->
     *          |   |--EXPR -> EXPR
     *          |   |   `--IDENT -> i
     *          |   `--SEMI -> ;
     *          |--SWITCH_RULE -> SWITCH_RULE
     *          |   |--LITERAL_DEFAULT -> default
     *          |   |--LAMBDA -> ->
     *          |   |--EXPR -> EXPR
     *          |   |   `--NUM_INT -> 2
     *          |   `--SEMI -> ;
     *          `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30">
     *     Java Language Specification, &sect;14.30</a>
     * @see #LITERAL_SWITCH
     * @see #PATTERN_VARIABLE_DEF
     * @see #LITERAL_INSTANCEOF
     * @see #SWITCH_RULE
     *
     * @since 10.7.0
     */
    public static final int LITERAL_WHEN =
        JavaLanguageLexer.LITERAL_WHEN;

    /**
     * A {@code record} pattern definition. A record pattern consists of a type,
     * a (possibly empty) record component pattern list which is used to match against
     * the corresponding record components, and an optional identifier. Appears as part of
     * an {@code instanceof} expression or a {@code case} label in a switch.
     *
     * <p>For example:</p>
     * {@snippet :
     * record R(Object o){}
     * if (o instanceof R(String s) myRecord) {}
     * switch (o) {
     *     case R(String s) myRecord -> {}
     * }
     * }
     *
     * <p>parses as:</p>
     * <pre>
     * |--RECORD_DEF -&gt; RECORD_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_RECORD -&gt; record
     * |   |--IDENT -&gt; R
     * |   |--LPAREN -&gt; (
     * |   |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     * |   |   `--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     * |   |       |--ANNOTATIONS -&gt; ANNOTATIONS
     * |   |       |--TYPE -&gt; TYPE
     * |   |       |   `--IDENT -&gt; Object
     * |   |       `--IDENT -&gt; o
     * |   |--RPAREN -&gt; )
     * |   `--OBJBLOCK -&gt; OBJBLOCK
     * |       |--LCURLY -&gt; {
     * |       `--RCURLY -&gt; }
     * |--LITERAL_IF -&gt; if
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--LITERAL_INSTANCEOF -&gt; instanceof
     * |   |       |--IDENT -&gt; o
     * |   |       `--RECORD_PATTERN_DEF -&gt; RECORD_PATTERN_DEF
     * |   |           |--MODIFIERS -&gt; MODIFIERS
     * |   |           |--TYPE -&gt; TYPE
     * |   |           |   `--IDENT -&gt; R
     * |   |           |--LPAREN -&gt; (
     * |   |           |--RECORD_PATTERN_COMPONENTS -&gt; RECORD_PATTERN_COMPONENTS
     * |   |           |   `--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |           |       |--MODIFIERS -&gt; MODIFIERS
     * |   |           |       |--TYPE -&gt; TYPE
     * |   |           |       |   `--IDENT -&gt; String
     * |   |           |       `--IDENT -&gt; s
     * |   |           |--RPAREN -&gt; )
     * |   |           `--IDENT -&gt; myRecord
     * |   |--RPAREN -&gt; )
     * |   `--SLIST -&gt; {
     * |       `--RCURLY -&gt; }
     * |--LITERAL_SWITCH -&gt; switch
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--IDENT -&gt; o
     * |   |--RPAREN -&gt; )
     * |   |--LCURLY -&gt; {
     * |   |--SWITCH_RULE -&gt; SWITCH_RULE
     * |   |   |--LITERAL_CASE -&gt; case
     * |   |   |   `--RECORD_PATTERN_DEF -&gt; RECORD_PATTERN_DEF
     * |   |   |       |--MODIFIERS -&gt; MODIFIERS
     * |   |   |       |--TYPE -&gt; TYPE
     * |   |   |       |   `--IDENT -&gt; R
     * |   |   |       |--LPAREN -&gt; (
     * |   |   |       |--RECORD_PATTERN_COMPONENTS -&gt; RECORD_PATTERN_COMPONENTS
     * |   |   |       |   `--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |   |       |       |--MODIFIERS -&gt; MODIFIERS
     * |   |   |       |       |--TYPE -&gt; TYPE
     * |   |   |       |       |   `--IDENT -&gt; String
     * |   |   |       |       `--IDENT -&gt; s
     * |   |   |       |--RPAREN -&gt; )
     * |   |   |       `--IDENT -&gt; myRecord
     * |   |   |--LAMBDA -&gt; -&gt;
     * |   |   `--SLIST -&gt; {
     * |   |       `--RCURLY -&gt; }
     * |   `--RCURLY -&gt; }
     * `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://openjdk.org/jeps/405">JEP 405: Record Patterns</a>
     * @see #LITERAL_WHEN
     * @see #PATTERN_VARIABLE_DEF
     * @see #LITERAL_INSTANCEOF
     * @see #SWITCH_RULE
     *
     * @since 10.12.0
     */
    public static final int RECORD_PATTERN_DEF =
        JavaLanguageLexer.RECORD_PATTERN_DEF;

    /**
     * A (possibly empty) record component pattern list which is used to match against
     * the corresponding record components. Appears as part of a record pattern definition.
     *
     * <p>For example:</p>
     * {@snippet :
     * record R(Object o){}
     * if (o instanceof R(String myComponent)) {}
     * switch (o) {
     *     case R(String myComponent) when "component".equalsIgnoreCase(myComponent) -> {}
     * }
     * }
     *
     * <p>parses as:</p>
     * <pre>
     * |--RECORD_DEF -&gt; RECORD_DEF
     * |   |--MODIFIERS -&gt; MODIFIERS
     * |   |--LITERAL_RECORD -&gt; record
     * |   |--IDENT -&gt; R
     * |   |--LPAREN -&gt; (
     * |   |--RECORD_COMPONENTS -&gt; RECORD_COMPONENTS
     * |   |   `--RECORD_COMPONENT_DEF -&gt; RECORD_COMPONENT_DEF
     * |   |       |--ANNOTATIONS -&gt; ANNOTATIONS
     * |   |       |--TYPE -&gt; TYPE
     * |   |       |   `--IDENT -&gt; Object
     * |   |       `--IDENT -&gt; o
     * |   |--RPAREN -&gt; )
     * |   `--OBJBLOCK -&gt; OBJBLOCK
     * |       |--LCURLY -&gt; {
     * |       `--RCURLY -&gt; }
     * |--LITERAL_IF -&gt; if
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--LITERAL_INSTANCEOF -&gt; instanceof
     * |   |       |--IDENT -&gt; o
     * |   |       `--RECORD_PATTERN_DEF -&gt; RECORD_PATTERN_DEF
     * |   |           |--MODIFIERS -&gt; MODIFIERS
     * |   |           |--TYPE -&gt; TYPE
     * |   |           |   `--IDENT -&gt; R
     * |   |           |--LPAREN -&gt; (
     * |   |           |--RECORD_PATTERN_COMPONENTS -&gt; RECORD_PATTERN_COMPONENTS
     * |   |           |   `--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |           |       |--MODIFIERS -&gt; MODIFIERS
     * |   |           |       |--TYPE -&gt; TYPE
     * |   |           |       |   `--IDENT -&gt; String
     * |   |           |       `--IDENT -&gt; myComponent
     * |   |           `--RPAREN -&gt; )
     * |   |--RPAREN -&gt; )
     * |   `--SLIST -&gt; {
     * |       `--RCURLY -&gt; }
     * |--LITERAL_SWITCH -&gt; switch
     * |   |--LPAREN -&gt; (
     * |   |--EXPR -&gt; EXPR
     * |   |   `--IDENT -&gt; o
     * |   |--RPAREN -&gt; )
     * |   |--LCURLY -&gt; {
     * |   |--SWITCH_RULE -&gt; SWITCH_RULE
     * |   |   |--LITERAL_CASE -&gt; case
     * |   |   |   `--PATTERN_DEF -&gt; PATTERN_DEF
     * |   |   |       `--LITERAL_WHEN -&gt; when
     * |   |   |           |--RECORD_PATTERN_DEF -&gt; RECORD_PATTERN_DEF
     * |   |   |           |   |--MODIFIERS -&gt; MODIFIERS
     * |   |   |           |   |--TYPE -&gt; TYPE
     * |   |   |           |   |   `--IDENT -&gt; R
     * |   |   |           |   |--LPAREN -&gt; (
     * |   |   |           |   |--RECORD_PATTERN_COMPONENTS -&gt; RECORD_PATTERN_COMPONENTS
     * |   |   |           |   |   `--PATTERN_VARIABLE_DEF -&gt; PATTERN_VARIABLE_DEF
     * |   |   |           |   |       |--MODIFIERS -&gt; MODIFIERS
     * |   |   |           |   |       |--TYPE -&gt; TYPE
     * |   |   |           |   |       |   `--IDENT -&gt; String
     * |   |   |           |   |       `--IDENT -&gt; myComponent
     * |   |   |           |   `--RPAREN -&gt; )
     * |   |   |           `--METHOD_CALL -&gt; (
     * |   |   |               |--DOT -&gt; .
     * |   |   |               |   |--STRING_LITERAL -&gt; "component"
     * |   |   |               |   `--IDENT -&gt; equalsIgnoreCase
     * |   |   |               |--ELIST -&gt; ELIST
     * |   |   |               |   `--EXPR -&gt; EXPR
     * |   |   |               |       `--IDENT -&gt; myComponent
     * |   |   |               `--RPAREN -&gt; )
     * |   |   |--LAMBDA -&gt; -&gt;
     * |   |   `--SLIST -&gt; {
     * |   |       `--RCURLY -&gt; }
     * |   `--RCURLY -&gt; }
     * `--RCURLY -&gt; }
     * </pre>
     *
     * @see <a href="https://openjdk.org/jeps/405">JEP 405: Record Patterns</a>
     * @see #LITERAL_WHEN
     * @see #PATTERN_VARIABLE_DEF
     * @see #LITERAL_INSTANCEOF
     * @see #SWITCH_RULE
     *
     * @since 10.12.0
     */
    public static final int RECORD_PATTERN_COMPONENTS =
            JavaLanguageLexer.RECORD_PATTERN_COMPONENTS;

    /**
     * An unnamed pattern variable definition. Appears as part of a pattern definition.
     *
     * <p>For example:</p>
     * {@snippet :
     *    if (r instanceof R(_)) {}
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * LITERAL_IF -> if
     *  |--LPAREN -> (
     *  |--EXPR -> EXPR
     *  |   `--LITERAL_INSTANCEOF -> instanceof
     *  |       |--IDENT -> r
     *  |       `--RECORD_PATTERN_DEF -> RECORD_PATTERN_DEF
     *  |           |--MODIFIERS -> MODIFIERS
     *  |           |--TYPE -> TYPE
     *  |           |   `--IDENT -> R
     *  |           |--LPAREN -> (
     *  |           |--RECORD_PATTERN_COMPONENTS -> RECORD_PATTERN_COMPONENTS
     *  |           |   `--UNNAMED_PATTERN_DEF -> _
     *  |           `--RPAREN -> )
     *  |--RPAREN -> )
     *  `--SLIST -> {
     *      `--RCURLY -> }
     * }
     *
     * @see #RECORD_PATTERN_COMPONENTS
     * @see #RECORD_PATTERN_DEF
     * @see #LITERAL_SWITCH
     * @see #LITERAL_INSTANCEOF
     * @see #SWITCH_RULE
     * @see #LITERAL_WHEN
     * @see #PATTERN_VARIABLE_DEF
     * @see #PATTERN_DEF
     *
     * @since 10.14.0
     */
    public static final int UNNAMED_PATTERN_DEF =
            JavaLanguageLexer.UNNAMED_PATTERN_DEF;

    /**
     * A {@code module} keyword.
     *
     * <p>For example:</p>
     * {@snippet :
     * import module java.base;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * MODULE_IMPORT -> import
     *  |--LITERAL_MODULE -> module
     *  |--DOT -> .
     *  |   |--IDENT -> java
     *  |   `--IDENT -> base
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.5.5">
     *     Java Language Specification, &sect;7.5.5</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7">
     *     Java Language Specification, &sect;7.7</a>
     * @see #IMPORT
     * @see #MODULE_IMPORT
     *
     * @since 12.2.0
     */
    public static final int LITERAL_MODULE = JavaLanguageLexer.LITERAL_MODULE;

    /**
     * A module import declaration - {@code import module}.
     *
     * <p>For example:</p>
     * {@snippet :
     * import module java.base;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * MODULE_IMPORT -> import
     *  |--LITERAL_MODULE -> module
     *  |--DOT -> .
     *  |   |--IDENT -> java
     *  |   `--IDENT -> base
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.5.5">
     *     Java Language Specification, &sect;7.5.5</a>
     * @see #IMPORT
     * @see #LITERAL_MODULE
     *
     * @since 12.2.0
     */
    public static final int MODULE_IMPORT = JavaLanguageLexer.MODULE_IMPORT;

    /**
     * An {@code open} keyword. This keyword marks a module declaration as open,
     * making all its packages available for deep reflection. It appears as a
     * child of {@link #MODULE_DEF}.
     *
     * <p>For example:</p>
     * {@snippet :
     * open module com.example.app {
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * MODULE_DEF -> MODULE_DEF
     *  |--ANNOTATIONS -> ANNOTATIONS
     *  |--LITERAL_OPEN -> open
     *  |--LITERAL_MODULE -> module
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> app
     *  `--DIRECTIVE_BLOCK -> DIRECTIVE_BLOCK
     *      |--LCURLY -> {
     *      `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7">
     *     Java Language Specification, &sect;7.7</a>
     * @see #MODULE_DEF
     *
     * @since 14.0.0
     */
    public static final int LITERAL_OPEN = JavaLanguageLexer.LITERAL_OPEN;

    /**
     * A {@code transitive} keyword. This keyword is a modifier of a
     * {@link #REQUIRES} directive and appears as a child of it.
     *
     * <p>For example:</p>
     * {@snippet :
     * requires transitive java.sql;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * REQUIRES -> requires
     *  |--LITERAL_TRANSITIVE -> transitive
     *  |--DOT -> .
     *  |   |--IDENT -> java
     *  |   `--IDENT -> sql
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.1">
     *     Java Language Specification, &sect;7.7.1</a>
     * @see #REQUIRES
     *
     * @since 14.0.0
     */
    public static final int LITERAL_TRANSITIVE = JavaLanguageLexer.LITERAL_TRANSITIVE;

    /**
     * A module declaration. The declaration of a Java Platform Module System
     * module, as found in a {@code module-info.java} file. Its children are
     * an {@link #ANNOTATIONS} node (which may be empty), an optional
     * {@link #LITERAL_OPEN}, a {@link #LITERAL_MODULE}, the module name, and
     * a {@link #DIRECTIVE_BLOCK}.
     *
     * <p>For example:</p>
     * {@snippet :
     * module com.example.app {
     *     requires java.base;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * COMPILATION_UNIT -> COMPILATION_UNIT
     *  `--MODULE_DEF -> MODULE_DEF
     *      |--ANNOTATIONS -> ANNOTATIONS
     *      |--LITERAL_MODULE -> module
     *      |--DOT -> .
     *      |   |--DOT -> .
     *      |   |   |--IDENT -> com
     *      |   |   `--IDENT -> example
     *      |   `--IDENT -> app
     *      `--DIRECTIVE_BLOCK -> DIRECTIVE_BLOCK
     *          |--LCURLY -> {
     *          |--REQUIRES -> requires
     *          |   |--DOT -> .
     *          |   |   |--IDENT -> java
     *          |   |   `--IDENT -> base
     *          |   `--SEMI -> ;
     *          `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7">
     *     Java Language Specification, &sect;7.7</a>
     * @see #DIRECTIVE_BLOCK
     * @see #LITERAL_MODULE
     * @see #LITERAL_OPEN
     * @see #ANNOTATIONS
     *
     * @since 14.0.0
     */
    public static final int MODULE_DEF = JavaLanguageLexer.MODULE_DEF;

    /**
     * A directive block. The braced body of a module declaration, containing
     * zero or more module directives. It is a child of {@link #MODULE_DEF}.
     *
     * <p>For example:</p>
     * {@snippet :
     * module com.example.app {
     *     uses com.example.api.Service;
     * }
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * MODULE_DEF -> MODULE_DEF
     *  |--ANNOTATIONS -> ANNOTATIONS
     *  |--LITERAL_MODULE -> module
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> app
     *  `--DIRECTIVE_BLOCK -> DIRECTIVE_BLOCK
     *      |--LCURLY -> {
     *      |--USES -> uses
     *      |   |--DOT -> .
     *      |   |   |--DOT -> .
     *      |   |   |   |--DOT -> .
     *      |   |   |   |   |--IDENT -> com
     *      |   |   |   |   `--IDENT -> example
     *      |   |   |   `--IDENT -> api
     *      |   |   `--IDENT -> Service
     *      |   `--SEMI -> ;
     *      `--RCURLY -> }
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7">
     *     Java Language Specification, &sect;7.7</a>
     * @see #MODULE_DEF
     *
     * @since 14.0.0
     */
    public static final int DIRECTIVE_BLOCK = JavaLanguageLexer.DIRECTIVE_BLOCK;

    /**
     * A requires directive. Declares a dependence of the current module on
     * another module. Its children are optional {@link #LITERAL_TRANSITIVE}
     * or {@link #LITERAL_STATIC} modifiers, the name of the required module,
     * and a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * requires transitive java.sql;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * REQUIRES -> requires
     *  |--LITERAL_TRANSITIVE -> transitive
     *  |--DOT -> .
     *  |   |--IDENT -> java
     *  |   `--IDENT -> sql
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.1">
     *     Java Language Specification, &sect;7.7.1</a>
     * @see #DIRECTIVE_BLOCK
     * @see #LITERAL_TRANSITIVE
     * @see #LITERAL_STATIC
     *
     * @since 14.0.0
     */
    public static final int REQUIRES = JavaLanguageLexer.REQUIRES;

    /**
     * An exports directive. Makes a package of the current module accessible
     * to other modules. Its children are the exported package name, an
     * optional {@link #TO} clause, and a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * exports com.example.api to com.example.one, com.example.two;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * EXPORTS -> exports
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> api
     *  |--TO -> to
     *  |   |--DOT -> .
     *  |   |   |--DOT -> .
     *  |   |   |   |--IDENT -> com
     *  |   |   |   `--IDENT -> example
     *  |   |   `--IDENT -> one
     *  |   |--COMMA -> ,
     *  |   `--DOT -> .
     *  |       |--DOT -> .
     *  |       |   |--IDENT -> com
     *  |       |   `--IDENT -> example
     *  |       `--IDENT -> two
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.2">
     *     Java Language Specification, &sect;7.7.2</a>
     * @see #DIRECTIVE_BLOCK
     * @see #TO
     *
     * @since 14.0.0
     */
    public static final int EXPORTS = JavaLanguageLexer.EXPORTS;

    /**
     * An opens directive. Makes a package of the current module available for
     * deep reflection to other modules. Its children are the opened package
     * name, an optional {@link #TO} clause, and a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * opens com.example.model;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * OPENS -> opens
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> model
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.2">
     *     Java Language Specification, &sect;7.7.2</a>
     * @see #DIRECTIVE_BLOCK
     * @see #TO
     *
     * @since 14.0.0
     */
    public static final int OPENS = JavaLanguageLexer.OPENS;

    /**
     * A uses directive. Declares a service whose implementations the current
     * module discovers via {@code java.util.ServiceLoader}. Its children are
     * the service type name and a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * uses com.example.api.Service;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * USES -> uses
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--DOT -> .
     *  |   |   |   |--IDENT -> com
     *  |   |   |   `--IDENT -> example
     *  |   |   `--IDENT -> api
     *  |   `--IDENT -> Service
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.3">
     *     Java Language Specification, &sect;7.7.3</a>
     * @see #DIRECTIVE_BLOCK
     *
     * @since 14.0.0
     */
    public static final int USES = JavaLanguageLexer.USES;

    /**
     * A provides directive. Declares implementations of a service that the
     * current module supplies. Its children are the service type name, a
     * {@link #WITH} clause, and a semicolon.
     *
     * <p>For example:</p>
     * {@snippet :
     * provides com.example.Service with com.example.Impl;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * PROVIDES -> provides
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> Service
     *  |--WITH -> with
     *  |   `--DOT -> .
     *  |       |--DOT -> .
     *  |       |   |--IDENT -> com
     *  |       |   `--IDENT -> example
     *  |       `--IDENT -> Impl
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.4">
     *     Java Language Specification, &sect;7.7.4</a>
     * @see #DIRECTIVE_BLOCK
     * @see #WITH
     *
     * @since 14.0.0
     */
    public static final int PROVIDES = JavaLanguageLexer.PROVIDES;

    /**
     * A to clause. Limits an {@link #EXPORTS} or {@link #OPENS} directive to
     * the named friend modules. Its children are the module names, separated
     * by commas.
     *
     * <p>For example:</p>
     * {@snippet :
     * opens com.example.secrets to com.example.friend;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * OPENS -> opens
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> secrets
     *  |--TO -> to
     *  |   `--DOT -> .
     *  |       |--DOT -> .
     *  |       |   |--IDENT -> com
     *  |       |   `--IDENT -> example
     *  |       `--IDENT -> friend
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.2">
     *     Java Language Specification, &sect;7.7.2</a>
     * @see #EXPORTS
     * @see #OPENS
     *
     * @since 14.0.0
     */
    public static final int TO = JavaLanguageLexer.TO;

    /**
     * A with clause. Names the implementation types supplied by a
     * {@link #PROVIDES} directive. Its children are the implementation type
     * names, separated by commas.
     *
     * <p>For example:</p>
     * {@snippet :
     * provides com.example.Service with com.example.Impl;
     * }
     *
     * <p>parses as:</p>
     * {@snippet :
     * PROVIDES -> provides
     *  |--DOT -> .
     *  |   |--DOT -> .
     *  |   |   |--IDENT -> com
     *  |   |   `--IDENT -> example
     *  |   `--IDENT -> Service
     *  |--WITH -> with
     *  |   `--DOT -> .
     *  |       |--DOT -> .
     *  |       |   |--IDENT -> com
     *  |       |   `--IDENT -> example
     *  |       `--IDENT -> Impl
     *  `--SEMI -> ;
     * }
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.4">
     *     Java Language Specification, &sect;7.7.4</a>
     * @see #PROVIDES
     *
     * @since 14.0.0
     */
    public static final int WITH = JavaLanguageLexer.WITH;

    /** Prevent instantiation. */
    private TokenTypes() {
    }

}
