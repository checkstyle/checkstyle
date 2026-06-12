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
import java.util.List;

// xdoc section -- start
class Example1 {
  @Nonnull @Deprecated
  public void foo1() {
  }

  @Deprecated public void test() {}

  @Nonnull
  @Deprecated @SafeVarargs
  public final void badMethodOne(String... str) {
  // violation above """Annotations on 'badMethodOne' must be all on one line
  // or all on separate lines."""
  }

  @Nonnull
  @Deprecated
  @SafeVarargs
  public final void goodMethodOne(String... str) {
  }

  @Nonnull @Deprecated @SafeVarargs
  public final void goodMethodTwo(String... str) {
  }

  @Nonnull @Deprecated public void fooBad() {
  // violation above 'Annotations must be on a separate line from 'fooBad'.'
  }

  @Deprecated @Nonnull public void fooGood() {}
  @Deprecated int test;

  // ok for Nullable and Deprecated due to checkstyle limitations
  public @Nullable @Deprecated List<String> getItemsOrNull(List<String> items) {
    return items;
  }

}
// xdoc section -- end
