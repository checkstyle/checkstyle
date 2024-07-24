package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

/** Some javadoc. */
public class InputOneTopLevelClassGood { // ok
  public InputOneTopLevelClassGood() throws CloneNotSupportedException {
    super.equals(new String());
    super.clone();
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public void method() throws CloneNotSupportedException {
    super.clone();
  }

  {
    super.clone();
  }
}
