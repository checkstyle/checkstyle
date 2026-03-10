/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedMethodVisibilityModifier">
      <property name="tokens" value="PACKAGE_DEF, IMPORT, METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example2 {

  @VisibleForTesting
  protected void allowedProtectedMethod() {}

  @VisibleForTesting
  void allowedPackagePrivateMethod() {}

  @VisibleForTesting
  protected int allowedProtectedField;

  @VisibleForTesting
  public void violationPublicMethod() {}
  // violation above 'Annotated element has disallowed visibility 'public'.'

  @VisibleForTesting
  private void violationPrivateMethod() {}
  // violation above 'Annotated element has disallowed visibility 'private'.'

  @VisibleForTesting
  @Deprecated
  public int allowedPublicField;

  @VisibleForTesting
  public Example2() {}

}
// xdoc section -- end
