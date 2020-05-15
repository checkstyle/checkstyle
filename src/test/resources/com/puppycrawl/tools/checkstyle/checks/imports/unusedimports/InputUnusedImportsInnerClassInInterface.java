package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation
import java.util.Set; // violation

public class InputUnusedImportsInnerClassInInterface {
    interface List {
        class Set {}
    }
}
