/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)JAVADOC_CONTENT

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;

class InputJavadocLinkFirstOccurrence {

    /**
     * Uses a {@link String}.
     * The String is returned.
     */
    public String valid1() {
        return "";
    }

    /**
     * Uses a {@link String}.
     * The {@link String} is returned.
     */
    public String invalid1() {
        return ""; // violation 3 lines above 'String'
    }

    /**
     * Uses a {@link String} and an {@link Object}.
     * The String is converted to an Object.
     */
    public Object valid2(String value) {
        return value;
    }

    /**
     * Uses a {@link String} and an {@link Object}.
     * The {@link String} is converted to an {@link Object}.
     */
    public Object invalid2(String value) {
        return value; // 2 violations 3 lines above:
        // 'String'
        // 'Object'
    }

    /**
     * Uses a {@link String}.
     * The {@linkplain String} is returned.
     */
    public String invalid3() {
        return ""; // violation 3 lines above 'String'
    }

    /**
     * Uses a {@link java.util.String}.
     * The {@link String} is returned.
     */
    public String invalid4() {
        return ""; // violation 3 lines above 'String'
    }

    /**
     * Uses a {@link String}.
     */
    public void valid3(String value) { }

    /**
     * Uses a {@link String} and a {@link String#length()}.
     */
    public int invalid5(String value) {
        return value.length(); // violation 3 lines above 'String'
    }

    /**
     * Uses {@code String} class.
     */
    public void valid5() { }

    /**
     * Method with no links.
     */
    public void valid6() { }

    /**
     * Uses {@link #method()} and {@link String}.
     */
    public void valid7() { }

    /**
     * Uses a {@link String}.
     * Also a {@link String}.
     * And another {@link String}.
     */
    public void invalid6() { } // violation 3 lines above 'String'
    // violation 3 lines above 'String'

    /**
     * Uses {@link String}.
     * Uses {@link Object}.
     * Uses {@link Integer}.
     * Uses {@link String}.
     * Uses {@link Object}.
     */
    public void invalid7() { } // violation 3 lines above 'String'
    // violation 3 lines above 'Object'

    /**
     * Uses {@link String} in <p>{@link String}</p>.
     */
    public void invalid8() { } // violation 2 lines above 'String'

}
