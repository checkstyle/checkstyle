/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="ParameterNumber">
      <property name="id" value="ParamNumberId"/>
    </module>
    <module name="NoWhitespaceAfter"/>
    <module name="SuppressWarningsHolder"/>
  </module>
  <module name="SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
public class Example1 {

  private int K; // violation, 'Name 'K' must match pattern'

  @SuppressWarnings({"membername"})
  private int J; // violation suppressed

  private static final int i = 0; // violation, 'Name 'i' must match pattern'

  @SuppressWarnings("checkstyle:constantname")
  private static final int m = 0; // violation suppressed

  // violation below, 'More than 7 parameters (found 8)'
  public void needsLotsOfParameters(
          int a, int b, int c, int d,
          int e, int f, int g, int h) {

  }

  @SuppressWarnings("ParamNumberId")
  public void needsLotsOfParameters1(
          int a, int b, int c, int d,
          int e, int f, int g, int h) { // violation suppressed

  }

  @SuppressWarnings("paramnum")
  public void needsLotsOfParameters2(
          int a, int b, int c, int d,
          int e, int f, int g, int h) {

  }

  private int [] ARR; // violation ''int' is followed by whitespace'
  // violation above, 'Name 'ARR' must match pattern'
  @SuppressWarnings("all")
  private int [] ARRAY; // violation suppressed
}
// xdoc section -- end
