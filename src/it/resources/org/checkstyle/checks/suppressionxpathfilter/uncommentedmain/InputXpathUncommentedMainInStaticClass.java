package org.checkstyle.checks.suppressionxpathfilter.uncommentedmain;

public class InputXpathUncommentedMainInStaticClass {
    public static class Launcher {
        public static void main(String[] args) {} // warn
    }
}
