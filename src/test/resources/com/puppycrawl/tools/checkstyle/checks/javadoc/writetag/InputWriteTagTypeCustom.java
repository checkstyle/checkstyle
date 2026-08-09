/*
WriteTag
tag = @customBlock
tagFormat = \\S
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeCustom {
    /**
     * @customBlock {@customInline {@nested <br>}}
     * @return value of type {@code String}
     */
    private String method() {
        return "";
    }
}
