// non-compiled with javac: compilable with Java25

package com.puppycrawl.tools.checkstyle.grammar.java25;

public class InputFlexibleConstructorBody {
    public InputFlexibleConstructorBody() {
        System.out.println("hello");
        super();
    }
}

class Jep512 {
    Jep512(int letter) {
    }
}
class Jep513 extends Jep512 {
    Jep513(int number) {
        if (number > 0) {
            throw new IllegalArgumentException("number must be positive");
        }
        super(number);
    }
}
