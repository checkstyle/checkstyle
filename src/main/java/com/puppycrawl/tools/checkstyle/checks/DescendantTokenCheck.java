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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;
import java.util.Set;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Checks for restricted tokens beneath other tokens.
 * </p>
 * <p>
 * Examples of how to configure the check:
 * </p>
 * <pre>
 * &lt;!-- String literal equality check --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="EQUAL,NOT_EQUAL"/&gt;
 *     &lt;property name="limitedTokens" value="STRING_LITERAL"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="maximumDepth" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Switch with no default --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_DEFAULT"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Assert statement may have side effects --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_ASSERT"/&gt;
 *     &lt;property name="limitedTokens" value="ASSIGN,DEC,INC,POST_DEC,
 *     POST_INC,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,
 *     BSR_ASSIGN,SR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,
 *     METHOD_CALL"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Initialiser in for performs no setup - use while instead? --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="FOR_INIT"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Condition in for performs no check --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="FOR_CONDITION"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Switch within switch --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="minimumDepth" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Return from within a catch or finally block --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_FINALLY,LITERAL_CATCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_RETURN"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Try within catch or finally block --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_CATCH,LITERAL_FINALLY"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_TRY"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many cases within a switch --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_CASE"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many local variables within a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many returns from within a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_RETURN"/&gt;
 *     &lt;property name="maximumNumber" value="3"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many fields within an interface --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="INTERFACE_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Limit the number of exceptions a method can throw --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_THROWS"/&gt;
 *     &lt;property name="limitedTokens" value="IDENT"/&gt;
 *     &lt;property name="maximumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Limit the number of expressions in a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="maximumNumber" value="200"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Disallow empty statements --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="EMPTY_STAT"/&gt;
 *     &lt;property name="limitedTokens" value="EMPTY_STAT"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="maximumDepth" value="0"/&gt;
 *     &lt;property name="maximumMessage"
 *         value="Empty statement is not allowed."/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many fields within a class --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="CLASS_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Tim Tyler &lt;tim@tt1.org&gt;
 * @author Rick Giles
 */
