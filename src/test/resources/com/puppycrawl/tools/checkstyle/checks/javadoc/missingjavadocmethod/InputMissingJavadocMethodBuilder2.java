/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
allowMissingBuilderJavadoc = true
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodBuilder2
{
    private String mString;
    private int mNumber;

    public InputMissingJavadocMethodBuilder2 withString(final String string) // ok
    {
        mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setString(final String string) // ok
    {
        this.mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 mString(final String mString) // ok
    {
        this.mString = mString;
        return this;
    }

    public void setNumber(final int number) // violation
    {
        mNumber = number;
    }
}
