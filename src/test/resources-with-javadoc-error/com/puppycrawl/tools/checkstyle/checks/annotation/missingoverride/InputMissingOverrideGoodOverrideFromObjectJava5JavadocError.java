/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideGoodOverrideFromObjectJava5JavadocError
{
    /**
     * {@inheritDoc no violation}
     *
     * @inheritDocs}
     *
     * {@inheritDoc
     */
    @Override
    public int hashCode() {
        return 1;
    }
}
