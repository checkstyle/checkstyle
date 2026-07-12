/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideGoodOverrideFromObjectJavadocError
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
