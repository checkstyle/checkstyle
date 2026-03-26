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

import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for a list of statements.
 *
 */
public class SlistHandler extends BlockParentHandler {

    /**
     * Parent token types.
     */
    private static final BitSet PARENT_TOKEN_TYPES = TokenUtil.asBitSet(
        TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.STATIC_INIT,
        TokenTypes.LITERAL_SYNCHRONIZED,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_ELSE,
        TokenTypes.LITERAL_TRY,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.LITERAL_FINALLY,
        TokenTypes.COMPACT_CTOR_DEF
    );

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public SlistHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "block", ast, parent);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        // this is:
        //  switch (var) {
        //     case 3: {
        //        break;
        //     }
        //  }
        //  ... the case SLIST is followed by a user-created SLIST and
        //  preceded by a switch

        final IndentLevel result;
        if (isDoubleBraceInstanceInit()) {
            // For double-brace initialization like new HashMap<>() {{ put(...); }},
            // the instance initializer's SLIST shares the anonymous class scope.
            if (needsAdditionalMethodCallOffset()) {
                result = new IndentLevel(getParent().getIndent(), getBasicOffset());
            }
            else {
                result = getParent().getIndent();
            }
        }
        // if our parent is a block handler we want to be transparent
        else if (getParent() instanceof BlockParentHandler
                && !(getParent() instanceof SlistHandler)
            || child instanceof SlistHandler
                && getParent() instanceof CaseHandler) {
            result = getParent().getSuggestedChildIndent(child);
        }
        else {
            result = super.getSuggestedChildIndent(child);
        }
        return result;
    }

    /**
     * Checks if this handler represents an instance initializer in a double-brace
     * initialization pattern. A double-brace initialization is when an anonymous class
     * has an instance initializer whose opening brace is on the same line as the
     * anonymous class opening brace, e.g. {@code new HashMap<>() {{ put(...); }}}.
     *
     * @return true if this is a double-brace instance initializer
     */
    private boolean isDoubleBraceInstanceInit() {
        boolean result = false;
        if (getMainAst().getType() == TokenTypes.INSTANCE_INIT) {
            final DetailAST parentAst = getMainAst().getParent();
            if (parentAst.getType() == TokenTypes.OBJBLOCK
                    && parentAst.getParent().getType() == TokenTypes.LITERAL_NEW) {
                final DetailAST objBlockLcurly =
                        parentAst.findFirstToken(TokenTypes.LCURLY);
                final DetailAST slist =
                        getMainAst().findFirstToken(TokenTypes.SLIST);
                result = objBlockLcurly != null && slist != null
                        && objBlockLcurly.getLineNo() == slist.getLineNo();
            }
        }
        return result;
    }

    /**
     * Checks if the anonymous class is used inside a chained call or nested method-call
     * arguments, which requires one more indentation step for the initializer block.
     *
     * @return true if the initializer should inherit an extra method-call offset
     */
    private boolean needsAdditionalMethodCallOffset() {
        final DetailAST newAst = getMainAst().getParent().getParent();
        return getMethodCallDepth(newAst) > 1 || isInChainedMethodCall(newAst);
    }

    /**
     * Counts the enclosing method calls for the anonymous-class creation.
     *
     * @param ast the {@code LITERAL_NEW} node
     * @return number of enclosing method calls
     */
    private static int getMethodCallDepth(DetailAST ast) {
        int depth = 0;
        for (DetailAST current = ast.getParent(); current != null; current = current.getParent()) {
            if (current.getType() == TokenTypes.METHOD_CALL) {
                depth++;
            }
        }
        return depth;
    }

    /**
     * Checks if the anonymous-class creation is inside a chained method call like
     * {@code foo().bar(new HashMap<>() {{ ... }})}.
     *
     * @param ast the {@code LITERAL_NEW} node
     * @return true if the new expression is part of a chained call
     */
    private static boolean isInChainedMethodCall(DetailAST ast) {
        boolean result = false;
        for (DetailAST current = ast.getParent(); current != null && !result;
                current = current.getParent()) {
            if (current.getType() == TokenTypes.DOT) {
                final DetailAST firstChild = current.getFirstChild();
                result = firstChild != null && firstChild.getType() == TokenTypes.METHOD_CALL;
            }
        }
        return result;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst();
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst();
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
    }

    /**
     * Determine if the expression we are handling has a block parent.
     *
     * @return true if it does, false otherwise
     */
    private boolean hasBlockParent() {
        final int parentType = getMainAst().getParent().getType();
        return PARENT_TOKEN_TYPES.get(parentType);
    }

    @Override
    public void checkIndentation() {
        // only need to check this if parent is not
        // an if, else, while, do, ctor, method
        if (!hasBlockParent() && !isSameLineCaseGroup()) {
            super.checkIndentation();
        }
    }

    /**
     * Checks if SLIST node is placed at the same line as CASE_GROUP node.
     *
     * @return true, if SLIST node is places at the same line as CASE_GROUP node.
     */
    private boolean isSameLineCaseGroup() {
        final DetailAST parentNode = getMainAst().getParent();
        return parentNode.getType() == TokenTypes.CASE_GROUP
            && TokenUtil.areOnSameLine(getMainAst(), parentNode);
    }

}