public class DescendantTokenCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MIN = "descendant.token.min";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MAX = "descendant.token.max";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SUM_MIN = "descendant.token.sum.min";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SUM_MAX = "descendant.token.sum.max";

    /** minimum depth */
    private int minimumDepth;
    /** maximum depth */
    private int maximumDepth = Integer.MAX_VALUE;
    /** minimum number */
    private int minimumNumber;
    /** maximum number */
    private int maximumNumber = Integer.MAX_VALUE;
    /** Whether to sum the number of tokens found. */
    private boolean sumTokenCounts;
    /** limited tokens */
    private int[] limitedTokens = new int[0];
    /** error message when minimum count not reached */
    private String minimumMessage;
    /** error message when maximum count exceeded */
    private String maximumMessage;

    /**
     * Counts of descendant tokens.
     * Indexed by (token ID - 1) for performance.
     */
    private int[] counts = new int[0];

    @Override
    public int[] getDefaultTokens() {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST ast) {
        //reset counts
        Arrays.fill(counts, 0);
        countTokens(ast, 0);

        if (sumTokenCounts) {
            logAsTotal(ast);
        }
        else {
            logAsSeparated(ast);
        }
    }

    /**
     * log violations for each Token
     * @param ast token
     */
    private void logAsSeparated(DetailAST ast) {
        // name of this token
        final String name = Utils.getTokenName(ast.getType());

        for (int element : limitedTokens) {
            final int tokenCount = counts[element - 1];
            if (tokenCount < minimumNumber) {
                final String descendantName = Utils
                        .getTokenName(element);
                log(ast.getLineNo(), ast.getColumnNo(),
                    minimumMessage == null ? MSG_KEY_MIN
                                : minimumMessage,
                        String.valueOf(tokenCount),
                        String.valueOf(minimumNumber),
                        name,
                        descendantName);
            }
            if (tokenCount > maximumNumber) {
                final String descendantName = Utils
                        .getTokenName(element);
                log(ast.getLineNo(), ast.getColumnNo(),
                    maximumMessage == null ? MSG_KEY_MAX
                                : maximumMessage,
                        String.valueOf(tokenCount),
                        String.valueOf(maximumNumber),
                        name,
                        descendantName);
            }
        }
    }

    /**
     * log validation as one violation
     * @param ast curent token
     */
    private void logAsTotal(DetailAST ast) {
        // name of this token
        final String name = Utils.getTokenName(ast.getType());

        int total = 0;
        for (int element : limitedTokens) {
            total += counts[element - 1];
        }
        if (total < minimumNumber) {
            log(ast.getLineNo(), ast.getColumnNo(),
                minimumMessage == null ? MSG_KEY_SUM_MIN
                            : minimumMessage,
                    String.valueOf(total),
                    String.valueOf(minimumNumber), name);
        }
        if (total > maximumNumber) {
            log(ast.getLineNo(), ast.getColumnNo(),
                maximumMessage == null ? MSG_KEY_SUM_MAX
                            : maximumMessage,
                    String.valueOf(total),
                    String.valueOf(maximumNumber),
                    name);
        }
    }

    /**
     * Counts the number of occurrences of descendant tokens.
     * @param ast the root token for descendants.
     * @param depth the maximum depth of the counted descendants.
     */
    private void countTokens(AST ast, int depth) {
        if (depth <= maximumDepth) {
            //update count
            if (depth >= minimumDepth) {
                final int type = ast.getType();
                if (type <= counts.length) {
                    counts[type - 1]++;
                }
            }
            AST child = ast.getFirstChild();
            final int nextDepth = depth + 1;
            while (child != null) {
                countTokens(child, nextDepth);
                child = child.getNextSibling();
            }
        }
    }

    @Override
    public int[] getAcceptableTokens() {
        // Any tokens set by property 'tokens' are acceptable
        final Set<String> tokenNames = getTokenNames();
        final int[] result = new int[tokenNames.size()];
        int i = 0;
        for (String name : tokenNames) {
            result[i] = Utils.getTokenId(name);
            i++;
        }
        return result;
    }

    /**
     * Sets the tokens which occurance as descendant is limited.
     * @param limitedTokensParam - list of tokens to ignore.
     */
    public void setLimitedTokens(String... limitedTokensParam) {
        limitedTokens = new int[limitedTokensParam.length];

        int maxToken = 0;
        for (int i = 0; i < limitedTokensParam.length; i++) {
            limitedTokens[i] = Utils.getTokenId(limitedTokensParam[i]);
            if (limitedTokens[i] > maxToken) {
                maxToken = limitedTokens[i];
            }
        }
        counts = new int[maxToken];
    }

    /**
     * Sets the minimum depth for descendant counts.
     * @param minimumDepth the minimum depth for descendant counts.
     */
    public void setMinimumDepth(int minimumDepth) {
        this.minimumDepth = minimumDepth;
    }

    /**
     * Sets the maximum depth for descendant counts.
     * @param maximumDepth the maximum depth for descendant counts.
     */
    public void setMaximumDepth(int maximumDepth) {
        this.maximumDepth = maximumDepth;
    }

    /**
     * Sets a minimum count for descendants.
     * @param minimumNumber the minimum count for descendants.
     */
    public void setMinimumNumber(int minimumNumber) {
        this.minimumNumber = minimumNumber;
    }

    /**
      * Sets a maximum count for descendants.
      * @param maximumNumber the maximum count for descendants.
      */
    public void setMaximumNumber(int maximumNumber) {
        this.maximumNumber = maximumNumber;
    }

    /**
     * Sets the error message for minimum count not reached.
     * @param message the error message for minimum count not reached.
     * Used as a {@code MessageFormat} pattern with arguments
     * <ul>
     * <li>{0} - token count</li>
     * <li>{1} - minimum number</li>
     * <li>{2} - name of token</li>
     * <li>{3} - name of limited token</li>
     * </ul>
     */
    public void setMinimumMessage(String message) {
        minimumMessage = message;
    }

    /**
     * Sets the error message for maximum count exceeded.
     * @param message the error message for maximum count exceeded.
     * Used as a {@code MessageFormat} pattern with arguments
     * <ul>
     * <li>{0} - token count</li>
     * <li>{1} - maximum number</li>
     * <li>{2} - name of token</li>
     * <li>{3} - name of limited token</li>
     * </ul>
     */

    public void setMaximumMessage(String message) {
        maximumMessage = message;
    }

    /**
     * Sets whether to use the sum of the tokens found, rather than the
     * individual counts.
     * @param sum whether to use the sum.
     */
    public void setSumTokenCounts(boolean sum) {
        sumTokenCounts = sum;
    }
}
