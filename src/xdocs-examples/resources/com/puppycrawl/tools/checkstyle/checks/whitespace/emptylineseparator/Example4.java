/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;
import java.io.Serializable;
// violation 2 lines above ''package' should be separated from previous line'
// violation 2 lines above ''import' should be separated from previous line'

class Example4 {

  int var1 = 1;
  int var2 = 2; // violation ''VARIABLE_DEF' should be separated from previous line'


  int var3 = 3; // violation ''VARIABLE_DEF' has more than 1 empty lines before'


  void method1() {} // violation ''METHOD_DEF' has more than 1 empty lines before'
  void method2() { // violation ''METHOD_DEF' should be separated from previous line'
      int var4 = 4;


      int var5 = 5;
  }
}
