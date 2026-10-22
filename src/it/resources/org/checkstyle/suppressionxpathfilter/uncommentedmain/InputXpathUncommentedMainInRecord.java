/*
UncommentedMain
excludedClasses = (default)^$


*/

package org.checkstyle.suppressionxpathfilter.uncommentedmain;

public record InputXpathUncommentedMainInRecord() {
    public static void main(String[] args) { // violation 'Uncommented main method found.'
    }
}
