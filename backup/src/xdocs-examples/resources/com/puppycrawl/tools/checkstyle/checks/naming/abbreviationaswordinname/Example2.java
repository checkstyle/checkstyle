/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="ignoreStatic" value="false"/>
      <property name="ignoreOverriddenMethods" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example2 extends SuperClass { // OK, camel case
  int CURRENT_COUNTER; // violation 'no more than '4' consecutive capital letters'
  // violation below 'no more than '4' consecutive capital letters'
  static int GLOBAL_COUNTER;
  final Set<String> stringsFOUND = new HashSet<>(); // OK, final is ignored

  @Override // violation below 'no more than '4' consecutive capital letters'
  public void printCOUNTER() {
    System.out.println(CURRENT_COUNTER); // OK, only definitions are checked
  }
  // violation below 'no more than '4' consecutive capital letters'
  void incrementCOUNTER() {
    CURRENT_COUNTER++; // OK, only definitions are checked
  }
  // violation below 'no more than '4' consecutive capital letters'
  static void incrementGLOBAL() {
    GLOBAL_COUNTER++; // OK, only definitions are checked
  }
}
// xdoc section -- end
