/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
class Example3 {

  protected Example3() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }

}
// xdoc section -- end
