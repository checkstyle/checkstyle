/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = true
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Some explanation.
 * @param <A> A type param
 * @param <B1> Another type param
 * @param <D123> The wrong type param   // violation
 * @author Nobody
 * @version 1.0
 */
public class InputJavadocTypeTypeParamsTags<A,B1,C456 extends Comparable>
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

    /**
     * Example inner class.
     * @param <A> documented parameter
     * @param <C> extra parameter  // violation
     */

    public static class InnerClass<A,B>
    {
    }

    /**
     * Some explanation.
     * @param <Z The wrong type param
     */
    public <Z> void unclosedGenericParam()
    {
    }
}

/** @param x */ // violation
class Test {}
