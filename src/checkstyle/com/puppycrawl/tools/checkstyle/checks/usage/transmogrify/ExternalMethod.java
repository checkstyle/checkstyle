
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




import java.lang.reflect.Method;

/**
 * <code>MethodDef</code> contains all the pertinent information for
 * a method, including return type, formal parameters, and exceptions
 * thrown
 *
 * @see ClassDef
 * @see MethodSignature
 */
public class ExternalMethod extends ExternalDefinition implements IMethod {
  private Method _javaMethod;
  private ISignature _signature;

  public ExternalMethod(Method javaMethod) {
    _javaMethod = javaMethod;
    _signature = new ExternalSignature(_javaMethod.getParameterTypes());
  }

  public String getName() {
    return _javaMethod.getName();
  }

  /**
   * Returns the <code>ClassDef</code> for the return type of this method.
   *
   * @return the <code>ClassDef</code> for the return type of this method
   */
  public IClass getType() {
    IClass result = null;
    if (_javaMethod.getReturnType().isArray()) {
      result = new ArrayDef(new ExternalClass(_javaMethod.getReturnType().getComponentType()));
    }
    else {
      result = new ExternalClass(_javaMethod.getReturnType());
    }

    return result;
  }

  /**
   * Returns the signature of this method.
   *
   * @return the signature of this method
   */
  public ISignature getSignature() {
    return _signature;
  }

  public boolean hasSameSignature(ISignature signature) {
    return _signature.isSame(signature);
  }

  public boolean hasCompatibleSignature(ISignature signature) {
    return signature.isCompatibleWith(getSignature());
  }

  public String getQualifiedName() {
    return getName() + getSignature();
  }

  public Method getJavaMethod() {
    return _javaMethod;
  }

  public IClass[] getExceptions() {
    Class[] javaExceptions = getJavaMethod().getExceptionTypes();
    IClass[] result = new IClass[javaExceptions.length];

    for (int i = 0; i < result.length; i++) {
      result[i] = new ExternalClass(javaExceptions[i]);
    }

    return result;
  }

  public String toString() {
    return getQualifiedName();
  }

  public boolean equals(Object o) {
    boolean result = false;

    if (o instanceof ExternalMethod) {
      ExternalMethod compared = (ExternalMethod)o;
      result = getJavaMethod().equals(compared.getJavaMethod());
    }

    return result;
  }

  public int hashCode() {
    return getJavaMethod().hashCode();
  }
}
