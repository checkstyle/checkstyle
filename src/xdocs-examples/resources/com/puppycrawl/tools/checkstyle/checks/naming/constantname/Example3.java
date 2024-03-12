/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstantName">
      <property name="applyToPublic" value="false"/>
      <property name="applyToProtected" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

// xdoc section -- start
class Example3 {
  public final static int FIRST_CONSTANT1 = 10;
  protected final static int SECOND_CONSTANT2 = 100;
  final static int third_Constant3 = 1000; // violation 'must match pattern'
  private final static int fourth_Const4 = 50; // violation 'must match pattern'
  public final static int log = 10;
  protected final static int logger = 50;
  final static int loggerMYSELF = 5; // violation 'must match pattern'
  final static int MYSELF = 100;
  protected final static int myselfConstant = 1;
}
// xdoc section -- end
