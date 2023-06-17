/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineRecordsAndCompactCtors {
    @NonNull1 // violation
    public record MyRecord1() {
    }

    @SuppressWarnings("deprecation") // violation

    public record MyRecord2() {
    }

    @SuppressWarnings("deprecation") // violation
            record MyRecord3() {
    }

    @SuppressWarnings("deprecation") public record MyRecord4() { // ok
        @SuppressWarnings("deprecation") // violation
        public MyRecord4 {
        }
    }

    @SuppressWarnings("deprecation") // violation
    public record MyRecord5() {
        record MyInnerRecord() {
                @NonNull1 @SuppressWarnings("Annotation") // violation
            public MyInnerRecord {
            }
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("deprecation") // violation
    public record MyRecord6() {
        record MyInnerRecord() {@SuppressWarnings("Annotation")public MyInnerRecord {} // ok
        }
    }

    @SuppressWarnings("deprecation") // violation
    /**
     * @return
     */
    public record MyRecord7() {
    }
}

@interface NonNull1 {

}
