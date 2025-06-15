package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputWhitespaceAroundArrow {

  static { 
    // violation below ''->' is not preceded with whitespace.'
    new JCheckBox().addActionListener((final ActionEvent e)-> {
      good();
    });
  }

  void foo1(Object o) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case 'p' -> {
      }
      default -> {}
    }
  }

  /** method. */
  void test(Object o) {
    switch (o) {
      case String s when (
          s.equals("a"))-> // violation ''->' is not preceded with whitespace.'
        {
        }
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      default-> // violation ''->' is not preceded with whitespace.'
        {}
    }

    int x = switch (o) {
      case String s -> {
        switch (o2) {
          case Integer newInt-> { // violation ''->' is not preceded with whitespace.'
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

  int test2() {
    Predicate predicate = value ->(value != null);
    // 2 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'

    Object b = ((VoidPredicate) ()->o1 instanceof String s).get();
    // 3 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
    //     'WhitespaceAround: '->' is not preceded with whitespace.'

    List<Integer> ints = new LinkedList<Integer>();
    // 3 violations 5 lines below:
    //     ''->' is not followed by whitespace.'
    //     ''->' is not followed by whitespace. .*'
    //     ''{' is not preceded with whitespace.'
    ints.stream()
        .map(t ->{
            return t * 2;
          }
        )
        .filter(t -> {
          return false;
        });
  }

  void test3() {
    ArrayList<Boolean> boolList
        = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
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

    result = boolList.stream().filter(
        // violation below 'WhitespaceAround: '->' is not preceded with whitespace.'
        statement-> someFunction())
        .findFirst()
        .orElseThrow(() ->new IllegalStateException("big problem"));
    // 2 violations above:
    //     ''->' is not followed by whitespace.'
    //     'WhitespaceAround: '->' is not followed by whitespace. .*'
  }
}
