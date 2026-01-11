/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.net.ResponseCache;
import java.util.List;

public class InputUnusedImportsWithNewlinesInsideTags {

    /**
     * This javadoc demonstrates issue #2840.
     * <p/>
     * An inline tag references {@link ResponseCache#getDefault
     * ResponseCache.getDefault} and is split across two lines.
     */
    public static void linkClassAndDescriptionSplitAcrossTwoLines() {
        // do nothing
    }

    /**
     * This javadoc demonstrates issue #359.
     * <p/>
     * An inline tag references {@link
     * List} but the class name is on
     * a different line than the tag name.
     */
    public static void linkAndClassnameSplitAcrossTwoLines() {
        // do nothing
    }
}
