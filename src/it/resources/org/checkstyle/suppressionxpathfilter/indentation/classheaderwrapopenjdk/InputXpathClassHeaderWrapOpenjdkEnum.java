/*
ClassHeaderWrapOpenjdk


*/
package org.checkstyle.suppressionxpathfilter.indentation.classheaderwrapopenjdk;

public class InputXpathClassHeaderWrapOpenjdkEnum {
    enum Nested implements Comparable<Nested>,
            Runnable {
        // violation 2 lines above """The 'implements' clause should be on a new line when
        // the class header is wrapped."""
        INSTANCE;

        @Override
        public void run() {
        }
    }
}
