/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = (default)public
excludeScope = (default)null
allowMissingPropertyJavadoc = true
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodSetterGetterBlockComment {
    private int x;

    public int getFoo() {
        // line comment
        return x;
    }

    public int getBar() {
        /* block comment */
        return x;
    }

    public boolean isSomething() {
        /* block comment */
        return false;
    }

    public void setX(final int value) {
        /* block comment */
        x = value;
    }

    public int getMultipleBlockComments() {
        /* first block comment */
        /* second block comment */
        return x;
    }

    public int getWithLineAndBlockComment() {
        // line comment
        /* block comment */
        return x;
    }

    public int getWithBlockAndLineComment() {
        /* block comment */
        // line comment
        return x;
    }

    public int getNumberThree() /* block comment */
    {
        return x;
    }

    public void setNumberThree(final int value) /* block comment */
    {
        x = value;
    }

    public void setY(final int value) {
        x = value;
        /* block comment */
    }

    public int getWithCalculation() { // violation 'Missing a Javadoc comment.'
        /* block comment */
        final int y = x + 1;
        return y;
    }
}
