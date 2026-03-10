/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility"/>
  </module>
</module>




*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example1 {

  // ok, default visibility includes protected
  @VisibleForTesting
  protected void protectedMethod() {}

  // ok, default visibility includes package-private
  @VisibleForTesting
  void packagePrivateMethod() {}

  // ok, default visibility includes protected
  @VisibleForTesting
  protected int protectedField;

  // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public void publicMethod() {}

  // violation 2 lines below 'Annotated element has disallowed visibility 'private'.'
  @VisibleForTesting
  private void privateMethod() {}

  // violation 3 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  @Deprecated
  public int publicField;

  // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
  @VisibleForTesting
  public Example1() {}

}
// xdoc section -- end
