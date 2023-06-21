/*
MethodParamPad
allowLineBreaks = true
option = space
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

class Example2 {
  // xdoc section -- start
  public Example2() {
    super();
  }

  public Example2 (int aParam) {
    super ();
  }

  public void method() {} // violation ''(' is not preceded with whitespace'

  public void methodWithVeryLongName
  () {} // OK, because allowLineBreaks is true
  // xdoc section -- end
}
