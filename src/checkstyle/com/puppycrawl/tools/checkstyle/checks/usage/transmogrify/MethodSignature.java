
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

import java.util.Vector;



/**
 * <code>MethodSignature</code> is used to resolve various methods
 * in the same scope of the same name based on formal parameter lists
 *
 * @see MethodDef
 */

public class MethodSignature implements ISignature{

  private IClass[] _argTypes = null;

  public MethodSignature(IClass[] argTypes) {
    _argTypes = argTypes;
  }

  public MethodSignature(Vector argTypes) {
    _argTypes = new IClass[argTypes.size()];
    argTypes.toArray(_argTypes);
  }

  /**
   * returns an array of the types of the arguments in the signature
   *
   * @return ClassDef[]
   */
  public IClass[] getParameters() {
    return _argTypes;
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

    IClass[] comparedArgTypes = signature.getParameters();
    if (_argTypes.length != comparedArgTypes.length) {
      result = false;
    }
    else {
      for (int i = 0; i < _argTypes.length; i++) {
        // TODO: Checkstyle modification. Why can _argTypes[i] be null?
        // if (!_argTypes[i].isCompatibleWith(comparedArgTypes[i])) {
        if ((_argTypes[i] != null)
            && !_argTypes[i].isCompatibleWith(comparedArgTypes[i]))
        {
          result = false;
          break;
        }
      }
    }

    return result;
  }

  public boolean isSame(ISignature signature) {
    return equals(signature);
  }

  /**
   * compares two objects for equality.  If the compared object is a
   * <code>MethodSignature</code> and the argTypes match, they are the
   * same
   *
   * @return boolean
   */
  public boolean equals(Object o) {
    boolean result = false;

    if (o instanceof MethodSignature) {
      MethodSignature signature = (MethodSignature)o;
      result = java.util.Arrays.equals(getParameters(), signature.getParameters());
    }

    return result;
  }

  /**
   * returns a String representation of this object.  Includes information
   * about the types of the arguments in the signature
   *
   * @return String
   */
  public String toString() {
    StringBuffer result = new StringBuffer( "(" );

    for ( int i = 0; i < _argTypes.length; i++ ) {
      result.append( _argTypes[i] != null ? _argTypes[i].getName() : "[null]" );
      if ( i < (_argTypes.length - 1) ) {
        result.append( ", " );
      }
    }
    result.append( ")" );

    return result.toString();
  }

}
