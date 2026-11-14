/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorExtraSemicolon {
  int field;
  ; // violation 'Extra semicolon should be removed or separated by empty line from previous code'

  enum Kind {
    SOFT,
    WEAK
  }; // violation 'Extra semicolon should be removed or separated by empty line from previous code'

  class Inner {}
  ; // violation 'Extra semicolon should be removed or separated by empty line from previous code'

  void method() {}
  ; // violation 'Extra semicolon should be removed or separated by empty line from previous code'

// violation below 'Extra semicolon should be removed or separated by empty line from previous code'
  interface I {};;;
  ;
  ;

  record R(int x) {}
  ; // violation 'Extra semicolon should be removed or separated by empty line from previous code'

  class Separated {}

  ;

  void next() {}
  int notSemicolon; // violation ''VARIABLE_DEF' should be separated from previous line'
}
