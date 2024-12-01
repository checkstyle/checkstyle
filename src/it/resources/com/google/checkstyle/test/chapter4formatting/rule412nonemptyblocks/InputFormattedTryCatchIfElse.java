package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedTryCatchIfElse {

  @interface TesterAnnotation {}

  void foo() throws Exception {
    int a = 90;
    boolean test = true;

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

    if (a == 99) {
      System.out.println("test");
    } else {
      System.out.println("before");
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

    try {
      /* foo */
    } catch (NullPointerException e) {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
      test = true;
    }

    try {
      /* foo */
    } catch (NullPointerException e) {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
      test = true;
    }

    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
      test = true;
    }

    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
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
