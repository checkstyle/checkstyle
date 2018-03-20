package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.util.Calendar;
import java.util.Date;

public class InputImportControlDisallowClassOfAllowPackage {
    public void test() {
        new Date();
        Calendar.getInstance();
    }
}
