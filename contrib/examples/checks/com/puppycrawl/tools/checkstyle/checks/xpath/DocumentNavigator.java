////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.xpath;

import java.util.ArrayList;
import java.util.Iterator;

import org.jaxen.DefaultNavigator;
import org.jaxen.XPath;
import org.jaxen.util.SingleObjectIterator;
import org.saxpath.SAXPathException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Navigates around a DetailAST, using XPath semantics.
 * Requires jaxen, http://jaxen.sourceforge.net and
 * saxpath, http://sourceforge.net/projects/saxpath/.
 * Idea shamelessly stolen from the equivalent PMD code (pmd.sourceforge.net).
 * @author Rick Giles
 */
public class DocumentNavigator
    extends DefaultNavigator
{
    /** Iterator for an empty sequence */
    private static final Iterator EMPTY_ITERATOR = new ArrayList().iterator();

    /**
     * @see org.jaxen.DefaultNavigator#getAttributeName(java.lang.Object)
     */
    public String getAttributeName(Object aObject)
    {
        return ((Attribute) aObject).getName();
    }

    /**
     * @see org.jaxen.DefaultNavigator#getAttributeNamespaceUri
     */
    public String getAttributeNamespaceUri(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getAttributeQName(java.lang.Object)
     */
    public String getAttributeQName(Object aObject)
    {
        return ((Attribute) aObject).getName();
    }

    /**
     * @see org.jaxen.DefaultNavigator#getAttributeStringValue(java.lang.Object)
     */
    public String getAttributeStringValue(Object aObject)
    {
        return ((Attribute) aObject).getValue();
    }

    /**
     * @see org.jaxen.DefaultNavigator#getCommentStringValue(java.lang.Object)
     */
    public String getCommentStringValue(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getElementName(java.lang.Object)
     */
    public String getElementName(Object aObject)
    {
        final int type = ((DetailAST) aObject).getType();
        return TokenTypes.getTokenName(type);
    }

    /**
     * @see org.jaxen.DefaultNavigator#getElementNamespaceUri(java.lang.Object)
     */
    public String getElementNamespaceUri(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getElementQName(java.lang.Object)
     */
    public String getElementQName(Object aObject)
    {
        return getElementName(aObject);
    }

    /**
     * @see org.jaxen.DefaultNavigator#getElementStringValue(java.lang.Object)
     */
    public String getElementStringValue(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getNamespacePrefix(java.lang.Object)
     */
    public String getNamespacePrefix(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getNamespaceStringValue(java.lang.Object)
     */
    public String getNamespaceStringValue(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#getTextStringValue(java.lang.Object)
     */
    public String getTextStringValue(Object aObject)
    {
        return null;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isAttribute(java.lang.Object)
     */
    public boolean isAttribute(Object aObject)
    {
        return aObject instanceof Attribute;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isComment(java.lang.Object)
     */
    public boolean isComment(Object aObject)
    {
        return false;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isDocument(java.lang.Object)
     */
    public boolean isDocument(Object aObject)
    {
        if (aObject instanceof DetailAST) {
            final DetailAST node = (DetailAST) aObject;
            return (node.getType() == TokenTypes.EOF);
        }
        else {
            return false;
        }
    }

    /**
     * @see org.jaxen.DefaultNavigator#isElement(java.lang.Object)
     */
    public boolean isElement(Object aObject)
    {
        return aObject instanceof DetailAST;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isNamespace(java.lang.Object)
     */
    public boolean isNamespace(Object aObject)
    {
        return false;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isProcessingInstruction(java.lang.Object)
     */
    public boolean isProcessingInstruction(Object aObject)
    {
        return false;
    }

    /**
     * @see org.jaxen.DefaultNavigator#isText(java.lang.Object)
     */
    public boolean isText(Object aObject)
    {
        return false;
    }

    /**
     * @see org.jaxen.DefaultNavigator#parseXPath(java.lang.String)
     */
    public XPath parseXPath(String aObject)
        throws SAXPathException
    {
        return null;
    }

    /**
     * @see org.jaxen.Navigator#getParentNode(java.lang.Object)
     */
    public Object getParentNode(Object aObject)
    {
        if (aObject instanceof DetailAST) {
            return ((DetailAST) aObject).getParent();
        }
        else {
            return ((Attribute) aObject).getParent();
        }
    }

    /**
     * @see org.jaxen.Navigator#getAttributeAxisIterator(java.lang.Object)
     */
    public Iterator getAttributeAxisIterator(Object aObject)
    {
        final DetailAST contextNode = (DetailAST) aObject;
        return new AttributeAxisIterator(contextNode);
    }

    /**
     * Get an iterator over all of this node's children.
     *
     * @param aObject The context node for the child axis.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getChildAxisIterator(Object aObject)
    {
        return new NodeIterator((DetailAST) aObject)
        {
            /** @see NodeIterator */
            protected DetailAST getFirstNode(DetailAST aAST)
            {
                return getFirstChild(aAST);
            }

            /** @see NodeIterator */
            protected DetailAST getNextNode(DetailAST aAST)
            {
                return getNextSibling(aAST);
            }
        };
    }

    /**
     * Get a (single-member) iterator over this node's parent.
     *
     * @param aObject the context node for the parent axis.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getParentAxisIterator(Object aObject)
    {
        if (isAttribute(aObject)) {
            return new SingleObjectIterator(((Attribute) aObject).getParent());
        }
        else {
            DetailAST parent = ((DetailAST) aObject).getParent();
            if (parent != null) {
                return new SingleObjectIterator(parent);
            }
            else {
                return EMPTY_ITERATOR;
            }
        }
    }

    /**
     * Get an iterator over all following siblings.
     *
     * @param aObject the context node for the sibling iterator.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getFollowingSiblingAxisIterator(Object aObject)
    {
        return new NodeIterator((DetailAST) aObject)
        {
            /** @see NodeIterator */
            protected DetailAST getFirstNode(DetailAST aAST)
            {
                return getNextNode(aAST);
            }

            /** @see NodeIterator */
            protected DetailAST getNextNode(DetailAST aAST)
            {
                return getNextSibling(aAST);
            }
        };
    }

    /**
     * Get an iterator over all preceding siblings.
     *
     * @param aObject The context node for the preceding sibling axis.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getPrecedingSiblingAxisIterator(Object aObject)
    {
        return new NodeIterator((DetailAST) aObject)
        {
            /** @see NodeIterator */
            protected DetailAST getFirstNode(DetailAST aAST)
            {
                return getNextNode(aAST);
            }

            /** @see NodeIterator */
            protected DetailAST getNextNode(DetailAST aAST)
            {
                return getPreviousSibling(aAST);
            }
        };
    }

    /**
     * Get an iterator over all following nodes, depth-first.
     *
     * @param aObject The context node for the following axis.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getFollowingAxisIterator(Object aObject)
    {
        return new NodeIterator((DetailAST) aObject)
        {
            /** @see NodeIterator */
            protected DetailAST getFirstNode(DetailAST aAST)
            {
                if (aAST == null) {
                    return null;
                }
                else {
                    final DetailAST sibling = getNextSibling(aAST);
                    if (sibling == null) {
                        return getFirstNode(aAST.getParent());
                    }
                    else {
                        return sibling;
                    }
                }
            }

            /** @see NodeIterator */
            protected DetailAST getNextNode(DetailAST aAST)
            {
                if (aAST == null) {
                    return null;
                }
                else {
                    DetailAST n = getFirstChild(aAST);
                    if (n == null) {
                        n = getNextSibling(aAST);
                    }
                    if (n == null) {
                        return getFirstNode(aAST.getParent());
                    }
                    else {
                        return n;
                    }
                }
            }
        };
    }

    /**
     * Get an iterator over all preceding nodes, depth-first.
     *
     * @param aObject The context node for the preceding axis.
     * @return A possibly-empty iterator (not null).
     */
    public Iterator getPrecedingAxisIterator(Object aObject)
    {
        return new NodeIterator((DetailAST) aObject)
        {
            /** @see NodeIterator */
            protected DetailAST getFirstNode(DetailAST aAST)
            {
                if (aAST == null) {
                    return null;
                }
                else {
                    final DetailAST sibling = getPreviousSibling(aAST);
                    if (sibling == null) {
                        return getFirstNode(aAST.getParent());
                    }
                    else {
                        return sibling;
                    }
                }
            }

            /** @see NodeIterator */
            protected DetailAST getNextNode(DetailAST aAST)
            {
                if (aAST == null) {
                    return null;
                }
                else {
                    DetailAST n = getLastChild(aAST);
                    if (n == null) {
                        n = getPreviousSibling(aAST);
                    }
                    if (n == null) {
                        return getFirstNode(aAST.getParent());
                    }
                    else {
                        return n;
                    }
                }
            }
        };
    }

    /** @see org.jaxen.Navigator#getDocumentNode(java.lang.Object) */
    public Object getDocumentNode(Object aObject)
    {
        if (isDocument(aObject)) {
            return aObject;
        }
        else {
            return getDocumentNode(getParentNode(aObject));
        }
    }
}
