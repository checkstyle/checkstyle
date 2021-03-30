////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2004
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;author
 * tagFormat = \\S
 */

/**
 * Testing tag writing
 * @author Daniel Grenner // violation
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTag
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTag()
    {
    }

    public void method()
    {
    }

    /**
     * @todo Add a comment
     */
    public void anotherMethod()
    {
    }
}
