// non-compiled with javac: Compilable with Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;

/** some javadoc. */
public class InputFormattedWhitespaceAroundArrow {
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

  /** method. */
  void test(Object o, Object o2, int y) {
    switch (o) {
      case String s when (
          s.equals("a"))-> // violation ''->' is not preceded with whitespace.'
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
    Predicate predicate = value ->(value != null);
    // 2 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
    Object b = ((VoidPredicate) ()->o1 instanceof String s).get();
    // 3 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
    //     'WhitespaceAround: '->' is not preceded with whitespace.'
    // 3 violations 5 lines below:
    //     ''->' is not followed by whitespace.'
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
        statement-> false)
        .findFirst()
        .orElseThrow(() ->new IllegalStateException("big problem"));
    // 2 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
  }

  /** some javadoc. */
  record Point(int x, int y) {}

  /** some javadoc. */
  public interface Predicate {
    /** some javadoc. */
    boolean test(Object value);
  }

  /** some javadoc. */
  public interface VoidPredicate {
    /** some javadoc. */
    public boolean get();
  }
}
