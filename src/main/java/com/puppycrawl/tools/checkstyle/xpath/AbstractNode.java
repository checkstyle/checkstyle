package com.puppycrawl.tools.checkstyle.xpath;


import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import net.sf.saxon.Configuration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.expr.parser.Location;
import net.sf.saxon.om.*;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.tree.wrapper.SiblingCountingNode;
import net.sf.saxon.tree.wrapper.VirtualNode;
import net.sf.saxon.type.SchemaType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents general class for {@code ElementNode} and {@code RootNode}
 *
 * @author Timur Tibeyev
 */
public class AbstractNode implements VirtualNode, SiblingCountingNode {
    /** The parent of the current node. */
    protected AbstractNode parent;

    /** The root node. */
    protected AbstractNode document;

    /** The children. */
    protected List<AbstractNode> children;

    /** The tree info. */
    protected TreeInfo treeInfo;

    /** The tree info. */
    protected int tokenType;

    /** The javadoc node. */
    protected DetailNode detailNode;

    /** The ast node. */
    protected DetailAST detailAST;

    /**
     * Creates a new {@code ElementNode} instance.
     *
     * @param tokenType the number representing constant from {@code TokenTypes}
     */
    public AbstractNode(int tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Creates a new {@code ElementNode} instance.
     */
    public AbstractNode() {
    }

    /**
     * Add new child node to children list.
     */
    public void addChild(AbstractNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

    /**
     * Returns underlying node.
     * @return underlying node
     */
    public Object getUnderlyingNode() {
        return detailAST;
    }

    /**
     * Returns underlying node.
     * @return underlying node
     */
    public Object getRealNode() {
        return detailAST;
    }

    /**
     * Returns object representing information about tree.
     * @return {@code TreeInfo}
     */
    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    /**
     * Set tree information.
     */
    public void setTreeInfo(TreeInfo treeInfo) {
        this.treeInfo = treeInfo;
    }

    /**
     * Returns true if nodes are same, false otherwise.
     * @return {@code TreeInfo}
     */
    public boolean isSameNodeInfo(NodeInfo nodeInfo) {
        return this == nodeInfo;
    }

    /**
     * Returns if implementation provides fingerprints.
     * @return {@code boolean}
     */
    public boolean hasFingerprint() {
        return false;
    }

    /**
     * Returns uri of the namespace for the current node.
     * @return uri
     */
    public String getURI() {
        return "";
    }

    /**
     * Returns parent of the current node.
     * @return parent
     */
    public NodeInfo getParent() {
        return parent;
    }

    /**
     * Returns root of the tree.
     * @return root
     */
    public NodeInfo getRoot() {
        return treeInfo.getRootNode();
    }

    /**
     * Returns if current node has children.
     * @return if current node has children
     */
    public boolean hasChildNodes() {
        return (children!= null && !children.isEmpty());
    }

    /**
     * Determines axis iteration algorithm
     * @implNote To be updated.
     */
    public AxisIterator iterateAxis(byte axisNumber) {
        if (parent == null) {
            return new ArrayIterator.OfNodes(children.toArray(new AbstractNode[children.size()]));
        } else {
            return SingleNodeIterator.makeIterator(parent);
        }
    }

    /**
     * Determines axis iteration algorithm with filter
     * @implNote To be updated.
     */
    public AxisIterator iterateAxis(byte b, NodeTest nodeTest) {
        AxisIterator axisIterator = this.iterateAxis(b);
        if(nodeTest != null) {
            axisIterator = new Navigator.AxisFilter(axisIterator, nodeTest);
        }

        return axisIterator;
    }


    public NamespaceBinding[] getDeclaredNamespaces(NamespaceBinding[] namespaceBindings) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isId() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isIdref() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isNilled() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isStreamed() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getSiblingPosition() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Configuration getConfiguration() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getNodeKind() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setSystemId(String systemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getSystemId() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getPublicId() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getBaseURI() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getLineNumber() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getColumnNumber() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Location saveLocation() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int compareOrder(NodeInfo nodeInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int comparePosition(NodeInfo nodeInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Item head() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public SequenceIterator iterate() throws XPathException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getStringValue() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CharSequence getStringValueCS() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getFingerprint() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getLocalPart() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getDisplayName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getPrefix() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public SchemaType getSchemaType() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public AtomicSequence atomize() throws XPathException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getAttributeValue(String s, String s1) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void generateId(FastStringBuffer fastStringBuffer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void copy(Receiver receiver, int i, Location location) throws XPathException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
