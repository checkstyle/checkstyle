package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

import java.io.IOException;
import java.io.Reader;
// case copied from sun.applet.AppletViewer in openjdk
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
                else {
                    String nm = scanIdentifier(in);
                    if (nm.equalsIgnoreCase("param")) {
                        ;
                    }
                    else if (nm.equalsIgnoreCase("applet")) {
                        ;
                    }
                    else if (nm.equalsIgnoreCase("object")) {
                        ;
                    }
                    else if (nm.equalsIgnoreCase("embed")) {
                        ;
                    }
                    else if (nm.equalsIgnoreCase("app")) {
                        ;
                    }
                }
            }
        }
    }

    public static String scanIdentifier(Reader in) throws IOException {
        return null;
    }
}
