
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




public class DefaultScope extends Scope {
  public DefaultScope(String name, Scope parentScope, SymTabAST node) {
    super(name, parentScope, node);
  }

  public void addDefinition(IPackage def) {
    throw new UnsupportedOperationException(getClass().getName());
  }

  public IClass getClassDefinition( String name ) {
    IClass result = (ClassDef)classes.get(name);

    if ( result == null && getParentScope() != null ) {
      result = getParentScope().getClassDefinition( name );
    }

    return result;
  }

  public IMethod getMethodDefinition(String name,
                                     ISignature signature) {
    IMethod result = null;
    if (getParentScope() != null) {
      result = getParentScope().getMethodDefinition(name, signature);
    }

    return result;
  }

  public IVariable getVariableDefinition( String name ) {
    IVariable result = (VariableDef)elements.get(name);

    if ( result == null && getParentScope() != null ) {
      result = getParentScope().getVariableDefinition( name );
    }

    return result;
  }

  public LabelDef getLabelDefinition(String name) {
    LabelDef result = (LabelDef)labels.get(name);

    if (result == null && getParentScope() != null) {
      result = getParentScope().getLabelDefinition(name);
    }

    return result;
  }
}
