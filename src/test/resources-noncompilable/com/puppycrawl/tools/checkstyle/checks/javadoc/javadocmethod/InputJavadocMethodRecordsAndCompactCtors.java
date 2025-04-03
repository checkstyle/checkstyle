/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
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

        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        // violation 3 lines above 'Unused @param tag'
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
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        // violation 3 lines above 'Unused @param tag '
        // violation below 'Expected @param tag for 'myString'.'
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
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong
         */
        // violation 3 lines above 'Unused @param tag for 'properties'.'
        // violation below 'Expected @param tag for 'myInt'.'
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
