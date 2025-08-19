// Java21
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
            catch (Exception exception) {  // warn
                System.out.println("infinity");
            }
            e.printStackTrace();
        }
    }
}
