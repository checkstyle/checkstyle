/*
AbbreviationAsWordInName
ignoreStatic = false
ignoreOverriddenMethods = false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

class Example2 extends SuperClass {
  // xdoc section -- start
  int CURRENT_COUNTER; // violation 'no more than '4' consecutive capital letters'
  // violation below 'no more than '4' consecutive capital letters'
  static int GLOBAL_COUNTER;
  final Set<String> stringsFOUND = new HashSet<>();

  @Override // violation below 'no more than '4' consecutive capital letters'
  public void printCOUNTER() {
    System.out.println(CURRENT_COUNTER);
  }
  // violation below 'no more than '4' consecutive capital letters'
  void incrementCOUNTER() {
    CURRENT_COUNTER++;
  }
  // violation below 'no more than '4' consecutive capital letters'
  static void incrementGLOBAL() {
    GLOBAL_COUNTER++;
  }
  // xdoc section -- end
}
