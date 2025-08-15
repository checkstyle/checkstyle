package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

/** some javadoc. */
public class InputEmptyCatchEmptyComment {
  private void foo() {
    try {
      throw new RuntimeException();
      // violation below 'Empty catch block'
    } catch (Exception expected) {
      //
    }
  }

  private void foo1() {
    try {
      throw new RuntimeException();
      // violation below 'Empty catch block'
    } catch (Exception e) {
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
    } catch (IOException | NullPointerException | ArithmeticException e) { //
    } // violation above 'Empty catch block'
  }

  private void some() {
    try {
      throw new IOException();
    } catch (IOException e) { //
    } // violation above 'Empty catch block'
  }

  private void some1() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException e) {
      /**/
    }
  }

  private void some2() {
    try {
      throw new IOException();
      // violation below 'Empty catch block'
    } catch (IOException e) {
      /* */
    }
  }

  private void some3() {
    try {
      throw new IOException();
    } catch (IOException e) {
      /* no violation. */
    }
  }

  private void some4() {
    try {
      throw new IOException();
    } catch (IOException | NullPointerException | ArithmeticException e) { /* no violation. */
    }
  }
}
