/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OneTopLevelClass"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

// xdoc section - start
public class UseCase1 { // ok, first top-level class
  // methods
}

class ViolationUseCase1 { // violation "has to reside in its own source file."
  // methods
}
// xdoc section - end
