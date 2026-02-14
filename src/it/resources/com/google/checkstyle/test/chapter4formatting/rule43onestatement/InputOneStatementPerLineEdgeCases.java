package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.util.function.Consumer;

/** Some javadoc. */
public class InputOneStatementPerLineEdgeCases {

  /** Some javadoc. */
  public void cases() {

    Consumer<String> c1 = s -> { int a; }; Consumer<String> c2 = s -> {};
    // 3 violations above:
    // 'Only one variable definition per line allowed.'
    // ''{' at column 32 should have line break after.'
    // 'Only one statement per line allowed.'

    // violation below 'Only one variable definition per line allowed.'
    Consumer<int[]> c3 = x -> {}; Consumer<int[]>
            c4 = x -> {};
    // violation above 'Only one statement per line allowed.'

    int x = 1; Consumer<int[]> outer = w -> { Consumer<int[]> inner = y -> { int a; int b; }; };
    // 7 violations above:
    // 'Only one variable definition per line allowed.'
    // ''{' at column 45 should have line break after.'
    // ''{' at column 76 should have line break after.'
    // 'Only one variable definition per line allowed.'
    // 'Only one statement per line allowed.'
    // 'Only one statement per line allowed.'
    // 'Only one statement per line allowed.'

    // violation below 'Only one variable definition per line allowed.'
    Object a1 = new Object() {}; Object a2 =
            new Object() {};
    // violation above 'Only one statement per line allowed.'

    Object outerA = new Object() {
        Object innerA = new Object() { void foo() { int a; int b; } }; };
    // 4 violations above:
    // ''{' at column 51 should have line break after.'
    // 'Only one variable definition per line allowed.'
    // 'Only one statement per line allowed.'
    // ''}' at column 67 should be alone on a line.'

    Object anon = new Object() {
        void bar() {
        // violation below 'Only one variable definition per line allowed.'
        Consumer<String> c1 = s -> foo(); Consumer<String>
              c2 = s -> foo(); } };
    // 2 violations above:
    // 'Only one statement per line allowed.'
    // ''}' at column 32 should be alone on a line.'
  }

  void foo() {}
}
