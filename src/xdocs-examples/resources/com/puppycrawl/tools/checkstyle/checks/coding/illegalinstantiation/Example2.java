/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalInstantiation">
      <property name="classes"
           value="java.lang.Boolean, java.lang.Integer"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

// xdoc section -- start
class Example2 {
  class Boolean {
    boolean a;
    public Boolean (boolean a) { this.a = a; }
  }

  void Example2 () {
    java.lang.Boolean[] newBoolArray = new java.lang.Boolean[]{true,true,false};
    Integer[] newIntArray = new Integer[]{1,2,3};
  }

  void Example2 (boolean a, int b) {
    Boolean c = new Boolean(a);
    java.lang.Boolean d = new java.lang.Boolean(a);
    // violation above, 'Instantiation of java.lang.Boolean should be avoided'
    Integer e = new Integer(b);
    // violation above, 'Instantiation of java.lang.Integer should be avoided'
    Integer f = Integer.valueOf(b);
  }
}
// xdoc section -- end
