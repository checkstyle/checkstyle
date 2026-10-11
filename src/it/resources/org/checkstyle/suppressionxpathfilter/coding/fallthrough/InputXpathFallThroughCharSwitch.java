package org.checkstyle.suppressionxpathfilter.coding.fallthrough;

public class InputXpathFallThroughCharSwitch {
    public void test() {
        char[] v = {'a','b','c'};
        for(char c:v) {
            switch (c) {
                case 'a':
                    System.out.println("a");
                    break;
                case 'b':
                    System.out.println("b");
                case 'c': //warn
                    System.out.println("c");
                    break;
            }
        }
    }
}
