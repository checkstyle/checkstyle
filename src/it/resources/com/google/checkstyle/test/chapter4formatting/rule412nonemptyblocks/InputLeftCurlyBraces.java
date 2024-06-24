package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyBraces
{ // violation ''{' at column 1 should be on the previous line.'
  /** @return helper func **/
  boolean condition()
  { // violation ''{' at column 5 should be on the previous line.'
    return false;
  }

  /** Test do/while loops **/
  void testDoWhile()
  { // violation ''{' at column 5 should be on the previous line.'

    do {
      testDoWhile();
    }
    while (condition());


    do testDoWhile(); while (condition());
  }

  /** Test while loops **/
  void testWhile()
  { // violation ''{' at column 5 should be on the previous line.'

    while (condition()) {
      testWhile();
    }


    while(condition());
    while (condition())
      testWhile();
    while (condition())
      if (condition())
        testWhile();
  }

  /** Test for loops **/
  void testFor()
  {  // violation ''{' at column 5 should be on the previous line.'

    for (int i = 1; i < 5; i++) {
      testFor();
    }


    for(int i = 1;i < 5;i++);
    for (int i = 1; i < 5; i++)
      testFor();
    for (int i = 1; i < 5;
         i++)
      if (i > 2)
        testFor();
  }

  /** Test if constructs **/
  public void testIf()
  { // violation ''{' at column 5 should be on the previous line.'

    if (condition()) {
      testIf();
    }
    else if (condition()) {
      testIf();
    }
    else {
      testIf();
    }


    if (condition());
    if (condition())
      testIf();
    if (condition())
      testIf();
    else
      testIf();
    if (condition())
      testIf();
    else {
      testIf();
    }
    if (condition()) {
      testIf();
    }
    else
      testIf();
    if (condition())
      if (condition())
        testIf();
  }

  void whitespaceAfterSemi()
  { // violation ''{' at column 5 should be on the previous line.'

    int i = 1;int j = 2;


    for (;;) {
    }
  }

  /** Empty constructor block. **/
  public InputLeftCurlyBraces() {}

  /** Empty method block. **/
  public void emptyImplementation() {}
}

class EnumContainerLeft {
  private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS } // ok
}

class WithArraysLeft { // ok
  String[] s = {""}; // ok
  String[] empty = {}; // ok
  String[] s1 = { // ok
          "foo", "foo",
  };
  String[] s2 =
          { // ok
                  "foo", "foo",
          };
  String[] s3 =
          { // ok
                  "foo",
                  "foo",
          };
  String[] s4 =
          {"foo", "foo"}; // ok
}
