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
  void testMethod() {
    try {
      try { // ok, current depth is 1, max allowed depth is 3
      } catch (Exception e) {}
    } catch (Exception e) {}

    try {
      try {
        try { // ok, current depth is 2, max allowed depth is also 3
        } catch (Exception e) {}
      } catch (Exception e) {}
    } catch (Exception e) {}

    try {
      try {
        try { // ok, current depth is 2, max allowed depth is 3
          try { // ok, current depth is 3, max allowed depth is 3
            try { // violation, current depth is 4, max allowed depth is 3
            } catch (Exception e) {}
          } catch (Exception e) {}
        } catch (Exception e) {}
      } catch (Exception e) {}
    } catch (Exception e) {}
  }
}
// xdoc section -- end
