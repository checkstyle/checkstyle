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

public class InputJavadocMethodInheritDoc
{
    /** {@inheritDoc} */
    InputJavadocMethodInheritDoc() // violation
    {
    }

    /** {@inheritDoc} */
    private void privateMethod() // violation
    {
    }

    /** {@inheritDoc} */
    void packageMethod() // ok
    {
    }

    /** {@inheritDoc} */
    protected void protectedMethod() // ok
    {
    }

    /** {@inheritDoc} */
    public void publicMethod() // ok
    {
    }

    /** {@inheritDoc} */
    private static void privateStaticMethod() // violation
    {
    }

    /** {@inheritDoc} */
    static void packageStaticMethod() // violation
    {
    }

    /** {@inheritDoc} */
    protected static void protectedStaticMethod() // violation
    {
    }

    /** {@inheritDoc} */
    public static void publicStaticMethod() // violation
    {
    }
}
