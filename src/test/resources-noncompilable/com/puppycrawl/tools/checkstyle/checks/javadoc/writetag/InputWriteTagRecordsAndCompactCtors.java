/*
WriteTag
tag = @incomplete
tagSeverity = error
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 'Type Javadoc tag @incomplete must match pattern '\\S''

public class InputWriteTagRecordsAndCompactCtors {

    // violation 2 lines below 'Type Javadoc tag @incomplete must match pattern '\\S''
    /**
     * @incomplete
     */
    class TestClass {
    }

    // violation 2 lines below 'Failed to recognize 'record' introduced in Java 14.'
    /**
     * @incomplete Failed to recognize 'record' introduced in Java 14.
     */
    record MyRecord1() {

    }
        // violation 'Failed to recognize 'record' introduced in Java 14.'

    record MyRecord2(String myString) {

        // violation 2 lines below 'Failed to recognize 'record' introduced in Java 14.'
        /**
         * @incomplete Failed to recognize 'record' introduced in Java 14.
         */
        public MyRecord2 {
        }
    }
        // violation 'Failed to recognize 'record' introduced in Java 14.'

    record MyRecord3(int x) {

        // violation 2 lines below 'Failed to recognize 'record' introduced in Java 14.'
        /**
         * @incomplete Failed to recognize 'record' introduced in Java 14.
         */
        MyRecord3() {
            this(3);
        }
    }
        // violation 'Failed to recognize 'record' introduced in Java 14.'

    record MyRecord4(int y) { // violation 'Failed to recognize 'record' introduced in Java 14.'

        private record MyRecord5(int z) {

            // violation 2 lines below 'Failed to recognize 'record' introduced in Java 14.'
            /**
             * @incomplete Failed to recognize 'record' introduced in Java 14.
             */
            public MyRecord5 {
            }
        }

    }
}
