/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;
import java.util.List;
class InputJavadocLinkFirstOccurrence {
    /**
     * Uses a {@link String}.
     * The String is returned.
     */
    public String valid1() { return ""; }
    // violation 3 lines below 'String'
    /**
     * Uses a {@link String}.
     * The {@link String} is returned.
     */
    public String invalid1() { return ""; }
    /**
     * Uses a {@link String} and an {@link Object}.
     * The String is converted to an Object.
     */
    public Object valid2(String value) { return value; }
    // violation 4 lines below 'String'
    // violation 3 lines below 'Object'
    /**
     * Uses a {@link String} and an {@link Object}.
     * The {@link String} is converted to an {@link Object}.
     */
    public Object invalid2(String value) { return value; }
    // violation 3 lines below 'String'
    /**
     * Uses a {@link String}.
     * The {@linkplain String} is returned.
     */
    public String invalid3() { return ""; }
    /**
     * Uses a {@link java.util.StringJoiner}.
     * The {@link String} is returned.
     */
    public String valid4() { return ""; }
    /**
     * Uses a {@link String}.
     */
    public void valid3(String value) { }
    /**
     * Uses a {@link String} and a {@link String#length()}.
     */
    public int valid5(String value) { return value.length(); }
    /**
     * Uses {@code String} and {@code Object} class.
     */
    public void valid5b() { }
    /**
     * Uses {@code String} and {@code Object} and {@link String}.
     */
    public void valid6() { }
    /**
     * Method with no links.
     */
    public void valid7() { }
    /**
     * Uses {@link #method()} and {@link String}.
     */
    public void valid8() { }
    // violation 4 lines below 'String'
    // violation 4 lines below 'String'
    /**
     * Uses a {@link String}.
     * Also a {@link String}.
     * And another {@link String}.
     */
    public void invalid4() { }
    // violation 6 lines below 'String'
    // violation 6 lines below 'Object'
    /**
     * Uses {@link String}.
     * Uses {@link Object}.
     * Uses {@link Integer}.
     * Uses {@link String}.
     * Uses {@link Object}.
     */
    public void invalid5() { }
    // violation 3 lines below '#method'
    /**
     * Uses a {@link #method}.
     * Also a {@link #method}.
     */
    public String invalid6() { return ""; }
    // violation 3 lines below 'String#length()'
    /**
     * Uses a {@link String#length()}.
     * Also a {@link String#length()}.
     */
    public String invalid7() { return ""; }
    /**
     * Uses a {@link String#length()} and a {@link String#isEmpty()}.
     */
    public String valid9() { return ""; }
    /**
     * Uses a {@link String} and a {@link java.util.List}.
     * They resolve to different classes, no violation.
     */
    public void validImportDiff() { }
    // violation 3 lines below 'java.lang.String'
    /**
     * Uses a {@link String}.
     * The {@link java.lang.String} is returned.
     */
    public void invalidFullyQualifiedName() { }
    // violation 3 lines below 'java.util.List'
    /**
     * Uses a {@link List}.
     * The {@link java.util.List} is returned.
     */
    public void invalidImport() { }
}
