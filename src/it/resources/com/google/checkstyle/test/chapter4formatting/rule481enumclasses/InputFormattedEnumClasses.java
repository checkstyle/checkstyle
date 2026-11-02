package com.google.checkstyle.test.chapter4formatting.rule481enumclasses;

/** Some javadoc. */
public class InputFormattedEnumClasses {

  private enum MultipleEmptyLinesBefore {
    ONE,
    TWO,

    THREE
  }

  private enum MultipleEmptyLinesWithComments {

    /** Description. */
    ONE,

    // Some comment
    TWO
  }

  private enum MultipleEmptyLinesAfter {
    ONE,
    TWO,
    THREE
  }

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

  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  }
}
