/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = true
allowMissingBuilderJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodBuilder
{
    private String mString;
    private int mNumber;

    public InputMissingJavadocMethodBuilder withString(final String string) // violation
    {
        mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder setString(final String string) // violation
    {
        this.mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder mString(final String mString) // violation
    {
        this.mString = mString;
        return this;
    }

    public void setNumber(final int number) // ok
    {
        mNumber = number;
    }
}
