/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = This[\\s\\S]*implementation


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionRequiredJavadocPhraseMultiLine {
    /**
     * This
     * implementation ..
     */
    public int foo1(int a, int b) {
        return a * b;
    }

    /**
     * This method can safely be overridden.
     */
    public int foo2(int a, int b) {  // violation
        return a + b;
    }
}
