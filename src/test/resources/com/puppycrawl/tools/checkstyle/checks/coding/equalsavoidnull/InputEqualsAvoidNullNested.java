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
          if (nm.equalsIgnoreCase("applet") || // violation 'left .* of .* equalsIgnoreCase'
              nm.equalsIgnoreCase("object") || // violation 'left .* of .* equalsIgnoreCase'
              nm.equalsIgnoreCase("embed")) { // violation 'left .* of .* equalsIgnoreCase'
            break;
          }
        }
        else {
          String nm = scanIdentifier(in);
          if (nm.equalsIgnoreCase("param")) { // violation 'left .* of .* equalsIgnoreCase'
            ;
          }
          else if (nm.equalsIgnoreCase("applet")) { // violation 'left .* of .* equalsIgnoreCase'
            ;
          }
          else if (nm.equalsIgnoreCase("object")) { // violation 'left .* of .* equalsIgnoreCase'
            ;
          }
          else if (nm.equalsIgnoreCase("embed")) { // violation 'left .* of .* equalsIgnoreCase'
            ;
          }
          else if (nm.equalsIgnoreCase("app")) { // violation 'left .* of .* equalsIgnoreCase'
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
