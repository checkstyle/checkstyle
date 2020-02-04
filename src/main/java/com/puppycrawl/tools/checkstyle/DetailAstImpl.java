////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.util.BitSet;

import antlr.CommonASTWithHiddenTokens;
import antlr.Token;
import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * The implementation of {@link DetailAST}. This should only be directly used to
 * create custom AST nodes.
 * @noinspection FieldNotUsedInToString, SerializableHasSerializationMethods
 */
public final class DetailAstImpl extends CommonASTWithHiddenTokens implements DetailAST {

    private static final long serialVersionUID = -2580884815577559874L;

    /** Constant to indicate if not calculated the child count. */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /** The line number. **/
    private int lineNo = NOT_INITIALIZED;
    /** The column number. **/
    private int columnNo = NOT_INITIALIZED;

    /** Number of children. */
    private int childCount = NOT_INITIALIZED;
    /** The parent token. */
    private DetailAstImpl parent;
    /** Previous sibling. */
    private DetailAstImpl previousSibling;

    /**
     * All token types in this branch.
     * Token 'x' (where x is an int) is in this branch
     * if branchTokenTypes.get(x) is true.
     */
    private BitSet branchTokenTypes;

    @Override
    public void initialize(Token tok) {
        super.initialize(tok);
        lineNo = tok.getLine();

        // expect columns to start @ 0
        columnNo = tok.getColumn() - 1;
    }

    @Override
    public void initialize(AST ast) {
        final DetailAstImpl detailAst = (DetailAstImpl) ast;
        setText(detailAst.getText());
        setType(detailAst.getType());
        lineNo = detailAst.getLineNo();
        columnNo = detailAst.getColumnNo();
        hiddenAfter = detailAst.getHiddenAfter();
        hiddenBefore = detailAst.getHiddenBefore();
    }

    @Override
    public void setFirstChild(AST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(this);
        super.setFirstChild(ast);
        if (ast != null) {
            ((DetailAstImpl) ast).setParent(this);
        }
    }

    @Override
    public void setNextSibling(AST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(parent);
        super.setNextSibling(ast);
        if (ast != null && parent != null) {
            ((DetailAstImpl) ast).setParent(parent);
        }
        if (ast != null) {
            ((DetailAstImpl) ast).previousSibling = this;
        }
    }

    /**
     * Add previous sibling.
     * @param ast
     *        DetailAST object.
     */
    public void addPreviousSibling(DetailAST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(parent);
        if (ast != null) {
            // parent is set in setNextSibling or parent.setFirstChild
            final DetailAstImpl previousSiblingNode = previousSibling;
            final DetailAstImpl astImpl = (DetailAstImpl) ast;

            if (previousSiblingNode != null) {
                astImpl.previousSibling = previousSiblingNode;
                previousSiblingNode.setNextSibling(astImpl);
            }
            else if (parent != null) {
                parent.setFirstChild(astImpl);
            }

            astImpl.setNextSibling(this);
            previousSibling = astImpl;
        }
    }

    /**
     * Add next sibling.
     * @param ast
     *        DetailAST object.
     */
    public void addNextSibling(DetailAST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(parent);
        if (ast != null) {
            // parent is set in setNextSibling
            final DetailAstImpl nextSibling = getNextSibling();
            final DetailAstImpl astImpl = (DetailAstImpl) ast;

            if (nextSibling != null) {
                astImpl.setNextSibling(nextSibling);
                nextSibling.previousSibling = astImpl;
            }

            astImpl.previousSibling = this;
            setNextSibling(astImpl);
        }
    }

    @Override
    public void addChild(AST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(this);
        if (ast != null) {
            final DetailAstImpl astImpl = (DetailAstImpl) ast;
            astImpl.setParent(this);
            astImpl.previousSibling = (DetailAstImpl) getLastChild();
        }
        super.addChild(ast);
    }

    @Override
    public int getChildCount() {
        // lazy init
        if (childCount == NOT_INITIALIZED) {
            childCount = 0;
            AST child = getFirstChild();

            while (child != null) {
                childCount += 1;
                child = child.getNextSibling();
            }
        }
        return childCount;
    }

