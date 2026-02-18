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
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface ThrowsAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface ThrowsAnnotation2 {}

// xdoc section -- start
class Example8 {
  void method1() throws @ThrowsAnnotation1 IOException { } // ok
  void method2() throws @ThrowsAnnotation1 @ThrowsAnnotation2 IOException { }
  // violation above, 'Annotation 'ThrowsAnnotation2' should be alone on line.'
}
// xdoc section -- end
