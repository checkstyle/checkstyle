
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

/**
 * implemented by all definitions of the <code>SymTabAST</code> tree, including
 * source/non-sourced node
 * @see net.sourceforge.transmogrify.symtab.Definition
 * @see net.sourceforge.transmogrify.symtab.ExternalDefinition
 */
public interface IDefinition {

  /**
   * verifies if this definition node is source/non-sourced(with no source-code)
   * @return <code>true</code> if the node is parsed from a source code
   *         <code>false</code> otherwise
   */
  public boolean isSourced();

  /**
   * gets the name that identified this particular definition/node
   * @return name for this definition
   */
  public String getName();

  /**
   * gets the fully qualified name of the definition, ie. dotted name for classes,
   * or method name with its signature for methods, etc
   * @return qualified name for this node
   */
  public String getQualifiedName();

  /**
   * adds any reference that this definition have to its collection of
   * <code>_references</code>
   * @param reference reference to be added which has <code>SymTabAST</code> node
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   */
  public void addReference(Reference reference);

  /**
   * gets the collection of references refer to by this definition
   * @return iterator of the references
   */
  public Iterator getReferences();

  /**
   * gets the number of references refer to by this definition
   * @return number of references
   */
  public int getNumReferences();
  
}