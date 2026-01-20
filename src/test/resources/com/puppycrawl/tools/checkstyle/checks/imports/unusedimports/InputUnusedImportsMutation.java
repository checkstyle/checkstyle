/*
UnusedImports
processJavadoc = (default)true

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputUnusedImportsMutation {

    /* @see List */
    public boolean isTrue() {
        return true;
    }
}
