/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility">
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// xdoc section -- start
import com.google.common.annotations.VisibleForTesting;

public class Example2 {

  // ok, default visibility includes protected
  @VisibleForTesting
  @com.google.common.annotations.Beta
  protected void protectedMethod() {}

  // ok, default visibility includes package-private
  @VisibleForTesting
  @com.google.common.annotations.Beta
  void packagePrivateMethod() {}

  // ok, fields are not checked when tokens only include METHOD_DEF
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

  // ok, fields are not checked when tokens only include METHOD_DEF
  @VisibleForTesting
  @com.google.common.annotations.Beta
  @Deprecated
  public int publicField;

  // ok, constructors are not checked when tokens only include METHOD_DEF
  @VisibleForTesting
  @com.google.common.annotations.Beta
  public Example2() {}

}
// xdoc section -- end
