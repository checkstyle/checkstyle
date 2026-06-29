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

  // ok, fields are not checked when tokens only include METHOD_DEF
  @VisibleForTesting
  @Deprecated
  public int publicField;

  // ok, constructors are not checked when tokens only include METHOD_DEF
  @VisibleForTesting
  public Example2() {}

}
// xdoc section -- end
