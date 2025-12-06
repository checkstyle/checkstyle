package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

// violation below ''abstract' modifier out of order with the JLS suggestions.'
strictfp abstract class InputModifierOrder {
  transient private String dontSaveMe;
  // violation above ''private' modifier out of order with the JLS suggestions.'

  volatile public int whatImReading;
  // violation above ''public' modifier out of order with the JLS suggestions.'

  public volatile boolean ssModifierOrderVar = false;

  /**
   * Illegal order of modifiers for methods. Make sure that the first and last modifier from the JLS
   * sequence is used.
   */
  strictfp private void doStuff() {}
  // violation above ''private' modifier out of order with the JLS suggestions.'

  /** Single annotation without other modifiers. */
  @MyAnnotation2 void someMethod() {}

  /** Illegal order of annotation - must come first. */
  private @MyAnnotation2 void someMethod2() {}
  // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

  /** Annotation in middle of other modifiers otherwise in correct order. */
  private @MyAnnotation2 strictfp void someMethod3() {}
  // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

  /** Correct order. */
  @MyAnnotation2 private strictfp void someMethod4() {}

  /** Annotation in middle of other modifiers otherwise in correct order. */
  @MyAnnotation2 public static @MyAnnotation4 strictfp void someMethod5() {}
  // violation above ''@MyAnnotation4' .* does not precede non-annotation modifiers.'

  @MyAnnotation2 public static final synchronized strictfp void fooMethod() {}

  // violation below ''protected' modifier out of order with the JLS suggestions.'
  strictfp protected final @MyAnnotation2 static synchronized void fooMethod1() {}

  // violation below ''@MyAnnotation2' .* does not precede non-annotation modifiers.'
  synchronized @MyAnnotation2 strictfp private final static void fooMethod2() {}

  // violation below ''final' modifier out of order with the JLS suggestions.'
  @MyAnnotation2 static synchronized final strictfp protected void fooMethod3() {}

  // violation below ''static' modifier out of order with the JLS suggestions.'
  @MyAnnotation2 strictfp static final synchronized private void fooMethod4() {}

  // violation below ''final' modifier out of order with the JLS suggestions.'
  synchronized final strictfp @MyAnnotation2 static public void fooMethod5() {}

  // violation below ''private' modifier out of order with the JLS suggestions.'
  @MyAnnotation2 static synchronized strictfp private final void fooMethod6() {}

  // violation below ''synchronized' modifier out of order with the JLS suggestions.'
  final strictfp synchronized static protected @MyAnnotation2 void fooMethod7() {}

  // violation below ''protected' modifier out of order with the JLS suggestions.'
  @MyAnnotation2 abstract protected void fooMet();

  // violation below ''@MyAnnotation2' .* does not precede non-annotation modifiers.'
  abstract @MyAnnotation2 public void fooMet1();

  /** Holder for redundant 'public' modifier check. */
  public static interface InputRedundantPublicModifier {
    public void abc1();

    void abc2();

    abstract void abc3();

    public float PI_PUBLIC = (float) 3.14;

    final float PI_FINAL = (float) 3.14;

    /** All OK. */
    float PI_OK = (float) 3.14;
  }

  // violation below ''private' modifier out of order with the JLS suggestions.'
  final private void method() {}
}

// violation below 'Top-level class RedundantFinalClass has to reside in its own source file.'
final class RedundantFinalClass {
  public final void finalMethod() {}

  public void method() {}

  protected @MyAnnotation2 static synchronized native void fooMethod();
  // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

  static protected @MyAnnotation2 synchronized native void fooMethod1();
  // violation above ''protected' modifier out of order with the JLS suggestions.'

  @MyAnnotation2 protected synchronized native void fooMethod2();

  native synchronized protected static @MyAnnotation2 void fooMethod3();
  // violation above ''synchronized' modifier out of order with the JLS suggestions.'

  native @MyAnnotation2 protected static synchronized void fooMethod4();
  // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

  public static @MyAnnotation2 synchronized native void fooMethod5();
  // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

  synchronized static native @MyAnnotation2 public void fooMethod6();
  // violation above ''static' modifier out of order with the JLS suggestions.'

  static synchronized private native @MyAnnotation2 void fooMethod7();
  // violation above ''private' modifier out of order with the JLS suggestions.'
}

// violation 2 lines below 'Top-level class .* has to reside in its own source file.'
/** Holder for redundant modifiers of inner implementation. */
interface InnerImplementation {
  InnerImplementation inner =
          new InnerImplementation() {
            /** Compiler requires 'public' modifier. */
            public void method() {}
          };

  void method();
}

// violation below 'Top-level class WithInner has to reside in its own source file.'
class WithInner {
  /**
   * Inner class.
   *
   * @author max
   */
  class Inner {
    transient private String dontSaveMe;
    // violation above ''private' modifier out of order with the JLS suggestions.'

