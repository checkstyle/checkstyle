/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber"/>
    <module name="SuppressWarningsHolder">
      <property name="aliasList" value="ParameterNumberCheck=paramcounter"/>
    </module>
  </module>
  <module name="SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
public class UseCase1 {

  private int K;

  @SuppressWarnings({"membername"})
  private int J;

  private static final int i = 0;

  @SuppressWarnings("checkstyle:constantname")
  private static final int m = 0;

  // violation below 'More than 7 parameters (found 8)'
  public void needsLotsOfParameters(
          int a, int b, int c, int d,
          int e, int f, int g, int h) {}

  // violation 2 lines below 'More than 7 parameters (found 8)'
  @SuppressWarnings("ParamNumberId")
  public void needsLotsOfParameters1(
          int a, int b, int c, int d,
          int e, int f, int g, int h) {}

  @SuppressWarnings("paramcounter")
  public void needsLotsOfParameters2(
          int a, int b, int c, int d,
          int e, int f, int g, int h) {}

  private int [] ARR;

  @SuppressWarnings("all")
  private int [] ARRAY;
}
// xdoc section -- end

