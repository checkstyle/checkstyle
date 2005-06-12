
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

import java.util.ArrayList;
import java.util.List;

public class PrimitiveClasses {

  public static final ExternalClass BOOLEAN = new ExternalClass(Boolean.TYPE);
  public static final ExternalClass CHAR = new ExternalClass(Character.TYPE);
  public static final ExternalClass BYTE = new ExternalClass(Byte.TYPE);
  public static final ExternalClass SHORT = new ExternalClass(Short.TYPE);
  public static final ExternalClass INT = new ExternalClass(Integer.TYPE);
  public static final ExternalClass LONG = new ExternalClass(Long.TYPE);
  public static final ExternalClass FLOAT = new ExternalClass(Float.TYPE);
  public static final ExternalClass DOUBLE = new ExternalClass(Double.TYPE);

  private static List order;

  static {
    order = new ArrayList();
    order.add(DOUBLE);
    order.add(FLOAT);
    order.add(LONG);
    order.add(INT);
    order.add(SHORT);
    order.add(BYTE);
  }

  public static boolean typesAreCompatible(ExternalClass hole,
                                           ExternalClass peg) {
    boolean result = false;

    if (hole.equals(BOOLEAN)) {
      result = peg.equals(BOOLEAN);
    }
    else if (hole.equals(CHAR)) {
      result = peg.equals(CHAR);
    }
    else if (peg.equals(CHAR)) {
      result = (hole.equals(CHAR) ||
                order.indexOf(hole) <= order.indexOf(INT));
    }
    else {
      result = (order.indexOf(hole) <= order.indexOf(peg));
    }

    return result;
  }

  public static IClass unaryPromote(IClass type) {
    IClass result = type;

    if (type.equals(BYTE) || type.equals(SHORT) || type.equals(CHAR)) {
      result = INT;
    }

    return result;
  }

  public static IClass binaryPromote(IClass a, IClass b) {
    IClass result = null;

    if (a.equals(DOUBLE) || b.equals(DOUBLE)) {
      result = DOUBLE;
    }
    else if (a.equals(FLOAT) || b.equals(FLOAT)) {
      result = FLOAT;
    }
    else if (a.equals(LONG) || b.equals(LONG)) {
      result = LONG;
    }
    else {
      result = INT;
    }

    return result;
  }

}
