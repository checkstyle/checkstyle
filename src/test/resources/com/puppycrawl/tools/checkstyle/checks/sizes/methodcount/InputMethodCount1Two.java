/*
MethodCount
maxTotal = 3
maxPrivate = 3
maxPackage = 3
maxProtected = 3
maxPublic = 3
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCount1Two { // 5 violations

  /**
   * Dummy inner class to check that the inner-classes methods are not counted
   * for the outer class.
   */
  public class PublicMethodsInnerclassInnerclass { // 2 violations
    /**
     * Dummy method doing nothing
     */
    public void doNothing50() {
    }

    /**
     * Dummy method doing nothing
     */
    public void doNothing51() {
    }

    /**
     * Dummy method doing nothing
     */
    public void doNothing52() {
    }

    /**
     * Dummy method doing nothing
     */
    public void doNothing53() {
    }

    /**
     * Dummy method doing nothing
     */
    public void doNothing54() {
    }
  }

  /**
   * Dummy inner class to check that the inner-classes methods are not counted
   * for the outer class.
   */
  public interface PublicMethodsInnerInterface { // 2 violations
    /**
     * Dummy method doing nothing
     */
    public void doNothing60();

    /**
     * Dummy method doing nothing
     */
    public void doNothing61();

    /**
     * Dummy method doing nothing
     */
    public abstract void doNothing62();

    /**
     * Dummy method doing nothing
     */
    abstract void doNothing63();

    /**
     * Dummy method doing nothing
     */
    void doNothing64();
  }

  /**
   * Dummy method doing nothing
   */
  private void doNothing31() {
  }

  /**
   * Dummy method doing nothing
   */
  private void doNothing32() {
  }

  /**
   * Dummy method doing nothing
   */
  private void doNothing33() {
  }

  /**
   * Dummy method doing nothing
   */
  private void doNothing34() {
  }
}
