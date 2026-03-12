/*
WriteTag
tag = @since
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

import java.io.IOException;

class InputWriteTagTypeSince {
    // violation 2 lines below 'Javadoc tag @since=2.2.2 (2019-12-31)'
    /**
     * @since      2.2.2 (2019-12-31)
     * @return value of type {@code String}
     * @throws IOException  when {@code i} is 0
     */
    String method(int i) throws IOException {
        if (i == 0) {
            throw new IOException("");
        }
        return "";
    }
}
