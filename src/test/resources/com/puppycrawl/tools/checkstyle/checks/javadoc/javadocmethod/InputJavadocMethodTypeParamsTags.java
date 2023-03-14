/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
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
     * @param <BB> The wrong type param // violation
     */
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
     * @param <Z The wrong type param // violation
     */
    public <Z> void unclosedGenericParam() // violation
    {
    }
}

/** @param x */
class Test {} // ok
