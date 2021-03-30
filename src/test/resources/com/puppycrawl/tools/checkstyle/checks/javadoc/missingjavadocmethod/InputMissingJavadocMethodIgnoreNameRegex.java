package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 * ignoreMethodNamesRegex = "^foo.*$"
 */
public class InputMissingJavadocMethodIgnoreNameRegex
{
    private void foo() { // ok

    }

    private void foo88() { // ok

    }

    private void foo2() { // ok
        int x = 0;
        int k = x >> 2;
        String s = String.valueOf(k);
        boolean b = false;
    }
}
