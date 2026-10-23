/**
MultilineCommentLeadingAsteriskPresence


*/

package org.checkstyle.suppressionxpathfilter.multilinecommentleadingasteriskpresence;

public class InputXpathMultilineCommentLeadingAsteriskPresenceThree {
    void method() {
        // violation below 'Multiline comment line(s) 13 should start with leading asterisk.'
        /*  // warn
           line6
         */
    }
}
