package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.Set;

public class InputUnusedImportsInnerClassInInterface {
    interface List { // violation
        class Set {} // violation
    }
}
