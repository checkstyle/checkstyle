package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.ArrayList;

/** Some javadoc. */
public class InputIllegalLineBreakAroundLambda {

  private interface MyLambdaInterface {
    int foo(int a, int b);
  }

  void test() {
    MyLambdaInterface div = (a, b)
        -> { // illegal line break before lambda, ok until #17253
          if (b != 0) {
            return a / b;
          }
          return 0;
        };

    // violation 2 lines below ''{' at column 7 should be on the previous line.'
    MyLambdaInterface div2 = (ab, bc) ->
      {
        if (ab != 0) {
          return ab / 2;
        }
        return 0;
      };

    MyLambdaInterface div3 = (ab, bc) -> {
      if (ab != 0) {
        return ab / 2;
      }
      return 0;
    };
  }

  void test2(int day) {
    ArrayList<String> list = new ArrayList<>();
    list.stream()
        .map(x
            -> x.toString()); // illegal line break before lambda, ok until #17253

    String dayString = switch (day) {
      case 1
          -> "one day of the week";  // illegal line break before lambda, ok until #17253
      case 2
          -> // illegal line break before lambda, ok until #17253
              "two day of the week";
      case 3 -> // ok because text following '->' is unbraced.
          "third day of the week";
      default ->  // ok because text following '->' is unbraced.
          throw new IllegalArgumentException();
    };
  }

  void test3(TransactionStatus transaction) {
    String status = switch (transaction) {
      case TransactionIsComplete -> "ok done";
      case NotValidTryAgain
          -> // illegal line break before lambda, ok until #17253
              "Please Enter the valid value. Try again this time with valid value";
      case CouldNotBeginTheProcess ->
          "Please try again after some time. Downtime.";
      case ErrorInProcessingTryAgain
          ->  "Please try again after some time. you made a mistake or there is something wrong.";
      // illegal line break before lambda above, ok until #17253
      default ->
          throw new IllegalArgumentException("");
    };
  }

  /** Some javadoc. */
  public enum TransactionStatus {
    NotValidTryAgain,
    ErrorInProcessingTryAgain,
    TransactionIsComplete,
    CouldNotBeginTheProcess
  }
}
