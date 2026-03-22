/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = ^foo.*$
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 * ignoreMethodNamesRegex = "^foo.*$"
 */
public class InputMissingJavadocMethodIgnoreNameRegex
{
    private void foo() {

    }

    private void foo88() {

    }

    private void foo2() {
        int x = 0;
        int k = x >> 2;
        String s = String.valueOf(k);
        boolean b = false;
    }
}
