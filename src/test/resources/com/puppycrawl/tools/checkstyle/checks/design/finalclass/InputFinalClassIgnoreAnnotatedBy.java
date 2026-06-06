/*
FinalClass
ignoreAnnotatedBy = Configuration, java.lang.Deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

@java.lang.Deprecated
class InputFinalClassIgnoreAnnotatedByDeprecated { // ok, skipped by annotation
    private InputFinalClassIgnoreAnnotatedByDeprecated() {
    }
}

class InputFinalClassIgnoreAnnotatedByNoAnnotation { // violation, 'should be declared as final'
    private InputFinalClassIgnoreAnnotatedByNoAnnotation() {
    }
}

@Configuration
class InputFinalClassIgnoreAnnotatedByConfiguration { // ok, skipped by annotation
    private InputFinalClassIgnoreAnnotatedByConfiguration() {
    }
}

@OtherAnnotation // violation
class InputFinalClassIgnoreAnnotatedByOtherAnnotation {
    private InputFinalClassIgnoreAnnotatedByOtherAnnotation() {
    }
}

@interface Configuration {}

@interface OtherAnnotation {}
