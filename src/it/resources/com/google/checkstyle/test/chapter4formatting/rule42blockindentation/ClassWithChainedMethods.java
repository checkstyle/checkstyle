package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** some javadoc. */
public class ClassWithChainedMethods {

  public ClassWithChainedMethods(Object... params) {}

  /** some javadoc. */
  public static void main(String[] args) {
    new ClassWithChainedMethods()
        .getInstance("string_one")
    .doNothing("string_one".trim(), "string_two");
    // violation above ''method call' child has incorrect indentation level 4, expected .* 8.'

    int length =
    new ClassWithChainedMethods("param1", "param2").getInstance().doNothing("nothing").length();
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'

    int length2 =
    new ClassWithChainedMethods("param1", "param2").getInstance().doNothing("nothing").length();
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'
  }

  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  public ClassWithChainedMethods getInstance(String... uselessParams) {
    return this;
  }
}
