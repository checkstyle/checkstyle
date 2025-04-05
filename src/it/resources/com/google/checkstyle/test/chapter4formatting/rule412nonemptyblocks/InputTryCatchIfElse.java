package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputTryCatchIfElse {

  @interface TesterAnnotation {} //ok

  void foo() throws Exception {
    int a = 90;
    boolean test = true;

    if (a == 1) {
    } else {}

    if (a == 1) {
    } else { }
    // violation above 'Empty blocks should have no spaces.'

    if (a == 45) {}

    if (a == 9) {} else {}

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
    // 2 violations above:
    //                    'WhitespaceAround: '{' is not followed by whitespace.'
    //                    ''}' at column 74 should be alone on a line.'

    try (MyResource r = new MyResource()) {} catch (Exception expected) { }
    // 3 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    'WhitespaceAround: '{' is not followed by whitespace.'
    //                    ''}' at column 75 should be alone on a line.'

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {}
    // violation above ''}' at column 35 should be alone on a line.'

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) { }
    // 2 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    ''}' at column 36 should be alone on a line.'

    try (MyResource r = new MyResource()) { ; }
    // violation above ''{' at column 43 should have line break after.'

    try {
      /* foo */
      // violation below ''}' at column 35 should be alone on a line.'
    } catch (Exception expected) {}

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

  /** some javadoc. */
  public class MyResource implements AutoCloseable {
    /** some javadoc. */
    @Override
    public void close() throws Exception {
      System.out.println("Closed MyResource");
    }
  }
}
