/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
class Example1 { // violation, 'Utility classes should not have a public or default constructor'

  public Example1() {
  }

  public static void fun() {
  }

}
// xdoc section -- end
