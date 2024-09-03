package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import java.util.Scanner;

/** Test input for GitHub issue #3090. https://github.com/checkstyle/checkstyle/issues/3090 . */
public class InputFormattedRightCurlyDoWhile {

  /** some javadoc. */
  public void foo1() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo2() {
    int i = 1;
    while (i < 5) {
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
      i++;
    }
  }

  /** some javadoc. */
  public void foo3() {
    int i = 1;
    do {
      i++;
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
    } while (i < 5);
  }

  /** some javadoc. */
  public void foo4() {
    int prog;
    int user;
    prog = (int) (Math.random() * 10) + 1;
    Scanner input = new Scanner(System.in, "utf-8");
    if (input.hasNextInt()) {
      do {
        user = input.nextInt();
        if (user == prog) {
          String.CASE_INSENSITIVE_ORDER.equals("Good!");
        } else {
          if (user > 0 && user <= 10) {
            String.CASE_INSENSITIVE_ORDER.equals("Bad! ");
            if (prog < user) {
              String.CASE_INSENSITIVE_ORDER.equals("My number is less than yours.");
            } else {
              String.CASE_INSENSITIVE_ORDER.equals("My number is greater than yours");
            }
          } else {
            String.CASE_INSENSITIVE_ORDER.equals("Violation!");
          }
        }
      } while (user != prog);
    } else {
      String.CASE_INSENSITIVE_ORDER.equals("Violation!");
    }
    String.CASE_INSENSITIVE_ORDER.equals("Goodbye!");
  }

  /** some javadoc. */
  public void foo5() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo6() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo7() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo8() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo9() {
    do {} while (true);
  }
}
