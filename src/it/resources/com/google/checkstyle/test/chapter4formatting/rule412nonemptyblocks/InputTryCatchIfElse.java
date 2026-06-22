package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputTryCatchIfElse {

  @interface TesterAnnotation {}

  void foo() throws Exception {
    int a = 90;
    boolean test = true; // violation 'Distance .* is .*, but allowed 3.'

    if (a == 1) {
    } else {}
    // violation above ''}' at column 13 should have line break before'

    if (a == 1) {
    } else { }
    // 2 violations above:
    // 'Empty blocks should have no spaces.'
    // ''}' at column 14 should have line break before'

    if (a == 45) {}

    if (a == 9) {} else {}
    // 2 violations above
    // ''}' at column 18 should have line break before'
    // ''}' at column 26 should have line break before'

    if (a == 99) {
      System.out.println("test");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else {
      System.out.println("before");
    }

    try (MyResource r = new MyResource()) { }
    // violation above 'Empty blocks should have no spaces.'

    try (MyResource r = new MyResource()) {}

    try (MyResource r = new MyResource()) {} catch (Exception expected) {}
    // 4 violations above:
    //                    'WhitespaceAround: '{' is not followed by whitespace.'
    //                    ''}' at column 44 should have line break before'
    //                    'Empty catch block'
    //                    ''}' at column 74 should be alone on a line'

    try (MyResource r = new MyResource()) {} catch (Exception expected) { }
    // 5 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    'WhitespaceAround: '{' is not followed by whitespace.'
    //                     ''}' at column 44 should have line break before'
    //                    'Empty catch block'
    //                     ''}' at column 75 should be alone on a line'

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {}
    // violation above 'Empty catch block'
    // violation 2 lines above ''}' at column 35 should be alone on a line.'

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) { }
    // 3 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    'Empty catch block'
    //                    ''}' at column 36 should be alone on a line.'

    try (MyResource r = new MyResource()) { ; }
    // violation above ''{' at column 43 should have line break after.'

    try {
      /* foo */

    } catch (Exception expected) {}
    // 2 violations above:
    //   'Empty catch block'
    //   ''}' at column 35 should be alone on a line.'

    try {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    catch (NullPointerException e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    catch (Exception e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    finally {
      test = true;
    }

    try {
      /* foo */
    } catch (NullPointerException e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    catch (Exception e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    finally {
      test = true;
    }

    try {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    catch (Exception e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    finally {
      test = true;
    }

    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    finally {
      test = true;
    }
  }

  /** Some javadoc. */
  public class MyResource implements AutoCloseable {
    /** Some javadoc. */
    @Override
    public void close() throws Exception {
      System.out.println("Closed MyResource");
    }
  }
}
