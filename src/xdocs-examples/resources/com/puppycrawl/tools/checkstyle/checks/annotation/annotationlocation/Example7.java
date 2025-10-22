/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="LITERAL_NEW"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.concurrent.Immutable;

import org.antlr.v4.runtime.misc.NotNull;

// xdoc section -- start
class Example7 {
  void method() {
    String[] arr1 = new @NotNull String[10]; // ok
    String[] arr2 = new @NotNull @Immutable String[10];
    // violation above, 'Annotation 'Immutable' should be alone on line.'
    Object obj = new @NotNull Object(); // ok
  }
}
// xdoc section -- end
