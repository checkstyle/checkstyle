/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedMethodVisibilityModifier" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example1 {

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
  public int violationPublicField;
  // violation above 'Annotated element has disallowed visibility 'public'.'

  @VisibleForTesting
  public Example1() {}
  // violation above 'Annotated element has disallowed visibility 'public'.'

}
// xdoc section -- end
