////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import net.sf.saxon.Configuration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.expr.parser.Location;
import net.sf.saxon.om.AtomicSequence;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NamespaceBinding;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.SchemaType;

/**
 * Represents general class for {@code ElementNode}, {@code RootNode} and {@code AttributeNode}.
 *
 * @author Timur Tibeyev
 */
public abstract class AbstractNode implements NodeInfo {

    /** The children. */
    private final List<AbstractNode> children = new ArrayList<>();

    /**
     * Getter method for token type.
     * @return token type
     */
    public abstract int getTokenType();

    /**
     * Returns underlying node.
     * @return underlying node
     */
    public abstract DetailAST getUnderlyingNode();

    /**
     * Getter method for children.
     * @return children list
     */
    protected List<AbstractNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Add new child node to children list.
     * @param node child node
     */
    protected void addChild(AbstractNode node) {
        children.add(node);
    }

    /**
     * Returns true if nodes are same, false otherwise.
     * @param nodeInfo other node
     * @return {@code TreeInfo}
     */
    @Override
    public boolean isSameNodeInfo(NodeInfo nodeInfo) {
        return this == nodeInfo;
    }

    /**
     * Returns if implementation provides fingerprints.
     * @return {@code boolean}
     */
    @Override
    public boolean hasFingerprint() {
        return false;
    }

    /**
     * Returns uri of the namespace for the current node.
     * @return uri
     */
    @Override
    public String getURI() {
        return "";
    }

    /**
     * Returns if current node has children.
     * @return if current node has children
     */
    @Override
    public boolean hasChildNodes() {
        return !children.isEmpty();
    }

    /**
     * Determines axis iteration algorithm.
     * @param axisNumber element from {@code AxisInfo}
     * @param nodeTest filter for iterator
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(byte axisNumber, NodeTest nodeTest) {
        AxisIterator axisIterator = iterateAxis(axisNumber);
        if (nodeTest != null) {
            axisIterator = new Navigator.AxisFilter(axisIterator, nodeTest);
        }
        return axisIterator;
    }

    /**
     * Compares current object with specified for order.
     * @param nodeInfo another {@code NodeInfo} object
     * @return number representing order of current object to specified one
     */
    @Override
    public int compareOrder(NodeInfo nodeInfo) {
        return getLocalPart().compareTo(nodeInfo.getLocalPart());
    }

    /**
     * Returns namespace array. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @param namespaceBindings namespace array
     * @return namespace array
     */
    @Override
    public final NamespaceBinding[] getDeclaredNamespaces(NamespaceBinding... namespaceBindings) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns tree info. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return tree info
     */
    @Override
    public final TreeInfo getTreeInfo() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return boolean
     */
    @Override
    public final boolean isId() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return boolean
     */
    @Override
    public final boolean isIdref() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return boolean
     */
    @Override
    public final boolean isNilled() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns boolean. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return boolean
     */
    @Override
    public final boolean isStreamed() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns configuration. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return configuration
     */
    @Override
    public final Configuration getConfiguration() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Sets system id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @param systemId system id
     */
    @Override
    public final void setSystemId(String systemId) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns system id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return system id
     */
    @Override
    public final String getSystemId() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns public id. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return public id
     */
    @Override
    public final String getPublicId() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns base uri. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return base uri
     */
    @Override
    public final String getBaseURI() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns location. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return location
     */
    @Override
    public final Location saveLocation() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Compares current object with specified for position. Throws
     * {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @param nodeInfo another {@code NodeInfo} object
     * @return constant from {@code AxisInfo} representing order of
     *      current object to specified one
     */
    @Override
    public final int comparePosition(NodeInfo nodeInfo) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns head. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return head
     */
    @Override
    public final Item head() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns iterator. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return iterator
     */
    @Override
    public final SequenceIterator iterate() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns CharSequence string value. Throws {@code UnsupportedOperationException},
     * because no child class implements it and this method is not used for querying.
     * @return CharSequence string value
     */
    @Override
    public final CharSequence getStringValueCS() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns fingerprint. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return fingerprint
     */
    @Override
    public final int getFingerprint() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns display name. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return display name
     */
    @Override
    public final String getDisplayName() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns prefix. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return prefix
     */
    @Override
    public final String getPrefix() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns type of the schema. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return type of the schema
     */
    @Override
    public final SchemaType getSchemaType() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns AtomicSequence. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @return AtomicSequence
     */
    @Override
    public final AtomicSequence atomize() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Generate id method. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @param fastStringBuffer fastStringBuffer
     */
    @Override
    public final void generateId(FastStringBuffer fastStringBuffer) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Copy method. Throws {@code UnsupportedOperationException}, because no child
     * class implements it and this method is not used for querying.
     * @param receiver receiver
     * @param index index
     * @param location location
     */
    @Override
    public final void copy(Receiver receiver, int index, Location location) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns UnsupportedOperationException exception. Methods which throws this exception are
     * not supported for all nodes.
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }
}
