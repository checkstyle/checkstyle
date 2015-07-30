package com.puppycrawl.tools.checkstyle.javadoc;

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

    /**
     * @param <T> some parameter
     * @param <E2> some exception parameter
     */
    public interface InnerInterface<T, E2 extends Throwable> {
        /**
         * Some javadoc.
         * @param t a parameter
         * @throws E2 in some case.
         * @return some string
         */
        public abstract String doStuff(T t) throws E2;
    }

    /**
     * @param <P> some parameter
     */
    public interface InvalidParameterInJavadoc<T> {}
}

