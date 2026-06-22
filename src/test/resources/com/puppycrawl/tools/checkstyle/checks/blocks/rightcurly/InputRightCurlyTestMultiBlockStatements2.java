/*
RightCurly
forbidSingleLineMultiBlock = true
option = (default)SAME
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputRightCurlyTestMultiBlockStatements2 {
    void method() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String first = br.readLine();
        } catch (IOException e) {} catch (Exception e) {} finally {}
        // 3 violations above
        // ''}' at column 34 should have line break before'
        // ''}' at column 57 should have line break before'
        // ''}' at column 68 should have line break before'
    }

    void method2() {
        boolean flag1 = true;
        boolean flag2 = false;
        int a;
        if (flag1) {a = 1;} else if (flag1 && flag2) {a= 2;} else {a = 3;}
        // 3 violations above
        // ''}' at column 27 should have line break before'
        // ''}' at column 60 should have line break before'
        // ''}' at column 74 should have line break before'

    }
    void method3() {}
}

class NonMultiBlockSingleLine {}
