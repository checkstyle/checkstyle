package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 *
 * @param <A> A type param
 * @param <B1> Another type param
 * @param <D123> The wrong type param
 * @author Nobody
 * @version 1.0
 */
public class InputJavadocMethodTypeParamsTags<A,B1,C456 extends Comparable> // ok
{
    /**
     * Some explanation.
     * @param <X> A type param
     * @param <Y1> Another type param
     * @return a string
     */
    public <X, Y1> String doSomething()
    {
        return null;
    }

    /**
     * Some explanation.
     * @param <BB> The wrong type param
     */ // violation above line
    public <Z> void doSomethingElse() // violation
    {
    }

    /**
     * Some explanation.
     * @param aAnEl A parameter
     * @param <L> A type parameter
     */
    public <L> void doSomethingElse2(L aAnEl) // ok
    {
    }

    /**
     * Example inner class.
     * @param <A> documented parameter
     * @param <C> extra parameter
     */

    public static class InnerClass<A,B> // ok
    {
    }

    /**
     * Some explanation.
     * @param <Z The wrong type param
     */ // violation above line
    public <Z> void unclosedGenericParam()
    {
    }
}

/** @param x */
class Test {} // ok
