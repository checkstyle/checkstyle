/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstantName">
      <property name="applyToPackage" value="false"/>
      <property name="applyToPrivate" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

// xdoc section -- start
class Example4 {
  public final static int FIRST_CONSTANT1 = 10;
  protected final static int SECOND_CONSTANT2 = 100;
  // ignored, package-private and applyToPackage = false
  final static int third_Constant3 = 1000;
  // ignored, private and applyToPrivate = false
  private final static int fourth_Const4 = 50;
  // violation, 'must match pattern'
  public final static int log = 10;
  // violation, 'must match pattern'
  protected final static int logger = 50;
  // ignored, package-private and applyToPackage = false
  final static int loggerMYSELF = 5;
  final static int MYSELF = 100;
  // violation, 'must match pattern'
  protected final static int myselfConstant = 1;
}
// xdoc section -- end
