/*
WriteTag
tag = @incomplete
tagSeverity = error
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

public class InputWriteTagRecordsAndCompactCtors {

    // violation 2 lines below 'Type Javadoc tag @incomplete must match pattern '\\S''
    /**
     * @incomplete
     */
    class TestClass {
    }

    // violation 2 lines below , 'Javadoc tag .*'
    /**
     * @incomplete // violation
     */
    record MyRecord1() {

    }

    record MyRecord2(String myString) {

        // violation 2 lines below , 'Javadoc tag .*'
        /**
         * @incomplete // violation
         */
        public MyRecord2 {
        }
    }

    record MyRecord3(int x) {

        // violation 2 lines below , 'Javadoc tag .*'
        /**
         * @incomplete // violation
         */
        MyRecord3() {
            this(3);
        }
    }

    record MyRecord4(int y) {
        private record MyRecord5(int z) {

            // violation 2 lines below , 'Javadoc tag .*'
            /**
             * @incomplete // violation
             */
            public MyRecord5 {
            }
        }

    }
}
