/*
MissingJavadocType
scope = protected
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypePublicOnly2Two // violation
{
    private int mSize; // ignore - when not relaxed about Javadoc
    int mLen; // ignore - when not relaxed about Javadoc
    protected int mDeer; // ignore
    public int aFreddo; // ignore

    // ignore - need Javadoc
    private InputMissingJavadocTypePublicOnly2Two(int aA)
    {
    }

    // ignore - need Javadoc when not relaxed
    InputMissingJavadocTypePublicOnly2Two(String aA)
    {
    }

    // ignore - always need javadoc
    protected InputMissingJavadocTypePublicOnly2Two(Object aA)
    {
    }

    // ignore - always need javadoc
    public InputMissingJavadocTypePublicOnly2Two(Class<Object> aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    private void method(int aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    void method(Long aA)
    {
    }

    // ignore - need javadoc
    protected void method(Class<Object> aA)
    {
    }

    // ignore - need javadoc
    public void method(StringBuffer aA)
    {
    }


    /**
     A param tag should not be required here when relaxed about Javadoc.
     Writing a little documentation should not be worse than not
     writing any documentation at all.
     */
    private void method(String aA)
    {
    }

    /**
     This inner class has no author tag, which is OK.
     */
    public class InnerWithoutAuthor
    {

    }

    /** {@inheritDoc} */
    public String toString()
    {
        return super.toString();
    }

    @Deprecated @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
