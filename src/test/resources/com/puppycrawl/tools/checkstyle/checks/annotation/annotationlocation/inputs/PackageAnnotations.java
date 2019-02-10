package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This is not a test-input. Actual test input is in the file package-info.java.
 */

@Target(ElementType.PACKAGE)
@interface PackageAnnotations {

    PackageAnnotation[] value();

}

@Repeatable(PackageAnnotations.class)
@Target(ElementType.PACKAGE)
@interface PackageAnnotation  {

    String value() default  "";

}
