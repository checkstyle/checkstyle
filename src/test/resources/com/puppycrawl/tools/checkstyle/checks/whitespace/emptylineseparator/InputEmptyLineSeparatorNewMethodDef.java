package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.util.ArrayList;

/**
 * Config:
 * allowMultipleEmptyLinesInsideClassMembers = false
 */
public class InputEmptyLineSeparatorNewMethodDef {
    private static final int MULTIPLICATOR;

    void processOne() { // ok

        int a;

        int b;

        new ArrayList<String>() {
            {
                add("String One");
                add("String Two"); // violation


                add("String Three");
            }
        }.add("String Four");
    }

    static { // violation at line below
        MULTIPLICATOR = 5;


    }

}
