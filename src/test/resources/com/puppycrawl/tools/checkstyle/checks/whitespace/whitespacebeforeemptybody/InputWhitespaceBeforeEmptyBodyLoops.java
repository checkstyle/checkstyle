/*
WhitespaceBeforeEmptyBody
tokens = LITERAL_DO, LITERAL_FOR, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyLoops {

    void method() {
        for (int i = 0; i < 1; i++){
            int b = 1;
        }

        for (int i = 0; i < 1; i++) {}
        for (int i = 0; i < 1; i++){}    // violation ''{' is not preceded with whitespace'

        for (int i = 0; i < 1; i++){     // violation ''{' is not preceded with whitespace'
            // comment
        }

        final boolean flag = false;

        int i = 0;
        while(i++ < 1){                  // violation ''{' is not preceded with whitespace'
        }

        do{                              // violation ''{' is not preceded with whitespace'
            /* comment */
        } while(flag);

        int a = 1;
        for (i = 0; i < 1; i++) a++;
    }
}
