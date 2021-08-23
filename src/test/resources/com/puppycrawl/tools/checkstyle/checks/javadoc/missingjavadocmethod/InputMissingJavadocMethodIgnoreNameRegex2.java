/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = regexThatDoesNotMatch
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 * ignoreMethodNamesRegex = "regexThatDoesNotMatch"
 */
public class InputMissingJavadocMethodIgnoreNameRegex2
{
    private void foo() { // violation

    }

    private void foo88() { // violation

    }

    private void foo2() { // violation
        int x = 0;
        int k = x >> 2;
        String s = String.valueOf(k);
        boolean b = false;
    }
}
