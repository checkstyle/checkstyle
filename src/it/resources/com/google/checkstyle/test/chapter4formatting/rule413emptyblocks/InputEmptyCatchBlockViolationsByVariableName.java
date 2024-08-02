package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

/** some javadoc. */
public class InputEmptyCatchBlockViolationsByVariableName {
  private void foo() {
    try {
      throw new RuntimeException();
    } catch (Exception expected) { // ok

    }
  }

  private void foo1() {
    try {
      throw new RuntimeException();
    } catch (Exception e) {
    } // violation above 'Empty catch block.'
  }

  private void foo2() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException expected) { // ok
    }
  }

  private void foo3() { // comment
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException e) {
    } // violation above 'Empty catch block.'
  }

  private void foo4() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException expected) { // ok
    }
  }

  private void foo5() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException e) {
    } // violation above 'Empty catch block.'
  }

  private void some() {
    try {
      throw new IOException();
    } catch (IOException e) {

    } // violation 2 lines above 'Empty catch block.'
  }
}
