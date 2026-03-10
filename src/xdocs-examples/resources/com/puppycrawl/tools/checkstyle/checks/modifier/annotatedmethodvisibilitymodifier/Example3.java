/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedMethodVisibilityModifier">
      <property name="annotations"
        value="com.google.common.annotations.Beta, com.google.common.annotations.VisibleForTesting"/>
      <property name="visibility" value="package-private"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import com.google.common.annotations.VisibleForTesting;

// xdoc section -- start
public class Example3 {

  @com.google.common.annotations.Beta
  protected void allowedProtectedMethod() {}
  // violation above 'Annotated element has disallowed visibility 'protected'.'

  @VisibleForTesting
  public void violationPublicMethod() {}
  // violation above 'Annotated element has disallowed visibility 'public'.'

  @com.google.common.annotations.Beta
  private void violationPrivateMethod() {}
  // violation above 'Annotated element has disallowed visibility 'private'.'

  @com.google.common.annotations.Beta
  int allowedPrivateField;

  @VisibleForTesting
  public Example3() {}
  // violation above 'Annotated element has disallowed visibility 'public'.'

}
// xdoc section -- end
