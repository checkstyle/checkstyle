///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for multiple occurrences of the same string literal within a single file.
 * </p>
 * <p>
 * Rationale: Code duplication makes maintenance more difficult, so it can be better
 * to replace the multiple occurrences with a constant.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowedDuplicates} - Specify the maximum number of occurrences
 * to allow without generating a warning.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * <li>
 * Property {@code ignoreStringsRegexp} - Specify RegExp for ignored strings (with quotation marks).
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^""$"}.
 * </li>
 * <li>
 * Property {@code ignoreOccurrenceContext} - Specify token type names where duplicate
 * strings are ignored even if they don't match ignoredStringsRegexp. This allows you to
 * exclude syntactical contexts like annotations or static initializers from the check.
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenTypesSet}.
 * Default value is {@code ANNOTATION}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MultipleStringLiterals&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   String a = "StringContents";
 *   String a1 = "unchecked";
 *   &#64;SuppressWarnings("unchecked") // OK, duplicate strings are ignored in annotations
 *   public void myTest() {
 *     String a2 = "StringContents"; // violation, "StringContents" occurs twice
 *     String a3 = "DoubleString" + "DoubleString"; // violation, "DoubleString" occurs twice
 *     String a4 = "SingleString"; // OK
 *     String a5 = ", " + ", " + ", "; // violation, ", " occurs three times
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check so that it allows two occurrences of each string:
 * </p>
 * <pre>
 * &lt;module name=&quot;MultipleStringLiterals&quot;&gt;
 *   &lt;property name=&quot;allowedDuplicates&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   String a = "StringContents";
 *   String a1 = "unchecked";
 *   &#64;SuppressWarnings("unchecked") // OK, duplicate strings are ignored in annotations
 *   public void myTest() {
 *     String a2 = "StringContents"; // OK, two occurrences are allowed
 *     String a3 = "DoubleString" + "DoubleString"; // OK, two occurrences are allowed
 *     String a4 = "SingleString"; // OK
 *     String a5 = ", " + ", " + ", "; // violation, three occurrences are NOT allowed
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check so that it ignores ", " and empty strings:
 * </p>
 * <pre>
 * &lt;module name=&quot;MultipleStringLiterals&quot;&gt;
 *   &lt;property name=&quot;ignoreStringsRegexp&quot;
 *     value='^((&quot;&quot;)|(&quot;, &quot;))$'/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   String a = "StringContents";
 *   String a1 = "unchecked";
 *   &#64;SuppressWarnings("unchecked") // OK, duplicate strings are ignored in annotations
 *   public void myTest() {
 *     String a2 = "StringContents"; // violation, "StringContents" occurs twice
 *     String a3 = "DoubleString" + "DoubleString"; // violation, "DoubleString" occurs twice
 *     String a4 = "SingleString"; // OK
 *     String a5 = ", " + ", " + ", "; // OK, multiple occurrences of ", " are allowed
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check so that it flags duplicate strings in all syntactical contexts,
 * even in annotations like {@code @SuppressWarnings("unchecked")}:
 * </p>
 * <pre>
 * &lt;module name=&quot;MultipleStringLiterals&quot;&gt;
 *   &lt;property name=&quot;ignoreOccurrenceContext&quot; value=&quot;&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   String a = "StringContents";
 *   String a1 = "unchecked";
 *   &#64;SuppressWarnings("unchecked") // violation, "unchecked" occurs twice
 *   public void myTest() {
 *     String a2 = "StringContents"; // violation, "StringContents" occurs twice
 *     String a3 = "DoubleString" + "DoubleString"; // violation, "DoubleString" occurs twice
 *     String a4 = "SingleString"; // OK
 *     String a5 = ", " + ", " + ", "; // violation, ", " occurs three times
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code multiple.string.literal}
 * </li>
 * </ul>
 *
 * @since 3.5
 */
@FileStatefulCheck
public class MultipleStringLiteralsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.string.literal";

    /**
     * Compiled pattern for all system newlines.
     */
    private static final Pattern ALL_NEW_LINES = Pattern.compile("\\R");

    /**
     * String used to amend TEXT_BLOCK_CONTENT so that it matches STRING_LITERAL.
     */
    private static final String QUOTE = "\"";

    /**
     * The found strings and their tokens.
     */
    private final Map<String, List<DetailAST>> stringMap = new HashMap<>();

    /**
     * Specify token type names where duplicate strings are ignored even if they
     * don't match ignoredStringsRegexp. This allows you to exclude syntactical
     * contexts like annotations or static initializers from the check.
     */
    @XdocsPropertyType(PropertyType.TOKEN_ARRAY)
    private final BitSet ignoreOccurrenceContext = new BitSet();

    /**
     * Specify the maximum number of occurrences to allow without generating a warning.
     */
    private int allowedDuplicates = 1;

    /**
     * Specify RegExp for ignored strings (with quotation marks).
     */
    private Pattern ignoreStringsRegexp;

    /**
     * Construct an instance with default values.
     */
    public MultipleStringLiteralsCheck() {
        setIgnoreStringsRegexp(Pattern.compile("^\"\"$"));
        ignoreOccurrenceContext.set(TokenTypes.ANNOTATION);
    }

    /**
     * Setter to specify the maximum number of occurrences to allow without generating a warning.
     *
     * @param allowedDuplicates The maximum number of duplicates.
     */
    public void setAllowedDuplicates(int allowedDuplicates) {
        this.allowedDuplicates = allowedDuplicates;
    }

    /**
     * Setter to specify RegExp for ignored strings (with quotation marks).
     *
     * @param ignoreStringsRegexp
     *        regular expression pattern for ignored strings
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public final void setIgnoreStringsRegexp(Pattern ignoreStringsRegexp) {
        if (ignoreStringsRegexp == null || ignoreStringsRegexp.pattern().isEmpty()) {
            this.ignoreStringsRegexp = null;
        }
        else {
            this.ignoreStringsRegexp = ignoreStringsRegexp;
        }
    }

    /**
     * Setter to specify token type names where duplicate strings are ignored even
     * if they don't match ignoredStringsRegexp. This allows you to exclude
     * syntactical contexts like annotations or static initializers from the check.
     *
     * @param strRep the string representation of the tokens interested in
     */
    public final void setIgnoreOccurrenceContext(String... strRep) {
        ignoreOccurrenceContext.clear();
        for (final String s : strRep) {
            final int type = TokenUtil.getTokenId(s);
            ignoreOccurrenceContext.set(type);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.STRING_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isInIgnoreOccurrenceContext(ast)) {
            final String currentString;
            if (ast.getType() == TokenTypes.TEXT_BLOCK_CONTENT) {
                final String strippedString =
                    CheckUtil.stripIndentAndInitialNewLineFromTextBlock(ast.getText());
                // We need to add quotes here to be consistent with STRING_LITERAL text.
                currentString = QUOTE + strippedString + QUOTE;
            }
            else {
                currentString = ast.getText();
            }
            if (ignoreStringsRegexp == null
                    || !ignoreStringsRegexp.matcher(currentString).find()) {
                stringMap.computeIfAbsent(currentString, key -> new ArrayList<>()).add(ast);
            }
        }
    }

    /**
     * Analyses the path from the AST root to a given AST for occurrences
     * of the token types in {@link #ignoreOccurrenceContext}.
     *
     * @param ast the node from where to start searching towards the root node
     * @return whether the path from the root node to ast contains one of the
     *     token type in {@link #ignoreOccurrenceContext}.
     */
    private boolean isInIgnoreOccurrenceContext(DetailAST ast) {
        boolean isInIgnoreOccurrenceContext = false;
        for (DetailAST token = ast;
             token.getParent() != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (ignoreOccurrenceContext.get(type)) {
                isInIgnoreOccurrenceContext = true;
                break;
            }
        }
        return isInIgnoreOccurrenceContext;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        stringMap.clear();
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (Map.Entry<String, List<DetailAST>> stringListEntry : stringMap.entrySet()) {
            final List<DetailAST> hits = stringListEntry.getValue();
            if (hits.size() > allowedDuplicates) {
                final DetailAST firstFinding = hits.get(0);
                final String recurringString =
                    ALL_NEW_LINES.matcher(
                        stringListEntry.getKey()).replaceAll("\\\\n");
                log(firstFinding, MSG_KEY, recurringString, hits.size());
            }
        }
    }
}

