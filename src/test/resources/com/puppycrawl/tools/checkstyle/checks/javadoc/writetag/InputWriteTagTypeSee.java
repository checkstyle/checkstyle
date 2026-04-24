/*
WriteTag
tag = @see
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeSee {
    // violation 4 lines below 'Javadoc tag @see=java.io.Closeable#close()'
    // violation 4 lines below 'Javadoc tag @see=<a href="https://docs.oracle.com/en/java/">ref</a>'
    // violation 4 lines below 'Javadoc tag @see="text"'
    /**
     * @see java.io.Closeable#close()
     * @see <a href="https://docs.oracle.com/en/java/">ref</a>
     * @see "text"
     * @return value of type {@code String}
     */
    private String method() {
        return "";
    }
}
