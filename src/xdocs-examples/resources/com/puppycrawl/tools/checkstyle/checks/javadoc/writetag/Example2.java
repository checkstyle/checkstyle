/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tag" value="@since"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section -- start
/**
* Some class
*/
public class Example2 { // violation, 'Type Javadoc comment is missing @since tag'
  /** some doc */
  void testMethod1() {} // OK, as methods are not checked by default

  public void testMethod2() {}
}
// xdoc section -- end
