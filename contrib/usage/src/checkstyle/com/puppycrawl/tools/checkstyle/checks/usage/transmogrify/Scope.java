
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;


/**
 * Represents a scope of Java code.
 *
 * @author andrew mccormick, dave wood
 * @version 1.0
 * @since 1.0
 * @see Definition
 * @see Resolvable
 */
public abstract class Scope extends Definition {

  // rename to references?
  protected SortedSet referencesInScope = new TreeSet();

  protected Hashtable elements = new Hashtable();
  protected Hashtable labels = new Hashtable();
  protected Hashtable classes = new Hashtable();

  public Scope( String name, Scope parentScope, SymTabAST node ) {
    super( name, parentScope, node );
  }

  public void addDefinition(VariableDef def) {
    elements.put(def.getName(), def);
  }

  public void addDefinition(BlockDef def) {
    elements.put(def.getName(), def);
  }

  public void addDefinition(ClassDef def) {
    classes.put(def.getName(), def);
  }

  public void addDefinition(LabelDef def) {
    labels.put(def.getName(), def);
  }

  public abstract void addDefinition(IPackage def);

  protected Enumeration getDefinitions() {
    Vector allElements = new Vector();

    allElements.addAll(elements.values());
    allElements.addAll(labels.values());
    allElements.addAll(classes.values());

    return allElements.elements();
  }

  protected Iterator getClasses() {
    return classes.values().iterator();
  }

  public abstract IMethod getMethodDefinition(String name, ISignature signature);
  public abstract IVariable getVariableDefinition(String name);
  public abstract LabelDef getLabelDefinition(String name);
  public abstract IClass getClassDefinition(String name);

  public Iterator getReferencesIn() {
    return referencesInScope.iterator();
  }

  public Reference getSymbol(String name, Occurrence location) {
    Reference result = null;

    for (Iterator it = getReferencesIn(); it.hasNext(); ) {
      Reference reference = (Reference)it.next();
//      if (name.equals(reference.getName())) {
        if (reference.getLine() == location.getLine() &&
            reference.getColumn() == location.getColumn()) {
          result = reference;
          break;
        }
//      }
    }
    return result;
  }

  public void addReferenceInScope( Reference reference ) {
    referencesInScope.add( reference );
  }

}
