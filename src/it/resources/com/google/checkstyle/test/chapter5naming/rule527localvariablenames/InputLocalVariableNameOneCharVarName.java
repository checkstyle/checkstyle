package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.util.HashMap;
import java.util.Map;

class InputLocalVariableNameOneCharVarName {
  /** Some javadoc. */
  public void fooMethod() {
    for (int i = 1; i < 10; i++) {
      // some code
    }

    for (int index = 1; index < 10; index++) {
      // some code
    }

    for (int I_ndex = 1; I_ndex < 10; I_ndex++) {
      // violation above 'Local variable name 'I_ndex' must match pattern'
      // some code
    }

    int index = 1;

    for (; index < 10; index++) {
      // some code
    }

    int i = 0;

    for (; i < 12; i++) {
      // some code
    }

    Map<String, String> map = new HashMap<String, String>();

    for (Map.Entry<String, String> e : map.entrySet()) {
      // some code
    }

    int[] b = {1};
    int[][] c = {{1}, {2}};
    for (int a = 0; a < 10; a++) {
      // something
    }

    for (int i_ndex = 1; i_ndex < 10; i_ndex++) {
      // violation above 'Local variable name 'i_ndex' must match pattern'
    }

    for (int ii_i1 = 1; ii_i1 < 10; ii_i1++) {
      // violation above 'Local variable name 'ii_i1' must match pattern'
    }

    for (int $index = 1; $index < 10; $index++) {
      // violation above 'Local variable name .* must match pattern'
    }

    for (int in$dex = 1; in$dex < 10; in$dex++) {
      // violation above 'Local variable name .* must match pattern'
    }

    for (int index$ = 1; index$ < 10; index$++) {
      // violation above 'Local variable name .* must match pattern'
    }
  }
}
