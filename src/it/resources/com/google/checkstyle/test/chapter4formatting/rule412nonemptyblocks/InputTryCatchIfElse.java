package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputTryCatchIfElse {

  @interface TesterAnnotation {} //ok

  void foo() throws Exception {
    int a = 90;

    if (a == 1) {
    } else {} // violation ''}' at column 13 should be alone on a line.'

    if (a == 1) {
    } else { }
    // 2 violations above:
    //  'Empty blocks should have no spaces.'
    //  ''}' at column 14 should be alone on a line.'

    if (a == 45) {}

    if (a == 9) {} else {} // violation ''}' at column 26 should be alone on a line.'

    try (MyResource r = new MyResource()) { }
    // violation above 'Empty blocks should have no spaces.'

    try (MyResource r = new MyResource()) {}

    try (MyResource r = new MyResource()) {} catch (Exception expected) {}
    // violation above ''}' at column 74 should be alone on a line.'

    try (MyResource r = new MyResource()) {} catch (Exception expected) { }
    // 2 violations above:
    //  'Empty blocks should have no spaces.'
    //  ''}' at column 75 should be alone on a line.'

    try (MyResource r = new MyResource()) {
      // violation below ''}' at column 35 should be alone on a line.'
    } catch (Exception expected) {}

    try (MyResource r = new MyResource()) {
      // 2 violations 3 lines below:
      //  'Empty blocks should have no spaces.'
      //  ''}' at column 36 should be alone on a line.'
    } catch (Exception expected) { }

    try (MyResource r = new MyResource()) { ; }
    // violation above ''{' at column 43 should have line break after.'
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
