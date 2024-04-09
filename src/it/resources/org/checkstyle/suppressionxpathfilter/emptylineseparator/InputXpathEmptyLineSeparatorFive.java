package org.checkstyle.suppressionxpathfilter.emptylineseparator;

import java.io.IOException;

public class InputXpathEmptyLineSeparatorFive {

    int foo1() throws Exception {
        int a = 1;
        int b = 2;
        try {
            if (a != b) {
                throw new IOException();
            }
            // warn


        } catch(IOException e) {
            return 1;
        }
        return 0;
    }
}
