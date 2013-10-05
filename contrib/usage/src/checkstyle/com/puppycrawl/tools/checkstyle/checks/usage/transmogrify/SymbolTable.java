
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
import java.util.Hashtable;
import java.util.Stack;




/**
 * this class contains all of the definitions, references, and scopes
 * created by the system.
 *
 * Other stuff this class does:
 * <ul>
 * <li> holds the "base" scope containing primitive definitions
 * <li> holds the java.lang package
 * <li> holds the definition of java.lang.Object, which is the base class
 *      of all class hierarchies
 * <li> kicks off the resolve step
 * <li> does some of the work of constructing object definitions
 * </ul>
 */

public class SymbolTable {

  private Hashtable packages = new Hashtable();
  private Stack scopes = new Stack();
  private ScopeIndex index = new ScopeIndex();

  private File currentFile;
  private BaseScope baseScope;

  private SymTabAST root;

//  private boolean outOfDate;

  /**
   * constructor takes <code>SymTabAST</code>
   * @param aRoot root of the <code>SymTabAST</code> tree
   */
  public SymbolTable(SymTabAST aRoot) {
    scopes = new Stack();
    this.root = aRoot;

    baseScope = new BaseScope( this );
    pushScope(baseScope);
  }

  /**
   * gets the root node
   * @return <code>SymTabAST</code>
   */
  public SymTabAST getTree() {
    return root;
  }

//  /**
//   * sets the <code>outOfDate</code> data member to <code>true</code>
//   * @return <code>void</code>
//   */
//  public void expire() {
//    outOfDate = true;
//  }
//
//  /**
//   * sets <code>outOfDate</code> member to <code>false</code>
//   * @param lastUpdated
//   * @return <code>void</code>
//   */
//  public void update(long lastUpdated) {
//    outOfDate = false;
//  }

  /**
   * returns the "base" scope
   *
   * @return Scope the base scope
   */
  // REDTAG -- this should eventually be replaced by a call
  //  to the lookup method that traverses scopes
  public BaseScope getBaseScope() {
    return baseScope;
  }

  /**
   * returns the current scope.  Scopes are nested in a stack (FIFO queue)
   * and pushed/popped based on the structure of the AST
   * @return <code>Scope</code>
   */
  public Scope getCurrentScope() {
    return (Scope)scopes.peek();
  }

  /**
   * pushes a new scope onto the stack
   *
   * @param scope the <code>Scope</code> to push
   * @return <code>void</code>
   */
  public void pushScope(Scope scope) {
    scopes.push(scope);
  }

  /**
   * pops a scope from the stack.
   *
   * @return <code>Scope</code>
   *
   */
  public Scope popScope() {
    Scope scope = (Scope)(scopes.pop());
    return scope;
  }

  /**
   * gets all packages stored in this symbol table
   * @return <code>Hashtable</code>
   */
  public Hashtable getPackages() {
    // REDTAG -- think about making this available as something simpler,
    //           perhaps an enumeration
    return packages;
  }

  /**
   * gets package by its name
   * @param name
   * @return <code>PackageDef</code>
   */
  public PackageDef getPackage( String name ) {
    return (PackageDef)(packages.get( name ));
  }

  /**
   * adds <code>PackageDef</code> to its parent scope and stores the
   * <code>PackageDef</code> in <code>packages</code>
   * @param pkg
   * @param parent
   * @return <code>void</code>
   */
  public void definePackage( PackageDef pkg, Scope parent ) {
    parent.addDefinition(pkg);
    packages.put(pkg.getQualifiedName(), pkg);
  }

  /**
   * defines a class in the symbol table.
   *
   * @param def the class to define
   * @return <code>void</code>
   * @see #indexScope(Scope)
   * @see #getCurrentScope()
   */
  public void defineClass(ClassDef def) {
    indexScope(def);
    getCurrentScope().addDefinition(def);
  }

  /**
   * defines a method in the symbol table
   *
   * @param method the method to be defined
   * @return <code>void</code>
   * @see #indexScope(Scope)
   * @see #getCurrentScope()
   */
  public void defineMethod(MethodDef method) {
    indexScope(method);
    ((ClassDef)getCurrentScope()).addDefinition(method);
  }

  /**
   * defines a variable in the symbol table
   *
   * @param v the variable to define
   * @return <code>void</code>
   * @see #getCurrentScope()
   */
  public void defineVariable(VariableDef v) {
    getCurrentScope().addDefinition(v);
  }

  /**
   * defines a block within the symbol table
   *
   * @param blockDef the block to define
   * @return <code>void</code>
   * @see #indexScope(Scope)
   * @see #getCurrentScope()
   */
  public void defineBlock(BlockDef blockDef) {
    indexScope(blockDef);
    getCurrentScope().addDefinition(blockDef);
  }

  /**
   * defines a label within the symbol table
   *
   * @param labelDef the label to define
   * @return <code>void</code>
   * @see #getCurrentScope()
   */
  // REDTAG -- label does not define a new scope
  public void defineLabel(LabelDef labelDef) {
    getCurrentScope().addDefinition(labelDef);
  }

  /**
   * places a scope in the symbol table's index
   *
   * @param scope the scope to index
   * @return <code>void</code>
   */
  public void indexScope(Scope scope) {
    index.addScope(scope);
  }

  /**
   * gets the symbol table's scope index
   *
   * @return ScopeIndex
   */
  public ScopeIndex getScopeIndex() {
    return index;
  }

  /**
   * sets the current file that the symbol table is processing
   *
   * @param file the <code>File</code> to use
   * @return <code>void</code>
   */
  public void setCurrentFile(File file) {
    currentFile = file;
  }

  /**
   * gets the file that the symbol table is currently processing
   *
   * @return <code>File</code>
   */
  public File getCurrentFile() {
    return currentFile;
  }
}
