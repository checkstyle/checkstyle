/*
WriteTag
tag = @todo
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF
severity = ignore
tagSeverity = (default)info

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
class InputWriteTagMethod
{
    // violation 2 lines below , 'Add a constructor comment.*'
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagMethod()
    {
    }

    public void method()
    {
    }

    // violation 2 lines below , 'Add a comment.*'
    /**
     * @todo Add a comment
     */
    public void anotherMethod()
    {
    }
}

