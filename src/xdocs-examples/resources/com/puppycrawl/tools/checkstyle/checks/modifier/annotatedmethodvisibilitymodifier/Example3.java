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

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example3 {

  // violation 2 lines below 'Annotated element has disallowed visibility 'protected'.'
  @com.google.common.annotations.Beta
  protected void protectedMethod() {}

  // ok, visibility includes protected
  @VisibleForTesting
  void packagePrivateMethod() {}

  // violation 2 lines below 'Annotated element has disallowed visibility 'protected'.'
  @com.google.common.annotations.Beta
  protected int protectedField;

  // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public void publicMethod() {}

  // violation 2 lines below 'Annotated element has disallowed visibility 'private'.'
  @com.google.common.annotations.Beta
  private void privateMethod() {}

  // violation 3 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @Deprecated
  public int publicField;

  // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public Example3() {}

}
// xdoc section -- end
