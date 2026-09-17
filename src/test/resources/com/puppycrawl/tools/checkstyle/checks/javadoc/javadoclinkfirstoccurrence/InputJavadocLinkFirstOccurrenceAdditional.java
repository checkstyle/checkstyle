/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;
import java.util.List;
import java.util.Map;
class InputJavadocLinkFirstOccurrenceAdditional {
    /**
     * Uses a {@link LocalType}.
     * The {@link java.lang.LocalType} is another reference.
     */
    public void validSamePackageType() { }
    // violation 3 lines below 'java.util.Map.Entry'
    /**
     * Uses a {@link Map.Entry}.
     * The {@link java.util.Map.Entry} is returned.
     */
    public void invalidNestedClass() { }
    /**
     * Uses a {@link String#substring(int)}.
     * Also a {@link String#substring(int, int)}.
     */
    public void validOverloads() { }
    /**
     * Uses a {@link List#add(Object)}.
     * Also a {@link List#add( Object )}.
     */
    public void validWhitespaceTest() { }
    /**
     * Uses a {@link List#add(Object)}.
     * Also a {@link List#add(java.lang.Object)}.
     */
    public void validOrInvalidParameterQualification() { }
    // violation 3 lines below 'String'
    /**
     * Uses a {@link String the String class}.
     * The {@link String another String reference} is returned.
     */
    public void invalidDifferentLabels() { }
    /**
     * Local helper type.
     */
    private static final class LocalType { }
}
