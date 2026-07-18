package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header mismatch*'

public class InputJavadocOne {

    // violation below 'Summary javadoc is missing.'
    /**
     *
     */
    class Temp{
    }

    /**
     * There should be a short sentence at start.
     *
     * @return a string
     */
    public String method1() {
        return "abc";
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * @return a string
     */
    public String method2() {
        return "";
    }

    // violation 2 lines below 'Summary javadoc is missing.'
    /**
     * {@summary  }
     */
    public String method3() {
        return "";
    }

    // violation 2 lines below 'Summary javadoc is missing.'
    /**
     * {@summary <p> </p>}
     */
    public String method4() {
        return "";
    }

    /**
     * {@summary <p>This is a javadoc with period.<p/>}
     */
    public void method5() {}

    // violation below 'First sentence of Javadoc is missing an ending period.'
    /**
     * Summary sentence should end with a period
     */
    public void method6() {}
}
