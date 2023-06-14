/*
MethodParamPad
allowLineBreaks = (default)false
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

class Example1 {
  public Example1() {
    super();
  }

  public Example1 (int aParam) { // violation ''(' is preceded with whitespace'
    super (); // violation ''(' is preceded with whitespace'
  }

  public void method() {}

  public void methodWithVeryLongName
  () {} // violation ''(' should be on the previous line.'
}
