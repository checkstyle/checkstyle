package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header is missing'

/** Invalid indentation examples for OpenJDK style section 3.7. */
public class InputIndentationInvalid {

    private void method() {
        int value = 0;

        switch (value) {
          case 1: // violation ''case' construct must use '{}'s.'
          // violation above '.* incorrect indentation level 10, expected .* 12.'
              value++;
              // violation above '.* incorrect indentation level 14, expected .* 16.'
                break;
            default: // violation ''default' construct must use '{}'s.'
                value--;
                break;
        }
    }
}
