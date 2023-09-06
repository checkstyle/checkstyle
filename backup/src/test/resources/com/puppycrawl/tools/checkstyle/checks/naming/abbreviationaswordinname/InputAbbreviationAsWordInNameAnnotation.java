/*
AbbreviationAsWordInName


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
