/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

public class InputMissingDeprecatedRecordComponentDeprecatedTag {

    /**
     * @deprecated use another record
     */
    @Deprecated
    public record Point(
            @Deprecated int x,
            /** The y coordinate. */
            int y) {
    }
}
