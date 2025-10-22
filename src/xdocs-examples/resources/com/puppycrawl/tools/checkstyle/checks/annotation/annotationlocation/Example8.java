/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="LITERAL_THROWS"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.io.IOException;

import org.antlr.v4.runtime.misc.NotNull;

@interface Critical {}

// xdoc section -- start
class Example8 {
  void method1() throws @NotNull IOException { } // ok
  void method2() throws @NotNull @Critical IOException { }
  // violation above, 'Annotation 'Critical' should be alone on line.'
}
// xdoc section -- end
