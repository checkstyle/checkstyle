/*
WriteTag
tag = @return
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeReturn {
    // violation 2 lines below 'Javadoc tag @return=value of type {@code String}'
    /**
     * @return value of type {@code String}
     */
    private String method() {
        return "";
    }
}
