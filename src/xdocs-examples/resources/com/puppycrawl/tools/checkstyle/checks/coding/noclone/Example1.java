/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoClone"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.noclone;

// xdoc section -- start
public class Example1 {
  public Example1 clone() {return null;} // violation, overrides the clone method
  public static Object clone(Object o) {return null;}
  public static Example1 clone(Example1 same) {return null;}
}
// xdoc section -- end
