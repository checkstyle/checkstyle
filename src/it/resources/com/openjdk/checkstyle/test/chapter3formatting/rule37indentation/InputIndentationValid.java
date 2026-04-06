package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

/** Valid indentation examples for OpenJDK style section 3.7. */
public class InputIndentationValid {

    private void method(int value) {
        switch (value) {
            case 1:
                value++;
                break;
            default:
                value--;
                break;
        }
    }
}