    @Override
    public int getChildCount(int type) {
        int count = 0;
        for (AST ast = getFirstChild(); ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * Set the parent token.
     * @param parent the parent token
     */
    private void setParent(DetailAstImpl parent) {
        DetailAstImpl instance = this;
        do {
            instance.clearBranchTokenTypes();
            instance.parent = parent;
            instance = instance.getNextSibling();
        } while (instance != null);
    }

    @Override
    public DetailAST getParent() {
        return parent;
    }

    @Override
    public int getLineNo() {
        int resultNo = -1;

        if (lineNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            resultNo = findLineNo(getFirstChild());

            if (resultNo == -1) {
                resultNo = findLineNo(getNextSibling());
            }
        }
        if (resultNo == -1) {
            resultNo = lineNo;
        }
        return resultNo;
    }

    /**
     * Set line number.
     * @param lineNo
     *        line number.
     */
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    @Override
    public int getColumnNo() {
        int resultNo = -1;

        if (columnNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            resultNo = findColumnNo(getFirstChild());

            if (resultNo == -1) {
                resultNo = findColumnNo(getNextSibling());
            }
        }
        if (resultNo == -1) {
            resultNo = columnNo;
        }
        return resultNo;
    }

    /**
     * Set column number.
     * @param columnNo
     *        column number.
     */
    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    @Override
    public DetailAST getLastChild() {
        DetailAST ast = getFirstChild();
        while (ast != null && ast.getNextSibling() != null) {
            ast = ast.getNextSibling();
        }
        return ast;
    }

    /**
     * Finds column number in the first non-comment node.
     *
     * @param ast DetailAST node.
     * @return Column number if non-comment node exists, -1 otherwise.
     */
    private static int findColumnNo(DetailAST ast) {
        int resultNo = -1;
        DetailAST node = ast;
        while (node != null) {
            // comment node can't be start of any java statement/definition
            if (TokenUtil.isCommentType(node.getType())) {
                node = node.getNextSibling();
            }
            else {
                resultNo = node.getColumnNo();
                break;
            }
        }
        return resultNo;
    }

    /**
     * Finds line number in the first non-comment node.
     *
     * @param ast DetailAST node.
     * @return Line number if non-comment node exists, -1 otherwise.
     */
    private static int findLineNo(DetailAST ast) {
        int resultNo = -1;
        DetailAST node = ast;
        while (node != null) {
            // comment node can't be start of any java statement/definition
            if (TokenUtil.isCommentType(node.getType())) {
                node = node.getNextSibling();
            }
            else {
                resultNo = node.getLineNo();
                break;
            }
        }
        return resultNo;
    }

    /**
     * Returns token type with branch.
     * @return the token types that occur in the branch as a sorted set.
     */
    private BitSet getBranchTokenTypes() {
        // lazy init
        if (branchTokenTypes == null) {
            branchTokenTypes = new BitSet();
            branchTokenTypes.set(getType());

            // add union of all children
            DetailAstImpl child = getFirstChild();
            while (child != null) {
                final BitSet childTypes = child.getBranchTokenTypes();
                branchTokenTypes.or(childTypes);

                child = child.getNextSibling();
            }
        }
        return branchTokenTypes;
    }

    @Override
    public boolean branchContains(int type) {
        return getBranchTokenTypes().get(type);
    }

    @Override
    public DetailAST getPreviousSibling() {
        return previousSibling;
    }

    @Override
    public DetailAST findFirstToken(int type) {
        DetailAST returnValue = null;
        for (DetailAST ast = getFirstChild(); ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == type) {
                returnValue = ast;
                break;
            }
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

    @Override
    public DetailAstImpl getNextSibling() {
        return (DetailAstImpl) super.getNextSibling();
    }

    @Override
    public DetailAstImpl getFirstChild() {
        return (DetailAstImpl) super.getFirstChild();
    }

    @Override
    public boolean hasChildren() {
        return getFirstChild() != null;
    }

    /**
     * Clears the child count for the ast instance.
     * @param ast The ast to clear.
     */
    private static void clearChildCountCache(DetailAstImpl ast) {
        if (ast != null) {
            ast.childCount = NOT_INITIALIZED;
        }
    }

    /**
     * Clears branchTokenTypes cache for all parents of the current DetailAST instance, and the
     * child count for the current DetailAST instance.
     */
    private void clearBranchTokenTypes() {
        DetailAstImpl prevParent = parent;
        while (prevParent != null) {
            prevParent.branchTokenTypes = null;
            prevParent = prevParent.parent;
        }
    }

}
