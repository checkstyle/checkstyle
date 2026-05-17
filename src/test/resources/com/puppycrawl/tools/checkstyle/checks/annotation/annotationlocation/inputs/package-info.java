/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = PACKAGE_DEF


*/

// violation 3 lines below '.* incorrect .* level 2, .* should be 0.'
// violation 3 lines below '.* should be alone on line.'
@PackageAnnotation(value = "foo")
  @PackageAnnotation
@PackageAnnotation("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs;

