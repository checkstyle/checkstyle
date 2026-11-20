package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.Arrays;

/** Some javdoc. */
public class InputSeparatorWrapMethodRef {

  void goodCase() {
    String[] stringArray = {
      "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda"
    };
    Arrays.sort(stringArray, String::compareToIgnoreCase);
  }

  void badCase() {
    String[] stringArray = {
      "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda"
    };
    Arrays.sort(
        stringArray,
        // violation below ''::' should be on a new line.'
        String::
            compareToIgnoreCase);
  }
}
