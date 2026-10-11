/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

// violation above ''package' has more than 1 empty lines before.'

public class InputFormattedEmptyLineSeparator3 {
  // violation above ''CLASS_DEF' has more than 1 empty lines before.'

  String s = "Hello"; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'
}
