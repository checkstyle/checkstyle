/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="COPYRIGHTED"/>
    <property name="maximum" value="1"/>
    <property name="ignoreCase" value="true"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;
// xdoc section -- start
/**
 * This file is copyrighted under CC.
 */ // violation above 'Line matches the illegal pattern'
public class Example5 {

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
