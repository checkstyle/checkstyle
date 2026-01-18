/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.Map;

public class InputUnusedImportsAnnotation {

    /**
     * {@link List}
     * {@link Map}
     * @return true.
     */
    @Annotation
    public boolean isTrue() {
        return true;
    }
}
