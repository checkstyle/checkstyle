/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility">
      <property name="visibility" value="package"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example4 {

  // violation below 'Annotated element has disallowed visibility 'protected'.'
  @VisibleForTesting
  @com.google.common.annotations.Beta
  protected void protectedMethod() {}

  // ok, visibility includes package-private
  @VisibleForTesting
  @com.google.common.annotations.Beta
  void packagePrivateMethod() {}

  // violation below 'Annotated element has disallowed visibility 'protected'.'
  @VisibleForTesting
  @com.google.common.annotations.Beta
  protected int protectedField;

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @com.google.common.annotations.Beta
  public void publicMethod() {}

  // ok, no VisibleForTesting annotation
  @com.google.common.annotations.Beta
  private void privateMethod() {}

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @com.google.common.annotations.Beta
  @Deprecated
  public int publicField;

  // violation below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @com.google.common.annotations.Beta
  public Example4() {}

}
// xdoc section -- end
