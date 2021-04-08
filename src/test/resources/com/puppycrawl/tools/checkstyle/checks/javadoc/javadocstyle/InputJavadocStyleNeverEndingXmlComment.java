package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/*
 * Config:
 * scope = private
 * excludeScope = null
 * checkFirstSentence = true
 * endOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)"
 * checkEmptyJavadoc = false
 * checkHtml = true
 */
public class InputJavadocStyleNeverEndingXmlComment {
    /** DTD: <!--. */ // ok
    public int i;
}
