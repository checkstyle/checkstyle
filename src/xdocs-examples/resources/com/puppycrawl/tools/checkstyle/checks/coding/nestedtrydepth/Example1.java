/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedTryDepth"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedtrydepth;

// xdoc section -- start
public class Example1 {
  void testMethod() {
    try {
      try { // ok, current depth is 1, default max allowed depth is also 1
      } catch (Exception e) {}
    } catch (Exception e) {}

    try {
      try {
        try { // violation 'Nested try depth is 2 (max allowed is 1).'
        } catch (Exception e) {}
      } catch (Exception e) {}
    } catch (Exception e) {}

    try {
      try {
        try { // violation 'Nested try depth is 2 (max allowed is 1).'
          try { // violation 'Nested try depth is 3 (max allowed is 1).'
            try { // violation 'Nested try depth is 4 (max allowed is 1).'
            } catch (Exception e) {}
          } catch (Exception e) {}
        } catch (Exception e) {}
      } catch (Exception e) {}
    } catch (Exception e) {}
  }
}
 // xdoc section -- end
