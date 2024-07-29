package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@TestClassAnnotation
class InputLeftCurlyAnnotations
  { // violation ''{' at column 3 should be on the previous line.'
    private static final int X = 10;

    @Override
    public boolean equals(
        Object other)
    { // violation ''{' at column 5 should be on the previous line.'
      return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode()
    { // violation ''{' at column 5 should be on the previous line.'
      int a = 10;
      return 1;
    }

    @Override
    @SuppressWarnings({"unused", "unchecked", "static-access"})
    public String toString()
    { // violation ''{' at column 5 should be on the previous line.'
      Integer i = this.X;
      List<String> l = new ArrayList();
      return "SomeString";
    }
  }

// violation below '.* InputLeftCurlyAnnotations2 has to reside in its own source file.'
@TestClassAnnotation
class InputLeftCurlyAnnotations2 {
  private static final int X = 10;

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  @SuppressWarnings("unused")
  public int hashCode() {
    int a = 10;
    return 1;
  }

  @Override
  @SuppressWarnings({"unused", "unchecked", "static-access"})
  public String toString()
    { // violation ''{' at column 5 should be on the previous line.'
      Integer i = this.X;
      List<String> l = new ArrayList();
      return "SomeString";
    }
}

// violation below 'Top-level class TestClassAnnotation has to reside in its own source file.'
@Target(ElementType.TYPE)
@interface TestClassAnnotation {}
