/*
WriteTag
tag = @see
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeSee {
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
