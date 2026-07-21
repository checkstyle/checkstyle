package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/** Some javadoc. */
public class InputLocalFinalVariableName {

  /** Some javadoc. */
  void methodWrongNaming() {
    final int BadName = 1;
    // violation above 'Local final variable name 'BadName' must match pattern'
    final int VARIABLE2 = 2;
    // 2 violations above:
    // 'VARIABLE2' must contain no more than '1' consecutive capital letters.'
    // 'Local final variable name 'VARIABLE2' must match pattern'
    final String FOO_2 = "foo";
    // 2 violations above:
    // 'FOO_2' must contain no more than '1' consecutive capital letters.'
    // 'Local final variable name 'FOO_2' must match pattern'
    final String A = "a";
    // violation above 'Local final variable name 'A' must match pattern'
    final boolean _wrong = true;
    // violation above 'Local final variable name '_wrong' must match pattern'
    final boolean badCharacter$ = true;
    // violation above 'Local final variable name '.*' must match pattern'
    final boolean a_a = true;
    // violation above 'Local final variable name 'a_a' must match pattern'
    final boolean aA = false;
    // violation above 'Local final variable name 'aA' must match pattern'
    final boolean SOLVE6X6 = true;
    // 2 violations above:
    // 'SOLVE6X6' must contain no more than '1' consecutive capital letters.'
    // 'Local final variable name 'SOLVE6X6' must match pattern'
  }

  /** Some javadoc. */
  void methodCorrectNaming() {
    final int badName = 1;

    final int variable = 2;

    final String foo2 = "foo";

    final String a = "a";

    final boolean wrong = true;

    final boolean badCharacter = true;

    final boolean aa = true;

    final boolean aaA = false;

    final boolean solve6x6 = true;
  }

  /** Some javadoc. */
  void foo(final String Name, final int Age) {
    // 2 violations above:
    // 'Parameter name 'Name' must match pattern'
    // 'Parameter name 'Age' must match pattern'
    try (final InputStreamReader In = new InputStreamReader(System.in);
        final OutputStreamWriter Out = new OutputStreamWriter(System.out)) {
      // violation 2 lines above 'Local final variable name 'In' must match pattern'
      // violation 2 lines above 'Local final variable name 'Out' must match pattern'
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Some javadoc. */
  void fooCorrect(final String name, final String age) {

    try (final InputStreamReader in = new InputStreamReader(System.in);
        final OutputStreamWriter out = new OutputStreamWriter(System.out)) {
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
