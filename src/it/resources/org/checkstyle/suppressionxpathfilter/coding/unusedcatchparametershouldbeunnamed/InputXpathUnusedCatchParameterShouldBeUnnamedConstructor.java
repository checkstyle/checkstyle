// Java21
package org.checkstyle.suppressionxpathfilter.coding.unusedcatchparametershouldbeunnamed;

public class InputXpathUnusedCatchParameterShouldBeUnnamedConstructor {

    InputXpathUnusedCatchParameterShouldBeUnnamedConstructor() {
        try {
            int x = 1 / 0;
        }
        catch (Exception e) {  // warn
            System.out.println("infinity");
        }
    }
}
