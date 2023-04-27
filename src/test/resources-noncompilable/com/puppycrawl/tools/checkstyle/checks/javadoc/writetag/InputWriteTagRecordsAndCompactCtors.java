/*
WriteTag
tag = @incomplete
tagSeverity = error
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;


public class InputWriteTagRecordsAndCompactCtors { // violation 'missing @incomplete tag.'

    // violation 2 lines below 'Type Javadoc tag @incomplete must match pattern '\\S''
    /**
     * @incomplete
     */
    class TestClass {
    }

    // violation 2 lines below 'This class needs more code.'
    /**
     * @incomplete This class needs more code...
     */
    record MyRecord1() {

    }


    record MyRecord2(String myString) { // violation 'missing @incomplete tag.'

        // violation 2 lines below 'This class needs more code.'
        /**
         * @incomplete This class needs more code...
         */
        public MyRecord2 {
        }
    }


    record MyRecord3(int x) { // violation 'Type Javadoc comment is missing @incomplete tag.*'

        // violation 2 lines below 'This class needs more code.'
        /**
         * @incomplete This class needs more code...
         */
        MyRecord3() {
            this(3);
        }
    }


    record MyRecord4(int y) { // violation 'Type Javadoc comment is missing @incomplete tag.*'

        private record MyRecord5(int z) { // violation 'missing @incomplete tag.'

            // violation 2 lines below 'This class needs more code.'
            /**
             * @incomplete This class needs more code...
             */
            public MyRecord5 {
            }
        }

    }
}
