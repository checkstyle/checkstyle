package com.google.checkstyle.test.chapter4formatting.rule481enumclasses;
public class InputFormattedEnumClasses {
  enum Answer {
    YES,

    NO,
    MAYBE
  }

  enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES
  }

  enum WithConstantBody {
    YES {
      @Override
      public String toString() {
        return "yes";
      }
    },
    NO
  }
}
