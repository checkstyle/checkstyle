/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = PACKAGE_DEF


*/

@PackageAnnotation(value = "foo")
  @PackageAnnotation // violation '.* incorrect .* level 2, .* should be 0.'
// violation below 'Annotation 'PackageAnnotation' should be alone on line.'
@PackageAnnotation("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs;

