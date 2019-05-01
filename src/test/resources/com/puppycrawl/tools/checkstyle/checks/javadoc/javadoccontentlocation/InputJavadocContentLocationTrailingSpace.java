package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

/**
 * config:  default
 * location="SECOND_LINE"
 */
public interface InputJavadocContentLocationTrailingSpace {

    /**  
     * ^^ There is a trailing space in the first line // OK
     */
    void space();

    /**********************
     * Trailing asterisks in the first line // OK
     *
     **********************/
    void asterisk();

    /** ******
     * There is a space, then leading asterisks in the first line // OK
     */
    void spaceAsterisk();

    /**********************              
     * Trailing asterisks, then spaces ^^ in the first line // OK
     *
     **********************/
    void asteriskSpace();

    /**      ***************************************            
     * There is a spaces, then leading asterisks, then spaces ^^ in the first line // OK
     */
    void spaceAsteriskSpace();

    /*** **
     * There is an extra asterisk after the Jvadoc start,
     * then a space, then leading asterisks in the first line // OK
     * The generated javadoc will be "There is an extra ..."
     */
    void asteriskSpaceAsterisk();

    /**   *  ***
     * There is a space, then a leading asterisk, then a space,
     * then asterisks in the first line // OK
     * The generated javadoc will be "*** There is a space ..."
     * Weird corner case, we do not report a violation here due to the parser limitation:
     * for /*** and /**  * the parsed tree will contains the same tokens:
     * JAVADOC_START and LEADING_ASTERISK
     */
    void spaceAsteriskSpaceAsterisk();

    /*** **  
     * There is an extra asterisk after the Jvadoc start,
     * then a space, then leading asterisks, then a space in the first line // OK
     * The generated javadoc will be "There is an extra ..."
     */
    void asteriskSpaceAsteriskSpace();

    /**   *  ***   
     * There is a space, then a leading asterisk, then a space again,
     * then asterisks, then third space in the first line // OK
     * The generated javadoc will be "*** There is a space ..."
     * Weird corner case, we do not report a violation here due to the parser limitation:
     * for /*** and /**  * the parsed tree will contains the same tokens:
     * JAVADOC_START and LEADING_ASTERISK
     */
    void spaceAsteriskSpaceAsteriskSpace();

    /*** **  **
     * There is an extra asterisk after the Jvadoc start, then a space, 
     * then leading asterisks, then a space, then third asterisk in the first line // violation
     * The generated javadoc will be "** There is an extra ..."
     */
    void asteriskSpaceAsteriskSpaceAsterisk();

    /**
     * 
     **  
     ***
     */
    void blankLinesOnly(); // OK
}
