package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

/** Invalid indentation examples for OpenJDK style section 3.7. */
public class InputIndentationInvalid {

    private void method(int value) {
        switch (value) {
          case 1:
          // violation above '.* child has incorrect indentation level 10, expected level should be 12'
              value++;
              // violation above '.* child has incorrect indentation level 14, expected level should be 16'
                break;
            default:
                value--;
                break;
        }
    }
}
