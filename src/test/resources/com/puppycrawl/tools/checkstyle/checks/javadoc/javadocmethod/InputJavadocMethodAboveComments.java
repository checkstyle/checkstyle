/*
JavadocMethod


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAboveComments {

    /**
     * A Javadoc comment.
     */
    //@ A JML Annotation
    public int foo() { // violation, '@return tag should be present and have description.'
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    /*@ A JML Annotation */
    public int foo2() { // violation, '@return tag should be present and have description.'
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    /**
     * A Javadoc comment.
     * @return 0
     */
    public int foo3() {
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    /**
     * A Javadoc comment.
     */
    public int foo4() { // violation, '@return tag should be present and have description.'
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    @SuppressWarnings("unchecked") // violation, '@return tag should be present.*.'
    /* generic type warnings are suppressed for this method
    because of reason xyz */
    public int method() {
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    /* generic type warnings are suppressed for this method
    because of reason xyz */
    @SuppressWarnings("unchecked")  // violation, '@return tag should be present.*.'
    public int method2() {
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    // generic type warnings are suppressed for this method because of reason xyz
    @SuppressWarnings("unchecked") // violation, '@return tag should be present.*.'
    public int method3() {
        return 0;
    }

    /**
     * A Javadoc comment.
     */
    @SuppressWarnings("unchecked") // violation, '@return tag should be present.*.'
    // generic type warnings are suppressed for this method because of reason xyz */
    public int method4() {
        return 0;
    }

    /*@ A JML Annotation */
    public int foo5() {
        return 0;
    }
}
