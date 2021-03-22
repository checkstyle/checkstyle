package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.io.IOException;

/**
 * Config:
 * allowMultipleEmptyLinesInsideClassMembers = false
 */
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
