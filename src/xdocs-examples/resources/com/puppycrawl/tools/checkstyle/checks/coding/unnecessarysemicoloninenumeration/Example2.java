/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonInEnumeration"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicoloninenumeration;

// xdoc section -- start
enum Normal {
    A,
    B,
    ; //enum body contains constructor
  Normal(){}
}
enum NoSemicolon {
    A, B //only enum constants without semicolon
}
// xdoc section -- end
