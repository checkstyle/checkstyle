/*
JavadocMethod
validateThrows = true
allowedAnnotations = (default)Override
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAnnotationPositions {

    // Test 1: Modifier (public) before annotation - tests getNextSibling() iteration
    // in findFirstAnnotationLine. Without getNextSibling(), the loop would stop at 'public'
    // and never find the @Deprecated annotation.
    /**
     * Javadoc before modifier and annotation.
     * @param x the value
     */
    public @Deprecated void methodWithModifierBeforeAnnotation(int x) {
    }

    // Test 2: Javadoc after annotation should be ignored - if conditional is always true,
    // this javadoc would incorrectly be picked up
    @Deprecated
    /**
     * This javadoc is after the annotation and should be ignored.
     * @param y the value
     */
    public void methodWithJavadocAfterAnnotation(int y) {
    }

    // Test 3: Multiple annotations with first child being a modifier
    // This tests iteration through siblings to find the first annotation
    /**
     * Javadoc before multiple annotations.
     * @param z the value
     */
    public @Deprecated @SuppressWarnings("unused") void methodWithVar(int z) {
    }

    // Test 4: No annotations - javadoc should be found normally
    /**
     * Javadoc for method without annotations.
     * @param a the value
     */
    public void methodWithoutAnnotation(int a) {
    }

    // Test 5: Private modifier before annotation - another getNextSibling test
    /**
     * Private method with annotation after modifier.
     * @param c the value
     */
    private @Deprecated void privateMethodWithAnnotation(int c) {
    }

    // Test 6: Protected modifier before annotation
    /**
     * Protected method with annotation after modifier.
     * @param d the value
     */
    protected @Deprecated void protectedMethodWithAnnotation(int d) {
    }

    // Test 7: Static before annotation
    /**
     * Static method with annotation after modifier.
     * @param e the value
     */
    public static @Deprecated void staticMethodWithAnnotation(int e) {
    }

    // Test 8: Final before annotation
    /**
     * Final method with annotation after modifier.
     * @param f the value
     */
    public final @Deprecated void finalMethodWithAnnotation(int f) {
    }

    // Test 9: Annotation first (no modifier before) - baseline test
    /**
     * Javadoc before annotation only.
     * @param g the value
     */
    @Deprecated
    public void methodWithAnnotationFirst(int g) {
    }
    /** javadoc */ @Deprecated
    public void method(int x) {}

    /** @param x used for printing */ @Deprecated
    public void method2(int x) {}

    /**
     * Test case where public modifier comes before annotation.
     */ public void  method3(int x) {}
}
