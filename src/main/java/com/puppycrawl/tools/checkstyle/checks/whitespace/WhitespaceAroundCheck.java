////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Checks that a token is surrounded by whitespace.
 *
 * <p>By default the check will check the following operators:
 *  {@link TokenTypes#LITERAL_ASSERT ASSERT},
 *  {@link TokenTypes#ASSIGN ASSIGN},
 *  {@link TokenTypes#BAND BAND},
 *  {@link TokenTypes#BAND_ASSIGN BAND_ASSIGN},
 *  {@link TokenTypes#BOR BOR},
 *  {@link TokenTypes#BOR_ASSIGN BOR_ASSIGN},
 *  {@link TokenTypes#BSR BSR},
 *  {@link TokenTypes#BSR_ASSIGN BSR_ASSIGN},
 *  {@link TokenTypes#BXOR BXOR},
 *  {@link TokenTypes#BXOR_ASSIGN BXOR_ASSIGN},
 *  {@link TokenTypes#COLON COLON},
 *  {@link TokenTypes#DIV DIV},
 *  {@link TokenTypes#DIV_ASSIGN DIV_ASSIGN},
 *  {@link TokenTypes#DO_WHILE DO_WHILE},
 *  {@link TokenTypes#EQUAL EQUAL},
 *  {@link TokenTypes#GE GE},
 *  {@link TokenTypes#GT GT},
 *  {@link TokenTypes#LAND LAND},
 *  {@link TokenTypes#LCURLY LCURLY},
 *  {@link TokenTypes#LE LE},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_RETURN LITERAL_RETURN},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 *  {@link TokenTypes#LOR LOR},
 *  {@link TokenTypes#LT LT},
 *  {@link TokenTypes#MINUS MINUS},
 *  {@link TokenTypes#MINUS_ASSIGN MINUS_ASSIGN},
 *  {@link TokenTypes#MOD MOD},
 *  {@link TokenTypes#MOD_ASSIGN MOD_ASSIGN},
 *  {@link TokenTypes#NOT_EQUAL NOT_EQUAL},
 *  {@link TokenTypes#PLUS PLUS},
 *  {@link TokenTypes#PLUS_ASSIGN PLUS_ASSIGN},
 *  {@link TokenTypes#QUESTION QUESTION},
 *  {@link TokenTypes#RCURLY RCURLY},
 *  {@link TokenTypes#SL SL},
 *  {@link TokenTypes#SLIST SLIST},
 *  {@link TokenTypes#SL_ASSIGN SL_ASSIGN},
 *  {@link TokenTypes#SR SR},
 *  {@link TokenTypes#SR_ASSIGN SR_ASSIGN},
 *  {@link TokenTypes#STAR STAR},
 *  {@link TokenTypes#STAR_ASSIGN STAR_ASSIGN},
 *  {@link TokenTypes#LITERAL_ASSERT LITERAL_ASSERT},
 *  {@link TokenTypes#TYPE_EXTENSION_AND TYPE_EXTENSION_AND}.
 *
 * <p>An example of how to configure the check is:
 *
 * <pre>
 * &lt;module name="WhitespaceAround"/&gt;
 * </pre>
 *
 * <p>An example of how to configure the check for whitespace only around
 * assignment operators is:
 *
 * <pre>
 * &lt;module name="WhitespaceAround"&gt;
 *     &lt;property name="tokens"
 *               value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,
 *                      MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,
 *                      BOR_ASSIGN,BAND_ASSIGN"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>An example of how to configure the check for whitespace only around
 * curly braces is:
 * <pre>
 * &lt;module name="WhitespaceAround"&gt;
 *     &lt;property name="tokens"
 *               value="LCURLY,RCURLY"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>In addition, this check can be configured to allow empty methods, types,
 * for, while, do-while loops, lambdas and constructor bodies.
 * For example:
 *
 * <pre>{@code
 * public MyClass() {}      // empty constructor
 * public void func() {}    // empty method
 * public interface Foo {} // empty interface
 * public class Foo {} // empty class
 * public enum Foo {} // empty enum
 * MyClass c = new MyClass() {}; // empty anonymous class
 * while (i = 1) {} // empty while loop
 * for (int i = 1; i &gt; 1; i++) {} // empty for loop
 * do {} while (i = 1); // empty do-while loop
 * Runnable noop = () -> {}; // empty lambda
 * public @interface Beta {} // empty annotation type
 * }</pre>
 *
 * <p>This check does not flag as violation double brace initialization like:</p>
 * <pre>
 *   new Properties() {{
 *     setProperty("key", "value");
 *   }};
 * </pre>
 *
 * <p>To configure the check to allow empty method blocks use
 *
 * <pre>   &lt;property name="allowEmptyMethods" value="true" /&gt;</pre>
 *
 * <p>To configure the check to allow empty constructor blocks use
 *
 * <pre>   &lt;property name="allowEmptyConstructors" value="true" /&gt;</pre>
 *
 * <p>To configure the check to allow empty type blocks use
 *
 * <pre>   &lt;property name="allowEmptyTypes" value="true" /&gt;</pre>
 *
 * <p>To configure the check to allow empty loop blocks use
 *
 * <pre>   &lt;property name="allowEmptyLoops" value="true" /&gt;</pre>
 *
 * <p>To configure the check to allow empty lambdas blocks use
 *
 * <pre>   &lt;property name="allowEmptyLambdas" value="true" /&gt;</pre>
 *
 * <p>Also, this check can be configured to ignore the colon in an enhanced for
 * loop. The colon in an enhanced for loop is ignored by default
 *
 * <p>To configure the check to ignore the colon
 *
 * <pre>   &lt;property name="ignoreEnhancedForColon" value="true" /&gt;</pre>
 *
 * @author Oliver Burn
 * @author maxvetrenko
 * @author Andrei Selkin
 */
public class WhitespaceAroundCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /** Whether or not empty constructor bodies are allowed. */
    private boolean allowEmptyConstructors;
    /** Whether or not empty method bodies are allowed. */
    private boolean allowEmptyMethods;
    /** Whether or not empty classes, enums and interfaces are allowed. */
    private boolean allowEmptyTypes;
    /** Whether or not empty loops are allowed. */
    private boolean allowEmptyLoops;
    /** Whether or not empty lambda blocks are allowed. */
    private boolean allowEmptyLambdas;
    /** Whether or not empty catch blocks are allowed. */
    private boolean allowEmptyCatches;
    /** Whether or not to ignore a colon in a enhanced for loop. */
    private boolean ignoreEnhancedForColon = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ASSIGN,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.DO_WHILE,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAMBDA,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ASSIGN,
            TokenTypes.ARRAY_INIT,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.DO_WHILE,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAMBDA,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.WILDCARD_TYPE,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    /**
     * Sets whether or not empty method bodies are allowed.
     * @param allow {@code true} to allow empty method bodies.
     */
    public void setAllowEmptyMethods(boolean allow) {
        allowEmptyMethods = allow;
    }

    /**
     * Sets whether or not empty constructor bodies are allowed.
     * @param allow {@code true} to allow empty constructor bodies.
     */
    public void setAllowEmptyConstructors(boolean allow) {
        allowEmptyConstructors = allow;
    }

    /**
     * Sets whether or not to ignore the whitespace around the
     * colon in an enhanced for loop.
     * @param ignore {@code true} to ignore enhanced for colon.
     */
    public void setIgnoreEnhancedForColon(boolean ignore) {
        ignoreEnhancedForColon = ignore;
    }

    /**
     * Sets whether or not empty type bodies are allowed.
     * @param allow {@code true} to allow empty type bodies.
     */
    public void setAllowEmptyTypes(boolean allow) {
        allowEmptyTypes = allow;
    }

    /**
     * Sets whether or not empty loop bodies are allowed.
     * @param allow {@code true} to allow empty loops bodies.
     */
    public void setAllowEmptyLoops(boolean allow) {
        allowEmptyLoops = allow;
    }

    /**
     * Sets whether or not empty lambdas bodies are allowed.
     * @param allow {@code true} to allow empty lambda expressions.
     */
    public void setAllowEmptyLambdas(boolean allow) {
        allowEmptyLambdas = allow;
    }

    /**
     * Sets whether or not empty catch blocks are allowed.
     * @param allow {@code true} to allow empty catch blocks.
     */
    public void setAllowEmptyCatches(boolean allow) {
        allowEmptyCatches = allow;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int currentType = ast.getType();
        if (!isNotRelevantSituation(ast, currentType)) {
            final String line = getLine(ast.getLineNo() - 1);
            final int before = ast.getColumnNo() - 1;
            final int after = ast.getColumnNo() + ast.getText().length();

            if (before >= 0) {
                final char prevChar = line.charAt(before);
                if (shouldCheckSeparationFromPreviousToken(ast)
                        && !Character.isWhitespace(prevChar)) {
                    log(ast.getLineNo(), ast.getColumnNo(),
                            MSG_WS_NOT_PRECEDED, ast.getText());
                }
            }

            if (after < line.length()) {
                final char nextChar = line.charAt(after);
                if (shouldCheckSeparationFromNextToken(ast, nextChar)
                        && !Character.isWhitespace(nextChar)) {
                    log(ast.getLineNo(), ast.getColumnNo() + ast.getText().length(),
                            MSG_WS_NOT_FOLLOWED, ast.getText());
                }
            }
        }
    }

    /**
     * Is ast not a target of Check.
     * @param ast ast
     * @param currentType type of ast
     * @return true is ok to skip validation
     */
    private boolean isNotRelevantSituation(DetailAST ast, int currentType) {
        final int parentType = ast.getParent().getType();
        final boolean starImport = currentType == TokenTypes.STAR
                && parentType == TokenTypes.DOT;
        final boolean slistInsideCaseGroup = currentType == TokenTypes.SLIST
                && parentType == TokenTypes.CASE_GROUP;

        final boolean starImportOrSlistInsideCaseGroup = starImport || slistInsideCaseGroup;
        final boolean colonOfCaseOrDefaultOrForEach =
                isColonOfCaseOrDefault(currentType, parentType)
                        || isColonOfForEach(currentType, parentType);
        final boolean emptyBlockOrType =
                isEmptyBlock(ast, parentType)
                    || allowEmptyTypes && isEmptyType(ast);

        return starImportOrSlistInsideCaseGroup
                || colonOfCaseOrDefaultOrForEach
                || emptyBlockOrType
                || isArrayInitialization(currentType, parentType);
    }

    /**
     * Check if it should be checked if previous token is separated from current by
     * whitespace.
     * This function is needed to recognise double brace initialization as valid,
     * unfortunately its not possible to implement this functionality
     * in isNotRelevantSituation method, because in this method when we return
     * true(is not relevant) ast is later doesn't check at all. For example:
     * new Properties() {{setProperty("double curly braces", "are not a style error");
     * }};
     * For second left curly brace in first line when we would return true from
     * isNotRelevantSituation it wouldn't later check that the next token(setProperty)
     * is not separated from previous token.
     * @param ast current AST.
     * @return true if it should be checked if previous token is separated by whitespace,
     *      false otherwise.
     */
    private static boolean shouldCheckSeparationFromPreviousToken(DetailAST ast) {
        return !isPartOfDoubleBraceInitializerForPreviousToken(ast);
    }

    /**
     * Check if it should be checked if next token is separated from current by
     * whitespace. Explanation why this method is needed is identical to one
     * included in shouldCheckSeparationFromPreviousToken method.
     * @param ast current AST.
     * @param nextChar next character.
     * @return true if it should be checked if next token is separated by whitespace,
     *      false otherwise.
     */
    private static boolean shouldCheckSeparationFromNextToken(DetailAST ast, char nextChar) {
        return !(ast.getType() == TokenTypes.LITERAL_RETURN
                    && ast.getFirstChild().getType() == TokenTypes.SEMI)
                && ast.getType() != TokenTypes.ARRAY_INIT
                && !isAnonymousInnerClassEnd(ast.getType(), nextChar)
                && !isPartOfDoubleBraceInitializerForNextToken(ast);
    }

    /**
     * Check for "})" or "};" or "},". Happens with anon-inners
     * @param currentType token
     * @param nextChar next symbol
     * @return true is that is end of anon inner class
     */
    private static boolean isAnonymousInnerClassEnd(int currentType, char nextChar) {
        return currentType == TokenTypes.RCURLY
                && (nextChar == ')'
                        || nextChar == ';'
                        || nextChar == ','
                        || nextChar == '.');
    }

    /**
     * Is empty block.
     * @param ast ast
     * @param parentType parent
     * @return true is block is empty
     */
    private boolean isEmptyBlock(DetailAST ast, int parentType) {
        return isEmptyMethodBlock(ast, parentType)
                || isEmptyCtorBlock(ast, parentType)
                || isEmptyLoop(ast, parentType)
                || isEmptyLambda(ast, parentType)
                || isEmptyCatch(ast, parentType);
    }

    /**
     * Tests if a given {@code DetailAST} is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   public void myMethod(int val) {}</pre>
     * </p>
     * In the above, the method body is an empty block ("{}").
     *
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @param match the parent token type we're looking to match.
     * @return {@code true} if {@code ast} makes up part of an
     *         empty block contained under a {@code match} token type
     *         node.
     */
    private static boolean isEmptyBlock(DetailAST ast, int parentType, int match) {
        final boolean result;
        final int type = ast.getType();
        if (type == TokenTypes.RCURLY) {
            final DetailAST parent = ast.getParent();
            final DetailAST grandParent = ast.getParent().getParent();
            result = parentType == TokenTypes.SLIST
                    && parent.getFirstChild().getType() == TokenTypes.RCURLY
                    && grandParent.getType() == match;
        }
        else {
            result = type == TokenTypes.SLIST
                && parentType == match
                && ast.getFirstChild().getType() == TokenTypes.RCURLY;
        }
        return result;
    }

    /**
     * Whether colon belongs to cases or defaults.
     * @param currentType current
     * @param parentType parent
     * @return true if current token in colon of case or default tokens
     */
    private static boolean isColonOfCaseOrDefault(int currentType, int parentType) {
        return currentType == TokenTypes.COLON
                && (parentType == TokenTypes.LITERAL_DEFAULT
                        || parentType == TokenTypes.LITERAL_CASE);
    }

    /**
     * Whether colon belongs to for-each.
     * @param currentType current
     * @param parentType parent
     * @return true if current token in colon of for-each token
     */
    private boolean isColonOfForEach(int currentType, int parentType) {
        return currentType == TokenTypes.COLON
                && parentType == TokenTypes.FOR_EACH_CLAUSE
                && ignoreEnhancedForColon;
    }

    /**
     * Is array initialization.
     * @param currentType current token
     * @param parentType parent token
     * @return true is current token inside array initialization
     */
    private static boolean isArrayInitialization(int currentType, int parentType) {
        return (currentType == TokenTypes.RCURLY || currentType == TokenTypes.LCURLY)
                && (parentType == TokenTypes.ARRAY_INIT
                        || parentType == TokenTypes.ANNOTATION_ARRAY_INIT);
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * method block.
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty method block.
     */
    private boolean isEmptyMethodBlock(DetailAST ast, int parentType) {
        return allowEmptyMethods
                && isEmptyBlock(ast, parentType, TokenTypes.METHOD_DEF);
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * constructor (ctor) block.
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty constructor block.
     */
    private boolean isEmptyCtorBlock(DetailAST ast, int parentType) {
        return allowEmptyConstructors
                && isEmptyBlock(ast, parentType, TokenTypes.CTOR_DEF);
    }

    /**
     *
     * @param ast ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty loop block.
     */
    private boolean isEmptyLoop(DetailAST ast, int parentType) {
        return allowEmptyLoops
                && (isEmptyBlock(ast, parentType, TokenTypes.LITERAL_FOR)
                        || isEmptyBlock(ast, parentType, TokenTypes.LITERAL_WHILE)
                        || isEmptyBlock(ast, parentType, TokenTypes.LITERAL_DO));
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * lambda block.
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty lambda block.
     */
    private boolean isEmptyLambda(DetailAST ast, int parentType) {
        return allowEmptyLambdas && isEmptyBlock(ast, parentType, TokenTypes.LAMBDA);
    }

    /**
     * Tests if the given {@code DetailAst} is part of an allowed empty
     * catch block.
     * @param ast the {@code DetailAst} to test.
     * @param parentType the token type of {@code ast}'s parent
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty catch block.
     */
    private boolean isEmptyCatch(DetailAST ast, int parentType) {
        return allowEmptyCatches && isEmptyBlock(ast, parentType, TokenTypes.LITERAL_CATCH);
    }

    /**
     * Test if the given {@code DetailAST} is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   class Foo {}</pre>
     * </p>
     *
     * @param ast ast the {@code DetailAST} to test.
     * @return {@code true} if {@code ast} makes up part of an
     *         empty block contained under a {@code match} token type
     *         node.
     */
    private static boolean isEmptyType(DetailAST ast) {
        final int type = ast.getType();
        final DetailAST nextSibling = ast.getNextSibling();
        final DetailAST previousSibling = ast.getPreviousSibling();
        return type == TokenTypes.LCURLY
                    && nextSibling.getType() == TokenTypes.RCURLY
                || type == TokenTypes.RCURLY
                    && previousSibling != null
                    && previousSibling.getType() == TokenTypes.LCURLY;
    }

    /**
     * Check if given ast is part of double brace initializer and if it
     * should omit checking if previous token is separated by whitespace.
     * @param ast ast to check
     * @return true if it should omit checking for previous token, false otherwise
     */
    private static boolean isPartOfDoubleBraceInitializerForPreviousToken(DetailAST ast) {
        final boolean initializerBeginsAfterClassBegins = ast.getType() == TokenTypes.SLIST
                && ast.getParent().getType() == TokenTypes.INSTANCE_INIT;
        final boolean classEndsAfterInitializerEnds = ast.getType() == TokenTypes.RCURLY
                && ast.getPreviousSibling() != null
                && ast.getPreviousSibling().getType() == TokenTypes.INSTANCE_INIT;
        return initializerBeginsAfterClassBegins || classEndsAfterInitializerEnds;
    }

    /**
     * Check if given ast is part of double brace initializer and if it
     * should omit checking if next token is separated by whitespace.
     * See <a href="https://github.com/checkstyle/checkstyle/pull/2845">
     * PR#2845</a> for more information why this function was needed.
     * @param ast ast to check
     * @return true if it should omit checking for next token, false otherwise
     */
    private static boolean isPartOfDoubleBraceInitializerForNextToken(DetailAST ast) {
        final boolean classBeginBeforeInitializerBegin = ast.getType() == TokenTypes.LCURLY
            && ast.getNextSibling().getType() == TokenTypes.INSTANCE_INIT;
        final boolean initalizerEndsBeforeClassEnds = ast.getType() == TokenTypes.RCURLY
            && ast.getParent().getType() == TokenTypes.SLIST
            && ast.getParent().getParent().getType() == TokenTypes.INSTANCE_INIT
            && ast.getParent().getParent().getNextSibling().getType() == TokenTypes.RCURLY;
        return classBeginBeforeInitializerBegin || initalizerEndsBeforeClassEnds;
    }
}
