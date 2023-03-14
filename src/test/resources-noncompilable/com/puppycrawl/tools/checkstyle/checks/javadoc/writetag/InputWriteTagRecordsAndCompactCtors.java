/*
WriteTag
tag = @incomplete
tagSeverity = error
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, COMPACT_CTOR_DEF, CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// violation below , 'Type Javadoc comment is missing @incomplete tag'
public class InputWriteTagRecordsAndCompactCtors { // violation

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

    // violation below , 'Type Javadoc comment is missing @incomplete tag'
    record MyRecord2(String myString) { // violation

        // violation 2 lines below , 'Javadoc tag .*'
        /**
         * @incomplete // violation
         */
        public MyRecord2 {
        }
    }

    // violation below , 'Type Javadoc comment is missing @incomplete tag'
    record MyRecord3(int x) { // violation

        // violation 2 lines below , 'Javadoc tag .*'
        /**
         * @incomplete // violation
         */
        MyRecord3() {
            this(3);
        }
    }

    // violation below , 'Type Javadoc comment is missing @incomplete tag'
    record MyRecord4(int y) { // violation
        // violation below , 'Type Javadoc comment is missing @incomplete tag'
        private record MyRecord5(int z) { // violation

            // violation 2 lines below , 'Javadoc tag .*'
            /**
             * @incomplete // violation
             */
            public MyRecord5 {
            }
        }

    }
}
