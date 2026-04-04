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
    public int foo2(int a, int b) {  // violation 'Class 'InputDesignForExtensionRequiredJavadocPhraseMultiLine' looks like designed for extension (can be subclassed), but the method 'foo2' does not have javadoc that explains how to do that safely. If class is not designed for extension consider making the class 'InputDesignForExtensionRequiredJavadocPhraseMultiLine' final or making the method 'foo2' static/final/abstract/empty, or adding allowed annotation for the method.'
        return a + b;
    }
}
