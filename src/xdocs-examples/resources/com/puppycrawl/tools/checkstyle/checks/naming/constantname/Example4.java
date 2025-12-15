/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstantName">
      <!-- Do not apply ConstantName rule to private and package-private fields -->
      <property name="applyToPrivate" value="false"/>
      <property name="applyToPackage" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

// xdoc section -- start
class Example4 { // no violations: private and package-private fields are ignored
  public static final int PUBLIC_CONSTANT = 1;
  private static final int privateConstant = 2; // ignored because applyToPrivate = false
  static final int packageConstant = 3;         // ignored because applyToPackage = false
}
// xdoc section -- end
