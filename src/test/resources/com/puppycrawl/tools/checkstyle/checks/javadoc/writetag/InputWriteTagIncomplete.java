/*
WriteTag
tag = @incomplete
tagFormat = \S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code... // violation
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagIncomplete
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagIncomplete() // ok
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
