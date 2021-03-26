package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;doubletag
 * tagFormat = \\S
 *
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */ // violation at 11
class InputWriteTagDoubleTag // violation at 12
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagDoubleTag() // ok
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
