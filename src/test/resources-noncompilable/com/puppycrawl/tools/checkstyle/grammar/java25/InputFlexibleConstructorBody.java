// Java25
package com.puppycrawl.tools.checkstyle.grammar.java25;

public class InputFlexibleConstructorBody {

    public InputFlexibleConstructorBody() {
        System.out.println("hello");
        super();
    }
}

class Jep512 {
    int i;

    Jep512() {

    }
}

class Jep513 extends Jep512 {

    int i;
    String s = "hello";

    Jep513(int number) {
        if (number > 0) {
            throw new IllegalArgumentException("number must be positive");
        }
        i = number;
        super();
    }
}
