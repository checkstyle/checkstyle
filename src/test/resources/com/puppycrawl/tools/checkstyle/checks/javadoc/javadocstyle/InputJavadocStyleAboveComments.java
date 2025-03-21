/*
JavadocStyle
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleAboveComments {

     // violation below, 'First sentence should end with a period'
     /**             Set of all class field names        */
     /*
        field
     */
    public String field;
}
