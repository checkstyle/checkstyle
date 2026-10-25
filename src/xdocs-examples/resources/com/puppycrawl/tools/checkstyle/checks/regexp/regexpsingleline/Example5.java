/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="COPY[R]IGHTED"/>
    <property name="maximum" value="1"/>
    <property name="ignoreCase" value="true"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;
// xdoc section - start
/**
 * This file is copyrighted under CC.
 */ // ok, the first occurrence is allowed
public class Example5 {
  // This method is copyrighted. // violation 'Line matches the illegal pattern'
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
// xdoc section - end
