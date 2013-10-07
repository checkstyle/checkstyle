
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
import java.util.Vector;


/**
 * <code>MethodDef</code> contains all the pertinent information for
 * a method, including return type, formal parameters, and exceptions
 * thrown
 *
 * @see ClassDef
 * @see MethodSignature
 */
public class MethodDef extends DefaultScope implements IMethod {

  private IClass returnType;
  private List exceptions;

  private List parameters;

  public MethodDef(String name, Scope parentScope, SymTabAST node) {
    super(name, parentScope, node);
    parameters = new Vector();
  }

  /**
   * Returns the <code>ClassDef</code> for the return type of this method.
   *
   * @return the <code>ClassDef</code> for the return type of this method
   */
  public IClass getType() {
    return returnType;
  }

  /**
   * Sets the return type of this method.
   *
   * @param type the <code>ClassDef</code> for the return type
   */
  public void setType(IClass type) {
    returnType = type;
  }

  /**
   * Adds a parameter to the collection of formal parameters
   *
   * @param parameter the <code>VariableDef</code> to add
   */
  public void addParameter(VariableDef parameter) {
    parameters.add( parameter );
    addDefinition(parameter);
  }

  /**
   * Whether this method has the same signature as the given signature.
   *
   * @param signature the <code>MethodSignature</code> to compare
   *
   * @return whether the signatures are equal
   */
  public boolean hasSameSignature(ISignature signature) {
    return getSignature().equals(signature);
  }

  /**
   * Whether this method has a signature compatible with the given signature.
   *
   * @param signature the signature being compared
   * @return whether the signatures are compatible
   */
  public boolean hasCompatibleSignature(ISignature signature) {
    return signature.isCompatibleWith(getSignature());
  }

  /**
   * Returns the signature of this method.
   *
   * @return the signature of this method
   */
  public ISignature getSignature() {
    Vector argTypes = new Vector();

    for (int i = 0; i < parameters.size(); i++) {
      argTypes.add(getParameterAt(i).getType());
    }

    return new MethodSignature(argTypes);
  }

  /**
   * Gets the <i>i</i>th parameter of this method
   *
   * @param i the index of the parameter
   *
   * @return the <code>VariableDef</code> of the <i>i</i>th parameter
   */
  private VariableDef getParameterAt( int i ) {
    return (VariableDef)(parameters.get( i ));
  }

  /**
   * Adds an exception that this method throws.
   *
   * @param exception the exception to add
   */
  public void addException(IClass exception) {
    if (exceptions == null) {
      exceptions = new Vector();
    }

    exceptions.add(exception);
  }

  /**
   * Returns the exceptions this method throws
   *
   * @return the exceptions this method throws
   */
  public IClass[] getExceptions() {
    return (IClass[])exceptions.toArray(new IClass[0]);
  }

  public String getQualifiedName() {
    return super.getQualifiedName() + getSignature();
  }
}