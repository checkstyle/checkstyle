package org.checkstyle.suppressionxpathfilter.indentation.classheaderwrapopenjdk;

public class InputXpathClassHeaderWrapOpenjdkEnum {
    enum Nested implements Comparable<Nested>, // warn
            Runnable {
        INSTANCE;

        @Override
        public void run() {
        }
    }
}
