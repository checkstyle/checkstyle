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

public class InputMethodCountDefaultsInnerClass {

  /**
   * Dummy inner class to check that the inner-class methods are not counted
   * for the outer class.
   */
  public class PublicMethodsInnerclassInnerclass {

    /** Dummy method doing nothing */
    public void doNothing50() {
    }

    /** Dummy method doing nothing */
    public void doNothing51() {
    }

    /** Dummy method doing nothing */
    public void doNothing52() {
    }

    /** Dummy method doing nothing */
    public void doNothing53() {
    }

    /** Dummy method doing nothing */
    public void doNothing54() {
    }
  }
}
