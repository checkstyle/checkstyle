package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.ArrayList;

/** some javadoc. */
public class InputIllegalLineBreakAroundLambda {

  private interface MyLambdaInterface {
    int foo(int a, int b);
  };
  // violation above '';' should be separated from previous line.'

  void test() {
    MyLambdaInterface div = (a, b)
        -> {
          if (b != 0) {
            return a / b;
          }
          return 0;
        };
  }

  void test2(int day) {
    ArrayList<String> list = new ArrayList<>();
    list.stream()
        .map(x
            -> x.toString());

    String dayString = switch (day) {
      case 1 
          -> "one day of the week";
      case 2 
          -> 
              "two day of the week";
      case 3 -> 
          "third day of the week"; 
      default -> 
          throw new IllegalArgumentException();
    };
  }
 
  void test3(TransactionStatus transaction) {
    String status = switch (transaction) {
      case TransactionIsComplete -> "ok done";
      case NotValidTryAgain 
          -> 
              "Please Enter the valid value. Try again this time with valid value";
      case CouldNotBeginTheProcess -> 
          "Please try again after some time. Downtime.";
      case ErrorInProcessingTryAgain 
          ->  "Please try again after some time. you made a mistake or there is something wrong.";
      default -> 
          throw new IllegalArgumentException();
    };
  }

  /** some javadoc. */
  public enum TransactionStatus {
    NotValidTryAgain,
    ErrorInProcessingTryAgain,
    TransactionIsComplete,
    CouldNotBeginTheProcess
  } 
}
