package com.puppycrawl.tools.checkstyle;

/**
 * Some explanation.
 * @param <A> A type param
 * @param <B> Another type param
 * @param <D> The wrong type param
 * @author Nobody
 * @version 1.0
 */
public class InputTypeParamsTags<A,B,C extends Comparable>
{
    /**
     * Some explanation.
     * @param <X> A type param
     * @param <Y> Another type param
     * @return a string
     */
    public <X, Y> String doSomething()
    {
        return null;
    }

    /**
     * Some explanation.
     * @param <BB> The wrong type param
     */
    public <Z> void doSomethingElse()
    {
    }

    /**
     * Some explanation.
     * @param aAnEl A parameter
     * @param <L> A type parameter
     */
    public <L> void doSomethingElse2(L aAnEl)
    {
    }
}
