
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



/**
 * <code>MethodDef</code> contains all the pertinent information for
 * a method, including return type, formal parameters, and exceptions
 * thrown
 *
 * @see ClassDef
 * @see MethodSignature
 * @see net.sourceforge.transmogrify.symtab.ExternalMethod
 * @see net.sourceforge.transmogrify.symtab.MethodDef
 */
public interface IMethod extends Typed {

  /**
   * Returns the signature of this method.
   *
   * @return the signature of this method
   */
  public ISignature getSignature();

  /**
   * verifies if the input signature is the same with signatures of this method
   * @return <code>true</code> if the two set signatures are equal
   *         <code>false</code> otherwise
   */
  public boolean hasSameSignature(ISignature signature);

  /**
   * verifies if the input signature type is compatible with this method signature
   * @return <code>true</code> if the two set of signatures are compatible
   *         <code>false</code> otherwise
   */
  public boolean hasCompatibleSignature(ISignature signature);

  public IClass[] getExceptions();
}