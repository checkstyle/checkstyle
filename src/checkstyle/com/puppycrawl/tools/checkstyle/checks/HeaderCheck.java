package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Checks that the header of the source file is correct.
 *
 * <p>
 * Rationale: In most projects each file must have a fixed header,
 * usually the header contains copyright information.
 * </p>
 *
 * @author Lars Kühne
 */
public class HeaderCheck extends Check
{
    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see Check */
    public void beginTree()
    {
        String[] lines = getLines();
        log(0,"file has " + lines.length + " lines");
    }
}
