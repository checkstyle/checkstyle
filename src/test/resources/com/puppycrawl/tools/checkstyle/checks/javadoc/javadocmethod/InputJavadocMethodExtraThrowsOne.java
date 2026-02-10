/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodExtraThrowsOne {

    /**
     * Extra throws in javadoc it is ok.
     * @param properties some value
     * @throws IllegalArgumentException when argument is wrong
     * @throws NullPointerException indicates null was passed
     */
    public  InputJavadocMethodExtraThrowsOne(String properties) {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
    }

    /**
     * Extra throws in javadoc it is ok
     * @param properties some value
     * @throws java.lang.IllegalArgumentException when argument is wrong
     * @throws java.lang.NullPointerException indicates null was passed
     */
    public void doSomething1(String properties) throws IllegalArgumentException {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
    }

    /**
     * declared exception in method signature is missed in javadoc
     * @param properties some value
     * @throws java.lang.IllegalArgumentException when argument is wrong
     * @throws java.lang.NullPointerException indicates null was passed
     */
    // violation below 'Expected @throws tag for 'IllegalStateException'.'
    public void doSomething2(String properties) throws IllegalStateException {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
    }

    /**
     * exception is explitly thrown in code missed in javadoc
     * @param properties some value
     * @throws java.lang.IllegalStateException when argument is wrong
     */
    public void doSomething3(String properties) throws IllegalStateException {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("cannot have char with code 0");
        }
    }

    /**
     * exception is explitly thrown in code missed in javadoc
     * @param properties some value
     * @throws java.lang.IllegalStateException when argument is wrong
     */
    public void doSomething4(String properties) {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            // violation below 'Expected @throws tag for 'IllegalArgumentException'.'
            throw new IllegalArgumentException("cannot have char with code 0");
        }
    }

    /**
     * exception is explitly thrown in code missed in javadoc
     * @param properties some value
     * @throws java.lang.IllegalStateException when argument is wrong
     */
    public void doSomething5(String properties) {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            // violation below 'Expected @throws tag for 'java.lang.IllegalArgumentException'.'
            throw new java.lang.IllegalArgumentException(
                    "cannot have char with code 0");
        }
    }

    /**
     * expicitly throwed is declared in javadoc
     * @param properties some value
     * @throws IllegalArgumentException when argument is wrong
     */
    public void doSomething6(String properties) {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            throw new java.lang.IllegalArgumentException("cannot have char with code 0");
        }
    }
}
