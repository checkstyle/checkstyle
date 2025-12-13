package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

/** Some javadoc. */
public class InputFormattedEmptyCatchEmptyComment {
  private void foo() {
    try {
      throw new RuntimeException();
      // violation below 'Empty catch block'
    } catch (Exception expected) {
      //
    }
  }

  private void foo2() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException ignore) {
      // ignore
    }
  }

  private void foo3() { // comment
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException | NullPointerException | ArithmeticException e) {
      /**/
    }
  }

  private void foo4() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException | NullPointerException | ArithmeticException e) {
      /* */
    }
  }

  private void foo5() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException | NullPointerException | ArithmeticException e) { //
    }
  }

  private void foo6() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException e) { //
    }
  }

  private void foo7() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException e) {
      /**/
    }
  }

  private void foo8() {
    try {
      throw new IOException();
    } catch (IOException e) {
      /* no violation. */
    }
  }

  private void foo9() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException e) {
      /* no violation. */
    }
  }

  private void foo10() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException | NullPointerException | ArithmeticException e) {
      /* */
    }
  }

  private void foo11() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException | NullPointerException | ArithmeticException e) {
      /**/
    }
  }
}
