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
    ; // ok, enum body contains constructor
  Normal(){}
}
enum NoSemicolon {
    A, B // ok, only enum constants without semicolon
}
// xdoc section -- end
