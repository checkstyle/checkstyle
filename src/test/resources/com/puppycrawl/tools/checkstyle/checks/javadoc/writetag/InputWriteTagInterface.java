/*
WriteTag
tag = @author
tagFormat = \\S
tagSeverity = error
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, BLOCK_COMMENT_BEGIN


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
interface InputWriteTagInterface {
    /**
     * @todo Add a comment
     */
    void anotherMethod();
}
