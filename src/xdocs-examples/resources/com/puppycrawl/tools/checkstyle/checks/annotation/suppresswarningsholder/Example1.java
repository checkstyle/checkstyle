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
class Example1 {
  private int K; // violation, 'Name 'K' must match pattern'
  @SuppressWarnings({"membername"})
  private int J; // violation suppressed

  private static final int i = 0; // violation, 'Name 'i' must match pattern'
  @SuppressWarnings("checkstyle:constantname")
  private static final int m = 0; // violation suppressed

  @SuppressWarnings("ParamNumberId")
    public void needsLotsOfParameters1 (int a, // violation suppressed
     int b, int c, int d, int e, int f, int g, int h) {
     // ...
  }

  private int [] ARR; // violation ''int' is followed by whitespace'
  // violation above, 'Name 'ARR' must match pattern'
  @SuppressWarnings("all")
  private int [] ARRAY; // violations suppressed
}
// xdoc section -- end
