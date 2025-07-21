// non-compiled with eclipse: extra semicolumn in imports

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

/*
 * This file contains test inputs for InputFormattedOneStatementPerLine2
 * which cause compilation problem in Eclipse 4.2.2 but still must be tested.
 */

/*
 * Two import statements and one 'empty' statement
 * which are not on the same line are legal.
 */
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JCheckBox;

/** Some javadoc. */
public class InputFormattedOneStatementPerLine2 {
  /*
   * According to java language specifications,
   * statements end with ';'. That is why ';;'
   * may be considered as two empty statements on the same line
   * and rises violation.
   */
  ;

  static {
    new JCheckBox()
        .addActionListener(
            (final ActionEvent e) -> {
              good();
            });
    List<Integer> ints = new LinkedList<Integer>();
    ints.stream()
        .map(
            t -> {
              return t * 2;
            })
        .filter(
            t -> {
              return false;
            });

    ints.stream()
        .map(
            t -> {
              int m = t * 2;
              return m;
            });

    ints.stream()
        .map(
            t -> {
              int m = t * 2;
              return m;
            });
    int i = 3;

    ints.stream().map(t -> t * 2);
    int k = 4;

    ints.stream().map(t -> t * 2);
    List<Integer> ints2 = new LinkedList<Integer>();

    ints.stream()
        .map(
            t -> {
              return ints2.stream()
                  .map(
                      w -> {
                        return w * 2;
                      });
            });

    ints.stream()
        .map(
            t -> {
              return ints2.stream()
                  .map(
                      w -> {
                        int m = w;
                        return m;
                      });
            });

    ints.stream()
        .map(
            t -> {
              return ints2.stream()
                  .map(
                      w -> {
                        int m = w * 2;
                        return m;
                      });
            });
    ints.stream()
        .map(
            t -> {
              int l = 0;
              for (int j = 0; j < 10; j++) {
                l = j + l;
              }
              return l;
            });
  }

  private static void good() {}
}
