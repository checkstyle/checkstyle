
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
 * the bottom scope of the scope stack, contains some extra information
 * to make resolution easier.
 */



public class BaseScope extends DefaultScope {
  private SymbolTable table;

  public BaseScope( SymbolTable symbolTable ) {
    super("~BASE~", null, null);
    this.table = symbolTable;
  }

  public boolean isBaseScope() {
    return true;
  }

  public void addDefinition(IPackage def) {
    elements.put(def.getName(), def);
  }

  /**
   * gets the package associated with a fully qualified name
   *
   * @param fullyQualifiedName the name of the package
   *
   * @return the package that was gotten
   */
  public IPackage getPackageDefinition(String fullyQualifiedName) {
    return (IPackage)(table.getPackages().get(fullyQualifiedName));
  }

  public IClass getClassDefinition(String name) {
    IClass result = null;

    result = LiteralResolver.getDefinition(name);

    if (result == null) {
      int lastDot = name.lastIndexOf(".");
      if (lastDot > 0) {
        String packageName = name.substring(0, lastDot);
        String className = name.substring(lastDot + 1);

        IPackage pkg = getPackageDefinition(packageName);
        if (pkg != null) {
          result = pkg.getClass(className);
        }
      }
    }

    if (result == null) {
      Class theClass = null;
      try {
        theClass = ClassManager.getClassLoader().loadClass(name);
        result = new ExternalClass(theClass);
      }
      catch (ClassNotFoundException e) {
        // no-op
      }
      catch (NoClassDefFoundError e) {
        // no-op, checkstyle bug 842781
      }
    }

    return result;
  }
}

