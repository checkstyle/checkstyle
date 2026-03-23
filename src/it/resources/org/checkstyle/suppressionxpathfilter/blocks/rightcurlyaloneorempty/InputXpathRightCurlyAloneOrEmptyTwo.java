package org.checkstyle.suppressionxpathfilter.blocks.rightcurlyaloneorempty;

public class InputXpathRightCurlyAloneOrEmptyTwo {
    void method() {
        int a = 10;
        switch (a) {
            case 10 : {

            } case 2 : { // warn
                int b = 20;
            }
        }
    }
}
