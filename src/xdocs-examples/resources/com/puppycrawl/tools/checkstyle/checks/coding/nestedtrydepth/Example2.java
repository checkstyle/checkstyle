/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedTryDepth">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedtrydepth;

// xdoc section -- start
public class Example2 {
  void testMethod(){
    try {
      try {} // OK, current depth is 1, default max allowed depth is also 1
      catch (Exception e) {}
    }catch (Exception e) {}

    try {
      try {
        try {} // OK, current depth is 2, default max allowed depth is also 1
        catch (Exception e) {}
      }catch (Exception e) {}
    }catch (Exception e) {}

    try {
      try {
        try {
          try {
            try {} // violation, current depth is 4, max allowed depth is 3
            catch (Exception e) {}
          }catch (Exception e) {}
        }catch (Exception e) {}
      }catch (Exception e) {}
    } catch (Exception e) {}
  }
}
// xdoc section -- end
