/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParamPad">
      <property name="allowLineBreaks" value="true"/>
      <property name="option" value="space"/>
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

// xdoc section -- start
class Example2 {
  public Example2() {
    super();
  }

  public Example2 (int aParam) {
    super ();
  }

  public void method() {} // violation ''(' is not preceded with whitespace'

  public void methodWithVeryLongName
  () {} // OK, because allowLineBreaks is true
}
// xdoc section -- end
