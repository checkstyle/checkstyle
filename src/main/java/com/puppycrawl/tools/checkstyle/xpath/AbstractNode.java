////
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

package com.puppycrawl.tools.checkstyle.xpath;

import java.util.Collections;
import java.util.List;

import net.sf.saxon.Configuration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.om.AtomicSequence;
import net.sf.saxon.om.NamespaceBinding;
import net.sf.saxon.om.NamespaceMap;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodePredicate;
import net.sf.saxon.s9api.Location;
import net.sf.saxon.str.UnicodeString;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.SchemaType;

/**
 * Represents general class for {@code ElementNode}, {@code RootNode} and {@code AttributeNode}.
 */
public abstract class AbstractNode implements NodeInfo {

    /** The {@code TreeInfo} object. */
    private final TreeInfo treeInfo;

    /** The children. */
    private List<AbstractNode> children;

    /**
     * Constructor of the abstract class {@code AbstractNode}.
     *
     * @param treeInfo {@code TreeInfo} object
     */
    protected AbstractNode(TreeInfo treeInfo) {
        this.treeInfo = treeInfo;
    }

    /**
     * Getter method for token type.
     *
     * @return token type
     */
    public abstract int getTokenType();

    /**
     * Returns underlying node.
     *
     * @return underlying node
     */
    public abstract Object getUnderlyingNode();

    /**
     * Getter method for node depth.
     *
     * @return depth
     */
    public abstract int getDepth();

    /**
     * Creates nodes for children.
     *
     * @return children list
     */
    protected abstract List<AbstractNode> createChildren();

    /**
     * Getter method for children.
     *
     * @return children list
     */
    protected List<AbstractNode> getChildren() {
        if (children == null) {
            children = createChildren();
        }
        return Collections.unmodifiableList(children);
    }

    /**
     * Returns true if nodes are same, false otherwise.
     *
     * @param nodeInfo other node
     * @return {@code TreeInfo}
     */
    @Override
    public boolean isSameNodeInfo(NodeInfo nodeInfo) {
        return this == nodeInfo;
    }

    /**
     * Returns if implementation provides fingerprints.
     *
     * @return {@code boolean}
     */
    @Override
    public boolean hasFingerprint() {
        return false;
    }

    /**
     * Get the URI part of the name of this node.
     *
     * @return The URI of the namespace of this node.
     */
    @Override
    public NamespaceUri getNamespaceUri() {
        return NamespaceUri.NULL;
    }

    /**
     * Returns uri of the namespace for the current node.
     *
     * @return uri
     */
    @Override
    public String getURI() {
        return "";
    }

    /**
     * Determines axis iteration algorithm.
     *
     * @param axisNumber element from {@code AxisInfo}
     * @param nodeTest filter for iterator
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(int axisNumber, NodePredicate nodeTest) {
        AxisIterator axisIterator = iterateAxis(axisNumber);
        if (nodeTest != null) {
            axisIterator = new Navigator.AxisFilter(axisIterator, nodeTest);
        }
        return axisIterator;
    }

    /**
     * Returns tree info.
     *
     * @return tree info
     */
    @Override
    public final TreeInfo getTreeInfo() {
        return treeInfo;
    }

    /**
     * Returns string value. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return string value
     */
    @Override
    public String getStringValue() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns namespace array. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @param namespaceBindings namespace array
     * @return namespace array
     */
    @Override
    public final NamespaceBinding[] getDeclaredNamespaces(NamespaceBinding[] namespaceBindings) {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns namespace array. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return namespace map
     */
    @Override
    public NamespaceMap getAllNamespaces() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return boolean
     */
    @Override
    public final boolean isId() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return boolean
     */
    @Override
    public final boolean isIdref() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return boolean
     */
    @Override
    public final boolean isNilled() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return boolean
     */
    @Override
    public final boolean isStreamed() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns configuration. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return configuration
     */
    @Override
    public final Configuration getConfiguration() {
        throw createUnsupportedOperationException();
    }

    /**
     * Sets system id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @param systemId system id
     */
    @Override
    public final void setSystemId(String systemId) {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns system id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return system id
     */
    @Override
    public final String getSystemId() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns public id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return public id
     */
    @Override
    public final String getPublicId() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns base uri. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return base uri
     */
    @Override
    public final String getBaseURI() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns location. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return location
     */
    @Override
    public final Location saveLocation() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns the value of the item as a Unicode string.
     * Throws {@code UnsupportedOperationException}, because no child class implements it and
     * this method is not used for querying.
     *
     * @return CharSequence string value
     */
    @Override
    public final UnicodeString getUnicodeStringValue() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns fingerprint. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return fingerprint
     */
    @Override
    public final int getFingerprint() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns display name. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return display name
     */
    @Override
    public final String getDisplayName() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns prefix. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return prefix
     */
    @Override
    public final String getPrefix() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns type of the schema. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return type of the schema
     */
    @Override
    public final SchemaType getSchemaType() {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns AtomicSequence. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @return AtomicSequence
     */
    @Override
    public final AtomicSequence atomize() {
        throw createUnsupportedOperationException();
    }

    /**
     * Generate id method. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @param buffer buffer
     */
    @Override
    public final void generateId(StringBuilder buffer) {
        throw createUnsupportedOperationException();
    }

    /**
     * Copy method. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     *
     * @param receiver receiver
     * @param index index
     * @param location location
     */
    @Override
    public final void copy(Receiver receiver, int index, Location location) {
        throw createUnsupportedOperationException();
    }

    /**
     * Returns UnsupportedOperationException exception. Methods which throws this exception are
     * not supported for all nodes.
     *
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException createUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }

}
