/*
WriteTag
tag = @author
tagFormat = (default)null
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
class InputWriteTagMissingFormat {
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagMissingFormat() // ok
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
