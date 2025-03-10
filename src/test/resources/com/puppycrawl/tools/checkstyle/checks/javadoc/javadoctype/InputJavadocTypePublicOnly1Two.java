/*
JavadocType
scope = protected
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocTypePublicOnly1Two {
     private InputJavadocTypePublicOnly1Two(int aA)
    {
    }


    InputJavadocTypePublicOnly1Two(String aA)
    {
    }


    protected InputJavadocTypePublicOnly1Two(Object aA)
    {
    }


    public InputJavadocTypePublicOnly1Two(Class<Object> aA)
    {
    }


    private void method(int aA)
    {
    }


    void method(Long aA)
    {
    }


    protected void method(Class<Object> aA)
    {
    }


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
