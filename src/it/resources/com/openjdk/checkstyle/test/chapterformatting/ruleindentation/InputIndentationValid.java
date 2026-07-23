package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header is missing'

/** Valid indentation examples for OpenJDK style section 3.7. */
public class InputIndentationValid {

    private void method() {
        int value = 0;
        switch (value) {
            case 1: // violation ''case' construct must use '{}'s.'
                value++;
                break;
            default: // violation ''default' construct must use '{}'s.'
                value--;
                break;
        }
    }
}
