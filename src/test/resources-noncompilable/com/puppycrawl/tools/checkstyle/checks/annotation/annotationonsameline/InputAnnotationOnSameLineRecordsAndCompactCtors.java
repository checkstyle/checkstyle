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

    @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."

    public record MyRecord2() {
    }

    @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
            record MyRecord3() {
    }

    @SuppressWarnings("deprecation") public record MyRecord4() {
        @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
        public MyRecord4 {
        }
    }

    @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
    public record MyRecord5() {
        record MyInnerRecord() {
                @NonNull1 @SuppressWarnings("Annotation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
            public MyInnerRecord {
            }
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
    public record MyRecord6() {
        record MyInnerRecord() {@SuppressWarnings("Annotation")public MyInnerRecord {}
        }
    }

    @SuppressWarnings("deprecation")
    // violation above, "Annotation 'SuppressWarnings' should be on the same line with its target."
    /**
     * @return
     */
    public record MyRecord7() {
    }
}

@interface NonNull1 {

}
