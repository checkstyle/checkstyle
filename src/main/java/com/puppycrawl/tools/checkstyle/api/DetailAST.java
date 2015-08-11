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

package com.puppycrawl.tools.checkstyle.api;

import java.util.BitSet;

import antlr.CommonASTWithHiddenTokens;
import antlr.Token;
import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.Utils;

/**
 * An extension of the CommonAST that records the line and column
 * number.  The idea was taken from <a target="_top"
 * href="http://www.jguru.com/faq/view.jsp?EID=62654">Java Guru
 * FAQ: How can I include line numbers in automatically generated
 * ASTs?</a>.
 * @author Oliver Burn
 * @author lkuehne
 * @see <a href="http://www.antlr.org/">ANTLR Website</a>
 */
public final class DetailAST extends CommonASTWithHiddenTokens {
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -2580884815577559874L;

    /** constant to indicate if not calculated the child count */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /** the line number **/
    private int lineNo = NOT_INITIALIZED;
    /** the column number **/
    private int columnNo = NOT_INITIALIZED;

    /** number of children */
    private int childCount = NOT_INITIALIZED;
    /** the parent token */
    private DetailAST parent;
    /** previous sibling */
    private DetailAST previousSibling;

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
        columnNo = tok.getColumn() - 1; // expect columns to start @ 0
    }

    @Override
    public void initialize(AST ast) {
        final DetailAST da = (DetailAST) ast;
        setText(da.getText());
        setType(da.getType());
        lineNo = da.getLineNo();
        columnNo = da.getColumnNo();
        hiddenAfter = da.getHiddenAfter();
        hiddenBefore = da.getHiddenBefore();
    }

    @Override
    public void setFirstChild(AST ast) {
        childCount = NOT_INITIALIZED;
        super.setFirstChild(ast);
        if (ast != null) {
            ((DetailAST) ast).setParent(this);
        }
    }

    @Override
    public void setNextSibling(AST ast) {
        super.setNextSibling(ast);
        if (ast != null && parent != null) {
            ((DetailAST) ast).setParent(parent);
        }
        if (ast != null) {
            ((DetailAST) ast).setPreviousSibling(this);
        }
    }

    /**
     * Add previous sibling.
     * @param ast
     *        DetailAST object.
     */
    public void addPreviousSibling(DetailAST ast) {
        if (ast != null) {
            ast.setParent(parent);
            final DetailAST previousSiblingNode = getPreviousSibling();

            if (previousSiblingNode != null) {
                ast.setPreviousSibling(previousSiblingNode);
                previousSiblingNode.setNextSibling(ast);
            }
            else if (parent != null) {
                parent.setFirstChild(ast);
            }

            ast.setNextSibling(this);
            setPreviousSibling(ast);
        }
    }

    /**
     * Add next sibling.
     * @param ast
     *        DetailAST object.
     */
    public void addNextSibling(DetailAST ast) {
        if (ast != null) {
            ast.setParent(parent);
            final DetailAST nextSibling = getNextSibling();

            if (nextSibling != null) {
                ast.setNextSibling(nextSibling);
                nextSibling.setPreviousSibling(ast);
            }

            ast.setPreviousSibling(this);
            setNextSibling(ast);
        }
    }

    /**
     * Sets previous sibling.
     * @param ast a previous sibling
     */
    void setPreviousSibling(DetailAST ast) {
        previousSibling = ast;
    }

    @Override
    public void addChild(AST ast) {
        super.addChild(ast);
        if (ast != null) {
            ((DetailAST) ast).setParent(this);
            getFirstChild().setParent(this);
        }
    }

    /**
     * Returns the number of child nodes one level below this node. That is is
     * does not recurse down the tree.
     * @return the number of child nodes
     */
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

    /**
     * Set the parent token.
     * @param parent the parent token
     */
    void setParent(DetailAST parent) {
        this.parent = parent;
        final DetailAST nextSibling = getNextSibling();
        if (nextSibling != null) {
            nextSibling.setParent(parent);
            nextSibling.setPreviousSibling(this);
        }
    }

    /**
     * Returns the parent token.
     * @return the parent token
     */
    public DetailAST getParent() {
        return parent;
    }

    /** @return the line number **/
    public int getLineNo() {
        if (lineNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            DetailAST child = getFirstChild();
            while (child != null) {
                // comment node can't be start of any java statement/definition
                if (Utils.isCommentType(child.getType())) {
                    child = child.getNextSibling();
                }
                else {
                    return child.getLineNo();
                }
            }

            DetailAST sibling = getNextSibling();
            while (sibling != null) {
                // comment node can't be start of any java statement/definition
                if (Utils.isCommentType(sibling.getType())) {
                    sibling = sibling.getNextSibling();
                }
                else {
                    return sibling.getLineNo();
                }
            }
        }
        return lineNo;
    }

    /**
     * Set line number.
     * @param lineNo
     *        line number.
     */
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    /** @return the column number **/
    public int getColumnNo() {
        if (columnNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            DetailAST child = getFirstChild();
            while (child != null) {
                // comment node can't be start of any java statement/definition
                if (Utils.isCommentType(child.getType())) {
                    child = child.getNextSibling();
                }
                else {
                    return child.getColumnNo();
                }
            }

            DetailAST sibling = getNextSibling();
            while (sibling != null) {
                // comment node can't be start of any java statement/definition
                if (Utils.isCommentType(sibling.getType())) {
                    sibling = sibling.getNextSibling();
                }
                else {
                    return sibling.getColumnNo();
                }
            }
        }
        return columnNo;
    }

    /**
     * Set column number.
     * @param columnNo
     *        column number.
     */
    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    /** @return the last child node */
    public DetailAST getLastChild() {
        DetailAST ast = getFirstChild();
        while (ast != null && ast.getNextSibling() != null) {
            ast = ast.getNextSibling();
        }
        return ast;
    }

    /**
     * @return the token types that occur in the branch as a sorted set.
     */
    private BitSet getBranchTokenTypes() {
        // lazy init
        if (branchTokenTypes == null) {

            branchTokenTypes = new BitSet();
            branchTokenTypes.set(getType());

            // add union of all childs
            DetailAST child = getFirstChild();
            while (child != null) {
                final BitSet childTypes = child.getBranchTokenTypes();
                branchTokenTypes.or(childTypes);

                child = child.getNextSibling();
            }
        }
        return branchTokenTypes;
    }

    /**
     * Checks if this branch of the parse tree contains a token
     * of the provided type.
     * @param type a TokenType
     * @return true if and only if this branch (including this node)
     * contains a token of type {@code type}.
     */
    public boolean branchContains(int type) {
        return getBranchTokenTypes().get(type);
    }

    /**
     * Returns the number of direct child tokens that have the specified type.
     * @param type the token type to match
     * @return the number of matching token
     */
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
     * Returns the previous sibling or null if no such sibling exists.
     * @return the previous sibling or null if no such sibling exists.
     */
    public DetailAST getPreviousSibling() {
        return previousSibling;
    }

    /**
     * Returns the first child token that makes a specified type.
     * @param type the token type to match
     * @return the matching token, or null if no match
     */
    public DetailAST findFirstToken(int type) {
        DetailAST retVal = null;
        for (DetailAST ast = getFirstChild(); ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == type) {
                retVal = ast;
                break;
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

    @Override
    public DetailAST getNextSibling() {
        return (DetailAST) super.getNextSibling();
    }

    @Override
    public DetailAST getFirstChild() {
        return (DetailAST) super.getFirstChild();
    }

}
