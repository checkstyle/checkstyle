package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

public class InputIndentationLineWrappedChainedMethodCalls {                //indent:0 exp:0

    void foo() {                                                            //indent:4 exp:4
        new Chainable()                                                     //indent:8 exp:8
            .chain()                                                        //indent:12 exp:12
            .chain();                                                       //indent:12 exp:12

        Chainable chainable1 =                                              //indent:8 exp:8
            new Chainable()                                                 //indent:12 exp:12
                .chain()                                                    //indent:16 exp:16
                .chain();                                                   //indent:16 exp:16

        Chainable chainable2 =                                              //indent:8 exp:8
            new Chainable()                                                 //indent:12 exp:12
            .chain()                                                        //indent:12 exp:16 warn
            .chain();                                                       //indent:12 exp:16 warn

        bar(                                                                //indent:8 exp:8
            new Chainable()                                                 //indent:12 exp:12
                .chain()                                                    //indent:16 exp:16
                .chain());                                                  //indent:16 exp:16
        bar(new Chainable()                                                 //indent:8 exp:8
            .chain());                                                      //indent:12 exp:12
    }                                                                       //indent:4 exp:4

    void bar(Chainable c) {                                                 //indent:4 exp:4
    }                                                                       //indent:4 exp:4

    void foo2() {                                                           //indent:4 exp:4
        int chainable3 =                                                    //indent:8 exp:8
            new Chainable()                                                 //indent:12 exp:12
                    .value;                                                 //indent:20 exp:12 warn
    }                                                                       //indent:4 exp:4

    void foo3() {                                                           //indent:4 exp:4
        Chainable chainable4 =                                              //indent:8 exp:8
            makeChainable()                                                 //indent:12 exp:12
                .chain()                                                    //indent:16 exp:16
                .chain();                                                   //indent:16 exp:16

        Chainable chainable5 =                                              //indent:8 exp:8
            makeChainable("first",                                          //indent:12 exp:12
            "second")                                                       //indent:12 exp:12
            .chain()                                                        //indent:12 exp:12
            .chain();                                                       //indent:12 exp:12

        if (true) {                                                         //indent:8 exp:8
            Chainable chainable6 =                                          //indent:12 exp:12
                    makeChainable()                                         //indent:20 exp:16 warn
                    .chain()                                                //indent:20 exp:16 warn
                    .chain();                                               //indent:20 exp:16 warn
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    Chainable makeChainable() {                                             //indent:4 exp:4
        return new Chainable();                                             //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    Chainable makeChainable(String first, String second) {                  //indent:4 exp:4
        return new Chainable();                                             //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    class Chainable {                                                       //indent:4 exp:4
        int value;                                                          //indent:8 exp:8

        Chainable chain() {                                                 //indent:8 exp:8
            return this;                                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0
