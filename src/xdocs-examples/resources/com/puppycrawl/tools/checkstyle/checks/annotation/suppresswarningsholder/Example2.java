/*xml
<module name="Checker">
  <module name="TreeWalker">
  <module name="com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck"/>

  <module name="com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder">
    <property name="aliasList" value=
      "com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramnum"/>
  </module>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
class Example2 {
  // violation below, 'More than 7 parameters (found 8)'
  public void needsLotsOfParameters (int a,
    int b, int c, int d, int e, int f, int g, int h) {
  // ...
  }

  @SuppressWarnings("paramnum")
  public void needsLotsOfParameters1 (int a, // violation suppressed
    int b, int c, int d, int e, int f, int g, int h) {
  // ...
  }

}
// xdoc section -- end
