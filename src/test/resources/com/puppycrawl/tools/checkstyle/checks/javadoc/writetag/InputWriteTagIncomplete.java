/*
WriteTag
tag = @incomplete
tagFormat = \\S
tagSeverity = error
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 4 lines below 'Javadoc tag @incomplete=This class needs more code...'
/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagIncomplete
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagIncomplete()
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
