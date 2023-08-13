/*
DescendantToken
tokens = LITERAL_SWITCH
maximumDepth = 2
minimumNumber = 1

*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// ok
public class InputDescendantTokenProperty {
}

class Test {
    public static void main(String[] args) {
        int x = 1;
        switch (x) { // ok
            case 1:
                System.out.println("hi");
                break;
            default:
                System.out.println("Default");
                break;
        }

        int y = 1;
        switch (y) {
            case 1:
                System.out.println("hi");
                break;
        }
    }
}
