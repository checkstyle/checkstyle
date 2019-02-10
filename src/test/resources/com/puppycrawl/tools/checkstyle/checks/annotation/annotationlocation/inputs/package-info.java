/**
 * This test-input is intended to be checked using following configuration:
 *
 * tokens = ["PACKAGE_DEF"]
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 *
 */
@PackageAnnotation(value = "foo")
  @PackageAnnotation //warn
@PackageAnnotation("bar") package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.inputs; //warn

