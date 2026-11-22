/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

public class InputMissingDeprecatedRecordComponentDeprecatedTag {

    public record Point(
            /** @deprecated use y instead */
            @Deprecated int x,
            /** @deprecated use x instead */
            int y) {
    }
}
