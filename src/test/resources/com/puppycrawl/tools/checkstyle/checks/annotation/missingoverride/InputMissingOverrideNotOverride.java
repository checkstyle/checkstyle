package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideNotOverride
{
    /**
     * {@inheritDoc}
     */
    private void bleh() {

    }

    /**
     * {@inheritDoc}
     */
    public static void eh() {

    }

    /**
     * {@inheritDoc}
     */
    public String junk = "";

    void dodoo() {}
}
