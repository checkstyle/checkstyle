package org.checkstyle.suppressionxpathfilter.javadoc.javadoclinkfirstoccurrence;

public class InputXpathJavadocLinkFirstOccurrenceThree {

    /**
     * Uses a {@link String} and a {@link String#length()}.
     */
    public int invalid(String value) {
        return value.length();
    }

}
