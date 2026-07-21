/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="^[ ]*\* This file is copyrighted"/>
    <property name="minimum" value="1"/>
    <!--  Need to specify a maximum, so 10 times is more than enough. -->
    <property name="maximum" value="10"/>
    <property name="message"
              value="File must contain copyright statement"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;
// xdoc section -- start
/**
 * MyClass as a configuration example.
 */
public class UseCase2 {

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
// violation first line 'File must contain copyright statement'
