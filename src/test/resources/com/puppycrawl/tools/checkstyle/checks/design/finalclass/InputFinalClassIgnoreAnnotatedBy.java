/*
FinalClass
ignoreAnnotatedBy = AnnotConf, java.lang.Deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

@java.lang.Deprecated
class InputFinalClassIgnoreAnnotatedBy { // ok, skipped by annotation
    private InputFinalClassIgnoreAnnotatedBy() {
    }
}

class InputFinalClassIgnoreAnnotatedByNoAnnotation { // violation, 'should be declared as final'
    private InputFinalClassIgnoreAnnotatedByNoAnnotation() {
    }
}

@AnnotConf
class InputFinalClassIgnoreAnnotatedByConfiguration { // ok, skipped by annotation
    private InputFinalClassIgnoreAnnotatedByConfiguration() {
    }
}

@OtherAnnotation // violation, 'should be declared as final'
class InputFinalClassIgnoreAnnotatedByOtherAnnotation {
    private InputFinalClassIgnoreAnnotatedByOtherAnnotation() {
    }
}

@interface AnnotConf {}

@interface OtherAnnotation {}
