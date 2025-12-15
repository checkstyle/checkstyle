package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

/**
 * This file contains correct vertical whitespace inside class members and also between class
 * members.
 */
public class InputFormattedMultipleEmptyLineInsideClassMembers {

  static int count;
  String str;
  int value;

  {
    str = "Hello";
  }

  {
    value = 10;
  }

  /** Constructor. */
  InputFormattedMultipleEmptyLineInsideClassMembers() {
    int a;

    int b;
  }

  static {
    count = 5;
  }

  static {
    count++;
  }

  /** Method. */
  public void foo() {
    int x = 10;

    int y = 20;
  }

  /** Another method. */
  public void bar() {
    int z = 30;
  }

  /** Main method. */
  public static void main(String[] args) {
    new InputFormattedMultipleEmptyLineInsideClassMembers();
  }
}
