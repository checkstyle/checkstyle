package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

/** Some javadoc. */
public class InputEmptyCatchBlockViolationsByVariableName {
  private void foo() {
    try {
      throw new RuntimeException();
    } catch (Exception expected) {
      // ignore
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
      // violation below 'Empty catch block.'
    } catch (IOException | NullPointerException | ArithmeticException expected) {
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
    } catch (IOException | NullPointerException | ArithmeticException expected) {
      // ignore
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
