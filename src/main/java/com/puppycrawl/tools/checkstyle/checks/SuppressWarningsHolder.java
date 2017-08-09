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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Maintains a set of check suppressions from {@link SuppressWarnings}
 * annotations.
 * @author Trevor Robinson
 * @author St&eacute;phane Galland
 */
public class SuppressWarningsHolder
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "suppress.warnings.invalid.target";

    /**
     * Optional prefix for warning suppressions that are only intended to be
     * recognized by checkstyle. For instance, to suppress {@code
     * FallThroughCheck} only in checkstyle (and not in javac), use the
     * suppression {@code "checkstyle:fallthrough"} or {@code "checkstyle:FallThrough"}.
     * To suppress the warning in both tools, just use {@code "fallthrough"}.
     */
    private static final String CHECKSTYLE_PREFIX = "checkstyle:";

    /** Java.lang namespace prefix, which is stripped from SuppressWarnings */
    private static final String JAVA_LANG_PREFIX = "java.lang.";

    /** Suffix to be removed from subclasses of Check. */
    private static final String CHECK_SUFFIX = "Check";

    /** Special warning id for matching all the warnings. */
    private static final String ALL_WARNING_MATCHING_ID = "all";

    /** A map from check source names to suppression aliases. */
    private static final Map<String, String> CHECK_ALIAS_MAP = new HashMap<>();

    /**
     * A thread-local holder for the list of suppression entries for the last
     * file parsed.
     */
    private static final ThreadLocal<List<Entry>> ENTRIES = new ThreadLocal<List<Entry>>() {
        @Override
        protected List<Entry> initialValue() {
            return new LinkedList<>();
        }
    };

    /**
     * Returns the default alias for the source name of a check, which is the
     * source name in lower case with any dotted prefix or "Check" suffix
     * removed.
     * @param sourceName the source name of the check (generally the class
     *        name)
     * @return the default alias for the given check
     */
    public static String getDefaultAlias(String sourceName) {
        int endIndex = sourceName.length();
        if (sourceName.endsWith(CHECK_SUFFIX)) {
            endIndex -= CHECK_SUFFIX.length();
        }
        final int startIndex = sourceName.lastIndexOf('.') + 1;
        return sourceName.substring(startIndex, endIndex).toLowerCase(Locale.ENGLISH);
    }

    /**
     * Returns the alias for the source name of a check. If an alias has been
     * explicitly registered via {@link #registerAlias(String, String)}, that
     * alias is returned; otherwise, the default alias is used.
     * @param sourceName the source name of the check (generally the class
     *        name)
     * @return the current alias for the given check
     */
    public static String getAlias(String sourceName) {
        String checkAlias = CHECK_ALIAS_MAP.get(sourceName);
        if (checkAlias == null) {
            checkAlias = getDefaultAlias(sourceName);
        }
        return checkAlias;
    }

    /**
     * Registers an alias for the source name of a check.
     * @param sourceName the source name of the check (generally the class
     *        name)
     * @param checkAlias the alias used in {@link SuppressWarnings} annotations
     */
    private static void registerAlias(String sourceName, String checkAlias) {
        CHECK_ALIAS_MAP.put(sourceName, checkAlias);
    }

    /**
     * Registers a list of source name aliases based on a comma-separated list
     * of {@code source=alias} items, such as {@code
     * com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=
     * paramnum}.
     * @param aliasList the list of comma-separated alias assignments
     */
    public void setAliasList(String... aliasList) {
        for (String sourceAlias : aliasList) {
            final int index = sourceAlias.indexOf('=');
            if (index > 0) {
                registerAlias(sourceAlias.substring(0, index), sourceAlias
                    .substring(index + 1));
            }
            else if (!sourceAlias.isEmpty()) {
                throw new IllegalArgumentException(
                    "'=' expected in alias list item: " + sourceAlias);
            }
        }
    }

    /**
     * Checks for a suppression of a check with the given source name and
     * location in the last file processed.
     * @param event audit event.
     * @return whether the check with the given name is suppressed at the given
     *         source location
     */
    public static boolean isSuppressed(AuditEvent event) {
        final List<Entry> entries = ENTRIES.get();
        final String sourceName = event.getSourceName();
        final String checkAlias = getAlias(sourceName);
        final int line = event.getLine();
        final int column = event.getColumn();
        boolean suppressed = false;
        for (Entry entry : entries) {
            final boolean afterStart = isSuppressedAfterEventStart(line, column, entry);
            final boolean beforeEnd = isSuppressedBeforeEventEnd(line, column, entry);
            final boolean nameMatches =
                ALL_WARNING_MATCHING_ID.equals(entry.getCheckName())
                    || entry.getCheckName().equalsIgnoreCase(checkAlias);
            final boolean idMatches = event.getModuleId() != null
                && event.getModuleId().equals(entry.getCheckName());
            if (afterStart && beforeEnd && (nameMatches || idMatches)) {
                suppressed = true;
                break;
            }
        }
        return suppressed;
    }

    /**
     * Checks whether suppression entry position is after the audit event occurrence position
     * in the source file.
     * @param line the line number in the source file where the event occurred.
     * @param column the column number in the source file where the event occurred.
     * @param entry suppression entry.
     * @return true if suppression entry position is after the audit event occurrence position
     *         in the source file.
     */
    private static boolean isSuppressedAfterEventStart(int line, int column, Entry entry) {
        return entry.getFirstLine() < line
            || entry.getFirstLine() == line
            && (column == 0 || entry.getFirstColumn() <= column);
    }

    /**
     * Checks whether suppression entry position is before the audit event occurrence position
     * in the source file.
     * @param line the line number in the source file where the event occurred.
     * @param column the column number in the source file where the event occurred.
     * @param entry suppression entry.
     * @return true if suppression entry position is before the audit event occurrence position
     *         in the source file.
     */
    private static boolean isSuppressedBeforeEventEnd(int line, int column, Entry entry) {
        return entry.getLastLine() > line
            || entry.getLastLine() == line && entry
                .getLastColumn() >= column;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.ANNOTATION};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        ENTRIES.get().clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        // check whether annotation is SuppressWarnings
        // expected children: AT ( IDENT | DOT ) LPAREN <values> RPAREN
        String identifier = getIdentifier(getNthChild(ast, 1));
        if (identifier.startsWith(JAVA_LANG_PREFIX)) {
            identifier = identifier.substring(JAVA_LANG_PREFIX.length());
        }
        if ("SuppressWarnings".equals(identifier)) {

            final List<String> values = getAllAnnotationValues(ast);
            if (!isAnnotationEmpty(values)) {
                final DetailAST targetAST = getAnnotationTarget(ast);

                if (targetAST == null) {
                    log(ast.getLineNo(), MSG_KEY);
                }
                else {
                    // get text range of target
                    final int firstLine = targetAST.getLineNo();
                    final int firstColumn = targetAST.getColumnNo();
                    final DetailAST nextAST = targetAST.getNextSibling();
                    final int lastLine;
                    final int lastColumn;
                    if (nextAST == null) {
                        lastLine = Integer.MAX_VALUE;
                        lastColumn = Integer.MAX_VALUE;
                    }
                    else {
                        lastLine = nextAST.getLineNo();
                        lastColumn = nextAST.getColumnNo() - 1;
                    }

                    // add suppression entries for listed checks
                    final List<Entry> entries = ENTRIES.get();
                    for (String value : values) {
                        String checkName = value;
                        // strip off the checkstyle-only prefix if present
                        checkName = removeCheckstylePrefixIfExists(checkName);
                        entries.add(new Entry(checkName, firstLine, firstColumn,
                                lastLine, lastColumn));
                    }
                }
            }
        }
    }

    /**
     * Method removes checkstyle prefix (checkstyle:) from check name if exists.
     *
     * @param checkName
     *            - name of the check
     * @return check name without prefix
     */
    private static String removeCheckstylePrefixIfExists(String checkName) {
        String result = checkName;
        if (checkName.startsWith(CHECKSTYLE_PREFIX)) {
            result = checkName.substring(CHECKSTYLE_PREFIX.length());
        }
        return result;
    }

    /**
     * Get all annotation values.
     * @param ast annotation token
     * @return list values
     */
    private static List<String> getAllAnnotationValues(DetailAST ast) {
        // get values of annotation
        List<String> values = null;
        final DetailAST lparenAST = ast.findFirstToken(TokenTypes.LPAREN);
        if (lparenAST != null) {
            final DetailAST nextAST = lparenAST.getNextSibling();
            final int nextType = nextAST.getType();
            switch (nextType) {
                case TokenTypes.EXPR:
                case TokenTypes.ANNOTATION_ARRAY_INIT:
                    values = getAnnotationValues(nextAST);
                    break;

                case TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR:
                    // expected children: IDENT ASSIGN ( EXPR |
                    // ANNOTATION_ARRAY_INIT )
                    values = getAnnotationValues(getNthChild(nextAST, 2));
                    break;

                case TokenTypes.RPAREN:
                    // no value present (not valid Java)
                    break;

                default:
                    // unknown annotation value type (new syntax?)
                    throw new IllegalArgumentException("Unexpected AST: " + nextAST);
            }
        }
        return values;
    }

    /**
     * Checks that annotation is empty.
     * @param values list of values in the annotation
     * @return whether annotation is empty or contains some values
     */
    private static boolean isAnnotationEmpty(List<String> values) {
        return values == null;
    }

    /**
     * Get target of annotation.
     * @param ast the AST node to get the child of
     * @return get target of annotation
     */
    private static DetailAST getAnnotationTarget(DetailAST ast) {
        final DetailAST targetAST;
        final DetailAST parentAST = ast.getParent();
        switch (parentAST.getType()) {
            case TokenTypes.MODIFIERS:
            case TokenTypes.ANNOTATIONS:
                targetAST = getAcceptableParent(parentAST);
                break;
            default:
                // unexpected container type
                throw new IllegalArgumentException("Unexpected container AST: " + parentAST);
        }
        return targetAST;
    }

    /**
     * Returns parent of given ast if parent has one of the following types:
     * ANNOTATION_DEF, PACKAGE_DEF, CLASS_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, CTOR_DEF,
     * METHOD_DEF, PARAMETER_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, TYPE, LITERAL_NEW,
     * LITERAL_THROWS, TYPE_ARGUMENT, IMPLEMENTS_CLAUSE, DOT.
     * @param child an ast
     * @return returns ast - parent of given
     */
    private static DetailAST getAcceptableParent(DetailAST child) {
        final DetailAST result;
        final DetailAST parent = child.getParent();
        switch (parent.getType()) {
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.PACKAGE_DEF:
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ENUM_CONSTANT_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.PARAMETER_DEF:
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.ANNOTATION_FIELD_DEF:
            case TokenTypes.TYPE:
            case TokenTypes.LITERAL_NEW:
            case TokenTypes.LITERAL_THROWS:
            case TokenTypes.TYPE_ARGUMENT:
            case TokenTypes.IMPLEMENTS_CLAUSE:
            case TokenTypes.DOT:
                result = parent;
                break;
            default:
                // it's possible case, but shouldn't be processed here
                result = null;
        }
        return result;
    }

    /**
     * Returns the n'th child of an AST node.
     * @param ast the AST node to get the child of
     * @param index the index of the child to get
     * @return the n'th child of the given AST node, or {@code null} if none
     */
    private static DetailAST getNthChild(DetailAST ast, int index) {
        DetailAST child = ast.getFirstChild();
        for (int i = 0; i < index && child != null; ++i) {
            child = child.getNextSibling();
        }
        return child;
    }

    /**
     * Returns the Java identifier represented by an AST.
     * @param ast an AST node for an IDENT or DOT
     * @return the Java identifier represented by the given AST subtree
     * @throws IllegalArgumentException if the AST is invalid
     */
    private static String getIdentifier(DetailAST ast) {
        if (ast == null) {
            throw new IllegalArgumentException("Identifier AST expected, but get null.");
        }
        final String identifier;
        if (ast.getType() == TokenTypes.IDENT) {
            identifier = ast.getText();
        }
        else {
            identifier = getIdentifier(ast.getFirstChild()) + "."
                + getIdentifier(ast.getLastChild());
        }
        return identifier;
    }

    /**
     * Returns the literal string expression represented by an AST.
     * @param ast an AST node for an EXPR
     * @return the Java string represented by the given AST expression
     *         or empty string if expression is too complex
     * @throws IllegalArgumentException if the AST is invalid
     */
    private static String getStringExpr(DetailAST ast) {
        final DetailAST firstChild = ast.getFirstChild();
        String expr = "";

        switch (firstChild.getType()) {
            case TokenTypes.STRING_LITERAL:
                // NOTE: escaped characters are not unescaped
                final String quotedText = firstChild.getText();
                expr = quotedText.substring(1, quotedText.length() - 1);
                break;
            case TokenTypes.IDENT:
                expr = firstChild.getText();
                break;
            case TokenTypes.DOT:
                expr = firstChild.getLastChild().getText();
                break;
            default:
                // annotations with complex expressions cannot suppress warnings
        }
        return expr;
    }

    /**
     * Returns the annotation values represented by an AST.
     * @param ast an AST node for an EXPR or ANNOTATION_ARRAY_INIT
     * @return the list of Java string represented by the given AST for an
     *         expression or annotation array initializer
     * @throws IllegalArgumentException if the AST is invalid
     */
    private static List<String> getAnnotationValues(DetailAST ast) {
        final List<String> annotationValues;
        switch (ast.getType()) {
            case TokenTypes.EXPR:
                annotationValues = Collections.singletonList(getStringExpr(ast));
                break;
            case TokenTypes.ANNOTATION_ARRAY_INIT:
                annotationValues = findAllExpressionsInChildren(ast);
                break;
            default:
                throw new IllegalArgumentException(
                        "Expression or annotation array initializer AST expected: " + ast);
        }
        return annotationValues;
    }

    /**
     * Method looks at children and returns list of expressions in strings.
     * @param parent ast, that contains children
     * @return list of expressions in strings
     */
    private static List<String> findAllExpressionsInChildren(DetailAST parent) {
        final List<String> valueList = new LinkedList<>();
        DetailAST childAST = parent.getFirstChild();
        while (childAST != null) {
            if (childAST.getType() == TokenTypes.EXPR) {
                valueList.add(getStringExpr(childAST));
            }
            childAST = childAST.getNextSibling();
        }
        return valueList;
    }

    /** Records a particular suppression for a region of a file. */
    private static class Entry {
        /** The source name of the suppressed check. */
        private final String checkName;
        /** The suppression region for the check - first line. */
        private final int firstLine;
        /** The suppression region for the check - first column. */
        private final int firstColumn;
        /** The suppression region for the check - last line. */
        private final int lastLine;
        /** The suppression region for the check - last column. */
        private final int lastColumn;

        /**
         * Constructs a new suppression region entry.
         * @param checkName the source name of the suppressed check
         * @param firstLine the first line of the suppression region
         * @param firstColumn the first column of the suppression region
         * @param lastLine the last line of the suppression region
         * @param lastColumn the last column of the suppression region
         */
        Entry(String checkName, int firstLine, int firstColumn,
            int lastLine, int lastColumn) {
            this.checkName = checkName;
            this.firstLine = firstLine;
            this.firstColumn = firstColumn;
            this.lastLine = lastLine;
            this.lastColumn = lastColumn;
        }

        /**
         * Gets he source name of the suppressed check.
         * @return the source name of the suppressed check
         */
        public String getCheckName() {
            return checkName;
        }

        /**
         * Gets the first line of the suppression region.
         * @return the first line of the suppression region
         */
        public int getFirstLine() {
            return firstLine;
        }

        /**
         * Gets the first column of the suppression region.
         * @return the first column of the suppression region
         */
        public int getFirstColumn() {
            return firstColumn;
        }

        /**
         * Gets the last line of the suppression region.
         * @return the last line of the suppression region
         */
        public int getLastLine() {
            return lastLine;
        }

        /**
         * Gets the last column of the suppression region.
         * @return the last column of the suppression region
         */
        public int getLastColumn() {
            return lastColumn;
        }
    }
}
