/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCountDefaultsInnerInterface {

  /**
   * Dummy inner interface to check that the inner-interface methods are not counted
   * for the outer class.
   */
  public interface PublicMethodsInnerInterface {

    /** Dummy method doing nothing */
    public void doNothing60();

    /** Dummy method doing nothing */
    public void doNothing61();

    /** Dummy method doing nothing */
    public abstract void doNothing62();

    /** Dummy method doing nothing */
    abstract void doNothing63();

    /** Dummy method doing nothing */
    void doNothing64();
  }
}
