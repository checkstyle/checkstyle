package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputNoWhitespaceBeforeColonOfLabel {

    {
        label1 : // warn
        for(int i = 0; i < 10; i++) {}
    }

    public void foo() {
        label2:
        while (true) {}
    }
}
