/*
UnusedCatchParameterShouldBeUnnamed


*/


package org.checkstyle.suppressionxpathfilter.coding.unusedcatchparametershouldbeunnamed;

public class InputXpathUnusedCatchParameterShouldBeUnnamedNested {

    void test() {
        try {
            int x = 1 / 0;
        }
        catch (Exception e) {
            try {
                int z = 5 / 0;
            }
            catch (Exception exception) {
            // violation above "Unused catch parameter 'exception' should be unnamed."
                System.out.println("infinity");
            }
            e.printStackTrace();
        }
    }
}
