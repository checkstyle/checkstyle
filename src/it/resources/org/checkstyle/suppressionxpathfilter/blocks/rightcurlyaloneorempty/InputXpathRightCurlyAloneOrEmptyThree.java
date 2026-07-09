package org.checkstyle.suppressionxpathfilter.blocks.rightcurlyaloneorempty;

public class InputXpathRightCurlyAloneOrEmptyThree {
    class Demo {
        void method() {}
        void method2() {int a = 10;} // warn
    }
}
