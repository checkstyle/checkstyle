/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for class-level Javadoc scenarios.
 *
 * <p>This class demonstrates various scenarios related to class-level
 * documentation that the check should handle properly.
 *
 * @author Test Author
 * @version 1.0
 * @since 1.0
 */
public class InputJavadocUtilizingTrailingSpaceClassLevel {

    /**
     * A constant value used throughout the application for configuration
     * purposes and default behavior settings.
     */
    public static final int CONSTANT = 42;

    // violation 2 lines below 'Line is smaller than 80 characters (found 31).'
    /**
     * Field documentation that
     * is too short here.
     */
    private String shortFieldDoc;

    /**
     * Field with proper documentation that uses the horizontal space
     * efficiently across multiple lines.
     */
    private int properFieldDoc;

    /** Single-line field doc. */
    private boolean singleLineField;

    /**
     * Constructor documentation.
     */
    public InputJavadocUtilizingTrailingSpaceClassLevel() { }

    /**
     * Overloaded constructor with parameters.
     *
     * @param value initialization value for the instance
     */
    public InputJavadocUtilizingTrailingSpaceClassLevel(int value) {
        this.properFieldDoc = value;
    }
}

/**
 * Inner static class documentation.
 */
class InnerStaticClass {

    /** Constant in inner class. */
    public static final String INNER_CONSTANT = "value";
}

/**
 * Interface documentation with proper formatting across lines.
 */
interface SampleInterface {

    /** Method in interface. */
    void interfaceMethod();
}

/**
 * Enum documentation.
 */
enum SampleEnum {
    /** First value. */
    FIRST,
    /** Second value. */
    SECOND,
    /**
     * Third value with longer documentation that spans properly.
     */
    THIRD
}
