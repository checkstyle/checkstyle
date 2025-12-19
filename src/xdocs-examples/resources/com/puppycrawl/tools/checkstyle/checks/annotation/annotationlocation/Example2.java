/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="allowSamelineSingleParameterlessAnnotation"
                value="false"/>
      <property name="allowSamelineParameterizedAnnotation" value="false"/>
      <property name="allowSamelineMultipleAnnotations" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.Nonnull;
import org.mockito.Mock;

interface DataLoader {

}

// xdoc section -- start
class Example2 {
  @Nonnull
  private boolean field1;
  @Override public int hashCode() { return 1; } // ok
  @Nonnull
  private boolean field2;
  @Override
  public boolean equals(Object obj) { return true; }
  @Mock
  DataLoader loader1;
  @SuppressWarnings("deprecation") DataLoader loader;
  @SuppressWarnings("deprecation") public int foo() { return 1; } // ok
  @Nonnull @Mock DataLoader loader2;
  // ok above as 'allowSamelineMultipleAnnotations' set to true
}
// xdoc section -- end
