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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that there are no
 * <a href="https://en.wikipedia.org/wiki/Magic_number_%28programming%29">
 * &quot;magic numbers&quot;</a> where a magic
 * number is a numeric literal that is not defined as a constant.
 * By default, -1, 0, 1, and 2 are not considered to be magic numbers.
 * </div>
 *
 * <p>Constant definition is any variable/field that has 'final' modifier.
 * It is fine to have one constant defining multiple numeric literals within one expression:
 * </p>
 * <pre>
 * static final int SECONDS_PER_DAY = 24 * 60 * 60;
 * static final double SPECIAL_RATIO = 4.0 / 3.0;
 * static final double SPECIAL_SUM = 1 + Math.E;
 * static final double SPECIAL_DIFFERENCE = 4 - Math.PI;
 * static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
 * static final Integer ANSWER_TO_THE_ULTIMATE_QUESTION_OF_LIFE = new Integer(42);
 * </pre>
 * <ul>
 * <li>
 * Property {@code constantWaiverParentToken} - Specify tokens that are allowed in the AST path
 * from the number literal to the enclosing constant definition.
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenTypesSet}.
 * Default value is
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ARRAY_INIT">
 * ARRAY_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ASSIGN">
 * ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND">
 * BAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BNOT">
 * BNOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR">
 * BOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR">
 * BSR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR">
 * BXOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COLON">
 * COLON</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV">
 * DIV</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ELIST">
 * ELIST</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EQUAL">
 * EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EXPR">
 * EXPR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GE">
 * GE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GT">
 * GT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LE">
 * LE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NEW">
 * LITERAL_NEW</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LT">
 * LT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_CALL">
 * METHOD_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS">
 * MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD">
 * MOD</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NOT_EQUAL">
 * NOT_EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS">
 * PLUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL">
 * SL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR">
 * SR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR">
 * STAR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#TYPECAST">
 * TYPECAST</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#UNARY_MINUS">
 * UNARY_MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#UNARY_PLUS">
 * UNARY_PLUS</a>.
 * </li>
 * <li>
 * Property {@code ignoreAnnotation} - Ignore magic numbers in annotation declarations.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreAnnotationElementDefaults} -
 * Ignore magic numbers in annotation elements defaults.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code ignoreFieldDeclaration} - Ignore magic numbers in field declarations.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreHashCodeMethod} - Ignore magic numbers in hashCode methods.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreNumbers} - Specify non-magic numbers.
 * Type is {@code double[]}.
 * Default value is {@code -1, 0, 1, 2}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_DOUBLE">
 * NUM_DOUBLE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_FLOAT">
 * NUM_FLOAT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_INT">
 * NUM_INT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_LONG">
 * NUM_LONG</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code magic.number}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class MagicNumberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "magic.number";

    /**
     * Specify tokens that are allowed in the AST path from the
     * number literal to the enclosing constant definition.
     */
    @XdocsPropertyType(PropertyType.TOKEN_ARRAY)
    private BitSet constantWaiverParentToken = TokenUtil.asBitSet(
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
        TokenTypes.QUESTION,
        TokenTypes.COLON,
        TokenTypes.EQUAL,
        TokenTypes.NOT_EQUAL,
        TokenTypes.MOD,
        TokenTypes.SR,
        TokenTypes.BSR,
        TokenTypes.GE,
        TokenTypes.GT,
        TokenTypes.SL,
        TokenTypes.LE,
        TokenTypes.LT,
        TokenTypes.BXOR,
        TokenTypes.BOR,
        TokenTypes.BNOT,
        TokenTypes.BAND
    );

    /** Specify non-magic numbers. */
    private double[] ignoreNumbers = {-1, 0, 1, 2};

    /** Ignore magic numbers in hashCode methods. */
    private boolean ignoreHashCodeMethod;

    /** Ignore magic numbers in annotation declarations. */
    private boolean ignoreAnnotation;

    /** Ignore magic numbers in field declarations. */
    private boolean ignoreFieldDeclaration;

    /** Ignore magic numbers in annotation elements defaults. */
    private boolean ignoreAnnotationElementDefaults = true;

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
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldTestAnnotationArgs(ast)
                && shouldTestAnnotationDefaults(ast)
                && !isInIgnoreList(ast)
                && shouldCheckHashCodeMethod(ast)
                && shouldCheckFieldDeclaration(ast)) {
            final DetailAST constantDefAST = findContainingConstantDef(ast);
            if (isMagicNumberExists(ast, constantDefAST)) {
                reportMagicNumber(ast);
            }
        }
    }

    /**
     * Checks if ast is annotation argument and should be checked.
     *
     * @param ast token to check
     * @return true if element is skipped, false otherwise
     */
    private boolean shouldTestAnnotationArgs(DetailAST ast) {
        return !ignoreAnnotation || !isChildOf(ast, TokenTypes.ANNOTATION);
    }

    /**
     * Checks if ast is annotation element default value and should be checked.
     *
     * @param ast token to check
     * @return true if element is skipped, false otherwise
     */
    private boolean shouldTestAnnotationDefaults(DetailAST ast) {
        return !ignoreAnnotationElementDefaults || !isChildOf(ast, TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks if the given AST node is a HashCode Method and should be checked.
     *
     * @param ast the AST node to check
     * @return true if element should be checked, false otherwise
     */
    private boolean shouldCheckHashCodeMethod(DetailAST ast) {
        return !ignoreHashCodeMethod || !isInHashCodeMethod(ast);
    }

    /**
     * Checks if the given AST node is a field declaration and should be checked.
     *
     * @param ast the AST node to check
     * @return true if element should be checked, false otherwise
     */
    private boolean shouldCheckFieldDeclaration(DetailAST ast) {
        return !ignoreFieldDeclaration || !isFieldDeclaration(ast);
    }

    /**
     * Is magic number somewhere at ast tree.
     *
     * @param ast ast token
     * @param constantDefAST constant ast
     * @return true if magic number is present
     */
    private boolean isMagicNumberExists(DetailAST ast, DetailAST constantDefAST) {
        boolean found = false;
        DetailAST astNode = ast.getParent();
        while (astNode != constantDefAST) {
            final int type = astNode.getType();

            if (!constantWaiverParentToken.get(type)) {
                found = true;
                break;
            }

            astNode = astNode.getParent();
        }
        return found;
    }

    /**
     * Finds the constant definition that contains aAST.
     *
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
            if (ScopeUtil.isInInterfaceOrAnnotationBlock(varDefAST)
                    || varDefAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                constantDef = varDefAST;
            }
            else {
                // explicit constant
                final DetailAST modifiersAST = varDefAST.findFirstToken(TokenTypes.MODIFIERS);

                if (modifiersAST.findFirstToken(TokenTypes.FINAL) != null) {
                    constantDef = varDefAST;
                }
            }
        }
        return constantDef;
    }

    /**
     * Reports aAST as a magic number, includes unary operators as needed.
     *
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
        log(reportAST,
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
        // find the method definition AST
        DetailAST currentAST = ast;
        while (currentAST != null
                && currentAST.getType() != TokenTypes.METHOD_DEF) {
            currentAST = currentAST.getParent();
        }
        final DetailAST methodDefAST = currentAST;
        boolean inHashCodeMethod = false;

        if (methodDefAST != null) {
            // Check for 'hashCode' name.
            final DetailAST identAST = methodDefAST.findFirstToken(TokenTypes.IDENT);

            if ("hashCode".equals(identAST.getText())) {
                // Check for no arguments.
                final DetailAST paramAST = methodDefAST.findFirstToken(TokenTypes.PARAMETERS);
                // we are in a 'public int hashCode()' method! The compiler will ensure
                // the method returns an 'int' and is public.
                inHashCodeMethod = !paramAST.hasChildren();
            }
        }
        return inHashCodeMethod;
    }

    /**
     * Decides whether the number of an AST is in the ignore list of this
     * check.
     *
     * @param ast the AST to check
     * @return true if the number of ast is in the ignore list of this check.
     */
    private boolean isInIgnoreList(DetailAST ast) {
        double value = CheckUtil.parseDouble(ast.getText(), ast.getType());
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
        DetailAST varDefAST = null;
        DetailAST node = ast;
        while (node != null && node.getType() != TokenTypes.OBJBLOCK) {
            if (node.getType() == TokenTypes.VARIABLE_DEF) {
                varDefAST = node;
                break;
            }
            node = node.getParent();
        }

        // contains variable declaration
        // and it is directly inside class or record declaration
        return varDefAST != null
                && (varDefAST.getParent().getParent().getType() == TokenTypes.CLASS_DEF
                || varDefAST.getParent().getParent().getType() == TokenTypes.RECORD_DEF
                || varDefAST.getParent().getParent().getType() == TokenTypes.LITERAL_NEW);

    }

    /**
     * Setter to specify tokens that are allowed in the AST path from the
     * number literal to the enclosing constant definition.
     *
     * @param tokens The string representation of the tokens interested in
     * @since 6.11
     */
    public void setConstantWaiverParentToken(String... tokens) {
        constantWaiverParentToken = TokenUtil.asBitSet(tokens);
    }

    /**
     * Setter to specify non-magic numbers.
     *
     * @param list numbers to ignore.
     * @since 3.1
     */
    public void setIgnoreNumbers(double... list) {
        ignoreNumbers = new double[list.length];
        System.arraycopy(list, 0, ignoreNumbers, 0, list.length);
        Arrays.sort(ignoreNumbers);
    }

    /**
     * Setter to ignore magic numbers in hashCode methods.
     *
     * @param ignoreHashCodeMethod decide whether to ignore
     *     hash code methods
     * @since 5.3
     */
    public void setIgnoreHashCodeMethod(boolean ignoreHashCodeMethod) {
        this.ignoreHashCodeMethod = ignoreHashCodeMethod;
    }

    /**
     * Setter to ignore magic numbers in annotation declarations.
     *
     * @param ignoreAnnotation decide whether to ignore annotations
     * @since 5.4
     */
    public void setIgnoreAnnotation(boolean ignoreAnnotation) {
        this.ignoreAnnotation = ignoreAnnotation;
    }

    /**
     * Setter to ignore magic numbers in field declarations.
     *
     * @param ignoreFieldDeclaration decide whether to ignore magic numbers
     *     in field declaration
     * @since 6.6
     */
    public void setIgnoreFieldDeclaration(boolean ignoreFieldDeclaration) {
        this.ignoreFieldDeclaration = ignoreFieldDeclaration;
    }

    /**
     * Setter to ignore magic numbers in annotation elements defaults.
     *
     * @param ignoreAnnotationElementDefaults decide whether to ignore annotation elements defaults
     * @since 8.23
     */
    public void setIgnoreAnnotationElementDefaults(boolean ignoreAnnotationElementDefaults) {
        this.ignoreAnnotationElementDefaults = ignoreAnnotationElementDefaults;
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
                break;
            }
            node = node.getParent();
        } while (node != null);

        return result;
    }

}
