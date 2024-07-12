package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

class InputNoFinalizeExtend {

  public static void doStuff() {
    // This method do some stuff
  }

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    try {
      doStuff();
    } finally {
      super.finalize();
    }
  }
}

// negates effect of superclass finalizer
// violation below 'Top-level class EmptyFinalizer has to reside in its own source file.'
class EmptyFinalizer {

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    // empty finalize ()
  }
}

// fails to call superclass finalize method
// violation below 'Top-level class WithoutTryCatchFinalizer has to reside in its own source file.'
class WithoutTryCatchFinalizer {

  public static void doStuff() {
    // This method do some stuff
  }

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    doStuff();
  }
}

// public finalizer
// violation below 'Top-level class InputPublicFinalizer has to reside in its own source file.'
class InputPublicFinalizer {

  public static void doStuff() {
    // This method do some stuff
  }

  public void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    try {
      doStuff();
    } finally {
      super.finalize();
    }
  }
}

// unless (or worse) finalizer
// violation below 'Top-level class InputSuperFinalizer has to reside in its own source file.'
class InputSuperFinalizer {

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    super.finalize();
  }
}

// public finalizer
// violation below 'Top-level class InputStaticFinalizer has to reside in its own source file.'
class InputStaticFinalizer {

  public static void doStuff() {
    // This method do some stuff
  }

  protected void finalize() { // violation 'Avoid using finalizer method.'
    try {
      doStuff();
    } finally {
      // do nothing
    }
  }

  class InnerFinalizer {

    protected void finalize() { // violation 'Avoid using finalizer method.'
      try {
        doStuff();
      } finally {
        // do nothing
      }
    }
  }
}

// violation below 'Top-level class WithoutFinalize has to reside in its own source file.'
class WithoutFinalize {
  public void doStuff() {
    // This method do some stuff
  }

  public void finalizeMe() {
    // This method do some stuff
  }

  public void doFinalize() {
    // This method do some stuff
  }
}

// violation below 'Top-level class WithoutMethods has to reside in its own source file.'
class WithoutMethods {}

// violation below 'Top-level class WithAnonymousClass has to reside in its own source file.'
class WithAnonymousClass {

  public static void doStuff() {
    // This method do some stuff
  }

  public void foo() {

    Ball b =
        new Ball() {

          public void hit() {
            System.identityHashCode("You hit it!");
          }

          protected void finalize() { // violation 'Avoid using finalizer method.'
            try {
              doStuff();
            } finally {
              // do nothing
            }
          }
        };
    b.hit();
  }

  interface Ball {
    void hit();
  }
}

// violation below 'Top-level class WithFinalizer has to reside in its own source file.'
interface WithFinalizer {
  void finalize(); // violation 'Avoid using finalizer method.'
}
