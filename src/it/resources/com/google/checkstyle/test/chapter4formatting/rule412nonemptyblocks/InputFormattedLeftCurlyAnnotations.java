package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@TestClassAnnotation2
class InputFormattedLeftCurlyAnnotations {
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
  public String toString() {
    Integer i = this.X;
    List<String> l = new ArrayList();
    return "SomeString";
  }
}

// violation below '.* InputFormattedLeftCurlyAnnotations2 has to reside in its own source file.'
@TestClassAnnotation2
class InputFormattedLeftCurlyAnnotations2 {
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
  public String toString() {
    Integer i = this.X;
    List<String> l = new ArrayList();
    return "SomeString";
  }
}

// violation below 'Top-level class TestClassAnnotation2 has to reside in its own source file.'
@Target(ElementType.TYPE)
@interface TestClassAnnotation2 {}
