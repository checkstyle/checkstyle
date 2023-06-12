/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = ALLOWED
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example7 {
  int counterXYZ = 1;
  final int customerID = 2;
  static int nextID = 3;
  static final int MAX_ALLOWED = 4;
}
