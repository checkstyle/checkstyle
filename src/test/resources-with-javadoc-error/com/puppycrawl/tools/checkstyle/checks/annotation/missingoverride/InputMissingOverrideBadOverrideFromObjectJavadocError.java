/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadOverrideFromObjectJavadocError
{
    /**
     * {@inheritDoc no violation}
     *
     * @inheritDocs}
     *
     * {@inheritDoc
     */
    public int hashCode() {
        return 1;
    }
}
