/*
RightCurly
forbidSingleLineMultiBlock = true
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.FileReader;

public class InputRightCurlyTestMultiBlockStatements {
    @interface TesterAnnotation {}

    void foo() throws Exception {
        int a = 90;
        boolean test = true;

        if (a == 1) {
        } else {}
        // violation above ''}' at column 17 should have line break before'

        if (a == 45) {}

        if (a == 9) {} else {}
        // 2 violations above
        // ''}' at column 22 should have line break before'
        // ''}' at column 30 should have line break before'

        // violation 3 lines below ''}' at column 9 should be on the same line as .*/else'
        if (a == 99) {
            System.out.println("test");
        }
        else {
            System.out.println("before");
        }

        try{
            int b = 1;
        } catch (Exception e) { throw new RuntimeException(e); } finally {}
        // 2 violations above
        // ''}' at column 75 should have line break before'
        // ''}' at column 68 should have line break before'


        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
            System.out.println(br.readLine());
        }

        if (a == 1) {}

        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {}

        try (BufferedReader br
                = new BufferedReader(new FileReader("file.txt"))) {} finally {}
        // 2 violations above
        // ''}' at column 68 should have line break before'
        // ''}' at column 79 should have line break before'

        try {} catch (Exception e) {}
        // 2 violations above
        // ''}' at column 14 should have line break before'
        // ''}' at column 37 should have line break before'
    }
}
