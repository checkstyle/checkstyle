/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalInstantiation">
      <property name="classes"
           value="java.lang.Boolean[], Boolean[], java.lang.Integer[], Integer[]"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

// xdoc section -- start
public class Example3 {
  public void Example3 () {
    Boolean[] newBoolArray = new Boolean[]{true,true,false};
    Integer[] newIntArray = new Integer[]{1,2,3};
  }
}
// xdoc section -- end
