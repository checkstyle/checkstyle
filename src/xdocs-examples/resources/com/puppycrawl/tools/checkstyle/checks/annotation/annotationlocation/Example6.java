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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface TypeCastAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface TypeCastAnnotation2 {}

// xdoc section -- start
class Example6 {
  void method(Object obj) {
    String s1 = (@TypeCastAnnotation1 String) obj; // ok
    String s2 = (@TypeCastAnnotation1 @TypeCastAnnotation2 String) obj;
    // violation above, 'Annotation 'TypeCastAnnotation2' should be alone on line.'
  }
}
// xdoc section -- end
