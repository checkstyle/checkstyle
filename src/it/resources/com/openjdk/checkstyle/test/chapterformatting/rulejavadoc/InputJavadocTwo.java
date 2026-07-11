package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header is missing*'

public class InputJavadocTwo {

    // violation below 'Javadoc content should start from the next line.'
    /** This comment causes a violation because it starts from the first line
     * and spans several lines.
     */
    private int field1;

    /**
     * This comment is OK because it starts from the second line.
     */
    private int field2;

    /** This comment is OK because it is on the single-line. */
    private int field3;
}
