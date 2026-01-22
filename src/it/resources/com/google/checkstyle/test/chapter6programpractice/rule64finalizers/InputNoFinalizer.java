package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

/** Some javadoc. */
public class InputNoFinalizer {

  /** Some javadoc. */
  public void finalize() { // violation 'Avoid using finalizer method.'
    // It's not enough to check if the METHOD_DEF branch contains a PARAMETER_DEF, as that would
    // treat this method as having a parameter.
    Runnable runnable =
        new Runnable() {

          public void run() {
            reallyFinalize("hi");
          }

          // generates a PARAMETER_DEF AST inside the METHOD_DEF of finalize()
          private void reallyFinalize(String s) {}
        };
    runnable.run();
  }

  /** Should not be reported by NoFinalizer check. */
  public void finalize(String x) {}
}
