/*
WriteTag
tag = @todo
tagFormat = \S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
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
     * @todo Add a constructor comment  // violation
     */
    public InputWriteTagMethod()
    {
    }

    public void method() // ok
    {
    }

    /**
     * @todo Add a comment  // violation
     */
    public void anotherMethod()
    {
    }
}

