/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = PACKAGE_DEF


*/

@PackageAnnotation(value = "foo")
  // violation below '.*'PackageAnnotation' have incorrect indentation level 2,.*should be 0.'
  @PackageAnnotation
// violation below 'Annotation 'PackageAnnotation' should be alone on line.'
@PackageAnnotation("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs;

