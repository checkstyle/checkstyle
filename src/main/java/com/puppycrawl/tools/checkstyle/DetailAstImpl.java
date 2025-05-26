///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.BitSet;
import java.util.List;

import javax.annotation.Nullable;

import org.antlr.v4.runtime.Token;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * The implementation of {@link DetailAST}. This should only be directly used to
 * create custom AST nodes and in 'JavaAstVisitor.java'.
 *
 * @noinspection FieldNotUsedInToString
 * @noinspectionreason FieldNotUsedInToString - We require a specific string format for
 *      printing to CLI.
 */
public final class DetailAstImpl implements DetailAST {

    /** Constant to indicate if not calculated the child count. */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /** The line number. **/
    private int lineNo = NOT_INITIALIZED;
    /** The column number. **/
    private int columnNo = NOT_INITIALIZED;

    /** Number of children. */
    private int childCount;
    /** The parent token. */
    private DetailAstImpl parent;
    /** Previous sibling. */
    private DetailAstImpl previousSibling;

    /** First child of this DetailAST. */
    private DetailAstImpl firstChild;

    /** First sibling of this DetailAST.*/
    private DetailAstImpl nextSibling;

    /** Text of this DetailAST. */
    private String text;

    /** The type of this DetailAST. */
    private int type;

    /**
     * All tokens on COMMENTS channel to the left of the current token up to the
     * preceding token on the DEFAULT_TOKEN_CHANNEL.
     */
    private List<Token> hiddenBefore;

    /**
     * All tokens on COMMENTS channel to the right of the current token up to the
     * next token on the DEFAULT_TOKEN_CHANNEL.
     */
    private List<Token> hiddenAfter;

    /**
     * All token types in this branch.
     * Token 'x' (where x is an int) is in this branch
     * if branchTokenTypes.get(x) is true.
     */
    private BitSet branchTokenTypes;

    /**
     * Initializes this DetailAstImpl.
     *
     * @param tokenType the type of this DetailAstImpl
     * @param tokenText the text of this DetailAstImpl
     */
    public void initialize(int tokenType, String tokenText) {
        type = tokenType;
        text = tokenText;
    }

    /**
     * Initializes this DetailAstImpl.
     *
     * @param token the token to generate this DetailAstImpl from
     */
    public void initialize(Token token) {
        text = token.getText();
        type = token.getType();
        lineNo = token.getLine();
        columnNo = token.getCharPositionInLine();
    }

    /**
     * Add previous sibling.
     *
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
                previousSiblingNode.setNextSibling(astImpl);
            }
            else if (parent != null) {
                parent.setFirstChild(astImpl);
            }

            astImpl.setNextSibling(this);
        }
    }

    /**
     * Add next sibling, pushes other siblings back.
     *
     * @param ast DetailAST object.
     */
    public void addNextSibling(DetailAST ast) {
        clearBranchTokenTypes();
        clearChildCountCache(parent);
        if (ast != null) {
            // parent is set in setNextSibling
            final DetailAstImpl sibling = nextSibling;
            final DetailAstImpl astImpl = (DetailAstImpl) ast;
            astImpl.setNextSibling(sibling);

            setNextSibling(astImpl);
        }
    }

    /**
     * Adds a new child to the current AST.
     *
     * @param child to DetailAST to add as child
     */
    public void addChild(DetailAST child) {
        clearBranchTokenTypes();
        clearChildCountCache(this);
        if (child != null) {
            final DetailAstImpl astImpl = (DetailAstImpl) child;
            astImpl.setParent(this);
        }
        DetailAST temp = firstChild;
        if (temp == null) {
            firstChild = (DetailAstImpl) child;
        }
        else {
            while (temp.getNextSibling() != null) {
                temp = temp.getNextSibling();
            }

            ((DetailAstImpl) temp).setNextSibling(child);
        }
    }

    @Override
    public int getChildCount() {
        // lazy init
        if (childCount == NOT_INITIALIZED) {
            childCount = 0;
            DetailAST child = firstChild;

            while (child != null) {
                childCount += 1;
                child = child.getNextSibling();
            }
        }
        return childCount;
    }

