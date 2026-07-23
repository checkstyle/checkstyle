package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.util.LinkedList;
import java.util.List;

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
