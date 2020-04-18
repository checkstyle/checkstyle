package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation
import java.util.Map; // violation
import java.util.Set; // violation

public class InputUnusedImportsEnumAndAnnotation {

    @interface Map {}

    @Map
    enum Set {
        List(0);
        Set(int arg){}
    }

    InputUnusedImportsEnumAndAnnotation() {
        Set x = Set.List;
    }

}
