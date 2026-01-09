package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.util.LinkedList;
import java.util.List;

/** Some javadoc. */
public class InputOneStatementPerLine2 {
  /*
   * According to java language specifications,
   * statements end with ';'. That is why ';;'
   * may be considered as two empty statements on the same line
   * and rises violation.
   */
  ;; // violation 'Only one statement per line allowed.'
  static {
    List<Integer> ints = new LinkedList<Integer>();
    ints.stream().map(t -> { return t * 2; }).filter(t -> { return false; });
    // 2 violations above:
    //  ''{' at column 28 should have line break after.'
    //  ''{' at column 59 should have line break after.'

    ints.stream().map(t -> { int m = t * 2; return m; });
    // 2 violations above:
    //  ''{' at column 28 should have line break after.'
    //  'Only one statement per line allowed.'
    ints.stream().map(t -> { int m = t * 2; return m; }); int i = 3;
    // 3 violations above:
    //  ''{' at column 28 should have line break after.'
    //  'Only one statement per line allowed.'
    //  'Only one statement per line allowed.'
    ints.stream().map(t -> t * 2); int k = 4;
    // violation above 'Only one statement per line allowed.'
    ints.stream().map(t -> t * 2);
    List<Integer> ints2 = new LinkedList<Integer>();
    ints.stream().map(t -> { return ints2.stream().map(w -> { return w * 2; }); });
    // 2 violations above:
    //  ''{' at column 28 should have line break after.'
    //  ''{' at column 61 should have line break after.'
    ints.stream().map(t -> { return ints2.stream().map(w -> { int m = w; return m; }); });
    // 3 violations above:
    //  ''{' at column 28 should have line break after.'
    //  ''{' at column 61 should have line break after.'
    //  'Only one statement per line allowed.'

    ints.stream().map(t -> {
      return ints2.stream().map(
        w -> {
            int m = w * 2;
            return m;
        });
    });
    ints.stream().map(t -> {
      int l = 0;
      for (int j = 0; j < 10; j++) {
        l = j + l;
      }
      return l;
    });
  }

  private static void good() {}
}
