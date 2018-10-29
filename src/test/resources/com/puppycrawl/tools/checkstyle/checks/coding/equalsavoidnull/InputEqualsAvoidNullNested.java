package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

import java.io.IOException;
import java.io.Reader;

public class InputEqualsAvoidNullNested {
    public void foo(Reader in) throws IOException {
        int c;
        while(true) {
            c = in.read();
            if (c == -1)
                break;

            if (c == '<') {
                c = in.read();
                if (c == '/') {
                    String nm = in.toString();
                    if (nm.equalsIgnoreCase("applet") ||
                            nm.equalsIgnoreCase("object") ||
                            nm.equalsIgnoreCase("embed")) {
                        break;
                    }
                }
            }
        }
    }
}
