package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodConstructor {
    private int field;
    public InputMissingJavadocMethodConstructor() {}
    public InputMissingJavadocMethodConstructor(Runnable p1) { this.field = 0; }
    /** */
    public InputMissingJavadocMethodConstructor(String p1) { this.field = 0; }
    /** Test. */
    public InputMissingJavadocMethodConstructor(Integer p1) { this.field = 0; }
    /** Test.
     * @param p1 */
    public InputMissingJavadocMethodConstructor(Long p1) { this.field = 0; }
    /** Test.
     * @param p1 Test. */
    public InputMissingJavadocMethodConstructor(Short p1) { this.field = 0; }
}
