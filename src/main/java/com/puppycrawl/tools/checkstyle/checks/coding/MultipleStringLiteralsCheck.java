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

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for multiple occurrences of the same string literal within a
 * single file.
 *
 * @author Daniel Grenner
 */
public class MultipleStringLiteralsCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.string.literal";

    /**
     * The found strings and their positions.
     * {@code <String, ArrayList>}, with the ArrayList containing StringInfo
     * objects.
     */
    private final Map<String, List<StringInfo>> stringMap = Maps.newHashMap();

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
    private Pattern pattern;

    /**
     * Construct an instance with default values.
     */
    public MultipleStringLiteralsCheck() {
        setIgnoreStringsRegexp("^\"\"$");
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
     * @throws org.apache.commons.beanutils.ConversionException
     *         if unable to create Pattern object
     */
    public final void setIgnoreStringsRegexp(String ignoreStringsRegexp) {
        if (ignoreStringsRegexp != null
            && !ignoreStringsRegexp.isEmpty()) {
            pattern = Utils.createPattern(ignoreStringsRegexp);
        }
        else {
            pattern = null;
        }
    }

    /**
     * Adds a set of tokens the check is interested in.
     * @param strRep the string representation of the tokens interested in
     */
    public final void setIgnoreOccurrenceContext(String... strRep) {
        ignoreOccurrenceContext.clear();
        for (final String s : strRep) {
            final int type = Utils.getTokenId(s);
            ignoreOccurrenceContext.set(type);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.STRING_LITERAL};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.STRING_LITERAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isInIgnoreOccurrenceContext(ast)) {
            return;
        }
        final String currentString = ast.getText();
        if (pattern == null || !pattern.matcher(currentString).find()) {
            List<StringInfo> hitList = stringMap.get(currentString);
            if (hitList == null) {
                hitList = Lists.newArrayList();
                stringMap.put(currentString, hitList);
            }
            final int line = ast.getLineNo();
            final int col = ast.getColumnNo();
            hitList.add(new StringInfo(line, col));
        }
    }

    /**
     * Analyses the path from the AST root to a given AST for occurrences
     * of the token types in {@link #ignoreOccurrenceContext}.
     *
     * @param ast the node from where to start searching towards the root node
     * @return whether the path from the root node to ast contains one of the
     * token type in {@link #ignoreOccurrenceContext}.
     */
    private boolean isInIgnoreOccurrenceContext(DetailAST ast) {
        for (DetailAST token = ast;
             token.getParent() != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (ignoreOccurrenceContext.get(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        super.beginTree(rootAST);
        stringMap.clear();
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (Map.Entry<String, List<StringInfo>> stringListEntry : stringMap.entrySet()) {
            final List<StringInfo> hits = stringListEntry.getValue();
            if (hits.size() > allowedDuplicates) {
                final StringInfo firstFinding = hits.get(0);
                final int line = firstFinding.getLine();
                final int col = firstFinding.getCol();
                log(line, col, MSG_KEY, stringListEntry.getKey(), hits.size());
            }
        }
    }

    /**
     * This class contains information about where a string was found.
     */
    private static final class StringInfo {
        /**
         * Line of finding
         */
        private final int line;
        /**
         * Column of finding
         */
        private final int col;
        /**
         * Creates information about a string position.
         * @param line int
         * @param col int
         */
        public StringInfo(int line, int col) {
            this.line = line;
            this.col = col;
        }

        /**
         * The line where a string was found.
         * @return int Line of the string.
         */
        private int getLine() {
            return line;
        }

        /**
         * The column where a string was found.
         * @return int Column of the string.
         */
        private int getCol() {
            return col;
        }
    }

}
