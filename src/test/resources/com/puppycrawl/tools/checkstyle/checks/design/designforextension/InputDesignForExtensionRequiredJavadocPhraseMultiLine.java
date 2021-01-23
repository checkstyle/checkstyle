package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

/* Config:
 * requiredJavadocPhrase = "This[\s\S]*implementation"
 *
 */
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