    @Override
    public int getChildCount(int tokenType) {
        int count = 0;
        for (DetailAST ast = firstChild; ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == tokenType) {
                count++;
            }
        }
        return count;
    }

    /**
     * Set the parent token.
     *
     * @param parent the parent token
     */
    private void setParent(DetailAstImpl parent) {
        DetailAstImpl instance = this;
        do {
            instance.clearBranchTokenTypes();
            instance.parent = parent;
            instance = instance.nextSibling;
        } while (instance != null);
    }

    @Override
    public DetailAST getParent() {
        return parent;
    }

    @Override
    public String getText() {
        return text;
    }

    /**
     * Sets the text for this DetailAstImpl.
     *
     * @param text the text field of this DetailAstImpl
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return type;
    }

    /**
     * Sets the type of this AST.
     *
     * @param type the token type of this DetailAstImpl
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getLineNo() {
        int resultNo = -1;

        if (lineNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            resultNo = findLineNo(firstChild);

            if (resultNo == -1) {
                resultNo = findLineNo(nextSibling);
            }
        }
        if (resultNo == -1) {
            resultNo = lineNo;
        }
        return resultNo;
    }

    /**
     * Set line number.
     *
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
            resultNo = findColumnNo(firstChild);

            if (resultNo == -1) {
                resultNo = findColumnNo(nextSibling);
            }
        }
        if (resultNo == -1) {
            resultNo = columnNo;
        }
        return resultNo;
    }

    /**
     * Set column number.
     *
     * @param columnNo
     *        column number.
     */
    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    @Override
    public DetailAST getLastChild() {
        DetailAstImpl ast = firstChild;
        while (ast != null && ast.nextSibling != null) {
            ast = ast.nextSibling;
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
     *
     * @return the token types that occur in the branch as a sorted set.
     */
    private BitSet getBranchTokenTypes() {
        // lazy init
        if (branchTokenTypes == null) {
            branchTokenTypes = new BitSet();
            branchTokenTypes.set(type);

            // add union of all children
            DetailAstImpl child = firstChild;
            while (child != null) {
                final BitSet childTypes = child.getBranchTokenTypes();
                branchTokenTypes.or(childTypes);

                child = child.nextSibling;
            }
        }
        return branchTokenTypes;
    }

    @Override
    public boolean branchContains(int tokenType) {
        return getBranchTokenTypes().get(tokenType);
    }

    @Override
    public DetailAST getPreviousSibling() {
        return previousSibling;
    }

    @Override
    @Nullable
    public DetailAST findFirstToken(int tokenType) {
        DetailAST returnValue = null;
        for (DetailAST ast = firstChild; ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == tokenType) {
                returnValue = ast;
                break;
            }
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return text + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

    @Override
    public DetailAstImpl getNextSibling() {
        return nextSibling;
    }

    @Override
    public DetailAstImpl getFirstChild() {
        return firstChild;
    }

    @Override
    public int getNumberOfChildren() {
        return getChildCount();
    }

    @Override
    public boolean hasChildren() {
        return firstChild != null;
    }

    /**
     * Clears the child count for the ast instance.
     *
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

    /**
     * Sets the next sibling of this AST.
     *
     * @param nextSibling the DetailAST to set as sibling
     */
    public void setNextSibling(DetailAST nextSibling) {
        clearBranchTokenTypes();
        clearChildCountCache(parent);
        this.nextSibling = (DetailAstImpl) nextSibling;
        if (nextSibling != null && parent != null) {
            ((DetailAstImpl) nextSibling).setParent(parent);
        }
        if (nextSibling != null) {
            ((DetailAstImpl) nextSibling).previousSibling = this;
        }
    }

    /**
     * Sets the first child of this AST.
     *
     * @param firstChild the DetailAST to set as first child
     */
    public void setFirstChild(DetailAST firstChild) {
        clearBranchTokenTypes();
        clearChildCountCache(this);
        this.firstChild = (DetailAstImpl) firstChild;
        if (firstChild != null) {
            ((DetailAstImpl) firstChild).setParent(this);
        }
    }

    /**
     * Removes all children of this AST.
     */
    public void removeChildren() {
        firstChild = null;
    }

    /**
     * Get list of tokens on COMMENTS channel to the left of the
     * current token up to the preceding token on the DEFAULT_TOKEN_CHANNEL.
     *
     * @return list of comment tokens
     */
    public List<Token> getHiddenBefore() {
        List<Token> returnList = null;
        if (hiddenBefore != null) {
            returnList = UnmodifiableCollectionUtil.unmodifiableList(hiddenBefore);
        }
        return returnList;
    }

    /**
     * Get list tokens on COMMENTS channel to the right of the current
     * token up to the next token on the DEFAULT_TOKEN_CHANNEL.
     *
     * @return list of comment tokens
     */
    public List<Token> getHiddenAfter() {
        List<Token> returnList = null;
        if (hiddenAfter != null) {
            returnList = UnmodifiableCollectionUtil.unmodifiableList(hiddenAfter);
        }
        return returnList;
    }

    /**
     * Sets the hiddenBefore token field.
     *
     * @param hiddenBefore comment token preceding this DetailAstImpl
     */
    public void setHiddenBefore(List<Token> hiddenBefore) {
        this.hiddenBefore = UnmodifiableCollectionUtil.unmodifiableList(hiddenBefore);
    }

    /**
     * Sets the hiddenAfter token field.
     *
     * @param hiddenAfter comment token following this DetailAstImpl
     */
    public void setHiddenAfter(List<Token> hiddenAfter) {
        this.hiddenAfter = UnmodifiableCollectionUtil.unmodifiableList(hiddenAfter);
    }
}
