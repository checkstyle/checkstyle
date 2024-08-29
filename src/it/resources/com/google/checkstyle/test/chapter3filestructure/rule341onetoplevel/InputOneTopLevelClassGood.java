package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

/** Some javadoc. */
public class InputOneTopLevelClassGood { // ok
  /** Some javadoc. */
  public InputOneTopLevelClassGood() throws CloneNotSupportedException {
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
