////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * <p>
 * Checks that there are no <a href="http://en.wikipedia.org/wiki/Magic_number_%28programming%29">
 * &quot;magic numbers&quot;</a> where a magic
 * number is a numeric literal that is not defined as a constant.
 * By default, -1, 0, 1, and 2 are not considered to be magic numbers.
 * </p>
 * <p>
 * It is fine to have one constant defining multiple numeric literals within one expression:
 * <pre>
 * <code>static final int SECONDS_PER_DAY = 24 * 60 * 60;
 * static final double SPECIAL_RATIO = 4.0 / 3.0;
 * static final double SPECIAL_SUM = 1 + Math.E;
 * static final double SPECIAL_DIFFERENCE = 4 - Math.PI;
 * static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
 * static final Integer ANSWER_TO_THE_ULTIMATE_QUESTION_OF_LIFE = new Integer(42);</code>
 * </pre>
 * </p>
 * <p>
 * Check have following options:
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
 * <code>
 *   {@literal @}MyAnnotation(6) // violation
 *   class MyClass {
 *       private field = 7; // violation
 *
 *       void foo() {
 *          int i = i + 1; // no violation
 *          int j = j + 8; // violation
 *       }
 *   }
 * </code>
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
 * <code>
 *   {@literal @}MyAnnotation(6) // no violation
 *   class MyClass {
 *       private field = 7; // no violation
 *
 *       void foo() {
 *          int i = i + 1; // no violation
 *          int j = j + 8; // violation
 *       }
 *   }
 * </code>
 * </pre>
 *
 * @author Rick Giles
 * @author Lars Kühne
 * @author Daniel Solano Gómez
 */
public class MagicNumberCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "magic.number";

    /**
     * The token types that are allowed in the AST path from the
     * number literal to the enclosing constant definition.
     */
    private static final int[] ALLOWED_PATH_TOKENTYPES = {
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

    static {
        Arrays.sort(ALLOWED_PATH_TOKENTYPES);
    }

    /** the numbers to ignore in the check, sorted */
    private double[] ignoreNumbers = {-1, 0, 1, 2};

    /** Whether to ignore magic numbers in a hash code method. */
    private boolean ignoreHashCodeMethod;

    /** Whether to ignore magic numbers in annotation. */
    private boolean ignoreAnnotation;

    /** Whether to ignore magic numbers in field declaration. */
    private boolean ignoreFieldDeclaration;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
        };
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
    public void visitToken(DetailAST ast) {
        if (ignoreAnnotation && isChildOf(ast, TokenTypes.ANNOTATION)) {
            return;
        }

        if (inIgnoreList(ast)
            || ignoreHashCodeMethod && isInHashCodeMethod(ast)) {
            return;
        }

        final DetailAST constantDefAST = findContainingConstantDef(ast);

        if (constantDefAST == null) {
            if (!(ignoreFieldDeclaration && isFieldDeclaration(ast))) {
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

    /**
     * is magic number some where at ast tree
     * @param ast ast token
     * @param constantDefAST constant ast
     * @return true if magic number is present
     */
    private boolean isMagicNumberExists(DetailAST ast, DetailAST constantDefAST) {
        boolean found = false;
        DetailAST astNode = ast.getParent();
        while (astNode != constantDefAST) {
            final int type = astNode.getType();
            if (Arrays.binarySearch(ALLOWED_PATH_TOKENTYPES, type) < 0) {
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
     * @return the constant def or null if ast is not
     * contained in a constant definition
     */
    private DetailAST findContainingConstantDef(DetailAST ast) {
        DetailAST varDefAST = ast;
        while (varDefAST != null
                && varDefAST.getType() != TokenTypes.VARIABLE_DEF
                && varDefAST.getType() != TokenTypes.ENUM_CONSTANT_DEF) {
            varDefAST = varDefAST.getParent();
        }

        // no containing variable definition?
        if (varDefAST == null) {
            return null;
        }

        // implicit constant?
        if (ScopeUtils.inInterfaceOrAnnotationBlock(varDefAST)
            || varDefAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            return varDefAST;
        }

        // explicit constant
        final DetailAST modifiersAST =
                varDefAST.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersAST.branchContains(TokenTypes.FINAL)) {
            return varDefAST;
        }

        return null;
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
     * method definition
     *
     * @return {@code true} if {@code ast} is in the scope of a valid hash
     * code method
     */
    private boolean isInHashCodeMethod(DetailAST ast) {
        // if not in a code block, can't be in hashCode()
        if (!ScopeUtils.inCodeBlock(ast)) {
            return false;
        }

        // find the method definition AST
        DetailAST methodDefAST = ast.getParent();
        while (null != methodDefAST
                && TokenTypes.METHOD_DEF != methodDefAST.getType()) {
            methodDefAST = methodDefAST.getParent();
        }

        if (null == methodDefAST) {
            return false;
        }

        // Check for 'hashCode' name.
        final DetailAST identAST =
            methodDefAST.findFirstToken(TokenTypes.IDENT);
        if (!"hashCode".equals(identAST.getText())) {
            return false;
        }

        // Check for no arguments.
        final DetailAST paramAST =
            methodDefAST.findFirstToken(TokenTypes.PARAMETERS);
        // we are in a 'public int hashCode()' method! The compiler will ensure
        // the method returns an 'int' and is public.
        return 0 == paramAST.getChildCount();
    }

    /**
     * Decides whether the number of an AST is in the ignore list of this
     * check.
     * @param ast the AST to check
     * @return true if the number of ast is in the ignore list of this
     * check.
     */
    private boolean inIgnoreList(DetailAST ast) {
        double value = CheckUtils.parseDouble(ast.getText(), ast.getType());
        final DetailAST parent = ast.getParent();
        if (parent.getType() == TokenTypes.UNARY_MINUS) {
            value = -1 * value;
        }
        return Arrays.binarySearch(ignoreNumbers, value) >= 0;
    }

    /**
     * Determines whether or not the given AST is field declaration
     *
     * @param ast AST from which to search for an enclosing field declaration
     *
     * @return {@code true} if {@code ast} is in the scope of field declaration
     */
    private boolean isFieldDeclaration(DetailAST ast) {
        DetailAST varDefAST = ast;
        while (varDefAST != null
                && varDefAST.getType() != TokenTypes.VARIABLE_DEF) {
            varDefAST = varDefAST.getParent();
        }

        // contains variable declaration
        // and it is directly inside class declaration
        return varDefAST != null
                && varDefAST.getParent().getParent().getType()
                    == TokenTypes.CLASS_DEF;
    }


    /**
     * Sets the numbers to ignore in the check.
     * BeanUtils converts numeric token list to double array automatically.
     * @param list list of numbers to ignore.
     */
    public void setIgnoreNumbers(double... list) {
        if (list.length == 0) {
            ignoreNumbers = new double[0];
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
     * hash code methods
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
     * in field declaration
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
