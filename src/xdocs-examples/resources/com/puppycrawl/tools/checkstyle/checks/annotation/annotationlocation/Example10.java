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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface DotAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface DotAnnotation2 {}

// xdoc section -- start
class Example10 {
  void method() {
    java.lang.@DotAnnotation1 String s; // ok
    java.lang.@DotAnnotation1 @DotAnnotation2 String s2;
    // violation above, 'Annotation 'DotAnnotation2' should be alone on line.'
  }
}
// xdoc section -- end
