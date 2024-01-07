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
  public static int ItStatic1 = 2; // violation, 'must match pattern'
  protected static int ItStatic2 = 2; // violation, 'must match pattern'
  private static int ItStatic = 2;
  public static int it_static = 2;
  public static int It_Static = 2; // violation, 'must match pattern'
  private static int It_Static1 = 2;
  static int It_Static2 = 2;
}
// xdoc section -- end
