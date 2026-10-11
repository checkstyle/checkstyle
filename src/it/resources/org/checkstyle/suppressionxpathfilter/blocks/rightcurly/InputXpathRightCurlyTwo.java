package org.checkstyle.suppressionxpathfilter.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

public class InputXpathRightCurlyTwo {
    public void fooMethod() throws IOException {
        try (BufferedReader br1 = new BufferedReader(null)) {
            ; } //warn
    }
}
