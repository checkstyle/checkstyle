/*
No config


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation.inputs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This is not a test-input. Actual test input is in the file package-info.java.
 */

@Repeatable(PackageAnnotationOne.class)
@Target(ElementType.PACKAGE)
@interface InputOpenjdkAnnotationLocationPackage {
    String value() default  "";
}

@Target(ElementType.PACKAGE)
@interface PackageAnnotationOne {
    InputOpenjdkAnnotationLocationPackage[] value();
}
