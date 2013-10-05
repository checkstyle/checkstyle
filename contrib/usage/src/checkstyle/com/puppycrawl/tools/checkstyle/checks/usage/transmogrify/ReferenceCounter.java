
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
import java.util.List;
import java.util.ListIterator;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This class is for counting the reference
 */
public class ReferenceCounter extends ReferenceTool {
  private List _references = new ArrayList();
  private List _resolvedReferences = new ArrayList();
  private List _unresolvedReferences = new ArrayList();

  public ReferenceCounter(SymbolTable table) {
    super(table.getTree());
    countReferences();
  }

  /**
   * Return the number of references
   */
  public int numberOfReferences() {
    return _references.size();
  }

  /**
   * Return the number of resolved references
   */
  public int numberOfResolvedReferences() {
    return _resolvedReferences.size();
  }

  public int numberOfUnresolvedReferences() {
    return _unresolvedReferences.size();
  }

  public ListIterator getUnresolvedReferences() {
    return _unresolvedReferences.listIterator();
  }

  private void countReferences() {
    handleNode( _tree );
  }

  protected void handleNode( SymTabAST node ) {
    if (node.getType() == TokenTypes.IDENT && node.isMeaningful()) {
      _references.add( node );
      if (node.getDefinition() != null && !(node.getDefinition() instanceof UnknownClass)) {
            _resolvedReferences.add( node );
      }
      else {
            _unresolvedReferences.add( node );
      }
    }
    walkChildren( node );
  }
}
