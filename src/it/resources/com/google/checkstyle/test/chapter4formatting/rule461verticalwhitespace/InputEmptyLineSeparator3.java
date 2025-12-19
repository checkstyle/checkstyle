/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/




package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace; // violation ''package' has more than 1 empty lines before.'




public class InputEmptyLineSeparator3 {
  // violation above 'Missing a Javadoc comment.'
  // violation 2 lines above ''CLASS_DEF' has more than 1 empty lines before.'


  String str = "Hello"; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'

  /** Another method. */
  public void bar() {}

}
