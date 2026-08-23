/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodConstructor {
    private int field;
    public InputJavadocMethodConstructor() {}
    public InputJavadocMethodConstructor(Runnable p1) { this.field = 0; }
    // violation 2 lines below 'Expected @param tag for 'p1''
    /** */
    public InputJavadocMethodConstructor(String p1) { this.field = 0; }
    // violation 2 lines below 'Expected @param tag for 'p1''
    /** Test. */
    public InputJavadocMethodConstructor(Integer p1) { this.field = 0; }
    /** Test.
     * @param p1 */
    public InputJavadocMethodConstructor(Long p1) { this.field = 0; }
    /** Test.
     * @param p1 Test. */
    public InputJavadocMethodConstructor(Short p1) { this.field = 0; }
}
