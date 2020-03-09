package org.checkstyle.suppressionxpathfilter.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ClassAnnotation("bar") class SuppressionXpathRegressionAnnotationLocationClass { //warn

}

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@interface ClassAnnotation {

    String value() default "";

}
