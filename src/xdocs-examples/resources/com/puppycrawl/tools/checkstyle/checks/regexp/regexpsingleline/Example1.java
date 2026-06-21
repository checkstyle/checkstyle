/*xml
<module name="Checker">
  <module name="RegexpSingleline" />
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;
// xdoc section -- start
/**
 * This file is copyrighted under CC.
 */
public class Example1 {

  void myFunction() {
    try {
      doSomething();
      System.exit(0);
    } catch (Exception e) {
      System.exit(1);
    }
  }

  void doSomething() {}
}
// xdoc section -- end
