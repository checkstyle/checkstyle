/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility">
      <property name="annotations"
        value="com.google.common.annotations.Beta, com.google.common.annotations.VisibleForTesting"/>
      <property name="visibility" value="package"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example3 {

  // violation below 'Annotated element has disallowed visibility 'protected'.'
  @com.google.common.annotations.Beta
  protected void protectedMethod() {}

  // ok, visibility includes package-private
  @VisibleForTesting
  void packagePrivateMethod() {}

  // violation below 'Annotated element has disallowed visibility 'protected'.'
  @com.google.common.annotations.Beta
  protected int protectedField;

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public void publicMethod() {}

  // violation below 'Annotated element has disallowed visibility 'private'.'
  @com.google.common.annotations.Beta
  private void privateMethod() {}

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @Deprecated
  public int publicField;

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public Example3() {}

}
// xdoc section -- end
