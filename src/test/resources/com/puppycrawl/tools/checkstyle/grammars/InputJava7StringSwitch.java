package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for Java 7 String in Switch.
 */
public class InputJava7StringSwitch
{
    public static void main(String[] args) {
        String mystr = "value" + "2";

        switch (mystr) {
            case "value1":
                break;
            case "value2":
                break;
            default:
                break;
        }
    }
}
