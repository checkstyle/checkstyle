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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <div>
 * Checks correct indentation of Java code.
 * </div>
 *
 * <p>
 * The idea behind this is that while
 * pretty printers are sometimes convenient for bulk reformats of
 * legacy code, they often either aren't configurable enough or
 * just can't anticipate how format should be done. Sometimes this is
 * personal preference, other times it is practical experience. In any
 * case, this check should just ensure that a minimal set of indentation
 * rules is followed.
 * </p>
 *
 * <p>
 * Basic offset indentation is used for indentation inside code blocks.
 * For any lines that span more than 1, line wrapping indentation is used for those lines
 * after the first. Brace adjustment, case, and throws indentations are all used only if
 * those specific identifiers start the line. If, for example, a brace is used in the
 * middle of the line, its indentation will not take effect. All indentations have an
 * accumulative/recursive effect when they are triggered. If during a line wrapping, another
 * code block is found and it doesn't end on that same line, then the subsequent lines
 * afterwards, in that new code block, are increased on top of the line wrap and any
 * indentations above it.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 * <pre>
 * if ((condition1 &amp;&amp; condition2)
 *         || (condition3 &amp;&amp; condition4)    // line wrap with bigger indentation
 *         ||!(condition5 &amp;&amp; condition6)) { // line wrap with bigger indentation
 *   field.doSomething()                    // basic offset
 *       .doSomething()                     // line wrap
 *       .doSomething( c -&gt; {               // line wrap
 *         return c.doSome();               // basic offset
 *       });
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code arrayInitIndent} - Specify how far an array initialization
 * should be indented when on next line.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code basicOffset} - Specify how far new indentation level should be
 * indented when on the next line.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code braceAdjustment} - Specify how far a braces should be indented
 * when on the next line.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code caseIndent} - Specify how far a case label should be indented
 * when on next line.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code forceStrictCondition} - Force strict indent level in line
 * wrapping case. If value is true, line wrap indent have to be same as
 * lineWrappingIndentation parameter. If value is false, line wrap indent
 * could be bigger on any value user would like.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code lineWrappingIndentation} - Specify how far continuation line
 * should be indented when line-wrapping is present.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code throwsIndent} - Specify how far a throws clause should be
 * indented when on next line.
 * Type is {@code int}.
 * Default value is {@code 4}.
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
 * {@code indentation.child.error}
 * </li>
 * <li>
 * {@code indentation.child.error.multi}
 * </li>
 * <li>
 * {@code indentation.error}
 * </li>
 * <li>
 * {@code indentation.error.multi}
 * </li>
 * </ul>
 *
 * @noinspection ThisEscapedInObjectConstruction
 * @noinspectionreason ThisEscapedInObjectConstruction - class is instantiated in handlers
 * @since 3.1
 */
@FileStatefulCheck
public class IndentationCheck extends AbstractCheck {

    /*  -- Implementation --
     *
     *  Basically, this check requests visitation for all handled token
     *  types (those tokens registered in the HandlerFactory).  When visitToken
     *  is called, a new ExpressionHandler is created for the AST and pushed
     *  onto the handlers stack.  The new handler then checks the indentation
     *  for the currently visiting AST.  When leaveToken is called, the
     *  ExpressionHandler is popped from the stack.
     *
     *  While on the stack the ExpressionHandler can be queried for the
     *  indentation level it suggests for children as well as for other
     *  values.
     *
     *  While an ExpressionHandler checks the indentation level of its own
     *  AST, it typically also checks surrounding ASTs.  For instance, a
     *  while loop handler checks the while loop as well as the braces
     *  and immediate children.
     *
     *   - handler class -to-&gt; ID mapping kept in Map
     *   - parent passed in during construction
     *   - suggest child indent level
     *   - allows for some tokens to be on same line (ie inner classes OBJBLOCK)
     *     and not increase indentation level
     *   - looked at using double dispatch for getSuggestedChildIndent(), but it
     *     doesn't seem worthwhile, at least now
     *   - both tabs and spaces are considered whitespace in front of the line...
     *     tabs are converted to spaces
     *   - block parents with parens -- for, while, if, etc... -- are checked that
     *     they match the level of the parent
     */

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ERROR = "indentation.error";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ERROR_MULTI = "indentation.error.multi";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CHILD_ERROR = "indentation.child.error";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CHILD_ERROR_MULTI = "indentation.child.error.multi";

    /** Default indentation amount - based on Sun. */
    private static final int DEFAULT_INDENTATION = 4;

    /** Handlers currently in use. */
    private final Deque<AbstractExpressionHandler> handlers = new ArrayDeque<>();

    /** Instance of line wrapping handler to use. */
    private final LineWrappingHandler lineWrappingHandler = new LineWrappingHandler(this);

    /** Factory from which handlers are distributed. */
    private final HandlerFactory handlerFactory = new HandlerFactory();

    /** Lines logged as having incorrect indentation. */
    private Set<Integer> incorrectIndentationLines;

    /** Specify how far new indentation level should be indented when on the next line. */
    private int basicOffset = DEFAULT_INDENTATION;

    /** Specify how far a case label should be indented when on next line. */
    private int caseIndent = DEFAULT_INDENTATION;

    /** Specify how far a braces should be indented when on the next line. */
    private int braceAdjustment;

    /** Specify how far a throws clause should be indented when on next line. */
    private int throwsIndent = DEFAULT_INDENTATION;

    /** Specify how far an array initialization should be indented when on next line. */
    private int arrayInitIndent = DEFAULT_INDENTATION;

