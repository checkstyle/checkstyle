/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalPatternVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

// xdoc section - start
class Example1 {
  void method(Object obj) {
    // violation below 'Pattern variable 'str' should be declared final.'
    if (obj instanceof String str) {
      System.out.println(str);
    }

    if (obj instanceof final String str) {
      System.out.println(str);
    }

    if (obj instanceof String str) {
      str = "new value";
      System.out.println(str);
    }
  }
}
// xdoc section - end
