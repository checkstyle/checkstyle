package com.google.checkstyle.test.chapter4formatting.rule481enumclasses;

/** Some javadoc. */
public class InputEnumClasses {

  private enum MultipleEmptyLinesBefore {


    ONE, // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
    TWO,


    THREE // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
  }

  private enum MultipleEmptyLinesWithComments {


    /** Description. */
    ONE, // violation above ''/\*' has more than 1 empty lines before.'


    // Some comment
    TWO // violation above ''//' has more than 1 empty lines before.'
  }

  private enum MultipleEmptyLinesAfter {
    ONE,
    TWO,
    THREE // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines after.'


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
