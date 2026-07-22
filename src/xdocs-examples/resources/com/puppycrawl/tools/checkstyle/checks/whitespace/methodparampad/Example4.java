/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParamPad">
      <property name="allowLineBreaks" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

// xdoc section -- start
class Example4 {
  public Example4() {
    super();
  }

  public Example4 (int aParam) { // violation ''(' is preceded with whitespace'
    super (); // violation ''(' is preceded with whitespace'
  }

  public void method() {}

  public void methodWithVeryLongName
  () {} // ok, because allowLineBreaks is true
}
// xdoc section -- end
