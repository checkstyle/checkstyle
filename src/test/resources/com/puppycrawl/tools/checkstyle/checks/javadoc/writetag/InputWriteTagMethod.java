package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = &#64;todo
 * tagFormat = \\S
 * tokens = INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF
 * severity = ignore
 *
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagMethod // ok
{
    /**
     * @todo Add a constructor comment
     */ // violation at line above
    public InputWriteTagMethod()
    {
    }

    public void method() // ok
    {
    }

    /**
     * @todo Add a comment
     */ // violation at line above
    public void anotherMethod()
    {
    }
}

