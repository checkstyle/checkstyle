package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

abstract strictfp class InputFormattedModifierOrder {
  private transient String dontSaveMe;

  public volatile int whatImReading;

  public volatile boolean ssModifierOrderVar = false;

  /**
   * Illegal order of modifiers for methods. Make sure that the first and last modifier from the JLS
   * sequence is used.
   */
  private strictfp void doStuff() {}

  /** Single annotation without other modifiers. */
  @MyAnnotationFormatted
  void someMethod() {}

  /** Illegal order of annotation - must come first. */
  private @MyAnnotationFormatted void someMethod2() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  /** Annotation in middle of other modifiers otherwise in correct order. */
  private @MyAnnotationFormatted strictfp void someMethod3() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  /** Correct order. */
  @MyAnnotationFormatted
  private strictfp void someMethod4() {}

  /** Annotation in middle of other modifiers otherwise in correct order. */
  @MyAnnotationFormatted
  public static @MyAnnotationFormatted2 strictfp void someMethod5() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  @MyAnnotationFormatted
  public static final synchronized strictfp void fooMethod() {}

  protected final strictfp @MyAnnotationFormatted static synchronized void fooMethod1() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  synchronized @MyAnnotationFormatted private static final strictfp void fooMethod2() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  @MyAnnotationFormatted
  protected static final synchronized strictfp void fooMethod3() {}

  @MyAnnotationFormatted
  private static final synchronized strictfp void fooMethod4() {}

  final synchronized strictfp @MyAnnotationFormatted public static void fooMethod5() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  @MyAnnotationFormatted
  private static final synchronized strictfp void fooMethod6() {}

  protected static final synchronized strictfp @MyAnnotationFormatted void fooMethod7() {}

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  @MyAnnotationFormatted
  protected abstract void fooMet();

  abstract @MyAnnotationFormatted public void fooMet1();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

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

  private final void method() {}
}

// violation below 'Top-level class RedundantFinalClass2 has to reside in its own source file.'
final class RedundantFinalClass2 {
  public final void finalMethod() {}

  public void method() {}

  protected @MyAnnotationFormatted static synchronized native void fooMethod();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  protected static @MyAnnotationFormatted synchronized native void fooMethod1();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  @MyAnnotationFormatted
  protected synchronized native void fooMethod2();

  protected static synchronized native @MyAnnotationFormatted void fooMethod3();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  native @MyAnnotationFormatted protected static synchronized void fooMethod4();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  public static @MyAnnotationFormatted synchronized native void fooMethod5();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  static synchronized native @MyAnnotationFormatted public void fooMethod6();

  // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

  private static synchronized native @MyAnnotationFormatted void fooMethod7();
  // violation above '.* annotation modifier does not precede non-annotation modifiers.'
}

// violation 2 lines below 'Top-level class .* has to reside in its own source file.'
/** Holder for redundant modifiers of inner implementation. */
interface InnerImplementation2 {
  InnerImplementation2 inner =
      new InnerImplementation2() {
        /** Compiler requires 'public' modifier. */
        public void method() {}
      };

  void method();
}

// violation below 'Top-level class WithInner2 has to reside in its own source file.'
class WithInner2 {
  /**
   * Inner class.
   *
   * @author max
   */
  class Inner {
    private transient String dontSaveMe;

    public volatile int whatImReading;

    @MyAnnotationFormatted
    protected synchronized native void fooMethod();

    protected @MyAnnotationFormatted synchronized native void fooMethod1();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    protected synchronized @MyAnnotationFormatted native void fooMethod2();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    protected synchronized native @MyAnnotationFormatted void fooMethod3();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    native @MyAnnotationFormatted protected synchronized void fooMethod4();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    public @MyAnnotationFormatted synchronized native void fooMethod5();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    synchronized native @MyAnnotationFormatted public void fooMethod6();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    private synchronized native @MyAnnotationFormatted void fooMethod7();

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    /** Anonymous class. */
    InnerImplementation2 foo =
        new InnerImplementation2() {

          public void method() {
            // OOOO Auto-generated method stub

          }

          private transient String dontSaveMe;

          public volatile int whatImReading;

          protected @MyAnnotationFormatted synchronized native void fooMethod();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          protected @MyAnnotationFormatted synchronized native void fooMethod1();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          protected synchronized @MyAnnotationFormatted native void fooMethod2();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          protected synchronized native @MyAnnotationFormatted void fooMethod3();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          @MyAnnotationFormatted
          protected synchronized native void fooMethod4();

          public @MyAnnotationFormatted synchronized native void fooMethod5();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          synchronized native @MyAnnotationFormatted public void fooMethod6();

          // violation 2 lines above '.* annotation .* does not precede non-annotation modifiers.'

          private synchronized native @MyAnnotationFormatted void fooMethod7();
          // violation above '.* annotation modifier does not precede non-annotation modifiers.'
        };
  }

  /**
   * Inner abstract class.
   *
   * @author max
   */
  abstract class AbsInner {
    private transient String dontSaveMe;

    public volatile int whatImReading;

    @MyAnnotationFormatted
    public final synchronized strictfp void fooMethod() {}

    protected final strictfp @MyAnnotationFormatted synchronized void fooMethod1() {}

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    synchronized @MyAnnotationFormatted private final strictfp void fooMethod2() {}

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    @MyAnnotationFormatted
    protected final synchronized strictfp void fooMethod3() {}

    @MyAnnotationFormatted
    private final synchronized strictfp void fooMethod4() {}

    final synchronized strictfp @MyAnnotationFormatted public void fooMethod5() {}

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    @MyAnnotationFormatted
    private final synchronized strictfp void fooMethod6() {}

    protected final synchronized strictfp @MyAnnotationFormatted void fooMethod7() {}

    // violation 2 lines above '.* annotation modifier does not precede non-annotation modifiers.'

    @MyAnnotationFormatted
    protected abstract void fooMet();

    abstract @MyAnnotationFormatted public void fooMet1();
    // violation above '.* annotation modifier does not precede non-annotation modifiers.'
  }
}

// violation 2 lines below 'Top-level class Annotation2 has to reside in its own source file.'
/** Holder for redundant modifiers of annotation fields/variables. */
@interface Annotation2 {
  public String s1 = "";
  final String s2 = "";
  static String s3 = "";
  String s4 = "";

  public String blah();

  abstract String blah2();
}

// violation below 'Top-level class MyAnnotationFormatted has to reside in its own source file.'
@interface MyAnnotationFormatted {}

// violation below 'Top-level class MyAnnotationFormatted2 has to reside in its own source file.'
@interface MyAnnotationFormatted2 {}

// violation 2 lines below 'Top-level class .* has to reside in its own source file.'
/** Illegal order of modifiers for interface methods. */
interface InputModifierOrderInterface2 {
  default strictfp void abc1() {}

  default strictfp void abc2() {}
}
