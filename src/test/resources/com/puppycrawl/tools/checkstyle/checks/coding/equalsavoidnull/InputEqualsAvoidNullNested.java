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
                    if (nm.equalsIgnoreCase("applet") || // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                            nm.equalsIgnoreCase("object") || // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                            nm.equalsIgnoreCase("embed")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                        break;
                    }
                }
                else {
                    String nm = scanIdentifier(in);
                    if (nm.equalsIgnoreCase("param")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                        ;
                    }
                    else if (nm.equalsIgnoreCase("applet")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                        ;
                    }
                    else if (nm.equalsIgnoreCase("object")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                        ;
                    }
                    else if (nm.equalsIgnoreCase("embed")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
                        ;
                    }
                    else if (nm.equalsIgnoreCase("app")) { // violation 'String literal expressions should be on the left side of an equalsIgnoreCase comparison.'
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
