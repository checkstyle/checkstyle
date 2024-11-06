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
public class Example1 {
  public class Boolean {
    boolean a;
    public Boolean (boolean a) { this.a = a; }
  }

  public void Example1 () {
    java.lang.Boolean[] newBoolArray = new java.lang.Boolean[]{true,true,false};
    Integer[] newIntArray = new Integer[]{1,2,3};
  }

  public void Example1 (boolean a, int b) {
    Boolean c = new Boolean(a);
    java.lang.Boolean d = new java.lang.Boolean(a);

    Integer e = new Integer(b);

    Integer f = Integer.valueOf(b);
  }
}
// xdoc section -- end
