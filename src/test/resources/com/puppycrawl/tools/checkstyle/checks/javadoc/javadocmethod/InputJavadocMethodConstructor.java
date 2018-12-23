package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodConstructor {
    private int field;
    public InputJavadocMethodConstructor() {}
    public InputJavadocMethodConstructor(Runnable p1) { this.field = 0; }
    /** */
    public InputJavadocMethodConstructor(String p1) { this.field = 0; }
    /** Test. */
    public InputJavadocMethodConstructor(Integer p1) { this.field = 0; }
    /** Test.
     * @param p1 */
    public InputJavadocMethodConstructor(Long p1) { this.field = 0; }
    /** Test.
     * @param p1 Test. */
    public InputJavadocMethodConstructor(Short p1) { this.field = 0; }
}
