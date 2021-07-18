/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodGenerics <E extends java.lang.Exception,
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
    public void method2() throws RE // violation
    {
    }

    /**
     * @throws E in some cases
     * @throws RE in other cases
     */
    public void method3() throws E, RE // ok
    {
    }

    /**
     * @throws RE in some cases
     * @throws NPE in some other cases
     */
    public <NPE extends NullPointerException> void method4() throws NPE, RE // violation
    {
    }

    public class InnerClass <RuntimeException extends ClassCastException>
    {
        /**
         * @throws E in some case
         * @throws RE in some other cases
         */
        public void method1() throws RuntimeException, RE, // violation
            java.lang.RuntimeException // violation
        {
        }
    }

    /**
     * @param <T> some parameter
     * @param <E2> some exception parameter
     */
    public interface InnerInterface<T, E2 extends Throwable> { // ok
        /**
         * Some javadoc.
         * @param t a parameter
         * @throws E2 in some case.
         * @return some string
         */
        public abstract String doStuff(T t) throws E2; // ok
    }

    /**
     * @param <P> some parameter
     */
    public interface InvalidParameterInJavadoc<T> {} // ok
}

