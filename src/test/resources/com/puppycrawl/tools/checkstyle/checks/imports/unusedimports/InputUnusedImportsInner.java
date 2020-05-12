package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Set;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsInner;

public class InputUnusedImportsInner {

    interface List {
        class Set {}
    }

    interface Map {}

    class Set {}
}
