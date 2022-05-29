/*
LeftCurly
option = NLOW
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestOptionNlow { // OK

  public void foo1() {// OK
  }
  
  public void foo2()
  { // violation ''{' at column 3 should be on the previous line'
  }
  
  public void foo3(int value1, int value2,
                   int value3, int value4)
  { // OK
  }
  
  public void foo4(int value1, int value2,
                   int value3, int value4) { // violation ''{' at column 44 should be on a new line'
  }
  
}
