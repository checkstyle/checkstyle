package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputNoWhitespaceBeforeEmptyForLoop {

    public static void f() {
        for (; ; ) {  
            break;
        }
        for (int x = 0; ; ) {  
            break;
        }
        for (int x = 0 ; ; ) { // warning
            break;
        }
        for (int x = 0; x < 10; ) {  
            break;
        }
        for (int x = 0; x < 10 ; ) { // warning
            break;
        }
    }
}
