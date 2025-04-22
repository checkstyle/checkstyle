/*
JavadocMethod
validateThrows = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAboveComments {

    /**
     * A Javadoc comment.
     * @return 0
     */
    //@ A JML Annotation
    public int foo() throws Exception { // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    /*@ A JML Annotation */
    public int foo2() throws Exception { // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A dangling Javadoc comment.
     * @throws Exception exception
     * @return 0
     */
    /**
     * A Javadoc comment.
     * @throws Exception exception
     * @return 0
     */
    public int foo3() throws Exception {
        return 0;
    }

    /**
     * A dangling Javadoc comment.
     * @throws Exception exception
     * @return 0
     */
    /**
     * A Javadoc comment.
     * @return 0
     */
    public int foo4() throws Exception {  // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    @SuppressWarnings("unchecked")
    /* generic type warnings are suppressed for this method
    because of reason xyz */
    public int method() throws Exception { // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    /* generic type warnings are suppressed for this method
    because of reason xyz */
    @SuppressWarnings("unchecked")
    public int method2() throws Exception {  // violation,'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    // generic type warnings are suppressed for this method because of reason xyz
    @SuppressWarnings("unchecked")
    public int method3() throws Exception { // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    @SuppressWarnings("unchecked")
    // generic type warnings are suppressed for this method because of reason xyz */
    public int method4() throws Exception { // violation, 'Expected @throws tag for 'Exception''
        return 0;
    }

    /*@ A JML Annotation */
    public int foo5() {
        return 0;
    }

    /**
     * A Javadoc comment.
     * @return 0
     */
    /*@ A JML Annotation */ public int foo6() { return 0; }


    public void foo7() throws Exception { }

    /**
     * A Javadoc comment.
     * @return 0
     */
    public int foo8() { return 0; } /* @ A JML Annotation */

    public void foo9() throws Exception { }
}
