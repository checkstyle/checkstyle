/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;
import java.util.*;
class InputJavadocLinkFirstOccurrenceStarImport {
    // violation 3 lines below 'java.util.List'
    /**
     * Uses a {@link List}.
     * The {@link java.util.List} is returned.
     */
    public void invalidStarImport() { }
}
