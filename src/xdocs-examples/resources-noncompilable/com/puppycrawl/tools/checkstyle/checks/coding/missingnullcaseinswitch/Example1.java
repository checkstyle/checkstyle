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
      case "something" -> {}
    }
    switch (obj) {
      case null -> {}
      case "something" -> {}
      default -> {}
    }
  }
  void testPatterns(Object obj) {
    // violation below, 'switch using reference types should have a null case.'
    switch (obj) {
      case Integer i : {} break;
      default : {}
    }
    switch (obj) {
      case null -> {}
      case Integer i -> {}
      default -> {}
    }
  }
  void testPrimitives(int obj) {
    switch (obj) {   // ok, this is a primitive type can't be null
      case 1 -> {}
    }
  }

  void testBoxedType(Integer obj) {
    // ok, we can't tell if '1' is of a reference or primitive type
    switch (obj) {
      case 1 -> {}
    }
  }

  void testEnum(E obj) {
    // ok, we can't tell if Ident 'ONE' is of a reference or primitive type
    switch (obj) {
      case ONE -> {}
    }
  }
  enum E {ONE}
}
// xdoc section -- end
