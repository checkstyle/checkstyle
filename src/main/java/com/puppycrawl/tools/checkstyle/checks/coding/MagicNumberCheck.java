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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * <p>
 * Checks that there are no <a href="https://en.wikipedia.org/wiki/Magic_number_%28programming%29">
 * &quot;magic numbers&quot;</a> where a magic
 * number is a numeric literal that is not defined as a constant.
 * By default, -1, 0, 1, and 2 are not considered to be magic numbers.
 * </p>
 *
 * <p>Constant definition is any variable/field that has 'final' modifier.
 * It is fine to have one constant defining multiple numeric literals within one expression:
 * <pre>
 * {@code static final int SECONDS_PER_DAY = 24 * 60 * 60;
 * static final double SPECIAL_RATIO = 4.0 / 3.0;
 * static final double SPECIAL_SUM = 1 + Math.E;
 * static final double SPECIAL_DIFFERENCE = 4 - Math.PI;
 * static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
 * static final Integer ANSWER_TO_THE_ULTIMATE_QUESTION_OF_LIFE = new Integer(42);}
 * </pre>
 *
 * <p>Check have following options:
 * ignoreHashCodeMethod - ignore magic numbers in hashCode methods;
 * ignoreAnnotation - ignore magic numbers in annotation declarations;
 * ignoreFieldDeclaration - ignore magic numbers in field declarations.
 * <p>
 * To configure the check with default configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;MagicNumber&quot;/&gt;
 * </pre>
 * <p>
 * results is following violations:
 * </p>
 * <pre>
 * {@code
 *   {@literal @}MyAnnotation(6) // violation
 *   class MyClass {
 *       private field = 7; // violation
 *
 *       void foo() {
 *          int i = i + 1; // no violation
 *          int j = j + 8; // violation
 *       }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check so that it checks floating-point numbers
 * that are not 0, 0.5, or 1:
 * </p>
 * <pre>
 *   &lt;module name=&quot;MagicNumber&quot;&gt;
 *       &lt;property name=&quot;tokens&quot; value=&quot;NUM_DOUBLE, NUM_FLOAT&quot;/&gt;
 *       &lt;property name=&quot;ignoreNumbers&quot; value=&quot;0, 0.5, 1&quot;/&gt;
 *       &lt;property name=&quot;ignoreFieldDeclaration&quot; value=&quot;true&quot;/&gt;
 *       &lt;property name=&quot;ignoreAnnotation&quot; value=&quot;true&quot;/&gt;
 *   &lt;/module&gt;
 * </pre>
 * <p>
 * results is following violations:
 * </p>
 * <pre>
 * {@code
 *   {@literal @}MyAnnotation(6) // no violation
 *   class MyClass {
 *       private field = 7; // no violation
 *
 *       void foo() {
 *          int i = i + 1; // no violation
 *          int j = j + (int)0.5; // no violation
 *       }
 *   }
 * }
 * </pre>
 * <p>
 * Config example of constantWaiverParentToken option:
 * </p>
 * <pre>
 *   &lt;module name=&quot;MagicNumber&quot;&gt;
 *       &lt;property name=&quot;constantWaiverParentToken&quot; value=&quot;ASSIGN,ARRAY_INIT,EXPR,
 *       UNARY_PLUS, UNARY_MINUS, TYPECAST, ELIST, DIV, PLUS &quot;/&gt;
 *   &lt;/module&gt;
 * </pre>
 * <p>
 * result is following violation:
 * </p>
 * <pre>
 * {@code
 * class TestMethodCall {
 *     public void method2() {
 *         final TestMethodCall dummyObject = new TestMethodCall(62);    //violation
 *         final int a = 3;        // ok as waiver is ASSIGN
 *         final int [] b = {4, 5} // ok as waiver is ARRAY_INIT
 *         final int c = -3;       // ok as waiver is UNARY_MINUS
 *         final int d = +4;       // ok as waiver is UNARY_PLUS
 *         final int e = method(1, 2) // ELIST is there but violation due to METHOD_CALL
 *         final int x = 3 * 4;    // violation
 *         final int y = 3 / 4;    // ok as waiver is DIV
 *         final int z = 3 + 4;    // ok as waiver is PLUS
 *         final int w = 3 - 4;    // violation
 *         final int x = (int)(3.4);    //ok as waiver is TYPECAST
 *     }
 * }
 * }
 * </pre>
 * @author Rick Giles
 * @author Lars Kühne
 * @author Daniel Solano Gómez
 */
public class MagicNumberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "magic.number";

    /**
     * The token types that are allowed in the AST path from the
     * number literal to the enclosing constant definition.
     */
    private int[] constantWaiverParentToken = {
        TokenTypes.ASSIGN,
        TokenTypes.ARRAY_INIT,
        TokenTypes.EXPR,
        TokenTypes.UNARY_PLUS,
        TokenTypes.UNARY_MINUS,
        TokenTypes.TYPECAST,
        TokenTypes.ELIST,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.STAR,
        TokenTypes.DIV,
        TokenTypes.PLUS,
        TokenTypes.MINUS,
    };

    /** The numbers to ignore in the check, sorted. */
    private double[] ignoreNumbers = {-1, 0, 1, 2};

    /** Whether to ignore magic numbers in a hash code method. */
    private boolean ignoreHashCodeMethod;

    /** Whether to ignore magic numbers in annotation. */
    private boolean ignoreAnnotation;

    /** Whether to ignore magic numbers in field declaration. */
    private boolean ignoreFieldDeclaration;

    /**
     * Constructor for MagicNumber Check.
     * Sort the allowedTokensBetweenMagicNumberAndConstDef array for binary search.
     */
    public MagicNumberCheck() {
        Arrays.sort(constantWaiverParentToken);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if ((!ignoreAnnotation || !isChildOf(ast, TokenTypes.ANNOTATION))
                && !isInIgnoreList(ast)
                && (!ignoreHashCodeMethod || !isInHashCodeMethod(ast))) {
            final DetailAST constantDefAST = findContainingConstantDef(ast);

            if (constantDefAST == null) {
                if (!ignoreFieldDeclaration || !isFieldDeclaration(ast)) {
                    reportMagicNumber(ast);
                }
            }
            else {
                final boolean found = isMagicNumberExists(ast, constantDefAST);
                if (found) {
                    reportMagicNumber(ast);
                }
            }
        }
    }

    /**
     * Is magic number some where at ast tree.
     * @param ast ast token
     * @param constantDefAST constant ast
     * @return true if magic number is present
     */
    private boolean isMagicNumberExists(DetailAST ast, DetailAST constantDefAST) {
        boolean found = false;
        DetailAST astNode = ast.getParent();
        while (astNode != constantDefAST) {
            final int type = astNode.getType();
            if (Arrays.binarySearch(constantWaiverParentToken, type) < 0) {
                found = true;
                break;
            }
            astNode = astNode.getParent();
        }
        return found;
    }

    /**
     * Finds the constant definition that contains aAST.
     * @param ast the AST
     * @return the constant def or null if ast is not contained in a constant definition.
     */
    private static DetailAST findContainingConstantDef(DetailAST ast) {
        DetailAST varDefAST = ast;
        while (varDefAST != null
                && varDefAST.getType() != TokenTypes.VARIABLE_DEF
                && varDefAST.getType() != TokenTypes.ENUM_CONSTANT_DEF) {
            varDefAST = varDefAST.getParent();
        }
        DetailAST constantDef = null;

        // no containing variable definition?
        if (varDefAST != null) {
            // implicit constant?
            if (ScopeUtils.isInInterfaceOrAnnotationBlock(varDefAST)
                    || varDefAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                constantDef = varDefAST;
            }
            else {
                // explicit constant
                final DetailAST modifiersAST = varDefAST.findFirstToken(TokenTypes.MODIFIERS);

                if (modifiersAST.branchContains(TokenTypes.FINAL)) {
                    constantDef = varDefAST;
                }
            }
        }
        return constantDef;
    }

    /**
     * Reports aAST as a magic number, includes unary operators as needed.
     * @param ast the AST node that contains the number to report
     */
    private void reportMagicNumber(DetailAST ast) {
        String text = ast.getText();
        final DetailAST parent = ast.getParent();
        DetailAST reportAST = ast;
        if (parent.getType() == TokenTypes.UNARY_MINUS) {
            reportAST = parent;
            text = "-" + text;
        }
        else if (parent.getType() == TokenTypes.UNARY_PLUS) {
            reportAST = parent;
            text = "+" + text;
        }
        log(reportAST.getLineNo(),
                reportAST.getColumnNo(),
                MSG_KEY,
                text);
    }

    /**
     * Determines whether or not the given AST is in a valid hash code method.
     * A valid hash code method is considered to be a method of the signature
     * {@code public int hashCode()}.
     *
     * @param ast the AST from which to search for an enclosing hash code
     *     method definition
     *
     * @return {@code true} if {@code ast} is in the scope of a valid hash code method.
     */
    private static boolean isInHashCodeMethod(DetailAST ast) {
        boolean inHashCodeMethod = false;

        // if not in a code block, can't be in hashCode()
        if (ScopeUtils.isInCodeBlock(ast)) {
            // find the method definition AST
            DetailAST methodDefAST = ast.getParent();
            while (methodDefAST != null
                    && methodDefAST.getType() != TokenTypes.METHOD_DEF) {
                methodDefAST = methodDefAST.getParent();
            }

            if (methodDefAST != null) {
                // Check for 'hashCode' name.
                final DetailAST identAST = methodDefAST.findFirstToken(TokenTypes.IDENT);

                if ("hashCode".equals(identAST.getText())) {
                    // Check for no arguments.
                    final DetailAST paramAST = methodDefAST.findFirstToken(TokenTypes.PARAMETERS);
                    // we are in a 'public int hashCode()' method! The compiler will ensure
                    // the method returns an 'int' and is public.
                    inHashCodeMethod = paramAST.getChildCount() == 0;
                }
            }
        }
        return inHashCodeMethod;
    }

    /**
     * Decides whether the number of an AST is in the ignore list of this
     * check.
     * @param ast the AST to check
     * @return true if the number of ast is in the ignore list of this check.
     */
    private boolean isInIgnoreList(DetailAST ast) {
        double value = CheckUtils.parseDouble(ast.getText(), ast.getType());
        final DetailAST parent = ast.getParent();
        if (parent.getType() == TokenTypes.UNARY_MINUS) {
            value = -1 * value;
        }
        return Arrays.binarySearch(ignoreNumbers, value) >= 0;
    }

    /**
     * Determines whether or not the given AST is field declaration.
     *
     * @param ast AST from which to search for an enclosing field declaration
     *
     * @return {@code true} if {@code ast} is in the scope of field declaration
     */
    private static boolean isFieldDeclaration(DetailAST ast) {
        DetailAST varDefAST = ast;
        while (varDefAST != null
                && varDefAST.getType() != TokenTypes.VARIABLE_DEF) {
            varDefAST = varDefAST.getParent();
        }

        // contains variable declaration
        // and it is directly inside class declaration
        return varDefAST != null
                && varDefAST.getParent().getParent().getType() == TokenTypes.CLASS_DEF;
    }

    /**
     * Sets the tokens which are allowed between Magic Number and defined Object.
     * @param tokens The string representation of the tokens interested in
     */
    public void setConstantWaiverParentToken(String... tokens) {
        constantWaiverParentToken = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            constantWaiverParentToken[i] = TokenUtils.getTokenId(tokens[i]);
        }
        Arrays.sort(constantWaiverParentToken);
    }

    /**
     * Sets the numbers to ignore in the check.
     * BeanUtils converts numeric token list to double array automatically.
     * @param list list of numbers to ignore.
     */
    public void setIgnoreNumbers(double... list) {
        if (list.length == 0) {
            ignoreNumbers = CommonUtils.EMPTY_DOUBLE_ARRAY;
        }
        else {
            ignoreNumbers = new double[list.length];
            System.arraycopy(list, 0, ignoreNumbers, 0, list.length);
            Arrays.sort(ignoreNumbers);
        }
    }

    /**
     * Set whether to ignore hashCode methods.
     * @param ignoreHashCodeMethod decide whether to ignore
     *     hash code methods
     */
    public void setIgnoreHashCodeMethod(boolean ignoreHashCodeMethod) {
        this.ignoreHashCodeMethod = ignoreHashCodeMethod;
    }

    /**
     * Set whether to ignore Annotations.
     * @param ignoreAnnotation decide whether to ignore annotations
     */
    public void setIgnoreAnnotation(boolean ignoreAnnotation) {
        this.ignoreAnnotation = ignoreAnnotation;
    }

    /**
     * Set whether to ignore magic numbers in field declaration.
     * @param ignoreFieldDeclaration decide whether to ignore magic numbers
     *     in field declaration
     */
    public void setIgnoreFieldDeclaration(boolean ignoreFieldDeclaration) {
        this.ignoreFieldDeclaration = ignoreFieldDeclaration;
    }

    /**
     * Determines if the given AST node has a parent node with given token type code.
     *
     * @param ast the AST from which to search for annotations
     * @param type the type code of parent token
     *
     * @return {@code true} if the AST node has a parent with given token type.
     */
    private static boolean isChildOf(DetailAST ast, int type) {
        boolean result = false;
        DetailAST node = ast;
        do {
            if (node.getType() == type) {
                result = true;
            }
            node = node.getParent();
        } while (node != null && !result);

        return result;
    }
}
