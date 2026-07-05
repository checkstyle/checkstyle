/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = PACKAGE_DEF


*/

@PackageAnnotation(value = "foo")
  @PackageAnnotation2 // violation '.* incorrect .* level 2, .* should be 0.'
// violation below 'Annotation 'PackageAnnotation3' should be alone on line.'
@PackageAnnotation3("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs;

@interface PackageAnnotation {
    String value();
}
@interface PackageAnnotation2 {}
@interface PackageAnnotation3 {
    String value();
}
