package com.puppycrawl.tools.checkstyle.indentation;

public class InputMethodCStyle {
    public InputMethodCStyle(int appleCount,
                             int bananaCount, // warn
                             int pearsCount) {  // warn
    }

    public InputMethodCStyle(String appleCount,
            int bananaCount, //ok
            int pearsCount) { //ok
    }
}