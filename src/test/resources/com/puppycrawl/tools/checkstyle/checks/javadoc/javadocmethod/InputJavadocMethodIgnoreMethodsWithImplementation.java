/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
ignoreMethodsWithImplementation = true
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public abstract class InputJavadocMethodIgnoreMethodsWithImplementation {

    /** A concrete method. */
    public void concreteMethod(int value) {
    }

    /** A native method. */
    public native void nativeMethod(int value); // violation 'Expected @param tag for 'value'.'

    /** An abstract method. */
    public abstract void abstractMethod(int value); // violation 'Expected @param tag for 'value'.'

    interface ExampleInterface {

        /** A bodyless interface method. */
        void bodylessMethod(int value); // violation 'Expected @param tag for 'value'.'

        /** A default interface method. */
        default void defaultMethod(int value) {
        }

        /** A static interface method. */
        static void staticMethod(int value) {
        }

        /** A private interface method. */
        private void privateMethod(int value) {
        }
    }

    public void undocumentedMethod() {
    } // ok, method has an implementation and no Javadoc is required
}
