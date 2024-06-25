/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingNullCaseInSwitch"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

// xdoc section -- start
public class Example1 {

  void testString(String obj) {
    // violation below, 'switch using reference types should have a null case.'
    switch (obj) {
      case "something" : {}
    }
    switch (obj) {
      case null : {}
      case "something" : {}
      default: {}
    }
  }

  void testPatterns(Object obj) {
    // violation below, 'switch using reference types should have a null case.'
    switch (obj) {
      case Integer i : {} break;
      default : {}
    }
  }

  void testPrimitives(int obj) {
    switch (obj) {   // ok, this is a primitive type can't be null
      case 1 : {}
    }
  }

  void testBoxedType() {
    switch (getInteger()) { // ok, we can't tell if '1' is an Integer type
      case 1 : {}
    }
  }

  void testIdent() {
    // ok, we can't tell if Ident 'ONE' is of a reference type,
    // i.e. Enum or primitive type i.e. local final variable
    switch (getEnum()) {
      case ONE: {}
    }
  }

  enum E { ONE }
  public static final int ONE = 5;
  public static Integer getInteger() { return 1; }
  public static E getEnum() { return E.ONE; }
}
// xdoc section -- end
