package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/**
 * This is a Javadoc comment.
 */
public class InputMissingJavadocTypeCheckNoViolations { // OK

    /**
     * This is a Javadoc comment.
     */
    public class Inner implements MyInterface{ // OK

    }

    /**
     * This is a Javadoc comment.
     */
    public enum MyEnum { // OK

    }

    /**
     * This is a Javadoc comment.
     */
    public interface MyInterface {  // OK

        /**
         * This is a Javadoc comment.
         */
        class MyInterfaceClass {}  // OK
    }

    /**
     * This is a Javadoc comment.
     */
    public @interface MyAnnotation { // OK

    }

    /**
     * This is a Javadoc comment.
     */
    protected class InnerProtected { // OK

    }

    /**
     * This is a Javadoc comment.
     */
    protected enum MyEnumProtected { // OK

    }

    /**
     * This is a Javadoc comment.
     */
    protected interface MyInterfaceProtected {  // OK

    }

    /**
     * This is a Javadoc comment.
     */
    protected @interface MyAnnotationProtected { // OK

    }

    public void myMethod() {
        class MyMethodClass {} // OK
    }
}

