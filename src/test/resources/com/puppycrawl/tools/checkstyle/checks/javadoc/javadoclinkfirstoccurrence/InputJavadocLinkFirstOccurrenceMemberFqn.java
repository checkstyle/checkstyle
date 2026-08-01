/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)LINK_INLINE_TAG, LINKPLAIN_INLINE_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;
import java.util.List;
class InputJavadocLinkFirstOccurrenceMemberFqn {
    // violation 3 lines below 'java.util.List#add(Object)'
    /**
     * Uses a {@link List#add(Object)}.
     * The {@link java.util.List#add(Object)} is returned.
     */
    public boolean invalidMemberFqnCollision() { return false; }
}
