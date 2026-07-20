/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParamPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

// xdoc section -- start
class Example3 {
  public Example3() {     // violation ''(' is not preceded with whitespace'
    super();              // violation ''(' is not preceded with whitespace'
  }

  public Example3 (int aParam) {
    super ();
  }

  public void method() {} // violation ''(' is not preceded with whitespace'

  public void methodWithVeryLongName
  () {} // violation ''(' should be on the previous line.'
}
// xdoc section -- end
