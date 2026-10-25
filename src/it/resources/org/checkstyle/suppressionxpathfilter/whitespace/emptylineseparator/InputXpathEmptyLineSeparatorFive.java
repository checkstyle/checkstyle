/*
EmptyLineSeparator
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
allowNoEmptyLineBetweenFields = (default)false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptylineseparator;

import java.io.IOException;

public class InputXpathEmptyLineSeparatorFive {

    int foo1() throws Exception {
        int a = 1;
        int b = 2;
        try {
            if (a != b) {
                throw new IOException();
            }
            // violation below "There is more than 1 empty line after this line."
            // warn


        } catch(IOException e) {
            return 1;
        }
        return 0;
    }
}
