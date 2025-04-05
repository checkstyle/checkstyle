/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodInheritDoc
{
    /** {@inheritDoc} */
    InputJavadocMethodInheritDoc() // violation 'Invalid use of the '@inheritDoc' tag.'
    {
    }

    /** {@inheritDoc} */
    private void privateMethod() // violation 'Invalid use of the '@inheritDoc' tag.'
    {
    }

    /** {@inheritDoc} */
    void packageMethod()
    {
    }

    /** {@inheritDoc} */
    protected void protectedMethod()
    {
    }

    /** {@inheritDoc} */
    public void publicMethod()
    {
    }

    /** {@inheritDoc} */
    private static void privateStaticMethod() // violation 'Invalid use of the '@inheritDoc' tag.'
    {
    }

    /** {@inheritDoc} */
    static void packageStaticMethod() // violation 'Invalid use of the '@inheritDoc' tag.'
    {
    }

    /** {@inheritDoc} */
    // violation below 'Invalid use of the '@inheritDoc' tag.'
    protected static void protectedStaticMethod()
    {
    }

    /** {@inheritDoc} */
    public static void publicStaticMethod() // violation 'Invalid use of the '@inheritDoc' tag.'
    {
    }
}
