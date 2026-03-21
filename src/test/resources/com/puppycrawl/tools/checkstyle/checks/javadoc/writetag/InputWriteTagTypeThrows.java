/*
WriteTag
tag = @throws
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

import java.io.IOException;

class InputWriteTagTypeThrows {
    // violation 3 lines below 'Javadoc tag @throws=IOException   when {@code i} is {@value Integer#MAX_VALUE}'
    /**
     * @return value of type {@code String}
     * @throws IOException   when {@code i} is {@value Integer#MAX_VALUE}
     */
    String method(int i) throws IOException {
        if (i == Integer.MAX_VALUE) {
            throw new IOException("");
        }
        return "";
    }
}
