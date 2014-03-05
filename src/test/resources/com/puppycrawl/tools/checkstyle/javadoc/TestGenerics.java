public class TestGenerics <E extends java.lang.Exception,
                           RE extends RuntimeException & java.io.Serializable>
{
    /**
     * @throws E in some cases
     * @throws RE in some cases
     */
    public void method1() throws E
    {
    }

    /**
     * RuntimeException is not declared.
     */
    public void method2() throws RE
    {
    }

    /**
     * @throws E in some cases
     * @throws RE in other cases
     */
    public void method3() throws E, RE
    {
    }

    /**
     * @throws RE in some cases
     * @throws NPE in some other cases
     */
    public <NPE extends NullPointerException> void method4() throws NPE, RE
    {
    }

    public class InnerClass <RuntimeException extends ClassCastException>
    {
        /**
         * @throws E in some case
         * @throws RE in some other cases
         */
        public void method1() throws RuntimeException, RE,
            java.lang.RuntimeException
        {
        }
    }
}
