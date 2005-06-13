package test.javadoc.method;
/** Test 3. */
public class Test3 {

    /** Do 1.
     * @throws TestException1 when error occurrs.
     * @throws TestException2 when error occurrs.
     */
    public void doStuff1() throws TestException1, TestException2 {
        try {
            doStuff2();
        } catch (TestException2 e) { }
        throw new Test3().new TestException1("");
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
