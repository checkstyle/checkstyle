package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;empty
 * tagFormat = ""
 *
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagEmptyTag // violation at 13
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagEmptyTag() // ok
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
