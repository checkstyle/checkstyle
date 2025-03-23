/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodAboveComments {

    public InputMissingJavadocMethodAboveComments() { } // violation, 'Missing a Javadoc comment.'

    /**
     *
     */
    /*
     */
    public InputMissingJavadocMethodAboveComments(String p1) {

    }


    /**
     * A Javadoc comment.
     * @return 0
     */
    public int method() /* comment */ { return 0;}

    public int method2() { // violation, 'Missing a Javadoc comment.'
        return 0;
    }
}
