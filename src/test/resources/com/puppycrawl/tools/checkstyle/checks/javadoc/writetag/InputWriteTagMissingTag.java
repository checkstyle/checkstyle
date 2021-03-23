package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;missing
 *
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagMissingTag // violation
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagMissingTag() // ok
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
