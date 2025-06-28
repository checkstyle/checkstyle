package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputFormattedWhitespaceAroundArrow {

  static {
    new JCheckBox()
        .addActionListener(
            (final ActionEvent e) -> {
              good();
            });
  }

  void foo1(Object o) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case 'p' -> {}
      default -> {}
    }
  }

  /** method. */
  void test(Object o) {
    switch (o) {
      case String s when (s.equals("a")) -> {}
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      default -> {}
    }

    int x =
        switch (o) {
          case String s -> {
            switch (o2) {
              case Integer newInt -> {
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
    Predicate predicate = value -> (value != null);

    Object b = ((VoidPredicate) () -> o1 instanceof String s).get();

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
  }

  void test3() {
    ArrayList<Boolean> boolList = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
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

    result =
        boolList.stream()
            .filter(statement -> someFunction())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("big problem"));
  }
}
