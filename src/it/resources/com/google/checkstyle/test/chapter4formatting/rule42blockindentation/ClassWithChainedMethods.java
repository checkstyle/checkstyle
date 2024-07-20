package com.google.checkstyle.test.chapter4formatting.rule42blockindentation; // indent:0 exp:0

public class ClassWithChainedMethods { // indent:0 exp:0

  public ClassWithChainedMethods(Object... params) { // indent:2 exp:2
  } // indent:2 exp:2

  public static void main(String[] args) { // indent:2 exp:2
    new ClassWithChainedMethods()
        .getInstance("string_one") // indent:4 exp:4
        // violation below ''method call' child has incorrect indentation level 4, expected .* 8.'
    .doNothing("string_one".trim(), "string_two"); // indent:15 exp:>=8

    int length =
        new ClassWithChainedMethods(
                "param1", // indent:4 exp:4
                "param2")
            .getInstance() // indent:32 exp:>=8
      // violation below ''.' has incorrect indentation level 6, expected level should be 8.'
      .doNothing("nothing")
      .length();
      // violation above ''.' has incorrect indentation level 6, expected level should be 8.'

    int length2 = // indent:4 exp:4
        // violation below ''new' has incorrect indentation level 4, expected level should be 8.'
    new ClassWithChainedMethods("param1", "param2")
            .getInstance() // indent:8 exp:8
            .doNothing("nothing") // indent:8 exp:8
            .length(); // indent:8 exp:8
  } // indent:2 exp:2

  public String doNothing(String something, String... uselessParams) { // indent:2 exp:2
    return something; // indent:4 exp:4
  } // indent:2 exp:2

  public ClassWithChainedMethods getInstance(String... uselessParams) { // indent:2 exp:2
    return this; // indent:4 exp:4
  } // indent:2 exp:2
} // indent:0 exp:0
