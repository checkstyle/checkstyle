// Transmogrify License
// 
// Copyright (c) 2001, ThoughtWorks, Inc.
// All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the ThoughtWorks, Inc. nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.puppycrawl.tools.checkstyle.checks.usage.transmogrify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * An iterator for dot ('.') delimited tokens.
 *
 * @version 1.0
 * @since 1.0
 * @see Iterator
 */
public class DotIterator implements Iterator {
    Iterator _nodesIter;
    List _nodes;

    public DotIterator(SymTabAST topNode)
    {
        _nodes = new ArrayList();
        makeNodes(topNode);
        _nodesIter = _nodes.iterator();
    }

    private void makeNodes(SymTabAST node)
    {
        if (node.getType() == TokenTypes.DOT) {
            SymTabAST left = (SymTabAST) node.getFirstChild();
            SymTabAST right = (SymTabAST) left.getNextSibling();

            makeNodes(left);
            makeNodes(right);
        }
        else {
            _nodes.add(node);
        }
    }

    /**
     * Returns true if the iteration has more elements. (In other words,
     * returns true if next would return an element rather than throwing an
     * exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    public boolean hasNext()
    {
        return _nodesIter.hasNext();
    }

    /**
     * Returns the next portion of the dotted name.
     *
     * @return the next node in the dotted name.
     */
    public Object next()
    {
        return _nodesIter.next();
    }

    /**
     * Returns the next portion of the dotted name.
     *
     * @return the next node in the dotted name.
     */
    public SymTabAST nextNode()
    {
        return (SymTabAST) _nodesIter.next();
    }

    /**
     * Unsupported operation.
     *
     */
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
