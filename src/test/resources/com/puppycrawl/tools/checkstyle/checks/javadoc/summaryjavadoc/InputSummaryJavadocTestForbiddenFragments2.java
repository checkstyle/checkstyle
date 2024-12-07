/*
SummaryJavadoc
violateExecutionOnNonTightHtml = true
forbiddenSummaryFragments = .*
period =

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocTestForbiddenFragments2 {

    /**
     * Returns {@link String} instance for the given module name.
     * This implementation uses {@link #getModuleConfig(String)} method inside.
     *
     * @param moduleName module name.
     * @return {@link String} instance for the given module name.
     */
    protected static String getModuleConfig(String moduleName) {
        return getModuleConfig(moduleName);
    }
}
