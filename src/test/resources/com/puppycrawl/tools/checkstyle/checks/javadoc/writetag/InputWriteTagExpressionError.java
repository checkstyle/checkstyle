/*
WriteTag
tag = @author
tagFormat = ABC
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 3 lines below 'tag @author must match pattern 'ABC'.'
/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
public class InputWriteTagExpressionError {
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagExpressionError ()
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
