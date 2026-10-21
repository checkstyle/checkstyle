/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="enforceWhitespaceAroundArrayInit" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
// xdoc section - start
class Example11 {
  int[] array1 = new int[] { 1, 2};
  // violation above ''}' is not preceded with whitespace'
  int[] array2 = new int[] { 1, 2 };
  int[] array3 = new int[] {};
  String[] array4 = { "a", "b"};
  // violation above ''}' is not preceded with whitespace'
}
// xdoc section - end
