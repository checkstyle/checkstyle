/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = PACKAGE_DEF


*/

@PackageAnnotation(value = "foo")
  @PackageAnnotation //warn
@PackageAnnotation("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs; //warn

