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

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtil9 {

    private int mNumber;

    public void setNumber(final int number) throws Exception {
        mNumber = number; // violation above 'Missing a Javadoc comment'
    }

}
