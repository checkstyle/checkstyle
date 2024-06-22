/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingNullCaseInSwitch"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

import java.util.List;
import java.util.concurrent.TimeUnit;
// xdoc section -- start
public class Example1 {

  void testString(String obj) {
    // violation below, 'Switch using reference types should have a null case.'
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
    // violation below, 'Switch using reference types should have a null case.'
    switch (obj) {
      case Integer i : {} break;
      default : {}
    }
  }

  void testPrimitives(int i) {
    switch (i) {   // ok, this is a primitive type can't be null
      case 1 : {}
    }
  }

  // This example is ok, because Checkstyle is not type-aware. The
  // value of 't' could be an enum constant, a primitive, or a reference type.
  void testEnum() {
    var t = TimeUnit.DAYS;
    switch (t) {
      case SECONDS -> {}
    }
  }

  // This is example is also ok, because we do not know the type
  // of the variable t. It could be an 'int' or an 'Integer'.
  void test() {
    var t = List.of().size();
    switch (t) {
      case 1 : {}
    }
  }
}
// xdoc section -- end
