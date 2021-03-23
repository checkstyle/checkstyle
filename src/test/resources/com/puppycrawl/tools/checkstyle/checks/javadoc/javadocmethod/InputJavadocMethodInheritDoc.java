package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
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
