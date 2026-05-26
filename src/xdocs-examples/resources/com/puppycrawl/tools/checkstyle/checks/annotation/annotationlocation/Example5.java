/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="TYPE_ARGUMENT"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

@Target(ElementType.TYPE_USE)
@interface TypeArgAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface TypeArgAnnotation2 {}

// xdoc section -- start
class Example5 {
  List<@TypeArgAnnotation1 String> names; // ok
  List<@TypeArgAnnotation1 @TypeArgAnnotation2 String> data;
  // violation above, 'Annotation 'TypeArgAnnotation2' should be alone on line.'
  Map<@TypeArgAnnotation1 String, @TypeArgAnnotation1 Integer> map; // ok
}
// xdoc section -- end
