/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="TYPECAST"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.concurrent.Immutable;

import org.antlr.v4.runtime.misc.NotNull;

// xdoc section -- start
class Example6 {
  void method(Object obj) {
    String s1 = (@NotNull String) obj; // ok
    String s2 = (@NotNull @Immutable String) obj;
    // violation above, 'Annotation 'Immutable' should be alone on line.'
  }
}
// xdoc section -- end
