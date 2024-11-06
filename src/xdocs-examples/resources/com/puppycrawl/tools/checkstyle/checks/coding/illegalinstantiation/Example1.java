/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalInstantiation"/>
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

  public void Example1 (boolean a, int b) {
    Boolean c = new Boolean(a);
    java.lang.Boolean d = new java.lang.Boolean(a);
    Integer e = new Integer(b);
    Integer f = Integer.valueOf(b);
  }
}
// xdoc section -- end
