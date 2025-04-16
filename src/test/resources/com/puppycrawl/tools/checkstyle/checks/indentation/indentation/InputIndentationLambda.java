//a comment //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationLambda { //indent:0 exp:0
  java.util.logging.Logger LOG =  java.util.logging.Logger.getLogger(""); //indent:2 exp:2
  interface Printer //indent:2 exp:2
  { //indent:2 exp:2
    void print(String s); //indent:4 exp:4
  } //indent:2 exp:2

  class LongTypeName { //indent:2 exp:2
  } //indent:2 exp:2

  interface SomeInterface { //indent:2 exp:2
    void someFunction(InputIndentationLambda.LongTypeName arg); //indent:4 exp:4
  } //indent:2 exp:2

  void function1(Runnable x) { //indent:2 exp:2
    Runnable r1 = () -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }; //indent:4 exp:4

    Runnable r2 = () -> { x.run(); }; //indent:4 exp:4

    Runnable r3 = () -> //indent:4 exp:4
        x.run(); //indent:8 exp:8

    Runnable r4 = () -> x.run(); //indent:4 exp:4

    InputIndentationLambda.Printer r5 = s -> LOG.info(s); //indent:4 exp:4

    InputIndentationLambda.Printer r6 = s -> String.CASE_INSENSITIVE_ORDER //indent:4 exp:4
        .equals(s); //indent:8 exp:8

    Runnable r7 = () //indent:4 exp:4
        -> //indent:8 exp:8
     { //indent:5 exp:4,8 warn
     }; //indent:5 exp:4,8 warn

    Runnable r8 = //indent:4 exp:4
         () //indent:9 exp:9
           -> //indent:11 exp:12 warn
         {}; //indent:9 exp:8 warn

    Runnable r9 = //indent:4 exp:4
        () //indent:8 exp:8
            -> //indent:12 exp:12
        {}; //indent:8 exp:8

    Object o = new Thread(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }); //indent:4 exp:4

    Runnable r01 = () -> { //indent:4 exp:4
       x.run(); //indent:7 exp:6 warn
     }; //indent:5 exp:4 warn

    Runnable r11 = //indent:4 exp:4
        () -> { //indent:8 exp:8
          x.run(); //indent:10 exp:10
        }; //indent:8 exp:8

    Runnable r21 = //indent:4 exp:4
        () -> { x.run(); }; //indent:8 exp:8

    Runnable r31 = //indent:4 exp:4
        () -> x //indent:8 exp:8
            .run(); //indent:12 exp:12

    Runnable r41 = //indent:4 exp:4
        () -> x.run(); //indent:8 exp:8

    InputIndentationLambda.Printer r51 = //indent:4 exp:4
        s -> LOG.info(s); //indent:8 exp:8

    InputIndentationLambda.Printer r61 = //indent:4 exp:4
        s -> String.CASE_INSENSITIVE_ORDER //indent:8 exp:8
            .equals(s); //indent:12 exp:12

    Object o1 = new Thread( //indent:4 exp:4
        () -> { //indent:8 exp:8
          x.run(); //indent:10 exp:10
        }); //indent:8 exp:8

    Object o2 = new Thread(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }).toString(); //indent:4 exp:4

    InputIndentationLambda.SomeInterface i1 = (InputIndentationLambda.LongTypeName //indent:4 exp:4
        arg) -> { //indent:8 exp:8
      LOG.info(arg.toString()); //indent:6 exp:6
    }; //indent:4 exp:4

    InputIndentationLambda.Printer[] mRun = new InputIndentationLambda.Printer[] { //indent:4 exp:4
        s -> LOG.info(s), //indent:8 exp:6,8
        s -> { LOG.info(s); }, //indent:8 exp:6,8
        s -> LOG //indent:8 exp:6,8
            .info(s), //indent:12 exp:12
        s -> { //indent:8 exp:6,8
          LOG.info(s); //indent:10 exp:10
        }, //indent:8 exp:8
    }; //indent:4 exp:4
  } //indent:2 exp:2

  void function3(Runnable x) { //indent:2 exp:2
    function1(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }); //indent:4 exp:4
  } //indent:2 exp:2

  class Person { //indent:2 exp:2
    String name; //indent:4 exp:4
    int age; //indent:4 exp:4
    Person(String name, int age) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

} //indent:0 exp:0
