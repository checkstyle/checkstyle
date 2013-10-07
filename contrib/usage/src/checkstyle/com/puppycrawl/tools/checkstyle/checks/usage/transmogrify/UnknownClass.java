
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
import java.util.List;
import java.util.Vector;



public class UnknownClass implements IClass {

  String _name;
  SymTabAST _node;

  public UnknownClass(String name, SymTabAST node) {
    _name = name;
    _node = node;
    //System.out.println("Creating unknown class [" + name + " : " + node + "]");
  }

  /**
   * returns the <code>ClassDef</code> that for the superclass
   *
   * @return the <code>ClassDef</code> for the superclass
   */
  public IClass getSuperclass() {
    return null;
  }

  public IClass[] getInterfaces() {
    return new IClass[0];
  }

  /**
   * returns a collection of the direct subclasses of this class
   *
   * @return a collection of the direct subclasses of this class
   */
  public List getSubclasses() {
    return new Vector();
  }

  public IClass getClassDefinition(String name) {
    return null;
  }

  /**
   * gets the method associated with the given name and signature
   *
   * @param name the name of the method
   * @param signature the signature (formal parameter types) of the method
   *
   * @return MethodDef
   *
   * @see MethodSignature
   */
  public IMethod getMethodDefinition(String name,
                                     ISignature signature) {
    return null;
  }

  /**
   * gets the <code>VariableDef</code> associated with the given name
   *
   * @param name the name of the variable
   *
   * @return VariableDef
   */
  public IVariable getVariableDefinition(String name) {
    return null;
  }

  // end definitions interface

  /**
   * adds <code>ClassDef</code> to the collection of (direct?) subclasses of
   * this class
   *
   * @param subclass the class to add
   */
  public void addSubclass(ClassDef subclass) {}

  /**
   * adds <code>ClassDef</code> to the collection of implemented interfaces
   * of this class
   *
   * @param implementor the interface to add
   */
  public void addImplementor(ClassDef implementor) {}

  /**
   * gets the list of <code>ClassDefs</code> that implmement this interface
   *
   * @return Vector the list of implementors
   */
  public List getImplementors() {
    return new Vector();
  }

  public boolean isCompatibleWith(IClass type) {
    return false;
  }

  public void addReference(Reference reference) {}
  public Iterator getReferences() {
    return new Vector().iterator();
  }

  public int getNumReferences() {
    return 0;
  }

  public boolean isPrimitive() {
    return false;
  }

  public boolean isSourced() {
    return false;
  }

  public IClass[] getInnerClasses() {
    return new IClass[0];
  }

  public String getName() {
    return _name;
  }

  public String getQualifiedName() {
    return _name;
  }

  public boolean equals(Object o) {
    //TODO: handle Checkstyle condition for two unknown classes
    if (o instanceof UnknownClass) {
        final UnknownClass other = (UnknownClass) o;
        return other.getName().equals(getName());
    }
    return false;
  }

  public String toString() {
    return UnknownClass.class + "[" + getName() + "]";
  }
}
