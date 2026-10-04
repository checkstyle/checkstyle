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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that a wrapped class, interface, enum, or record header is wrapped according to the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-wrapping-class-declarations">
 * OpenJDK Java Style Guide</a>.
 * </div>
 *
 * <p>
 * This check only applies when the type declaration header, from the declaration
 * keyword through the opening curly brace, spans more than one line. Single-line
 * headers are not in scope.
 * </p>
 *
 * <p>
 * The two rules enforced are:
 * </p>
 * <ol>
 * <li>Each of the {@code extends} and {@code implements} clauses, when present, must
 * <em>stand out</em> on its own new line once the header has started wrapping, or once
 * that clause's own list of supertypes wraps across lines. Wrapping that happens only
 * inside a single supertype's type arguments (the same way method arguments may wrap)
 * does not require the clause keyword itself to move to a new line.</li>
 * <li>Only checked when rule 1 above found nothing to report: the header should not have
 * been wrapped in the first place if, joined back onto one line, it would still fit
 * within 80 columns.</li>
 * </ol>
 *
 * @since 14.4.0
 */
@StatelessCheck
public class ClassHeaderWrapOpenjdkCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_NOT_ON_NEW_LINE = "openjdk.classheader.new.line";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_UNNECESSARY_WRAP = "openjdk.classheader.unnecessary.wrap";

    /**
     * The maximum number of columns a class header may occupy before wrapping it
     * is considered necessary.
     */
    private static final int MAX_COLUMN_LIMIT = 80;

    /**
     * Creates a new {@code ClassHeaderWrapOpenjdkCheck} instance.
     */
    public ClassHeaderWrapOpenjdkCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST headerStart = findHeaderStart(ast);
        final DetailAST lcurly = ast.findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.LCURLY);

        if (headerStart.getLineNo() != lcurly.getLineNo()) {
            final DetailAST extendsClause = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
            final DetailAST implementsClause = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
            final boolean extendsMisplaced = isClauseMisplaced(extendsClause);
            final boolean implementsMisplaced = isClauseMisplaced(implementsClause);

            if (!extendsMisplaced && !implementsMisplaced) {
                checkNecessaryWrap(headerStart, lcurly);
            }
        }
    }

    /**
     * Checks the given clause's placement if it is present.
     *
     * @param clause the {@code EXTENDS_CLAUSE} or {@code IMPLEMENTS_CLAUSE} to check, or
     *     {@code null} if the type declaration has none
     * @return {@code true} if a violation was logged for this clause
     */
    private boolean isClauseMisplaced(DetailAST clause) {
        boolean misplaced = false;
        if (clause != null) {
            misplaced = checkClausePlacement(clause);
        }
        return misplaced;
    }

    /**
     * Finds the token that marks the true start of the header on the declaration's own
     * line: the first modifier that already shares a line with the declaration keyword
     * ({@code class}/{@code interface}/{@code enum}/{@code record}), skipping past any
     * annotations placed on their own preceding line, or the declaration keyword itself
     * if there is no such modifier.
     *
     * @param ast the class, interface, enum, or record declaration
     * @return the token that starts the header
     */
    private static DetailAST findHeaderStart(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final DetailAST declarationKeyword = modifiers.getNextSibling();
        DetailAST headerStart = declarationKeyword;
        DetailAST modifier = modifiers.getFirstChild();

        while (modifier != null) {
            if (modifier.getLineNo() == declarationKeyword.getLineNo()) {
                headerStart = modifier;
                break;
            }
            modifier = modifier.getNextSibling();
        }
        return headerStart;
    }

    /**
     * Checks that the given {@code extends}/{@code implements} clause stands out on its
     * own new line, if required. It is required once a preceding {@code extends} clause
     * has already achieved its own new line (so {@code implements} must match it), or
     * once this clause's own list of supertypes wraps across multiple lines. Wrapping in
     * whatever precedes the clause for some other reason, such as a wrapped type
     * parameter or record component list, does not by itself require the clause to move.
     *
     * @param clause the {@code EXTENDS_CLAUSE} or {@code IMPLEMENTS_CLAUSE} to check
     * @return {@code true} if a violation was logged for this clause
     */
    private boolean checkClausePlacement(DetailAST clause) {
        final DetailAST previous = clause.getPreviousSibling();
        final boolean sharesLineWithPrevious = clause.getLineNo() == getEndLineNo(previous);
        final boolean misplaced = sharesLineWithPrevious
                && (isPrecededByWrappedClause(previous) || isTopLevelWrapped(clause));

        if (misplaced) {
            log(clause, MSG_KEY_NOT_ON_NEW_LINE, clause.getText());
        }
        return misplaced;
    }

    /**
     * Checks whether the given node is an {@code EXTENDS_CLAUSE} that already stands out
     * on its own new line, meaning a following {@code implements} clause must match it.
     *
     * @param previous the node immediately preceding the clause under check
     * @return {@code true} if {@code previous} is an {@code EXTENDS_CLAUSE} that does not
     *     share a line with whatever precedes it
     */
    private static boolean isPrecededByWrappedClause(DetailAST previous) {
        return previous.getType() == TokenTypes.EXTENDS_CLAUSE
                && previous.getLineNo() != getEndLineNo(previous.getPreviousSibling());
    }

    /**
     * Checks whether the header, reconstructed as a single line, would still have fit
     * within the maximum column limit, in which case wrapping it was unnecessary.
     *
     * @param headerStart the declaration keyword ({@code class}/{@code interface}/
     *     {@code enum}/{@code record}) that starts the header
     * @param lcurly the opening brace of the type's body
     */
    private void checkNecessaryWrap(DetailAST headerStart, DetailAST lcurly) {
        final StringBuilder unwrapped = new StringBuilder(MAX_COLUMN_LIMIT);
        final String[] lines = getLines();

        for (int lineNo = headerStart.getLineNo(); lineNo <= lcurly.getLineNo(); lineNo++) {
            final String line = lines[lineNo - 1];
            final int fromIndex = findFromIndex(headerStart, lineNo);
            final int toIndex = findToIndex(lcurly, lineNo, line.length());
            final String segment = line.substring(fromIndex, toIndex).trim();

            if (!segment.isEmpty()) {
                if (!unwrapped.isEmpty()) {
                    unwrapped.append(' ');
                }
                unwrapped.append(segment);
            }
        }

        if (headerStart.getColumnNo() + unwrapped.length() <= MAX_COLUMN_LIMIT) {
            log(headerStart, MSG_KEY_UNNECESSARY_WRAP);
        }
    }

    /**
     * Finds the index at which the given line's relevant content starts: the header's own
     * starting column on the header's first line, or the start of the line otherwise.
     *
     * @param headerStart the token that starts the header
     * @param lineNo the 1-indexed line number currently being processed
     * @return the index at which to start extracting content from this line
     */
    private static int findFromIndex(DetailAST headerStart, int lineNo) {
        int fromIndex = 0;
        if (lineNo == headerStart.getLineNo()) {
            fromIndex = headerStart.getColumnNo();
        }
        return fromIndex;
    }

    /**
     * Finds the index at which the given line's relevant content ends: just past the
     * opening curly brace on the line it occupies, or the end of the line otherwise.
     *
     * @param lcurly the opening brace of the type's body
     * @param lineNo the 1-indexed line number currently being processed
     * @param lineLength the length of the line currently being processed
     * @return the index at which to stop extracting content from this line
     */
    private static int findToIndex(DetailAST lcurly, int lineNo, int lineLength) {
        int toIndex = lineLength;
        if (lineNo == lcurly.getLineNo()) {
            toIndex = lcurly.getColumnNo() + 1;
        }
        return toIndex;
    }

    /**
     * Checks whether the given node's direct children span more than one source line,
     * ignoring further wrapping nested inside any of those children (e.g. inside a
     * supertype's own type arguments).
     *
     * @param clause the {@code EXTENDS_CLAUSE} or {@code IMPLEMENTS_CLAUSE} to inspect
     * @return {@code true} if two direct children of {@code clause} start on different lines
     */
    private static boolean isTopLevelWrapped(DetailAST clause) {
        final int firstLineNo = clause.getFirstChild().getLineNo();
        boolean wrapped = false;
        DetailAST child = clause.getFirstChild().getNextSibling();

        while (child != null) {
            if (child.getLineNo() != firstLineNo) {
                wrapped = true;
                break;
            }
            child = child.getNextSibling();
        }
        return wrapped;
    }

    /**
     * Returns the line number of the last token in the given subtree.
     *
     * @param ast the root of the subtree to inspect
     * @return the 1-indexed line number of the deepest last child of {@code ast}
     */
    private static int getEndLineNo(DetailAST ast) {
        DetailAST last = ast;
        while (last.getLastChild() != null) {
            last = last.getLastChild();
        }
        return last.getLineNo();
    }

}
