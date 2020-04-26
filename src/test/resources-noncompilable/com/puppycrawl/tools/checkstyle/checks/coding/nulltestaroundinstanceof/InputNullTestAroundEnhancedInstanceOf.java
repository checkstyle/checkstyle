//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.nulltestaroundinstanceof;

/* Config:
 *
 * default
 */
public class InputNullTestAroundEnhancedInstanceOf {
    public void main(int[] obj) {
        int[] myObj = obj;
        Boolean a = myObj != null;

        if (myObj != null && myObj instanceof Object test) { // violation

        }

        if (myObj != null) { // violation
            if (myObj instanceof Object test)
                System.out.println();
        }
    }
}
