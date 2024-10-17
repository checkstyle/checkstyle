package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputTryCatchIfElse {

  @interface TesterAnnotation {} //ok

  void foo() throws Exception {
    int a = 90;

    if (a == 1) {
    } else {}

    if (a == 1) {
    } else { }
    // violation above 'Empty blocks should have no spaces.'

    if (a == 45) {}

    if (a == 9) {} else {}

    try (MyResource r = new MyResource()) { }
    // violation above 'Empty blocks should have no spaces.'

    try (MyResource r = new MyResource()) {}

    try (MyResource r = new MyResource()) {} catch (Exception expected) {}

    try (MyResource r = new MyResource()) {} catch (Exception expected) { }
    // violation above 'Empty blocks should have no spaces.'

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {}

    try (MyResource r = new MyResource()) {
      // violation below 'Empty blocks should have no spaces.'
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
