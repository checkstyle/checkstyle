//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.unusedcatchparametershouldbeunnamed;

public class InputXpathUnusedCatchParameterShouldBeUnnamedSimple {

    void test() {
        try {
            int x = 1 / 0;
        }
        catch (Exception e) {  // warn
            System.out.println("infinity");
        }
    }
}
