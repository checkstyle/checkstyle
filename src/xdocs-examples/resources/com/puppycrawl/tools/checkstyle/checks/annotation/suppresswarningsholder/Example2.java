/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="ParameterNumber">
      <property name="id" value="ParamListShouldBeShort"/>
    </module>
    <module name="SuppressWarningsHolder">
      <property name="aliasList"
        value="com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramcounter"/>
    </module>
    <module name="NoWhitespaceAfter"/>
    <module name="SuppressWarningsHolder"/>
  </module>
  <module name="SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
class Example2 {
  private int K; // violation, 'Name 'K' must match pattern'
  @SuppressWarnings({"membername"})
  private int J;

  @SuppressWarnings("ParamListShouldBeShort")
  public void needsLotsOfParameters1 (int a,
    int b, int c, int d, int e, int f, int g, int h) {}

  private int [] ARR; // violation ''int' is followed by whitespace'
  // violation above, 'Name 'ARR' must match pattern'
  @SuppressWarnings("all")
  private int [] ARRAY;

  @SuppressWarnings("paramcounter")
  public void needsLotsOfParameters2 (int a,
    int b, int c, int d, int e, int f, int g, int h) {}
}
// xdoc section -- end
