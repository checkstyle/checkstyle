package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config: default
 */

/** Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
public class InputWriteTagDefault {
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagDefault() // ok
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
