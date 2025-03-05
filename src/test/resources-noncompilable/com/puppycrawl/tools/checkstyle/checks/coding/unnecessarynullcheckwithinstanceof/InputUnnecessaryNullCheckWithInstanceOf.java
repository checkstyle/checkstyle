/*
UnnecessaryNullCheckWithInstanceOf

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOf {

    public void basicIfStatements(Object obj) {
        if (obj != null && obj instanceof String s) { // violation, 'Unnecessary nullity check'
            String str = (String) obj;
        }
    }

}
