
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

import java.io.File;




/**
 * represents a place where a definition is used.  There are two flavors
 * of references -- resolved (those that have a definition associated with
 * them) and unresolved (those that don't have a definition associated).
 * The goal of the resolution step is to get all of the references in the
 * symbol table to fall into the resolved category.
 */

public class Reference implements Comparable {

  private SymTabAST _node;
  private Occurrence _occurrence;

  public Reference( SymTabAST node ) {
    _node = node;
    _occurrence = new Occurrence( _node.getFile(), ASTUtil.getLine(_node), ASTUtil.getColumn(_node) );
  }

  /**
   * gets the definition associated with this reference
   *
   * @return Definition the (possibly null) definition associated with
   *                    this reference
   */
  public IDefinition getDefinition() {
    return _node.getDefinition();
  }

  /**
   * return the node that was passed in during ctor
   */
  public SymTabAST getTreeNode() {
    return _node;
  }

  /**
   * gets the occurrence of this reference
   *
   * @return Occurrence
   */
  public Occurrence getOccurrence() {
    return _occurrence;
  }

  /**
   * gets the line where the node resides
   */
  public int getLine() {
    return getOccurrence().getLine();
  }

  /**
   * gets the column for where the node resides
   */
  public int getColumn() {
    return getOccurrence().getColumn();
  }

  /**
   * gets the enclosing file for the node
   */
  public File getFile() {
    return getOccurrence().getFile();
  }

  /**
   * gets the name of the reference
   *
   * @return String the name of the definition associated with this reference
   *                if this reference is resolved, else null
   */
  public String getName() {
    return _node.getName();
  }

  /**
   * returns a string representation of the reference.
   *
   * @return String
   */
  public String toString() {
    return getOccurrence().toString();
  }

  /**
   * returns whether the <code>Reference</code>s are equal
   *
   * @return whether the <code>Reference</code>s are equal
   */
   // REDTAG -- not finished
  public boolean equals(Object obj){
    boolean result = false;
    if (obj instanceof Reference) {
      result = getOccurrence().equals(((Reference)obj).getOccurrence());
    }
    return result;
  }

  public int compareTo(Object o) {
    if (!(o instanceof Reference)) {
      throw new ClassCastException(getClass().getName());
    }

    return getOccurrence().compareTo(((Reference)o).getOccurrence());
  }
}
