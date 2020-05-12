package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Set;
import java.util.Map;

public class InputUnusedImportsInner {

    interface Map {} // violation

    class Set {} // violation
}
