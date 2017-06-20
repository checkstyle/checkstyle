package com.puppycrawl.tools.checkstyle.xpath;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.type.Type;

/**
 * Represents element node of Xpath-tree.
 *
 * @author Timur Tibeyev
 */
public class ElementNode extends AbstractNode {
    /** Represents value of TokenTypes#IDENT. */
    private String ident;

    /**
     * Creates a new {@code ElementNode} instance.
     *
     * @param tokenType the number representing constant from {@code TokenTypes}
     * @param parent {@code Node} parent of the current node
     * @param document {@code Node} root of the tree
     * @param detailNode reference to {@code DetailNode}
     * @param detailAST reference to {@code DetailAST}
     */
    public ElementNode(int tokenType, AbstractNode parent, AbstractNode document, DetailNode detailNode, DetailAST detailAST) {
        super(tokenType);
        this.parent = parent;
        this.document = document;
        this.detailNode = detailNode;
        this.detailAST = detailAST;

        if (detailAST.branchContains(TokenTypes.IDENT)) {
            this.ident = detailAST.findFirstToken(TokenTypes.IDENT).getText();
        }

        if (parent != null) {
            (parent).addChild(this);
        }
    }

    /**
     * Returns type of the node.
     * @return node kind
     */
    public int getNodeKind() {
        return Type.ELEMENT;
    }

    /**
     * Returns line number.
     * @return line number
     */
    public int getLineNumber() {
        return detailAST.getLineNo();
    }

    /**
     * Returns column number.
     * @return column number
     */
    public int getColumnNumber() {
        return detailAST.getColumnNo();
    }

    /**
     * Returns local part.
     * @return local part
     */
    public String getLocalPart() {
        return detailAST.getText();
    }

    /**
     * Returns string value.
     * @return string value
     */
    public String getStringValue() {
        return detailAST.getText();
    }

    /**
     * Returns display name.
     * @return display name
     */
    public String getDisplayName() {
        return detailAST.getText();
    }

    /**
     * Returns attribute value.
     * @param namespace namespace
     * @param localPart actual name of the attribute
     * @return attribute value
     */
    public String getAttributeValue(String namespace, String localPart) {
        if (localPart.equals("text")) {
            return ident;
        } else {
            throw new RuntimeException("Not implemented yet");
        }
    }
}
