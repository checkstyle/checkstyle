
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
import java.util.SortedSet;
import java.util.TreeSet;




/**
 * <code>Definition</code> contains basic information for everything
 * that is defined in a java source file.  A definition has a list
 * of <code>Reference</code>s, an <code>Occurrence</code>, a name, and
 * a parent <code>Scope</code>.
 *
 * @see Occurrence
 * @see Scope
 * @see Reference
 */

public abstract class Definition implements IDefinition, Comparable {
  private String _name = null;
  private Scope _parentScope = null;
  private SymTabAST _node = null;
  private SortedSet _references;

  private Occurrence _occurrence = null;

  public Definition(String name, Scope parentScope, SymTabAST node) {
    _name = name;
    _parentScope = parentScope;
    _node = node;
    _references = new TreeSet();

    if ( node != null ) {
      _occurrence = new Occurrence( _node.getFile(),
                                    ASTUtil.getLine( _node ),
                                    ASTUtil.getColumn( _node ));
    }
  }

  public boolean isSourced() {
    return true;
  }

  /**
   * returns the name of the definition
   *
   * @return String
   */

  public String getName() {
    return _name;
  }

  /**
   * returns the node in the AST that represents this definition
   *
   * @return the node in the AST that represents this definition
   */
  public SymTabAST getTreeNode() {
    return _node;
  }

  /**
   * Adds a <code>Reference</code> to the collection of <code>Reference</code>s
   *
   * @param reference the <code>Reference</code> to add
   */
  public void addReference( Reference reference ) {
    _references.add( reference );
  }

  /**
   * Returns the <code>Reference</code>s to this definition
   *
   * @return the <code>Reference</code>s to this definition
   */
  public Iterator getReferences() {
    return _references.iterator();
  }

  public int getNumReferences() {
    return _references.size();
  }

  /**
   * Returns the scope in which this definition is defined
   *
   * @return the scope in which this definition is defined
   */
  public Scope getParentScope() {
    return _parentScope;
  }

  /**
   * returns the fully qualifed name of this defintion.  The name of
   * the parentScope and all of its parents are considered when constructing
   * this name.
   *
   * @return the fully qualified name of this definition
   */
  public String getQualifiedName() {
    String nameToUse = _name;
    String result;

    if (_name == null) {
      nameToUse = "~NO NAME~";
    }

    if (getParentScope() != null &&
         !(getParentScope() instanceof BaseScope)) {
      result = getParentScope().getQualifiedName() + "." + nameToUse;
    }
    else {
      result = nameToUse;
    }
    return result;
  }

  /**
   * Returns the <code>Occurrence</code> for the location of this definition
   *
   * @return the <code>Occurrence</code> for the location of this definition
   */
  public Occurrence getOccurrence() {
    return _occurrence;
  }

  /**
   * Returns the <code>ClassDef</code> that this scope is contained in.
   *
   * @return the <code>ClassDef</code> this definition is contained in
   */
  public ClassDef getEnclosingClass() {
    ClassDef result = null;

    if ( getParentScope() != null ) {
      result = getParentScope().getEnclosingClass();
    }

    return result;
  }

  public IPackage getEnclosingPackage() {
    IPackage result = null;
    if (getParentScope() != null) {
      result = getParentScope().getEnclosingPackage();
    }

    return result;
  }

  /**
   * returns a String representation of the definition. This string includes
   * the class of the defintion and its qualified name
   *
   * @return String
   */
  public String toString() {
    return getClass().getName() + "[" + getQualifiedName() + "]";
  }

  public int compareTo(Object o) {
    int result = 0;

    if (!(o instanceof Definition)) {
      throw new ClassCastException(o.getClass().getName());
    }
	result = getQualifiedName().compareTo(((Definition)o).getQualifiedName());

    return result;
  }

}
