/*
com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = true
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodSetterGetter4 {

    private int mNumber;

    public void setNumber(final int number) throws Exception {
        mNumber = number; // violation above 'Missing a Javadoc comment'
    }

    public int Cost1() // violation 'Missing a Javadoc comment'
    {
        return 666;
    }

    public int getCost1(int forMe) // violation 'Missing a Javadoc comment'
    {
        return 666;
    }
}
