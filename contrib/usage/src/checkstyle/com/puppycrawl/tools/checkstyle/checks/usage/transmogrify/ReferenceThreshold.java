
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


import java.util.Set;
import java.util.TreeSet;



public class ReferenceThreshold extends ReferenceTool {
  private Set _underreferencedDefinitions;
  private int _threshold;

  public ReferenceThreshold(SymbolTable table, int threshold) {
    super(table.getTree());
    _threshold = threshold;
    _underreferencedDefinitions = new TreeSet();
    go();
  }

  private void go() {
    collectUnderreferencedDefinitions();
  }

  private void collectUnderreferencedDefinitions() {
    handleNode(_tree);
  }

  protected void handleNode(SymTabAST node) {
    if (node.isMeaningful()) {
      IDefinition def = node.getDefinition();
      if (def != null &&
          def.getNumReferences() <= _threshold &&
          def instanceof Definition) {
        // this is inheritently hackish
        // basically we want to ignore
        // all test cases and main() methods
        if (def.getQualifiedName().indexOf("test") < 0 &&
            def.getQualifiedName().indexOf("main") < 0) {
          _underreferencedDefinitions.add(def);
        }
      }
    }

    walkChildren(node);
  }

  public Set getUnderreferencedDefinitions() {
    return _underreferencedDefinitions;
  }
}
