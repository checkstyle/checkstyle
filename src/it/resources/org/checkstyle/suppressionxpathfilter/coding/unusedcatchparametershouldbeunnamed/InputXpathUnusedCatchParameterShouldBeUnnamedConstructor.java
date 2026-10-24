/*
UnusedCatchParameterShouldBeUnnamed


*/


package org.checkstyle.suppressionxpathfilter.coding.unusedcatchparametershouldbeunnamed;

public class InputXpathUnusedCatchParameterShouldBeUnnamedConstructor {

    InputXpathUnusedCatchParameterShouldBeUnnamedConstructor() {
        try {
            int x = 1 / 0;
        }
        catch (Exception e) { // violation "Unused catch parameter 'e' should be unnamed."
            System.out.println("infinity");
        }
    }
}
