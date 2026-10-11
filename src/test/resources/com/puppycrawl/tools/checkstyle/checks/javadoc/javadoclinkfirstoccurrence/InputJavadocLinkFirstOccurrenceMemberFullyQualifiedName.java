/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;
import java.util.List;
class InputJavadocLinkFirstOccurrenceMemberFullyQualifiedName {
    // violation 3 lines below 'java.util.List#add(Object)'
    /**
     * Uses a {@link List#add(Object)}.
     * The {@link java.util.List#add(Object)} is returned.
     */
    public boolean invalidMemberFullyQualifiedNameCollision() { return false; }
}
