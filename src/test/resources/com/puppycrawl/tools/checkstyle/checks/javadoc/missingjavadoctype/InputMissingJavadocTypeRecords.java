/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = NonNull1
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
public record InputMissingJavadocTypeRecords() {
    // violation below 'Missing a Javadoc comment.'
    class TestClass {
    }

    //no javadoc here
    // violation below 'Missing a Javadoc comment.'
    record MyRecord1() {

    }

    // violation below 'Missing a Javadoc comment.'
    record MyRecord2(String myString) {

        public MyRecord2 {
        }
    }

    @NonNull1
    record MyRecord3(int x) { // ok due to annotation
        // violation below 'Missing a Javadoc comment.'
        protected record MyRecord4(int y) {
            // violation below 'Missing a Javadoc comment.'
            private record MyRecord5(int z) {

            }


        }
    }
}

// violation below 'Missing a Javadoc comment.'
@interface NonNull1{}
