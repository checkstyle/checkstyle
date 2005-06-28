
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

import java.lang.reflect.Constructor;

public class ExternalConstructor extends ExternalDefinition implements IMethod {

  private Constructor _javaConstructor;
  private ISignature _signature;

  public ExternalConstructor(Constructor javaConstructor) {
    _javaConstructor = javaConstructor;
    _signature = new ExternalSignature(_javaConstructor.getParameterTypes());
  }

  public String getName() {
    return _javaConstructor.getDeclaringClass().getName();
  }

  public IClass getType() {
    return new ExternalClass(_javaConstructor.getDeclaringClass());
  }

  public ISignature getSignature() {
    return _signature;
  }

  public boolean hasSameSignature(ISignature signature) {
    return getSignature().isSame(signature);
  }

  public boolean hasCompatibleSignature(ISignature signature) {
    return signature.isCompatibleWith(getSignature());
  }

  public String getQualifiedName() {
    return getName() + getSignature();
  }

  public Constructor getJavaConstructor() {
    return _javaConstructor;
  }

  public IClass[] getExceptions() {
    Class[] javaExceptions = getJavaConstructor().getExceptionTypes();
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

    if (o instanceof ExternalConstructor) {
      ExternalConstructor constructor = (ExternalConstructor)o;
      result = getJavaConstructor().equals(constructor.getJavaConstructor());
    }

    return result;
  }

  public int hashCode() {
    return getJavaConstructor().hashCode();
  }

}
