package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;author
 * tagFormat = ABC
 */

/**
 * Testing tag writing
 * @author Daniel Grenner  // violation
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
public class InputWriteTagExpressionError {
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagExpressionError () // ok
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
