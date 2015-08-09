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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks correct indentation of Java Code.
 *
 * <p>
 * The basic idea behind this is that while
 * pretty printers are sometimes convenient for bulk reformats of
 * legacy code, they often either aren't configurable enough or
 * just can't anticipate how format should be done.  Sometimes this is
 * personal preference, other times it is practical experience.  In any
 * case, this check should just ensure that a minimal set of indentation
 * rules are followed.
 * </p>
 *
 * <p>
 * Implementation --
 *  Basically, this check requests visitation for all handled token
 *  types (those tokens registered in the HandlerFactory).  When visitToken
 *  is called, a new ExpressionHandler is created for the AST and pushed
 *  onto the handlers stack.  The new handler then checks the indentation
 *  for the currently visiting AST.  When leaveToken is called, the
 *  ExpressionHandler is popped from the stack.
 * </p>
 *
 * <p>
 *  While on the stack the ExpressionHandler can be queried for the
 *  indentation level it suggests for children as well as for other
 *  values.
 * </p>
 *
 * <p>
 *  While an ExpressionHandler checks the indentation level of its own
 *  AST, it typically also checks surrounding ASTs.  For instance, a
 *  while loop handler checks the while loop as well as the braces
 *  and immediate children.
 * </p>
 * <pre>
 *   - handler class -to-&gt; ID mapping kept in Map
 *   - parent passed in during construction
 *   - suggest child indent level
 *   - allows for some tokens to be on same line (ie inner classes OBJBLOCK)
 *     and not increase indentation level
 *   - looked at using double dispatch for suggestedChildLevel(), but it
 *     doesn't seem worthwhile, at least now
 *   - both tabs and spaces are considered whitespace in front of the line...
 *     tabs are converted to spaces
 *   - block parents with parens -- for, while, if, etc... -- are checked that
 *     they match the level of the parent
 * </pre>
 *
 * @author jrichard
 * @author o_sukhodolsky
 * @author Maikel Steneker
 * @author maxvetrenko
 */
public class IndentationCheck extends Check {
    /** Default indentation amount - based on Sun */
    private static final int DEFAULT_INDENTATION = 4;

    /** how many tabs or spaces to use */
    private int basicOffset = DEFAULT_INDENTATION;

    /** how much to indent a case label */
    private int caseIndentationAmount = DEFAULT_INDENTATION;

    /** how far brace should be indented when on next line */
    private int braceAdjustment;

    /** how far throws should be indented when on next line */
    private int throwsIndentationAmount = DEFAULT_INDENTATION;

    /** how much to indent an array initialization when on next line */
    private int arrayInitIndentationAmount = DEFAULT_INDENTATION;

    /** how far continuation line should be indented when line-wrapping is present */
    private int lineWrappingIndentation = DEFAULT_INDENTATION;

    /**
     * Force strict condition in line wrapping case. If value is true, line wrap indent
     * have to be same as lineWrappingIndentation parameter, if value is false, line wrap indent
     * have to be not less than lineWrappingIndentation parameter.
     */
    private boolean forceStrictCondition;

    /** handlers currently in use */
    private final Deque<AbstractExpressionHandler> handlers = new ArrayDeque<>();

    /** factory from which handlers are distributed */
    private final HandlerFactory handlerFactory = new HandlerFactory();

    /**
     * Get forcing strict condition.
     * @return forceStrictCondition value.
     */
    public boolean isForceStrictCondition() {
        return forceStrictCondition;
    }

    /**
     * Set forcing strict condition.
     * @param value user's value of forceStrictCondition.
     */
    public void setForceStrictCondition(boolean value) {
        forceStrictCondition = value;
    }

    /**
     * Set the basic offset.
     *
     * @param basicOffset   the number of tabs or spaces to indent
     */
    public void setBasicOffset(int basicOffset) {
        this.basicOffset = basicOffset;
    }

    /**
     * Get the basic offset.
     *
     * @return the number of tabs or spaces to indent
     */
    public int getBasicOffset() {
        return basicOffset;
    }

    /**
     * Adjusts brace indentation (positive offset).
     *
     * @param adjustmentAmount   the brace offset
     */
    public void setBraceAdjustment(int adjustmentAmount) {
        braceAdjustment = adjustmentAmount;
    }

    /**
     * Get the brace adjustment amount.
     *
     * @return the positive offset to adjust braces
     */
    public int getBraceAdjustment() {
        return braceAdjustment;
    }

    /**
     * Set the case indentation level.
     *
     * @param amount   the case indentation level
     */
    public void setCaseIndent(int amount) {
        caseIndentationAmount = amount;
    }

    /**
     * Get the case indentation level.
     *
     * @return the case indentation level
     */
    public int getCaseIndent() {
        return caseIndentationAmount;
    }

    /**
     * Set the throws indentation level.
     *
     * @param throwsIndent the throws indentation level
     */
    public void setThrowsIndent(int throwsIndent) {
        throwsIndentationAmount = throwsIndent;
    }

    /**
     * Get the throws indentation level.
     *
     * @return the throws indentation level
     */
    public int getThrowsIndent() {
        return throwsIndentationAmount;
    }

    /**
     * Set the array initialisation indentation level.
     *
     * @param arrayInitIndent the array initialisation indentation level
     */
    public void setArrayInitIndent(int arrayInitIndent) {
        arrayInitIndentationAmount = arrayInitIndent;
    }

    /**
     * Get the line-wrapping indentation level.
     *
     * @return the initialisation indentation level
     */
    public int getArrayInitIndent() {
        return arrayInitIndentationAmount;
    }

    /**
     * Get the array line-wrapping indentation level.
     *
     * @return the line-wrapping indentation level
     */
    public int getLineWrappingIndentation() {
        return lineWrappingIndentation;
    }

    /**
     * Set the line-wrapping indentation level.
     *
     * @param lineWrappingIndentation the line-wrapping indentation level
     */
    public void setLineWrappingIndentation(int lineWrappingIndentation) {
        this.lineWrappingIndentation = lineWrappingIndentation;
    }

    /**
     * Log an error message.
     *
     * @param line the line number where the error was found
     * @param key the message that describes the error
     * @param args the details of the message
     *
     * @see java.text.MessageFormat
     */
    public void indentationLog(int line, String key, Object... args) {
        log(line, key, args);
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
        return handlerFactory.getHandledTypes();
    }

    @Override
    public void beginTree(DetailAST ast) {
        handlerFactory.clearCreatedHandlers();
        handlers.clear();
        final PrimordialHandler primordialHandler = new PrimordialHandler(this);
        handlers.push(primordialHandler);
        primordialHandler.checkIndentation();
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
     * Accessor for the handler factory.
     *
     * @return the handler factory
     */
    final HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }
}
