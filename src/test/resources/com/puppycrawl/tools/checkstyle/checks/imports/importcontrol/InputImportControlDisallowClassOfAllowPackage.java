/*
ImportControl
file = (file)InputImportControlDisallowClassOfAllowPackage.xml
path = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.util.Calendar;
import java.util.Date; // violation

public class InputImportControlDisallowClassOfAllowPackage {
    public void test() {
        new Date();
        Calendar.getInstance();
    }
}
