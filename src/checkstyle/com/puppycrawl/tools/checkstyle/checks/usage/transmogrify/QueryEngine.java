
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
import java.util.Iterator;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;



/**
 * a wrapper around a <code>SymbolTable</code> that makes Definition and
 * reference lookup easier
 */
public class QueryEngine {
  private SymbolTable symbolTable;
  private ScopeIndex index;

  public QueryEngine(SymbolTable aSymbolTable) {
    this.symbolTable = aSymbolTable;
    setIndex();
  }

  /**
   * sets the scope index that this <code>QueryEngine</code> uses
   */
  private void setIndex() {
    index = symbolTable.getScopeIndex();
  }

  /**
   * gets a symbol from the associated symbol table
   *
   * @param name the name of the symbol to get
   * @param location the location of that symbol
   *
   * @return Object the (possibly null) result of the lookup
   */
  public Reference getSymbol(String name, Occurrence location) {
    Scope enclosingScope = index.lookup(location);
    Reference result = enclosingScope.getSymbol(name, location);

    // REDTAG -- for cases like a label on the same line as the
    //           block it names, e.g. 'bar: for(int i = 0; ...'
    if (result == null) {
      result = enclosingScope.getParentScope().getSymbol(name, location);
    }

    return result;
  }

  /**
   * gets the definition of the given symbol
   *
   * @param name the name of the symbol to consider
   * @param location the <code>Occurrence</code> that represents the
   *                 location of the symbol
   *
   * @return Definition the (possibly null) result of the lookup
   */
  public IDefinition getDefinition(String name, Occurrence location) {
    Reference symbol = getSymbol(name, location);

    //if (symbol != null) {
    //  System.out.println("  found " + name);
    //}
    //else {
    //  System.out.println("  !could not find " + name);
    //}

    return resolveDefinition(symbol);
  }

  public IDefinition getDefinition(Occurrence location) {
    IDefinition result = null;

    SymTabAST node = getWordNodeAtOccurrence(location);
    if ( node != null ) {
      result = node.getDefinition();
    }

    return result;
  }

  private IDefinition resolveDefinition(Reference reference) {
    IDefinition result = null;

    if ( reference != null ) {
      result = reference.getDefinition();
    }

    return result;
  }

  /**
   * gets a collection of references determined by a symbol and location
   *
   * @param name the name of the symbol to consider
   * @param location the <code>Occurrence</code> that represents its location
   *
   * @return
   */
  public Iterator getReferences(String name, Occurrence location) {
    Reference symbol = getSymbol(name, location);
    return resolveReferences(symbol);
  }

  public Iterator getReferences(Occurrence location) {
    Iterator result = null;

    SymTabAST node = getWordNodeAtOccurrence(location);
    if ( node != null && node.getDefinition() != null ) {
      result = node.getDefinition().getReferences();
    }

    return result;
  }

  private Iterator resolveReferences(Reference reference) {
    return reference.getDefinition().getReferences();
  }

  public SymTabAST getFileNode(File file) {
    return ASTUtil.getFileNode(symbolTable.getTree(), file);
  }

  private SymTabAST getWordNodeAtOccurrence(Occurrence location) {
    SymTabAST result = null;

    SymTabAST fileNode = getFileNode(location.getFile());
    if ( fileNode != null ) {
      SymTabAST node = fileNode.getEnclosingNode(location.getLine(),
                                                 location.getColumn());

      if ( (node != null) && (node.getType() == TokenTypes.IDENT) ) {
        result = node;
      }
    }

    return result;
  }

  public String getWordAtOccurrence(Occurrence location ) {
    String result = null;

    SymTabAST node = getWordNodeAtOccurrence(location);
    if ( node != null ) {
      result = node.getText();
    }

    return result;
  }

}




