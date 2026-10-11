package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

/** Some javadoc. */
public class InputOneTopLevelClassBasic {
  /** Some javadoc. */
  public InputOneTopLevelClassBasic() throws CloneNotSupportedException {
    super.equals(new String());
    super.clone();
  }

  /** Some javadoc. */
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /** Some javadoc. */
  public void method() throws CloneNotSupportedException {
    super.clone();
  }

  {
    super.clone();
  }
}

// violation below 'Top-level class NoSuperClone has to reside in its own source file.'
class NoSuperClone {
  public Object clone() {
    return null;
  }
}

// violation below 'Top-level class InnerClone has to reside in its own source file.'
class InnerClone {
  public Object clone() {
    class Inner {
      public Object clone() throws CloneNotSupportedException {
        return super.clone();
      }
    }

    return null;
  }
}

// This could not pass as valid semantically but tests that
// type arguments are ignored when checking super calls
// violation below 'Top-level class CloneWithTypeArguments has to reside in its own source file.'
class CloneWithTypeArguments {
  // Some code
}

// violation below '.* class CloneWithTypeArgumentsAndNoSuper has to reside in .* own source file.'
class CloneWithTypeArgumentsAndNoSuper {}

// Check that super keyword isn't snagged here
// violation below '.* class MyClassWithGenericSuperMethod has to reside in its own source file.'
class MyClassWithGenericSuperMethod {
  void someMethod(java.util.List<? super java.util.Map> l) {
    // Some code
  }

  /**
   * Not a valid clone override. Should not get flagged.
   *
   * @param o some object
   * @return a cloned Object?
   */
  public static Object clone(Object o) {
    return null;
  }
}

// violation below 'Top-level class AnotherClass has to reside in its own source file.'
class AnotherClass {

  /**
   * Not a valid clone override. Should not get flagged.
   *
   * @param t some type
   * @param <T> a type
   * @return a cloned type?
   */
  public <T> T clone(T t) {
    return null;
  }
}
