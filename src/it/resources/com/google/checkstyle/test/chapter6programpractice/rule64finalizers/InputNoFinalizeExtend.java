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
class EmptyFinalizer {

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    // empty finalize ()
  }
}

// fails to call superclass finalize method
class WithoutTryCatchFinalizer {

  public static void doStuff() {
    // This method do some stuff
  }

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    doStuff();
  }
}

// public finalizer
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
class InputSuperFinalizer {

  protected void finalize() throws Throwable { // violation 'Avoid using finalizer method.'
    super.finalize();
  }
}

// public finalizer
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

class WithoutMethods {}

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

interface WithFinalizer {
  void finalize(); // violation 'Avoid using finalizer method.'
}
