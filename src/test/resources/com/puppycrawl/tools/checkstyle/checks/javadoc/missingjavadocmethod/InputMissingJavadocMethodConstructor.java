/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 */
public class InputMissingJavadocMethodConstructor {
    private int field;
    public InputMissingJavadocMethodConstructor() {} // violation
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
