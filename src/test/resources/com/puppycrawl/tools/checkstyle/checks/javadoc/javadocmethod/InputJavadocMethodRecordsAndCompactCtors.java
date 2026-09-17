/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodRecordsAndCompactCtors {
    // methods
    public record MyRecord() {
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        public void doSomething4(String properties) {
            // here is NPE possible
            if (properties.charAt(0) == 0) {
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("cannot have char with code 0");
            }
        }

        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        public void doSomething5(String properties) {
            // here is NPE possible
            if (properties.charAt(0) == 0) {
                // violation below '.* @throws .* 'java.lang.IllegalArgumentException'.'
                throw new java.lang.IllegalArgumentException("cannot have char with code 0");
            }
        }
    }

    // static field, compact ctor
    public record MySecondRecord() {
        static String props = "";

        // violation 4 lines below 'Unused @param tag'
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        public MySecondRecord {
            // here is NPE possible
            if (props.charAt(0) == 0) {
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("cannot have char with code 0");
            }
        }
    }

    // Record component, compact ctor
    public record MyThirdRecord(String myString) {
        // violation 5 lines below 'Unused @param tag '
        // violation 7 lines below 'Expected @param tag for 'myString'.'
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        public MyThirdRecord {
            // here is NPE possible
            if (myString.charAt(0) == 0) {
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("cannot have char with code 0");
            }
        }
    }

    // Record component, ctor
    public record MyFourthRecord(String myString) {
        // violation 5 lines below 'Unused @param tag for 'properties'.'
        // violation 7 lines below 'Expected @param tag for 'myInt'.'
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        public MyFourthRecord(int myInt) {
            this("my string");
            // here is NPE possible
            if (myString.charAt(0) == 0) {
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("cannot have char with code 0");
            }
        }
    }
}
