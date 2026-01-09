package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.util.function.Consumer;

/** Some javadoc. */
public class InputFormattedOneStatementPerLineEdgeCases {

  /** Some javadoc. */
  public void cases() {

    Consumer<String> c1 =
        s -> {
          int a;
        };
    Consumer<String> c2 = s -> {};

    Consumer<int[]> c3 = x -> {};
    Consumer<int[]> c4 = x -> {};

    int x = 1;
    Consumer<int[]> outer =
        w -> {
          Consumer<int[]> inner =
              y -> {
                int a;
                int b;
              };
        };

    Object a1 = new Object() {};
    Object a2 = new Object() {};

    Object outerA =
        new Object() {
          Object innerA =
              new Object() {
                void foo() {
                  int a;
                  int b;
                }
              };
        };

    Object anon =
        new Object() {
          void bar() {

            Consumer<String> c1 = s -> foo();
            Consumer<String> c2 = s -> foo();
          }
        };
  }

  void foo() {}
}
