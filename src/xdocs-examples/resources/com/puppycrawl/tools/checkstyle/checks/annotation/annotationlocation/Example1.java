/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.Nonnull;
import org.mockito.Mock;


// xdoc section -- start
class Example1 {

  interface DataLoader {

  }

  @Nonnull
  private boolean field1; // ok
  @Override public int hashCode() { return 1; } // ok
  @Nonnull // ok
  private boolean field2;
  @Override // ok
  public boolean equals(Object obj) { return true; }
  @Mock DataLoader loader1; // ok
  @SuppressWarnings("deprecation") DataLoader loader2;
  // violation above, 'Annotation 'SuppressWarnings' should be alone on line'
  @SuppressWarnings("deprecation") public int foo() { return 1; }
  // violation above, 'Annotation 'SuppressWarnings' should be alone on line'
  @Nonnull @Mock DataLoader loader3;
  // violation above, 'Annotation 'Mock' should be alone on line'
}
// xdoc section -- end
