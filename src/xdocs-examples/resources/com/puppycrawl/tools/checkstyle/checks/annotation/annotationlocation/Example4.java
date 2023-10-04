/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="allowSamelineMultipleAnnotations" value="false"/>
      <property name="allowSamelineSingleParameterlessAnnotation"
                value="true"/>
      <property name="allowSamelineParameterizedAnnotation" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import org.antlr.v4.runtime.misc.NotNull;
import org.mockito.Mock;

// xdoc section -- start
class Example4 {
  @NotNull private boolean field1; // ok, as 'tokens' property set to METHOD_DEF only
  @Override public int hashCode() { return 1; }
  @NotNull
  private boolean field2;
  @Override
  public boolean equals(Object obj) { return true; }
  @Mock
  DataLoader loader1;
  @SuppressWarnings("deprecation") DataLoader loader;
  @SuppressWarnings("deprecation") public int foo() { return 1; }
  // violation above, 'Annotation 'SuppressWarnings' should be alone on line.'
  @NotNull @Mock DataLoader loader2;
}
// xdoc section -- end
