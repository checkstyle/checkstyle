/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EqualsHashCode"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

// xdoc section -- start
public class Example1 {
  public int hashCode() { // violation, no valid 'equals'
    // code
    return 0;
  }
  public boolean equals(String o) {
    // code
    return false;
  }
}
class ExampleNoHashCode {
  public boolean equals(Object o) { // violation, no 'hashCode'
    // code
    return false;
  }
  public boolean equals(String o) {
    // code
    return false;
  }
}
class ExampleBothMethods1 {
  public int hashCode() {
    // code
    return 0;
  }
  public boolean equals(Object o) { // ok, both methods exist
    // code
    return false;
  }
  public boolean equals(String o) {
    // code
    return false;
  }
}
class ExampleBothMethods2 {
  public int hashCode() {
    // code
    return 0;
  }
  public boolean equals(java.lang.Object o) { // ok, both methods exist
    // code
    return false;
  }
}
class ExampleNoValidHashCode {
  public static int hashCode(int i) {
    // code
    return 0;
  }
  public boolean equals(Object o) { // violation, no valid 'hashCode'
    // code
    return false;
  }
}
class ExampleNoValidEquals {
  public int hashCode() { // violation, no valid 'equals'
    // code
    return 0;
  }
  public static boolean equals(Object o, Object o2) {
    // code
    return false;
  }
}
// xdoc section -- end
