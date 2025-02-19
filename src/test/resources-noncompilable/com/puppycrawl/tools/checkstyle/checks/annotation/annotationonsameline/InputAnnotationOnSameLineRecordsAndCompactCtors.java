/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineRecordsAndCompactCtors {
    @NonNull1 // violation, "Annotation 'NonNull1' should be on the same line with its target."
    public record MyRecord1() {
    }

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")

    public record MyRecord2() {
    }

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
            record MyRecord3() {
    }
    @SuppressWarnings("deprecation") public record MyRecord4() {
    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
        @SuppressWarnings("deprecation")
        public MyRecord4 {
        }
    }

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
    public record MyRecord5() {
        record MyInnerRecord() {
            // 2 violations 3 lines below:
            //    "Annotation 'NonNull1' should be on the same line with its target."
            //    "Annotation 'SuppressWarnings' should be on the same line with its target."
            @NonNull1 @SuppressWarnings("Annotation")
            public MyInnerRecord {
            }
        }
    }

    /**
     * @return
     */
    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
    public record MyRecord6() {
        record MyInnerRecord() {@SuppressWarnings("Annotation")public MyInnerRecord {}
        }
    }

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
    /**
     * @return
     */
    public record MyRecord7() {
    }
}

@interface NonNull1 {

}
