package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
public class InputJavadocMethodConstructor {
    private int field; // ok
    public InputJavadocMethodConstructor() {} // ok
    public InputJavadocMethodConstructor(Runnable p1) { this.field = 0; } // ok
    /** */
    public InputJavadocMethodConstructor(String p1) { this.field = 0; } // violation
    /** Test. */
    public InputJavadocMethodConstructor(Integer p1) { this.field = 0; } // violation
    /** Test.
     * @param p1 */
    public InputJavadocMethodConstructor(Long p1) { this.field = 0; } // ok
    /** Test.
     * @param p1 Test. */
    public InputJavadocMethodConstructor(Short p1) { this.field = 0; } // ok
}
