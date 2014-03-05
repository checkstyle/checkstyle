package com.puppycrawl.tools.checkstyle.checks.javadoc;
/** Test 3. */
public class Input_03 {

    /** Do 1.
     * @throws TestException1 when error occurrs.
     * @throws TestException2 when error occurrs.
     */
    public void doStuff1() throws TestException1, TestException2 {
        try {
            doStuff2();
        } catch (final TestException2 e) { }
        throw new Input_03().new TestException1("");
    }
    /** Do 2.
     * @throws TestException2 when error occurrs.
     */
    private static void doStuff2() throws TestException2 {
        throw new TestException2("");
    }
    /** Exception 1.
     */
    class TestException1 extends Exception {
        /** Exception 1.
         * @param messg message
         */
        TestException1(String messg) {
            super(messg);
        }
    }
    /** Exception 2.
     */
    public static class TestException2 extends Exception {
        /** Exception 2.
         * @param messg message
         */
        TestException2(String messg) {
            super(messg);
        }
    }
}
