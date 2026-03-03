/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;
// xdoc section -- start
class Example1 {
  public @interface TestAnnotation {}

  public @interface TestAnnotation2 {
    String someValue(); } // violation, "should be alone on a line."

  public @interface TestAnnotation3 {
    String someValue();
  }
}
// xdoc section -- end
