/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

public class InputAnnotationLocationRecordsAndCompactCtors {
    @NonNull1 public record MyRecord1() { // ok, no param
    }

    // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
    @SuppressWarnings("deprecation") public record MyRecord2() {
    }

    // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
    @SuppressWarnings("deprecation") record MyRecord3() {
    }

    // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
    @SuppressWarnings("deprecation") public record MyRecord4() {
        // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
        @SuppressWarnings("deprecation")public MyRecord4{}
    }

    @SuppressWarnings("deprecation")
    public record MyRecord5() {
        record MyInnerRecord(){
            // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
            @SuppressWarnings("Annotation")public MyInnerRecord{}
        }
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public record MyRecord6() {
        record MyInnerRecord () {
            // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
            @SuppressWarnings("Annotation")public MyInnerRecord {
            }
        }
    }

    @SuppressWarnings("deprecation")
    /**
     * @return
     */
    public record MyRecord7() {
        record MyInnerRecord () {@SuppressWarnings("Annotation")public MyInnerRecord {
            } // violation above 'Annotation 'SuppressWarnings' should be alone on line.'
        }
    }
}

@interface NonNull1{}
