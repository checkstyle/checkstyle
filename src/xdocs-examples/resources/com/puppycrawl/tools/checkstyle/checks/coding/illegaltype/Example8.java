/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalType" />
    <module name="IllegalType">
      <property name="illegalClassNames" value="var"/>
      <property name="tokens" value="VARIABLE_DEF"/>
      <property name="id" value="IllegalTypeVarAsField"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

// xdoc section -- start
public class Example8 {
  public void var() {
    // violation below 'Usage of type 'var' is not allowed'
    var message = "Hello, World!";

    // violation below 'Usage of type 'var' is not allowed'
    var count = 42;

    String regularString = "This is fine";

    // violation below 'Usage of type 'var' is not allowed'
    for (var i = 0; i < 10; i++) {
      System.out.println(i);
    }
  }
}
// xdoc section -- end
