
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

public class ExternalSignature implements ISignature {

  private IClass[] _parameters;

  public ExternalSignature(Class[] parameters) {
    _parameters = new IClass[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i].isArray()) {
        _parameters[i] =
          new ArrayDef(new ExternalClass(parameters[i].getComponentType()));
      }
      else {
        _parameters[i] = new ExternalClass(parameters[i]);
      }
    }
  }

  /**
   * Whether this method signature is compatible with the signature of the
   * argument.  That is to say, each type for this signature are subclasses,
   * subinterfaces, or implement the interface for each corresponding type
   * in the argument signature.
   *
   * @param signature the signature of the method definition being compared
   * @return whether the signatures are compatible
   */
  public boolean isCompatibleWith(ISignature signature) {
    boolean result = true;

    if (_parameters.length == signature.getParameters().length) {
      for (int i = 0; i < _parameters.length; i++) {
        if (!getParameters()[i].isCompatibleWith(signature.getParameters()[i])) {
          result = false;
          break;
        }
      }
    }
    else {
      result = false;
    }

    return result;
  }

  public boolean isSame(ISignature signature) {
    return java.util.Arrays.equals(_parameters, signature.getParameters());
  }

  public IClass[] getParameters() {
    return _parameters;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("(");
    for (int i = 0; i < _parameters.length; i++) {
      result.append(_parameters[i]);
      if (i < _parameters.length - 1) {
        result.append(", ");
      }
    }
    result.append(")");

    return result.toString();
  }

}
