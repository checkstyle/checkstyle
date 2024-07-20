package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

import java.util.Iterator;

public class InputIndentationCorrectClass implements Runnable, Cloneable {

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

  class InnerClass2 extends SecondClassWithLongLongLongLongName {
    public InnerClass2(String string) {
      super();
      // OOOO Auto-generated constructor stub
    }
  }
}

class SecondClassWithLongLongLongLongName extends InputIndentationCorrectClass {}
