/*
WriteTag
tag = @author
tagFormat = \\S
tagSeverity = error
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// violation 3 lines below 'Javadoc tag @author=Daniel Grenner'
/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagSeverity
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagSeverity() // ok
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

