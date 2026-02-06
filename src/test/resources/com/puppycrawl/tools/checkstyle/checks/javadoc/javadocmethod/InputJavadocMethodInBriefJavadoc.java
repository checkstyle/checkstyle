/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
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

    @SuppressWarnings("GenericsArray")/** returns the tokens that this check is interested in. */
    long getAccessTime2() {
        return 0;
    }
}
