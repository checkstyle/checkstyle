/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;


public class InputSummaryJavadocPeriodAtEnd {
    /**
     * JAXB 1.0 only default validation event handler
     */
    public static final byte NUL = 0;
    // violation below
    /**
     * @throws Exception if a problem occurs.
     */
    public void foo1() throws Exception {

    }
    // violation below
    /**
     * @return 1.
     */
    public int foo2(){
        return 1;
    }
    // violation below
    /**
     * <a href="mailto:vlad@htmlbook.ru"></a>
     */
    public void foo3() {

    }
    // violation below
    /**
     *  A {@code Foo.  Foo}
     */
    public void foo(){

    }
    /**
     * This is test
     * Valid or invalid.
     */
    public void foo4(){

    }
    /**
     * <p>This is valid java doc.</p>
     */
    public void foo5(){

    }
    // violation below
    /**
     * <p>Sentence without period</p>
     *
     * <p> this is a <br>
     *     paragraph.</p>
     */
    public void foo6() {

    }
}
