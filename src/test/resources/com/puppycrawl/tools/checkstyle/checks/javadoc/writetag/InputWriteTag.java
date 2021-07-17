/*
WriteTag
tag = @author
tagFormat = \S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

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
