/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF
message.javadoc.return.expected = @return tag should be present and have description :)
message.javadoc.expectedTag = Expected {0} tag for ''{1}'' :)
message.javadoc.unusedTag = Unused {0} tag for ''{1}'' :)

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import org.apache.tools.ant.types.selectors.SelectSelector;
import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.Test;

public class InputJavadocMethodInBriefJavadoc {

    /* Methods below all add specific selectors */

    /**
     * add a "Select" selector entry on the selector list
     *
     * @param selector the selector to add
     */
    void addSelector(SelectSelector selector) {}

    /* package */ String[] getTokens() {
        return new String[0];
    }

    @SuppressWarnings("GenericsArray")
    /** returns the tokens that this check is interested in. */
    long getAccessTime() {
        return 0;
    }

    /** returns the tokens that this check is interested in. */
    // violation below '@return tag should be present and have description.'
    @Deprecated
    long getAccessTime4() {
        return 0;
    }

    @SuppressWarnings("GenericsArray")/** returns the tokens that this check is interested in. */
    long getAccessTime2() {
        return 0;
    }

    @SuppressWarnings("GenericsArray")
    @Time
    /**
     * returns the tokens that this check is interested in.
     */
    @Deprecated
    long getAccessTime3() {
        return 0;
    }
    @Test
    /**
     * returns the tokens that this check is interested in.
     */
    public void getAccessTime5() throws Throwable {
        assert (true);
    }

    /**
     * Create the S3NodeRepository as a separate bean so it's lifecycle is managed by spring
     */
    // violation below 'Expected @throws tag for 'Throwable' :)'
    public void getAccessTime6() throws Throwable {
        assert (true);
    }

    @Test
   /** Create the S3NodeRepository as a separate bean so it's lifecycle is managed by spring */
    public void getAccessTime7() throws Throwable {
        assert (true);
    }

    @Test
    @Deprecated
   /** Create the S3NodeRepository as a separate bean so it's lifecycle is managed by spring */
    public void getAccessTime8() throws Throwable {
        assert (true);
    }

    /**
     * Test case where public modifier comes before annotation.
     * This tests that findFirstAnnotation properly iterates through siblings.
     * No violation - javadoc is not found because it's outside MODIFIERS node.
     */
    public @Deprecated void getAccessTime9() throws Throwable {
        assert (true);
    }

    /**
     * Test case where public modifier comes before annotation.
     * This tests that findFirstAnnotation properly iterates through siblings.
     * No violation - javadoc is not found because it's outside MODIFIERS node.
     */
    // violation below 'Expected @throws tag for 'Throwable' :)'
    public void getAccessTime10() throws Throwable {
        assert (true);
    }
}
