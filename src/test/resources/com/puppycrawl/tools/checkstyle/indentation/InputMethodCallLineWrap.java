package com.puppycrawl.tools.checkstyle.indentation;

public class InputMethodCallLineWrap {

    void foo() {
        new String()
            .substring(
                0, 100
            )
            .substring(
                0, 50
            );
    }

    class InnerFoo {

    	void foo() {
            new String()
                .substring(
                    0, 100
                )
                .substring(
                    0, 50
                );
        }
    }

    InnerFoo anon = new InnerFoo() {

    	void foo() {
            new String()
                .substring(
                    0, 100
                )
                .substring(
                  0, 50 //incorrect
              ); //incorrect
        }
    };
}
