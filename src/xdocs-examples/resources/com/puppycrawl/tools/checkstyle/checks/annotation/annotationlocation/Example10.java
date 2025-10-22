/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="DOT"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.concurrent.Immutable;

import org.antlr.v4.runtime.misc.NotNull;

// xdoc section -- start
class Example10 {
  void method() {
    java.lang.@NotNull String s; // ok
    java.lang.@NotNull @Immutable String s2;
    // violation above, 'Annotation 'Immutable' should be alone on line.'
  }
}
// xdoc section -- end
