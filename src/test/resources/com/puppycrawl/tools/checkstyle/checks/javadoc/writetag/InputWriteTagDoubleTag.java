/*
WriteTag
tag = @doubletag
tagFormat = \\S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 6 lines below , 'Javadoc tag .*'
// violation 6 lines below , 'Javadoc tag .*'
/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagDoubleTag
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagDoubleTag() // ok
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
