package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

/** Invalid indentation examples for OpenJDK style section 3.7. */
public class InputIndentationInvalid {

    private void method(int value) {
        switch (value) {
          case 1:
          // violation above '.* incorrect indentation level.*'
              value++;
              // violation above '.* incorrect indentation level.*'
                break;
            default:
                value--;
                break;
        }
    }
}
