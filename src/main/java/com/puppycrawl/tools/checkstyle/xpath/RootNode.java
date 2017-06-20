package com.puppycrawl.tools.checkstyle.xpath;

import net.sf.saxon.type.Type;

/**
 * Represents root node of Xpath-tree.
 *
 * @author Timur Tibeyev
 */
public class RootNode extends AbstractNode {

    /**
     * Returns type of the node.
     * @return node kind
     */
    public int getNodeKind() {
        return Type.DOCUMENT;
    }

    /**
     * Returns line number.
     * @return line number
     */
    public int getLineNumber() {
        return 0;
    }

    /**
     * Returns column number.
     * @return column number
     */
    public int getColumnNumber() {
        return 0;
    }

    /**
     * Returns local part.
     * @return local part
     */
    public String getLocalPart() {
        return "ROOT";
    }

    /**
     * Returns string value.
     * @return string value
     */
    public String getStringValue() {
        return "ROOT";
    }

    /**
     * Returns display name.
     * @return display name
     */
    public String getDisplayName() {
        return "ROOT";
    }
}
