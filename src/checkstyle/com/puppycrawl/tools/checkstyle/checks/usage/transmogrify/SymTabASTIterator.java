
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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator for the children of a tree node.
 *
 * @version 1.0
 * @since 1.0
 * @see Iterator
 */
public class SymTabASTIterator implements Iterator {
  private SymTabAST _current;

  /**
   * Creates a new <tt>SymTabASTIterator</tt>.
   *
   * @param parent the node whose children will be iterated over.
   */
  public SymTabASTIterator(SymTabAST parent) {
    _current = (SymTabAST)parent.getFirstChild();
  }

  /**
   * Whether the node has another child.  (In other words, returns
   * <tt>true</tt> if <tt>next</tt> would return an element rather than
   * throwing an exception.)
   *
   * @return the next child node.
   */
  public boolean hasNext() {
    return (_current != null);
  }

  /**
   * The next child node.
   *
   * @return the next child node.
   */
  public Object next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }

    Object result = _current;
    _current = (SymTabAST)_current.getNextSibling();

    return result;
  }

  /**
   * The next child node.
   *
   * @return the next child node.
   */
  public SymTabAST nextChild() {
    return (SymTabAST)next();
  }

  /**
   * Unsupported operation
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
