//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

/* Config:
 *
 * allowSamelineMultipleAnnotations = false
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * tokens = {CLASS_DEF , INTERFACE_DEF , PACKAGE_DEF , ENUM_CONSTANT_DEF , ENUM_DEF ,
 *  METHOD_DEF , CTOR_DEF , VARIABLE_DEF , ANNOTATION_DEF , ANNOTATION_FIELD_DEF ,
 *  RECORD_DEF, COMPACT_CTOR_DEF}
 */
public class InputAnnotationLocationRecordsAndCompactCtors {
    @NonNull1 public record MyRecord1() { // ok, no param
    }

    @SuppressWarnings("deprecation") public record MyRecord2() { // violation
    }

    @SuppressWarnings("deprecation") record MyRecord3() { // violation
    }

    @SuppressWarnings("deprecation") public record MyRecord4() { // violation
        @SuppressWarnings("deprecation")public MyRecord4{} // violation
    }

    @SuppressWarnings("deprecation")
    public record MyRecord5() {
        record MyInnerRecord(){
            @SuppressWarnings("Annotation")public MyInnerRecord{} // violation
        }
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("deprecation") // ok
    public record MyRecord6() {
        record MyInnerRecord () {
            @SuppressWarnings("Annotation")public MyInnerRecord { // violation
            }
        }
    }

    @SuppressWarnings("deprecation")
    /**
     * @return
     */
    public record MyRecord7() {
        record MyInnerRecord () {@SuppressWarnings("Annotation")public MyInnerRecord {
            } // violation
        }
    }
}

@interface NonNull1{}