    /** Specify how far continuation line should be indented when line-wrapping is present. */
    private int lineWrappingIndentation = DEFAULT_INDENTATION;

    /**
     * Force strict indent level in line wrapping case. If value is true, line wrap indent
     * have to be same as lineWrappingIndentation parameter. If value is false, line wrap indent
     * could be bigger on any value user would like.
     */
    private boolean forceStrictCondition;

    /**
     * Getter to query strict indent level in line wrapping case. If value is true, line wrap indent
     * have to be same as lineWrappingIndentation parameter. If value is false, line wrap indent
     * could be bigger on any value user would like.
     *
     * @return forceStrictCondition value.
     */
    public boolean isForceStrictCondition() {
        return forceStrictCondition;
    }

    /**
     * Setter to force strict indent level in line wrapping case. If value is true, line wrap indent
     * have to be same as lineWrappingIndentation parameter. If value is false, line wrap indent
     * could be bigger on any value user would like.
     *
     * @param value user's value of forceStrictCondition.
     * @since 6.3
     */
    public void setForceStrictCondition(boolean value) {
        forceStrictCondition = value;
    }

    /**
     * Setter to specify how far new indentation level should be indented when on the next line.
     *
     * @param basicOffset   the number of tabs or spaces to indent
     * @since 3.1
     */
    public void setBasicOffset(int basicOffset) {
        this.basicOffset = basicOffset;
    }

    /**
     * Getter to query how far new indentation level should be indented when on the next line.
     *
     * @return the number of tabs or spaces to indent
     */
    public int getBasicOffset() {
        return basicOffset;
    }

    /**
     * Setter to specify how far a braces should be indented when on the next line.
     *
     * @param adjustmentAmount   the brace offset
     * @since 3.1
     */
    public void setBraceAdjustment(int adjustmentAmount) {
        braceAdjustment = adjustmentAmount;
    }

    /**
     * Getter to query how far a braces should be indented when on the next line.
     *
     * @return the positive offset to adjust braces
     */
    public int getBraceAdjustment() {
        return braceAdjustment;
    }

    /**
     * Setter to specify how far a case label should be indented when on next line.
     *
     * @param amount   the case indentation level
     * @since 3.1
     */
    public void setCaseIndent(int amount) {
        caseIndent = amount;
    }

    /**
     * Getter to query how far a case label should be indented when on next line.
     *
     * @return the case indentation level
     */
    public int getCaseIndent() {
        return caseIndent;
    }

    /**
     * Setter to specify how far a throws clause should be indented when on next line.
     *
     * @param throwsIndent the throws indentation level
     * @since 5.7
     */
    public void setThrowsIndent(int throwsIndent) {
        this.throwsIndent = throwsIndent;
    }

    /**
     * Getter to query how far a throws clause should be indented when on next line.
     *
     * @return the throws indentation level
     */
    public int getThrowsIndent() {
        return throwsIndent;
    }

    /**
     * Setter to specify how far an array initialization should be indented when on next line.
     *
     * @param arrayInitIndent the array initialization indentation level
     * @since 5.8
     */
    public void setArrayInitIndent(int arrayInitIndent) {
        this.arrayInitIndent = arrayInitIndent;
    }

    /**
     * Getter to query how far an array initialization should be indented when on next line.
     *
     * @return the initialization indentation level
     */
    public int getArrayInitIndent() {
        return arrayInitIndent;
    }

    /**
     * Getter to query how far continuation line should be indented when line-wrapping is present.
     *
     * @return the line-wrapping indentation level
     */
    public int getLineWrappingIndentation() {
        return lineWrappingIndentation;
    }

    /**
     * Setter to specify how far continuation line should be indented when line-wrapping is present.
     *
     * @param lineWrappingIndentation the line-wrapping indentation level
     * @since 5.9
     */
    public void setLineWrappingIndentation(int lineWrappingIndentation) {
        this.lineWrappingIndentation = lineWrappingIndentation;
    }

    /**
     * Log a violation message.
     *
     * @param  ast the ast for which error to be logged
     * @param key the message that describes the violation
     * @param args the details of the message
     *
     * @see java.text.MessageFormat
     */
    public void indentationLog(DetailAST ast, String key, Object... args) {
        if (!incorrectIndentationLines.contains(ast.getLineNo())) {
            incorrectIndentationLines.add(ast.getLineNo());
            log(ast, key, args);
        }
    }

    /**
     * Get the width of a tab.
     *
     * @return the width of a tab
     */
    public int getIndentationTabWidth() {
        return getTabWidth();
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
        return handlerFactory.getHandledTypes();
    }

    @Override
    public void beginTree(DetailAST ast) {
        handlerFactory.clearCreatedHandlers();
        handlers.clear();
        final PrimordialHandler primordialHandler = new PrimordialHandler(this);
        handlers.push(primordialHandler);
        primordialHandler.checkIndentation();
        incorrectIndentationLines = new HashSet<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final AbstractExpressionHandler handler = handlerFactory.getHandler(this, ast,
            handlers.peek());
        handlers.push(handler);
        handler.checkIndentation();
    }

    @Override
    public void leaveToken(DetailAST ast) {
        handlers.pop();
    }

    /**
     * Accessor for the line wrapping handler.
     *
     * @return the line wrapping handler
     */
    public LineWrappingHandler getLineWrappingHandler() {
        return lineWrappingHandler;
    }

    /**
     * Accessor for the handler factory.
     *
     * @return the handler factory
     */
    public final HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

}
