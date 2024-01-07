/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="StaticVariableName">
      <property name="applyToPublic" value="false"/>
      <property name="applyToProtected" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.staticvariablename;

// xdoc section -- start
class Example2 {
  public static int goodStatic = 2;
  private static int badStatic = 2;
  public static int GoodStatic1 = 2;
  protected static int GoodStatic2 = 2;
  private static int BadStatic = 2;
  // violation above, 'Name 'BadStatic' must match pattern'
  public static int good_static = 2;
  public static int Bad_Static = 2;
  private static int Good_Static1 = 2;
  // violation above, 'Name 'Good_Static1' must match pattern'
  static int Good_Static2 = 2;
  // violation above, 'Name 'Good_Static2' must match pattern'
}
// xdoc section -- end
