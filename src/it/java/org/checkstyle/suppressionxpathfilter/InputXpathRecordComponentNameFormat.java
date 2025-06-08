package org.checkstyle.suppressionxpathfilter;

/* Config:
 * format = ^[a-z][a-zA-Z0-9]*$
 */
public class InputXpathRecordComponentNameFormat {
    public record MyRecord(int _underscoreValue, // ok
                           int otherValue) { } // warn
}
