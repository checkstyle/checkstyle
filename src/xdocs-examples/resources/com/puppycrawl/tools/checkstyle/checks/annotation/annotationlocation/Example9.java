/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="IMPLEMENTS_CLAUSE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface ImplementsAnnotation1 {}

@Target(ElementType.TYPE_USE)
@interface ImplementsAnnotation2 {}

// xdoc section -- start
class Example9 implements @ImplementsAnnotation1 Serializable { } // ok
class Example9a implements @ImplementsAnnotation1 @ImplementsAnnotation2
        Serializable { }
// violation above, 'Annotation 'ImplementsAnnotation2' should be alone on line.'
// xdoc section -- end
