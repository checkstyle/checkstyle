/*
WriteTag
tag = @incomplete
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;


public class InputWriteTagRecordsAndCompactCtors {

    // violation 2 lines below 'Javadoc tag @incomplete must match pattern '\\S''
    /**
     * @incomplete
     */
    class TestClass {
    }

    /**
     * @incomplete Failed to recognize 'record' introduced in Java 14.
     */
    record MyRecord1() {

    }


    record MyRecord2(String myString) {

        /**
         * @incomplete Failed to recognize 'record' introduced in Java 14.
         */
        public MyRecord2 {
        }
    }


    record MyRecord3(int x) {

        /**
         * @incomplete Failed to recognize 'record' introduced in Java 14.
         */
        MyRecord3() {
            this(3);
        }
    }


    record MyRecord4(int y) {

        private record MyRecord5(int z) {

            /**
             * @incomplete Failed to recognize 'record' introduced in Java 14.
             */
            public MyRecord5 {
            }
        }

    }
}
