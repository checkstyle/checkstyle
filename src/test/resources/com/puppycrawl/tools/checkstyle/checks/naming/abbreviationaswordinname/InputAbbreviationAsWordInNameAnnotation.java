/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


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
