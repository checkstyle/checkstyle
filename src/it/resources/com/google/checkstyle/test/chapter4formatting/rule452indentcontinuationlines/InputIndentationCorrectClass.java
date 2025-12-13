package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

import java.util.Iterator;

/** Some javadoc. */
public class InputIndentationCorrectClass implements Runnable, Cloneable {

  /** Some javadoc. */
  @Override
  public void run() {
    SecondClassWithLongLongLongLongName anon = new SecondClassWithLongLongLongLongName() {};

    SecondClassWithLongLongLongLongName anon2 = new SecondClassWithLongLongLongLongName() {};
  }

  class InnerClass implements Iterable<String>, Cloneable {
    @Override
    public Iterator<String> iterator() {
      return null;
    }
  }

  class InnerClass2 extends SecondClassReturnWithVeryVeryVeryLongName {
    public InnerClass2(String string) {
      super();
      // OOOO Auto-generated constructor stub
    }
  }

  class SecondClassWithLongLongLongLongName extends InputIndentationCorrectClass {}

  class SecondClassReturnWithVeryVeryVeryLongName {}
}
