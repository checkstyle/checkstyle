/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.io.IOException;

public class InputEmptyLineSeparatorRecursive {

    int foo1() throws Exception {
        int a = 1;
        int b = 2;
        try {
            if (a != b) {
                throw new IOException();
            }
            // empty lines below should cause a violation
            // violation


        } catch(IOException e) {
            System.out.println("IO error: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}
