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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Iterator for DetailAST nodes in a syntax tree.
 * @author Rick Giles
 */
public abstract class NodeIterator
    implements Iterator
{

    /** The DetailAST for this iterator */
    private DetailAST mNode;

    /**
     * Constructs a <code>NodeIterator</code> for a DetailAST.
     * @param aAST the DetailAST.
     */
    public NodeIterator(DetailAST aAST)
    {
        this.mNode = getFirstNode(aAST);
    }

    /** @see java.util.Iterator#hasNext() */
    public boolean hasNext()
    {
        return mNode != null;
    }

    /** @see java.util.Iterator#next() */
    public Object next()
    {
        if (mNode == null) {
            throw new NoSuchElementException();
        }
        final DetailAST ret = mNode;
        mNode = getNextNode(mNode);
        return ret;
    }

    /** @see java.util.Iterator#remove() */
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the first node of an iterator over a DetailAST.
     * @param aAST the DetailAST.
     * @return the first node of an iterator over aAST.
     */
    protected abstract DetailAST getFirstNode(DetailAST aAST);

    /**
     * Gets the next node for an iterator over a DetailAST.
     * @param aAST the DetailAST.
     * @return the next node of aAST.
     */
    protected abstract DetailAST getNextNode(DetailAST aAST);

    /**
     * Gets the previous sibling of a DetailAST.
     * @param aAST the DetailAST.
     * @return the previous sibling of aAST.
     */
    protected DetailAST getPreviousSibling(DetailAST aAST)
    {
        return aAST.getPreviousSibling();
    }

    /**
     * Get the next sibling of a DetailAST.
     * @param aAST the DetailAST.
     * @return the next sibling of aAST.
     */
    protected DetailAST getNextSibling(DetailAST aAST)
    {
        return (DetailAST) aAST.getNextSibling();
    }

    /**
     * Get the first child of a DetailAST.
     * @param aAST the DetailAST.
     * @return the first child of aAST.
     */
    protected DetailAST getFirstChild(DetailAST aAST)
    {
        return (DetailAST) aAST.getFirstChild();
    }

    /**
     * Get the last child of a DetailAST.
     * @param aAST the DetailAST.
     * @return the last child of aAST.
     */
    protected DetailAST getLastChild(DetailAST aAST)
    {
        return aAST.getLastChild();
    }
}