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

import java.util.ArrayList;

public class InputEmptyLineSeparatorNewMethodDef {
    private static final int MULTIPLICATOR;

    void processOne() {

        int a;

        int b;

        new ArrayList<String>() {
            {
                add("String One");
                add("String Two"); // violation 'There is more than 1 empty line after this line.'


                add("String Three");
            }
        }.add("String Four");
    }

    static { // violation below 'There is more than 1 empty line after this line.'
        MULTIPLICATOR = 5;


    }

}
