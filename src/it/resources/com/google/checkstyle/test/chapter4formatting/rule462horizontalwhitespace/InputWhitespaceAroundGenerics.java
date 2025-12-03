package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Collection;
import java.util.Map;

// we need these interfaces for generics
// violation below 'Top-level class Foo has to reside in its own source file.'
interface Foo {}

// violation below 'Top-level class Foo2 has to reside in its own source file.'
interface Foo2 {}

/** Some javadoc. */
public class InputWhitespaceAroundGenerics {}

// No whitespace after commas
// violation below 'Top-level class BadCommas has to reside in its own source file.'
class BadCommas < A, B, C extends Map < A, String > > {
  // 7 violations above:
  //  ''\<' is followed by whitespace.'
  //  ''\<' is preceded with whitespace.'
  //  ''\<' is followed by whitespace.'
  //  ''\<' is preceded with whitespace.'
  //  ''\>' is followed by whitespace.'
  //  ''\>' is preceded with whitespace.'
  //  ''\>' is preceded with whitespace.'
  private final java.util.Hashtable < Integer, Foo > test =
      // 3 violations above:
      //  ''\<' is followed by whitespace.'
      //  ''\<' is preceded with whitespace.'
      //  ''\>' is preceded with whitespace.'
      new java.util.Hashtable < Integer, Foo >();
      // 3 violations above:
      //  ''\<' is followed by whitespace.'
      //  ''\<' is preceded with whitespace.'
      //  ''\>' is preceded with whitespace.'
}

// violation below 'Top-level class Wildcard has to reside in its own source file.'
class Wildcard {
  public static void foo(Collection < ? extends Wildcard[] > collection) {
    // 3 violations above:
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is preceded with whitespace.'
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
