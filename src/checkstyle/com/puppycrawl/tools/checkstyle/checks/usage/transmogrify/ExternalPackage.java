
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

import java.util.HashMap;
import java.util.Map;



/**
 * <code>PackageDef</code> contains all pertinent information about a
 * package.
 */
public class ExternalPackage extends ExternalDefinition implements IPackage {

  String _name;
  IPackage _parent;

  Map _packages;

  public ExternalPackage(String name, IPackage parent) {
    _name = name;
    _parent = parent;

    _packages = new HashMap();
  }

  public IClass getClass(String name) {
    IClass result = null;

    try {
      Class theClass
        = ClassManager.getClassLoader().loadClass(getQualifiedName()
                                                  + "."
                                                  + name);
      result = new ExternalClass(theClass);
    }
    catch (ClassNotFoundException e) {
      // look elsewhere for the class
    }

    return result;
  }

  public void addDefinition(IPackage pkg) {
    _packages.put(pkg.getName(), pkg);
  }

  public IPackage getEnclosingPackage() {
    return _parent;
  }

  public String getName() {
    return _name;
  }

  public String getQualifiedName() {
    StringBuffer result = new StringBuffer();

    if (_parent != null) {
      result.append(_parent.getQualifiedName());
      result.append(".");
    }

    result.append(getName());

    return result.toString();
  }
}
