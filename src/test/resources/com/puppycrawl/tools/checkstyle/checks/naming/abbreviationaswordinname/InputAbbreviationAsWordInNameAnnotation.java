/*
AbbreviationAsWordInName
ignoreOverriddenMethods = (default)true
ignoreStatic = (default)true
tokens = (default)CLASS_DEF,INTERFACE_DEF,ENUM_DEF,ANNOTATION_DEF,ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF,VARIABLE_DEF,METHOD_DEF,PATTERN_VARIABLE_DEF,RECORD_DEF,RECORD_COMPONENT_DEF
ignoreFinal = (default)true
allowedAbbreviations = (default)
ignoreStaticFinal = (default)true
allowedAbbreviationLength = (default)3

*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public interface InputAbbreviationAsWordInNameAnnotation extends BaseClass {
    @Annotation1
    @Override
    String readMETHOD();
}
abstract interface BaseClass {
    String readMETHOD(); // violation 'name 'readMETHOD' must contain no more than '4' .* cap.*.'
}
