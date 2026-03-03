/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

class InputRightCurlyAloneOrEmptyNoViolations {
    void method1() {
        int x = 1;
    } // ok

    void method2() {
        int x = 1; } // violation
}
