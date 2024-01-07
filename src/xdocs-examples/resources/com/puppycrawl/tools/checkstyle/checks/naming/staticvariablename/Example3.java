/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="StaticVariableName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
      <property name="applyToPrivate" value="false"/>
      <property name="applyToPackage" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.staticvariablename;

// xdoc section -- start
class Example3 {
  public static int goodStatic = 2;
  private static int badStatic = 2;
  public static int GoodStatic1 = 2;
  // violation above, 'Name 'GoodStatic1' must match pattern'
  protected static int GoodStatic2 = 2;
  // violation above, 'Name 'GoodStatic2' must match pattern'
  private static int BadStatic = 2;
  public static int good_static = 2;
  public static int Bad_Static = 2;
  // violation above, 'Name 'Bad_Static' must match pattern'
  private static int Good_Static1 = 2;
  static int Good_Static2 = 2;
}
// xdoc section -- end
