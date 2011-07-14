////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

// TODO: allow preset indentation styles (IE... GNU style, Sun style, etc...)?

// TODO: optionally make imports (and other?) statements required to start
//   line? -- but maybe this should be a different check

// TODO: optionally allow array children, throws clause, etc...
//   to be of any indentation > required, for emacs-style indentation

// TODO: this is not illegal, but probably should be:
//        myfunc3(11, 11, Integer.
//            getInteger("mytest").intValue(),  // this should be in 4 more
//            11);

// TODO: any dot-based indentation doesn't work (at least not yet...) the
//  problem is that we don't know which way an expression tree will be built
//  and with dot trees, they are built backwards.  This means code like
//
//  org.blah.mystuff
//      .myclass.getFactoryObject()
//          .objFunc().otherMethod();
// and
//  return ((MethodCallHandler) parent)
//      .findContainingMethodCall(this);
//  is all checked at the level of the first line.  Simple dots are actually
// checked but the method call handler will have to be changed drastically
// to fix the above...


/**
 * Checks correct indentation of Java Code.
 *
 * <p>
 * The basic idea behind this is that while
 * pretty printers are sometimes convienent for bulk reformats of
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
 *  onto the mHandlers stack.  The new handler then checks the indentation
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
 *   - handler class -to-> ID mapping kept in Map
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
 */
public class IndentationCheck extends Check
{
    /** Default indentation amount - based on Sun */
    private static final int DEFAULT_INDENTATION = 4;

    /** how many tabs or spaces to use */
    private int mBasicOffset = DEFAULT_INDENTATION;

    /** how much to indent a case label */
    private int mCaseIndentationAmount = DEFAULT_INDENTATION;

    /** how far brace should be indented when on next line */
    private int mBraceAdjustment;

    /** handlers currently in use */
    private final FastStack<ExpressionHandler> mHandlers =
        FastStack.newInstance();

    /** factory from which handlers are distributed */
    private final HandlerFactory mHandlerFactory = new HandlerFactory();

    /** Creates a new instance of IndentationCheck. */
    public IndentationCheck()
    {
    }

    /**
     * Set the basic offset.
     *
     * @param aBasicOffset   the number of tabs or spaces to indent
     */
    public void setBasicOffset(int aBasicOffset)
    {
        mBasicOffset = aBasicOffset;
    }

    /**
     * Get the basic offset.
     *
     * @return the number of tabs or spaces to indent
     */
    public int getBasicOffset()
    {
        return mBasicOffset;
    }

    /**
     * Adjusts brace indentation (positive offset).
     *
     * @param aAdjustmentAmount   the brace offset
     */
    public void setBraceAdjustment(int aAdjustmentAmount)
    {
        mBraceAdjustment = aAdjustmentAmount;
    }

    /**
     * Get the brace adjustment amount.
     *
     * @return the positive offset to adjust braces
     */
    public int getBraceAdjustement()
    {
        return mBraceAdjustment;
    }

    /**
     * Set the case indentation level.
     *
     * @param aAmount   the case indentation level
     */
    public void setCaseIndent(int aAmount)
    {
        mCaseIndentationAmount = aAmount;
    }

    /**
     * Get the case indentation level.
     *
     * @return the case indentation level
     */
    public int getCaseIndent()
    {
        return mCaseIndentationAmount;
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     * @param aArgs the details of the message
     *
     * @see java.text.MessageFormat
     */
    public void indentationLog(int aLine, String aKey, Object... aArgs)
    {
        super.log(aLine, aKey, aArgs);
    }

    /**
     * Get the width of a tab.
     *
     * @return the width of a tab
     */
    public int getIndentationTabWidth()
    {
        return getTabWidth();
    }

    @Override
    public int[] getDefaultTokens()
    {
        return mHandlerFactory.getHandledTypes();
    }

    @Override
    public void beginTree(DetailAST aAst)
    {
        mHandlerFactory.clearCreatedHandlers();
        mHandlers.clear();
        mHandlers.push(new PrimordialHandler(this));
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if ((aAST.getType() == TokenTypes.VARIABLE_DEF)
            && ScopeUtils.isLocalVariableDef(aAST))
        {
            // we have handler only for members
            return;
        }

        final ExpressionHandler handler = mHandlerFactory.getHandler(this, aAST,
            mHandlers.peek());
        mHandlers.push(handler);
        try {
            handler.checkIndentation();
        }
        catch (final NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        if ((aAST.getType() == TokenTypes.VARIABLE_DEF)
            && ScopeUtils.isLocalVariableDef(aAST))
        {
            // we have handler only for members
            return;
        }
        mHandlers.pop();
    }

    /**
     * Accessor for the handler factory.
     *
     * @return the handler factory
     */
    final HandlerFactory getHandlerFactory()
    {
        return mHandlerFactory;
    }
}
