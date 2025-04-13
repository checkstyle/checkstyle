/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber"/>
    <module name="SuppressWarningsHolder">
      <property name="aliasList" value="ParameterNumberCheck=paramnum"/>
    </module>
  </module>
  <module name="SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
public class Example3 {
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
