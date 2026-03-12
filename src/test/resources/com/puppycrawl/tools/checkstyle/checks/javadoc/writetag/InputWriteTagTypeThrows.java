/*
WriteTag
tag = @throws
tagFormat = \\S
tagSeverity = (default)info
target = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

import java.io.IOException;

class InputWriteTagTypeThrows {
    // violation 3 lines below 'Javadoc tag @throws=IOException   when {@code i} is {@value 0}'
    /**
     * @return value of type {@code String}
     * @throws IOException   when {@code i} is {@value 0}
     */
    String method(int i) throws IOException {
        if (i == 0) {
            throw new IOException("");
        }
        return "";
    }
}
