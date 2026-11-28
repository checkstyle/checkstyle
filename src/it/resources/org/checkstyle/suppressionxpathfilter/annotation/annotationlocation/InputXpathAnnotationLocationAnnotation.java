/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = ANNOTATION_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// violation below 'Annotation 'Annotation' should be alone on line.'
@Annotation("bar") @interface InputXpathAnnotationLocationAnnotation {

}

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@interface Annotation  {

    String value() default "";

}
