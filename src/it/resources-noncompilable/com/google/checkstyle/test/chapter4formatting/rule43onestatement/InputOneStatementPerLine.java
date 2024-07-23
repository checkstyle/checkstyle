//non-compiled with eclipse: extra semicolumn in imports

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

/*
 * This file contains test inputs for InputOneStatementPerLine
 * which cause compilation problem in Eclipse 4.2.2 but still must be tested.
 */

/*
 * Two import statements and one 'empty' statement
 * which are not on the same line are legal.
 */
import java.awt.event.ActionEvent;
import java.lang.Integer;
import java.lang.String;
import java.lang.annotation.Annotation;
; // non-compilable by eclipse
// violation above '';' should be separated from previous line.'
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;

/** Some javadoc. */
public class InputOneStatementPerLine {
  /*
   * According to java language specifications,
   * statements end with ';'. That is why ';;'
   * may be considered as two empty statements on the same line
   * and rises violation.
   */
  ;; // violation 'Only one statement per line allowed.'
  static {
    new JCheckBox().addActionListener((final ActionEvent e) -> { good(); });
    // violation above ''{' at column 64 should have line break after.'
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
