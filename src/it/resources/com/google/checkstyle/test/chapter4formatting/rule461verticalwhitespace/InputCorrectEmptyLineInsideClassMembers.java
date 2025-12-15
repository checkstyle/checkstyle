package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

/**
 * This file contains correct vertical whitespace inside class members.
 */
public class InputCorrectEmptyLineInsideClassMembers {

  static int count;
  String str;

  {
    str = "Hello";
  }

  /** Constructor. */
  InputCorrectEmptyLineInsideClassMembers() {
    int a;

    int b;
  }

  static {
    count = 5;
  }

  /** Method. */
  public void foo() {
    int x = 10;

    int y = 20;
  }

  /** Main method. */
  public static void main(String[] args) {
    new InputCorrectEmptyLineInsideClassMembers();
  }
}
