package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedTryCatchIfElse {

  @interface TesterAnnotation {} // ok

  void foo() throws Exception {
    int a = 90;

    if (a == 1) {
    } else {
    }

    if (a == 1) {
    } else {
    }

    if (a == 45) {}

    if (a == 9) {
    } else {
    }

    try (MyResource r = new MyResource()) {}

    try (MyResource r = new MyResource()) {}

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {
    }

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {
    }

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {
    }

    try (MyResource r = new MyResource()) {
    } catch (Exception expected) {
    }

    try (MyResource r = new MyResource()) {
      ;
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
