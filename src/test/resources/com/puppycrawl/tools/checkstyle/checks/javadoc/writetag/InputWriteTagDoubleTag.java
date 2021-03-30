package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;doubletag
 * tagFormat = \\S
 */

/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text  // violation
 * @doubletag second text  // violation
 * @emptytag
 */
class InputWriteTagDoubleTag
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
