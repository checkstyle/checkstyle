/*
WriteTag
tag = @incomplete
tagFormat = \S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

public class InputWriteTagRecordsAndCompactCtors {
    /**
     * @incomplete
     */
    class TestClass { // violation
    }

    /**
     * @incomplete .// violation
     */
    record MyRecord1() {

    }

    record MyRecord2(String myString) {

        /**
         * @incomplete // violation
         */
        public MyRecord2 {
        }
    }

    record MyRecord3(int x) {

        /**
         * @incomplete // violation
         */
        MyRecord3() {
            this(3);
        }
    }

    record MyRecord4(int y) {
        private record MyRecord5(int z) {

            /**
             * @incomplete // violation
             */
            public MyRecord5 {
            }
        }

    }
}
