/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example1 extends SuperClass { // ok, camel case
  int CURRENT_COUNTER; // violation 'no more than '4' consecutive capital letters'
  static int GLOBAL_COUNTER; // ok, static is ignored
  final Set<String> stringsFOUND = new HashSet<>(); // ok, final is ignored

  @Override
  public void printCOUNTER() { // ok, overridden method is ignored
    System.out.println(CURRENT_COUNTER);
  }
  // violation below 'no more than '4' consecutive capital letters'
  void incrementCOUNTER() {
    CURRENT_COUNTER++; // ok, only definitions are checked
  }
  // violation below 'no more than '4' consecutive capital letters'
  static void incrementGLOBAL() {
    GLOBAL_COUNTER++; // ok, only definitions are checked
  }
}
// xdoc section -- end
