/*
AnonInnerLength
max = 5


*/

package org.checkstyle.suppressionxpathfilter.sizes.anoninnerlength;

public class InputXpathAnonInnerLengthField {
    // violation below 'Anonymous inner class length is 9 lines (max allowed is 5).'
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int x = 0;
            x++;
            x++;
            x++;
        }
    };
}
