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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NewObjectAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface NewObjectAnnotation2 {}

// xdoc section -- start
class Example7 {
  void method() {
    String[] arr1 = new @NewObjectAnnotation1 String[10]; // ok
    String[] arr2 = new @NewObjectAnnotation1 @NewObjectAnnotation2 String[10];
    // violation above, 'Annotation 'NewObjectAnnotation2' should be alone on line.'
    Object obj = new @NewObjectAnnotation1 Object(); // ok
  }
}
// xdoc section -- end
