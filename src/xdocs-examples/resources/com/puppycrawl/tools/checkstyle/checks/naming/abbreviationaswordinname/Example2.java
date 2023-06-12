/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = false
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

class Example2 extends SuperClass {
  int CURRENT_COUNTER; // violation 'no more than '4' consecutive capital letters'
  static int GLOBAL_COUNTER; // violation 'no more than '4' consecutive capital letters'
  final Set<String> stringsFOUND = new HashSet<>();

  @Override
  public void printCOUNTER() { // violation 'no more than '4' consecutive capital letters'
    System.out.println(CURRENT_COUNTER);
  }

  void incrementCOUNTER() { // violation 'no more than '4' consecutive capital letters'
    CURRENT_COUNTER++;
  }

  static void incrementGLOBAL() { // violation 'no more than '4' consecutive capital letters'
    GLOBAL_COUNTER++;
  }
}
