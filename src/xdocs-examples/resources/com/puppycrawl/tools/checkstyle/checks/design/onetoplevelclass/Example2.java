/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OneTopLevelClass"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

// xdoc section -- start
class Example2 { // OK, first top-level class
  // methods
}

class ViolationExample2 { // violation, "has to reside in its own source file."
  // methods
}
// xdoc section -- end
