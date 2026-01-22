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
public class InputWhitespaceAroundArrow {
  static {
    // violation below ''->' is not preceded with whitespace.'
    new JCheckBox().addActionListener((final ActionEvent e)-> {
      test3();
    });
  }

  void foo1(Object o) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case String s2 -> {
      }
      default -> {}
    }
  }

  /** Method. */
  void test(Object o, Object o2, int y) {
    switch (o) {
      case String s when (
          s.equals("a"))-> // violation ''->' is not preceded with whitespace.'
        // violation below ''{' at column 9 should be on the previous line.'
        {
        }
      case Point(int x, int xy) when !(x >= 0 && y >= 0) -> {}
      default-> // violation ''->' is not preceded with whitespace.'
        {}
    }

    int x = switch (o) {
      case String s -> {
        switch (o2) {
          case Integer i when i == 0-> { // violation ''->' is not preceded with whitespace.'
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
    // violation below, 'WhitespaceAround: '->' is not followed by whitespace. .*'
    Predicate predicate = value ->(value != null);

    Object b = ((VoidPredicate) ()->o1 instanceof String s).get();
    // 2 violations above:
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
    //     'WhitespaceAround: '->' is not preceded with whitespace.'
    // 2 violations 4 lines below:
    //     ''->' is not followed by whitespace. .*'
    //     ''{' is not preceded with whitespace.'
    new LinkedList<Integer>().stream()
        .map(t ->{
            return t * 2;
          }
        )
        .filter(t -> {
          return false;
        });
    return k * 2;
  }

  static void test3() {
    ArrayList<Boolean> boolList = new ArrayList<Boolean>(Arrays.asList(false, true, false));
    // violation 2 lines below 'WhitespaceAround: '->' is not preceded with whitespace.'
    List<Boolean> filtered = boolList.stream()
        .filter(statement-> {
          if (!statement) {
            return true;
          } else {
            return false;
          }
        })
        .collect(Collectors.toList());
    Object result = boolList.stream().filter(
        // violation below 'WhitespaceAround: '->' is not preceded with whitespace.'
        statement-> false).findFirst()
        // violation below, 'WhitespaceAround: '->' is not followed by whitespace. .*'
        .orElseThrow(() ->new IllegalStateException("big problem"));
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
