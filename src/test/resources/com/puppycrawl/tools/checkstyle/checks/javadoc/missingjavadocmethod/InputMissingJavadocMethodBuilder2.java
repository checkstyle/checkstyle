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
    private InputMissingJavadocMethodBuilder2 recursive;
    private String[] array;

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

    public InputMissingJavadocMethodBuilder2 misnamed(String string) // violation
    {
        mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setNothing(int number) // ok
    {
        mNumber++;
        return this;
    }

    public String setAndReturn(String string) // violation
    {
        mString = string;
        return string;
    }

    public InputMissingJavadocMethodBuilder2 tooLong(String string) // violation
    {
        mString = string;
        mNumber = 5;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 tooShort(String string) // violation
    {
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setMultiple(String string, int number) // violation
    {
        mString = string;
        mNumber = number;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setRecursiveWithThis(String string) // ok
    {
        this.recursive.mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setRecursive(String string) // ok
    {
        recursive.mString = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setArray(String string) // ok
    {
        array[0] = string;
        return this;
    }

    public InputMissingJavadocMethodBuilder2 setThrows(String string) throws Exception // violation
    {
        this.mString = string;
        return this;
    }
}
