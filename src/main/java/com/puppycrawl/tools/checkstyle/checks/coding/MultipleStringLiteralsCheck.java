////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Checks for multiple occurrences of the same string literal within a
 * single file.
 *
 */
@FileStatefulCheck
public class MultipleStringLiteralsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.string.literal";

    /**
     * The found strings and their tokens.
     */
    private final Map<String, List<DetailAST>> stringMap = new HashMap<>();

    /**
     * Marks the TokenTypes where duplicate strings should be ignored.
     */
    private final BitSet ignoreOccurrenceContext = new BitSet();

    /**
     * The allowed number of string duplicates in a file before an error is
     * generated.
     */
    private int allowedDuplicates = 1;

    /**
     * Pattern for matching ignored strings.
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
     * Sets the maximum allowed duplicates of a string.
     * @param allowedDuplicates The maximum number of duplicates.
     */
    public void setAllowedDuplicates(int allowedDuplicates) {
        this.allowedDuplicates = allowedDuplicates;
    }

    /**
     * Sets regular expression pattern for ignored strings.
     * @param ignoreStringsRegexp
     *        regular expression pattern for ignored strings
     * @noinspection WeakerAccess
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
     * Adds a set of tokens the check is interested in.
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
        return new int[] {TokenTypes.STRING_LITERAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isInIgnoreOccurrenceContext(ast)) {
            final String currentString = ast.getText();
            if (ignoreStringsRegexp == null || !ignoreStringsRegexp.matcher(currentString).find()) {
                List<DetailAST> hitList = stringMap.get(currentString);
                if (hitList == null) {
                    hitList = new ArrayList<>();
                    stringMap.put(currentString, hitList);
                }
                hitList.add(ast);
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
                log(firstFinding, MSG_KEY, stringListEntry.getKey(), hits.size());
            }
        }
    }

}
