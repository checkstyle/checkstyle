/*
UncommentedMain
excludedClasses = (default)^$


*/

package org.checkstyle.suppressionxpathfilter.uncommentedmain;

public class InputXpathUncommentedMainInStaticClass {
    public static class Launcher {
        public static void main(String[] args) {} // violation 'Uncommented main method found.'
    }
}
