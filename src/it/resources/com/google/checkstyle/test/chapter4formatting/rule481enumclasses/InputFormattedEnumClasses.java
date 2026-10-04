package com.google.checkstyle.test.chapter4formatting.rule481enumclasses;

/** Some javadoc. */
public class InputFormattedEnumClasses {

  private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }

  private enum Answer {
    YES {
      @Override
      public String toString() {
        return "yes";
      }
    },

    NO,
    MAYBE
  }

  private enum ValidSpacing {
    ONE,
    TWO,
    THREE
  }

  private enum ValidSpacingWithEmptyLines {
    ONE,

    TWO,

    THREE
  }

  private enum ValidWithComments {
    /** Description for ONE. */
    ONE,

    // Comment for TWO
    TWO
  }

  private enum ValidWithMembers {
    ADD(1),
    SUB(2);

    private final int code;

    ValidWithMembers(int code) {
      this.code = code;
    }

    public int getCode() {
      return code;
    }
  }
}
