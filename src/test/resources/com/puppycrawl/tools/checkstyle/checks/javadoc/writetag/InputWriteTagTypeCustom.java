/*
WriteTag
tag = @customBlock
tagFormat = \\S
tagSeverity = (default)info
target = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeCustom {
    // violation 2 lines below 'Javadoc tag @customBlock={@customInline {@nested <br>}}'
    /**
     * @customBlock {@customInline {@nested <br>}}
     * @return value of type {@code String}
     */
    private String method() {
        return "";
    }
}