    volatile public int whatImReading;
    // violation above ''public' modifier out of order with the JLS suggestions.'

    @MyAnnotation2 protected synchronized native void fooMethod();

    protected @MyAnnotation2 synchronized native void fooMethod1();
    // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

    synchronized protected @MyAnnotation2 native void fooMethod2();
    // violation above ''protected' modifier out of order with the JLS suggestions.'

    native synchronized protected @MyAnnotation2 void fooMethod3();
    // violation above ''synchronized' modifier out of order with the JLS suggestions.'

    native @MyAnnotation2 protected synchronized void fooMethod4();
    // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

    public @MyAnnotation2 synchronized native void fooMethod5();
    // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

    synchronized native @MyAnnotation2 public void fooMethod6();
    // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

    synchronized private native @MyAnnotation2 void fooMethod7();
    // violation above ''private' modifier out of order with the JLS suggestions.'

    /** Anonymous class. */
    InnerImplementation foo =
            new InnerImplementation() {

              public void method() {
                // OOOO Auto-generated method stub

              }

              transient private String dontSaveMe;
              // violation above ''private' modifier out of order with the JLS suggestions.'

              volatile public int whatImReading;
              // violation above ''public' modifier out of order with the JLS suggestions.'

              protected @MyAnnotation2 synchronized native void fooMethod();
              // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

              protected @MyAnnotation2 synchronized native void fooMethod1();
              // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

              synchronized protected @MyAnnotation2 native void fooMethod2();
              // violation above ''protected' modifier out of order with the JLS suggestions.'

              native synchronized protected @MyAnnotation2 void fooMethod3();
              // violation above ''synchronized' modifier out of order with the JLS suggestions.'

              @MyAnnotation2 protected synchronized native void fooMethod4();

              public @MyAnnotation2 synchronized native void fooMethod5();
              // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

              synchronized native @MyAnnotation2 public void fooMethod6();
              // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'

              synchronized private native @MyAnnotation2 void fooMethod7();
              // violation above ''private' modifier out of order with the JLS suggestions.'
            };
  }

  /**
   * Inner abstract class.
   *
   * @author max
   */
  abstract class AbsInner {
    transient private String dontSaveMe;
    // violation above ''private' modifier out of order with the JLS suggestions.'

    volatile public int whatImReading;
    // violation above ''public' modifier out of order with the JLS suggestions.'

    @MyAnnotation2 public final synchronized strictfp void fooMethod() {}

    // violation below ''protected' modifier out of order with the JLS suggestions.'
    strictfp protected final @MyAnnotation2 synchronized void fooMethod1() {}

    // violation below ''@MyAnnotation2' .* does not precede non-annotation modifiers.'
    synchronized @MyAnnotation2 strictfp private final void fooMethod2() {}

    // violation below ''final' modifier out of order with the JLS suggestions.'
    @MyAnnotation2 synchronized final strictfp protected void fooMethod3() {}

    // violation below ''final' modifier out of order with the JLS suggestions.'
    @MyAnnotation2 strictfp final synchronized private void fooMethod4() {}

    // violation below ''final' modifier out of order with the JLS suggestions.'
    synchronized final strictfp @MyAnnotation2 public void fooMethod5() {}

    // violation below ''private' modifier out of order with the JLS suggestions.'
    @MyAnnotation2 synchronized strictfp private final void fooMethod6() {}

    // violation below ''synchronized' modifier out of order with the JLS suggestions.'
    final strictfp synchronized protected @MyAnnotation2 void fooMethod7() {}

    @MyAnnotation2 abstract protected void fooMet();
    // violation above ''protected' modifier out of order with the JLS suggestions.'

    abstract @MyAnnotation2 public void fooMet1();
    // violation above ''@MyAnnotation2' .* does not precede non-annotation modifiers.'
  }
}

// violation 2 lines below 'Top-level class Annotation has to reside in its own source file.'
/** Holder for redundant modifiers of annotation fields/variables. */
@interface Annotation {
  public String s1 = "";
  final String s2 = "";
  static String s3 = "";
  String s4 = "";

  public String blah();

  abstract String blah2();
}

// violation below 'Top-level class MyAnnotation2 has to reside in its own source file.'
@interface MyAnnotation2 {}

// violation below 'Top-level class MyAnnotation4 has to reside in its own source file.'
@interface MyAnnotation4 {}

// violation 2 lines below 'Top-level class .* has to reside in its own source file.'
/** Illegal order of modifiers for interface methods. */
interface InputModifierOrderInterface {
  default strictfp void abc1() {}

  strictfp default void abc2() {}
  // violation above ''default' modifier out of order with the JLS suggestions.'
}
