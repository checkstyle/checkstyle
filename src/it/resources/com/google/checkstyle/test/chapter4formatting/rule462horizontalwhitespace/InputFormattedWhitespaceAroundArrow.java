// Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;

/** Some javadoc. */
public class InputFormattedWhitespaceAroundArrow {
  static {
    new JCheckBox()
        .addActionListener(
            (final ActionEvent e) -> {
              test3();
            });
  }

  void foo1(Object o) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case String s2 -> {}
      default -> {}
    }
  }

  /** Method. */
  void test(Object o, Object o2, int y) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case Point(int x, int xy) when !(x >= 0 && y >= 0) -> {}
      default -> {}
    }
    int x =
        switch (o) {
          case String s -> {
            switch (o2) {
              case Integer i when i == 0 -> {
                if (y == 0) {
                  System.out.println(0);
                }
              }
              default -> {}
            }
            yield 3;
          }
          default -> 3;
        };
  }

  int test2(int k, Object o1) {
    Predicate predicate = value -> (value != null);
    Object b = ((VoidPredicate) () -> o1 instanceof String s).get();
    new LinkedList<Integer>()
        .stream()
            .map(
                t -> {
                  return t * 2;
                })
            .filter(
                t -> {
                  return false;
                });
    return k * 2;
  }

  static void test3() {
    ArrayList<Boolean> boolList = new ArrayList<Boolean>(Arrays.asList(false, true, false));
    List<Boolean> filtered =
        boolList.stream()
            .filter(
                statement -> {
                  if (!statement) {
                    return true;
                  } else {
                    return false;
                  }
                })
            .collect(Collectors.toList());
    Object result =
        boolList.stream()
            .filter(statement -> false)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("big problem"));
  }

  /** Some javadoc. */
  record Point(int x, int y) {}

  /** Some javadoc. */
  public interface Predicate {
    /** Some javadoc. */
    boolean test(Object value);
  }

  /** Some javadoc. */
  public interface VoidPredicate {
    /** Some javadoc. */
    public boolean get();
  }
}
