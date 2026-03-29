package org.checkstyle.suppressionxpathfilter.sizes.anoninnerlength;

public class InputXpathAnonInnerLengthField {
    Runnable runnable = new Runnable() { // warn
        @Override
        public void run() {
            int x = 0;
            x++;
            x++;
            x++;
        }
    };
}
