
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



import java.util.Enumeration;
import java.util.Iterator;

public class DefinitionTraverser {

  protected SymbolTable _symbolTable;

  public DefinitionTraverser( SymbolTable symbolTable ) {
    _symbolTable = symbolTable;
  }

  public void traverse() {
    Enumeration packages = _symbolTable.getPackages().elements();
    while ( packages.hasMoreElements() ) {
      traverse( (PackageDef)(packages.nextElement()) );
    }
  }

  private void traverse( Definition def ) {
    if ( def instanceof PackageDef ) {
      traverse( (PackageDef)def );
    }
    else if (def instanceof AnonymousInnerClass) {
      traverse((AnonymousInnerClass)def);
    }
    else if ( def instanceof ClassDef ) {
      traverse( (ClassDef)def );
    }
    else if (def instanceof DefaultConstructor) {
      traverse((DefaultConstructor)def);
    }
    else if ( def instanceof MethodDef ) {
      traverse( (MethodDef)def );
    }
    else if ( def instanceof BlockDef ) {
      traverse( (BlockDef)def );
    }
    else if ( def instanceof VariableDef ) {
      traverse( (VariableDef)def );
    }
    else if ( def instanceof LabelDef ) {
      traverse( (LabelDef)def );
    }
  }

  private void traverse(PackageDef pkg) {
    handlePackage(pkg);
    traversePackage(pkg);
  }

  private void traverse(AnonymousInnerClass innerClass) {
    handleAnonymousInnerClass(innerClass);
    traverseChildren(innerClass);
  }

  private void traverse( ClassDef classDef ) {
    handleClass( classDef );
    traverseChildren( classDef );
  }

  private void traverse(DefaultConstructor constructor) {
    handleDefaultConstructor(constructor);
  }

  private void traverse( MethodDef method ) {
    handleMethod( method );
    traverseChildren( method );
  }

  private void traverse( BlockDef block ) {
    handleBlock( block );
    traverseChildren( block );
  }

  private void traverse( VariableDef variable ) {
    handleVariable( variable );
  }

  private void traverse( LabelDef label ) {
    handleLabel( label );
  }

  private void traversePackage(PackageDef pkg) {
    Iterator definitions = pkg.getClasses();
    while (definitions.hasNext()) {
      ClassDef classDef = (ClassDef)definitions.next();
      traverse(classDef);
    }
  }

  private void traverseChildren(Scope scope) {
    Enumeration definitions = scope.getDefinitions();
    while ( definitions.hasMoreElements() ) {
      Definition def = (Definition)(definitions.nextElement());
      traverse(def);
    }
  }

  protected void handlePackage( PackageDef pkg ) {}
  protected void handleAnonymousInnerClass(AnonymousInnerClass innerClass) {}
  protected void handleClass( ClassDef classDef ) {}
  protected void handleDefaultConstructor(DefaultConstructor constructor) {}
  protected void handleMethod( MethodDef method ) {}
  protected void handleBlock( BlockDef block ) {}
  protected void handleVariable( VariableDef variable ) {}
  protected void handleLabel( LabelDef label ) {}
}
