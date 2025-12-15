/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="validateUnnamedVariables" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
public class Example5
{
  static void test() {
    int _ = 1;
    // violation above, 'Variable '_' should be declared final'
  }
  public static void main(String []args) {
    test();
  }
}
// xdoc section -- end

