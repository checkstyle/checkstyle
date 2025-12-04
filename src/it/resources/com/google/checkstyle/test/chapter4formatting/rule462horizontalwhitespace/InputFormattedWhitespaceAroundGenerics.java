package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Collection;
import java.util.Map;

// we need these interfaces for generics
// violation below 'Top-level class Foo3 has to reside in its own source file.'
interface Foo3 {}

// violation below 'Top-level class Foo22 has to reside in its own source file.'
interface Foo22 {}

/** Some javadoc. */
public class InputFormattedWhitespaceAroundGenerics {}

// No whitespace after commas
// violation below 'Top-level class BadCommas2 has to reside in its own source file.'
class BadCommas2<A, B, C extends Map<A, String>> {
  private final java.util.Hashtable<Integer, Foo> test = new java.util.Hashtable<Integer, Foo>();
}

// violation below 'Top-level class Wildcard2 has to reside in its own source file.'
class Wildcard2 {
  public static void foo(Collection<? extends Wildcard[]> collection) {
    // A statement is important in this method to flush out any
    // issues with parsing the wildcard in the signature
    collection.size();
  }

  public static void foo2(Collection<? extends Wildcard[]> collection) {
    // A statement is important in this method to flush out any
    // issues with parsing the wildcard in the signature
    collection.size();
  }
}
