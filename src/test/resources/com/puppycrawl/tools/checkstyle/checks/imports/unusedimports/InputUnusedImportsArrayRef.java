/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList; // violation 'Unused import - java.util.ArrayList.'

public class InputUnusedImportsArrayRef {

    private static final Set<String> FOO;
    static {
        FOO = new HashSet<>();

        FOO.add( HashMap[].class.getName() );
        FOO.add( java.util.ArrayList[].class.getName() );
    }
}
