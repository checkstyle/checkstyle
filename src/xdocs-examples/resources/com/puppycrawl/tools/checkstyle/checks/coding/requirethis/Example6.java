/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

// xdoc section -- start
class Example6 {
  String prefix;

  String modifyPrefix(String prefix) {
    prefix = "^" + prefix + "$";  // ok, because method parameter is returned
    return prefix;
  }
}
// xdoc section -- end
