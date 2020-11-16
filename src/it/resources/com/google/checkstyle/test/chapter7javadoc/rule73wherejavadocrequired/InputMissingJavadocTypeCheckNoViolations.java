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


    public enum MyEnum { // OK, google style does not require public enums to have javadocs

    }

    public interface MyInterface {  // OK, google style does not require
                                    // public interfaces to have javadocs

    }
}

