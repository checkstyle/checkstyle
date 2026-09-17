package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Some javadoc. */
public class InputMultilineCommentLeadingAsterisk {

  // violation below 'Multiline comment line(s) 8, 9 should start with leading asterisk'
  /*
    Some Comment
    Some Comment2
  */

  // violation below 'Multiline comment line(s) 14, 15 should start with leading asterisk'
  /*
    This is
    okay.
  */

  // violation below 'Multiline comment line(s) 20, 22, 24 should start with leading asterisk'
  /* Line 1
    Line 2
  * Line 3
    Line 4
  * Line 5
    Line 6
  */

  // violation below 'Multiline comment line(s) 29 should start with leading asterisk'
  /*
  Some Comment */

  /** Bad Examples. */
  public void missingLeadingAsterisk() {}

  /*
   * Some Comment
   * Some Comment2
   */

  /*
   * This is
   * okay.
   */

  /* Line 1
   * Line 2
   * Line 3
   * Line 4
   * Line 5
   * Line 6
   */

  /*
   * Some Comment */

  /** Good Examples. */
  public void presentLeadingAsterisk() {}

  /** Some javadoc. */
  public native void doSomething() /*-{
    console.log("hello from JS");
    this.@com.example.MyClass::someJavaMethod()();
  }-*/;

  /** Some javadoc. */
  public native void logName() /*-{
    var name = this.@com.example.MyClass::name;
    console.log(name);
  }-*/;

  /** Some javadoc. */
  public native String getProperty() /*-{
    return "value";
  }-*/;
}
