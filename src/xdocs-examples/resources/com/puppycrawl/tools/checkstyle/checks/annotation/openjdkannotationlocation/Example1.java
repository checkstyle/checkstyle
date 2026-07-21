/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkAnnotationLocation"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// xdoc section -- start
class Example1 {

  @Deprecated
  @Override
  public String toString() {
    return "";
  }

  @Nonnull @Deprecated
  public void foo1() {
  }

  @Deprecated public void test() {}

  // violation 3 lines below """Annotation 'Deprecated' must be alone on a line,
  // or all on one line."""
  @Nonnull
  @Deprecated @SafeVarargs
  public final void foo2(String... str) {
  // violation 2 lines above """Annotation 'SafeVarargs' must be alone on a line,
  // or all on one line."""
  }

  // violation 2 lines below """Annotation 'Nonnull' must be on a separate
  // line from target."""
  @Nonnull @Deprecated public void foo() {
  // violation above, """Annotation 'Deprecated' must be on a separate
  // line from target."""
  }

  @Deprecated @Nonnull public void foo2() {}

  @Deprecated int test;

  // violation 2 lines below """Annotation 'Deprecated' must be on a separate
  // line from target."""
  @Deprecated public @Nullable Long getStartTimeOrNull() {
    return 0l;
  }
}
// xdoc section -- end
