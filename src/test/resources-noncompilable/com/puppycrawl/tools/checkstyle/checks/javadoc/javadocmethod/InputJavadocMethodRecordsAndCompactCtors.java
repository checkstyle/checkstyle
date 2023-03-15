/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF, RECORD_DEF, CLASS_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodRecordsAndCompactCtors {
    // methods
    public record MyRecord() {
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong // ok
         */
        public void doSomething4(String properties) {
            // here is NPE possible
            if (properties.charAt(0) == 0) {
                throw new IllegalArgumentException("cannot have char with code 0"); // violation
            }
        }

        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value
         * @throws java.lang.IllegalStateException when argument is wrong // ok
         */
        public void doSomething5(String properties) {
            // here is NPE possible
            if (properties.charAt(0) == 0) { // violation below
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
         * @param properties some value // violation
         * @throws java.lang.IllegalStateException when argument is wrong // ok
         */
        public MySecondRecord {
            // here is NPE possible
            if (props.charAt(0) == 0) {
                throw new IllegalArgumentException("cannot have char with code 0"); // violation
            }
        }
    }

    // Record component, compact ctor
    public record MyThirdRecord(String myString) {
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value // violation
         * @throws java.lang.IllegalStateException when argument is wrong // ok
         */
        public MyThirdRecord {
            // here is NPE possible
            if (myString.charAt(0) == 0) {
                throw new IllegalArgumentException("cannot have char with code 0"); // violation
            }
        }
    }

    // Record component, ctor
    public record MyFourthRecord(String myString) {
        /**
         * exception is explitly thrown in code missed in javadoc
         *
         * @param properties some value // violation
         * @throws java.lang.IllegalStateException when argument is wrong // ok
         */
        public MyFourthRecord(int myInt) { // violation
            this("my string");
            // here is NPE possible
            if (myString.charAt(0) == 0) {
                throw new IllegalArgumentException("cannot have char with code 0"); // violation
            }
        }
    }
}
