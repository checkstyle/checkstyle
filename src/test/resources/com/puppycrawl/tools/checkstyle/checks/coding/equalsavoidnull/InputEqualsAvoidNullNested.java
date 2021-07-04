/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

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
                    if (nm.equalsIgnoreCase("applet") || // violation
                            nm.equalsIgnoreCase("object") || // violation
                            nm.equalsIgnoreCase("embed")) { // violation
                        break;
                    }
                }
                else {
                    String nm = scanIdentifier(in);
                    if (nm.equalsIgnoreCase("param")) { // violation
                        ;
                    }
                    else if (nm.equalsIgnoreCase("applet")) { // violation
                        ;
                    }
                    else if (nm.equalsIgnoreCase("object")) { // violation
                        ;
                    }
                    else if (nm.equalsIgnoreCase("embed")) { // violation
                        ;
                    }
                    else if (nm.equalsIgnoreCase("app")) { // violation
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
