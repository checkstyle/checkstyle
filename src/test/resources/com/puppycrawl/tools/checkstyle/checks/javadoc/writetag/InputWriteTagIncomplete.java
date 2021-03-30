package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;incomplete
 * tagFormat = \\S
 */

/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code... // violation
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagIncomplete
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagIncomplete() // ok
    {
    }

    public void method() // ok
    {
    }

    /**
     * @todo Add a comment
     */
    public void anotherMethod() // ok
    {
    }
}
