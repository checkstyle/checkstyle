package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;author
 * tagFormat = \\S
 * severity = ignore
 */

/**
 * Testing tag writing
 * @author Daniel Grenner  // violation
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagSeverity
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagSeverity() // ok
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

