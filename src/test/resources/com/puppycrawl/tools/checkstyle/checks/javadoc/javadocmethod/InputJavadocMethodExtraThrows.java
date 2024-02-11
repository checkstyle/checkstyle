/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputJavadocMethodExtraThrows {

    /**
     * Extra throws in javadoc it is ok.
     * @param properties some value
     * @throws IllegalArgumentException when argument is wrong
     * @throws NullPointerException indicates null was passed
     */
    public InputJavadocMethodExtraThrows(String properties) {
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
    public void doSomething(String properties) throws IllegalArgumentException {
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

    /**
     * expicitly throwed  but throwed as variable, we do not catch this for now
     * @param properties some value
     */
    public void doSomething7(String properties) {
        // here is NPE possible
        if (properties.charAt(0) == 0) {
            IllegalArgumentException exception =
                    new IllegalArgumentException("cannot have char with code 0");
            throw exception;
        }
    }

    /**
     * Actual exception thrown is child class of class that is declared in throws.
     * It is limitation of checkstyle (as checkstyle does not know type hierarchy).
     * Javadoc is valid not declaring {@link FileNotFoundException}
     * BUT checkstyle can not distinguish relationship between exceptions.
     * @param file some file
     * @throws IOException if some problem
     */
    public void doSomething8(File file) throws IOException {
        if (file == null) {
            // violation below 'Expected @throws tag for 'FileNotFoundException'.'
            throw new FileNotFoundException();
        }
    }

   /**
    * Exact throw type referencing in javadoc even first is parent of second type.
    * It is a limitation of checkstyle (as checkstyle does not know type hierarchy).
    * This javadoc is valid for checkstyle and for javadoc tool.
    * @param file some file
    * @throws IOException if some problem
    * @throws FileNotFoundException if file is not found
    */
    public void doSomething9(File file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException();
        }
    }
}
