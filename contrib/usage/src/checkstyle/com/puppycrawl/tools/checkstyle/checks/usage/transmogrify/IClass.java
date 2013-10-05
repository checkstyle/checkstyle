
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



import java.util.List;

/**
 * interface implemented by classes/interfaces definition, for source or
 * non-sourced classes and interfaces
 * The implementor of this class have all information about its inner classes,
 * methods, variables, subclasses, etc.
 * @see net.sourceforge.transmogrify.symtab.ClassDef
 * @see net.sourceforge.transmogrify.symtab.ExternalClass
 */
public interface IClass extends IDefinition {

  /**
   * gets superclass definition of this class
   * @return superclass of this class definition
   */
  public IClass getSuperclass();

  /**
   * gets interfaces definition implemented by this class definition
   * @return interfaces implemented
   */
  public IClass[] getInterfaces();

  /**
   * gets subclasses definition of this class definition
   * @return list of subclasses definition
   */
  public List getSubclasses();

  /**
   * gets class definition referenced by this class, including its inner classes,
   * imported classes, packages, and its parent scope referenced class definitions
   * @param name name of the class definition to be searched
   * @return class definition that matches the input name
   */
  public IClass getClassDefinition(String name);

  /**
   * gets the method associated with the given name and signature
   *
   * @param name the name of the method
   * @param signature the signature (formal parameter types) of the method
   *
   * @return <code>MethodDef</code>
   *
   * @see MethodSignature
   */
  public IMethod getMethodDefinition(String name,
                                     ISignature signature);

  /**
   * gets the <code>VariableDef</code> associated with the given name
   *
   * @param name the name of the variable
   *
   * @return <code>VariableDef</code>
   */
  public IVariable getVariableDefinition(String name);

  // end definitions interface

  /**
   * adds <code>ClassDef</code> to the collection of (direct?) subclasses of
   * this class
   *
   * @param subclass the class to add
   * @return <code>void</code>
   */
  public void addSubclass(ClassDef subclass);

  /**
   * adds <code>ClassDef</code> to the collection of implemented interfaces
   * of this class
   *
   * @param implementor the interface to add
   * @return <code>void</code>
   */
  public void addImplementor(ClassDef implementor);

  /**
   * gets the list of <code>ClassDefs</code> that implmement this interface
   *
   * @return Vector the list of implementors
   */
  public List getImplementors();

  /**
   * verifies if the input type is equal to this class or its superclass or
   * its interfaces
   * @param type class to be compared with
   * @return <code>true</code> if the input type is equals
   *         <code>false</code> otherwise
   */
  public boolean isCompatibleWith(IClass type);

  /**
   * verifies if this class is of primitive Java type
   * @return <code>true</code> if the class is a primitive type
   *         <code>false</code> otherwise
   */
  public boolean isPrimitive();

  /**
   * gets inner classes definition associated with this class
   * @return array of inner classes
   */
  public IClass[] getInnerClasses();

}
