/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="allowSamelineMultipleAnnotations" value="false"/>
      <property name="allowSamelineSingleParameterlessAnnotation"
                value="false"/>
      <property name="allowSamelineParameterizedAnnotation" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.Nonnull;
import org.mockito.Mock;

// xdoc section -- start
class Example3 {

  interface DataLoader {

  }

  // violation below, 'Annotation 'Nonnull' should be alone on line.'
  @Nonnull private boolean field1;
  // violation below, 'Annotation 'Override' should be alone on line.'
  @Override public int hashCode() { return 1; }
  @Nonnull
  private boolean field2;
  @Override
  public boolean equals(Object obj) { return true; }
  @Mock
  DataLoader loader;
  @SuppressWarnings("deprecation") DataLoader loader1;
  @SuppressWarnings("deprecation") public int foo() { return 1; }
  // violation below, 'Annotation 'Nonnull' should be alone on line.'
  @Nonnull @Mock DataLoader loader2;
  // violation above, 'Annotation 'Mock' should be alone on line.'
}
// xdoc section -- end
