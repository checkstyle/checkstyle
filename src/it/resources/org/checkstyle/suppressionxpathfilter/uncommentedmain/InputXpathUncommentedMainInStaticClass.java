package org.checkstyle.suppressionxpathfilter.uncommentedmain;

public class InputXpathUncommentedMainInStaticClass {
    public static class Launcher {
        public static void main(String[] args) {} // warn
    }
}
