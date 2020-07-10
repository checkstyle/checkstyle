//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.ArrayList;
import java.util.Locale;
// config:
// allowedAbbreviationLength="1"
// allowedAbreviations="XML"

public class InputAbbreviationAsWordInNameCheckEnhancedInstanceofAllowXmlLength1 {

    public void t(Object o1, Object o2) {
        // Should cause two violations, STRING and INTEGER, capital letter count > allowed
        if (!(o1 instanceof String STRING) // violation
                && (o2 instanceof Integer INTEGER)) {} //violation

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> aXML) { // ok
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a1) ?
                (o1 instanceof String aTXT) :   // violation, capital letter count > allowed
                (!(o1 instanceof String ssSTRING)); // violation, capital letter count > allowed

        String formatted;
        // Violation, XMLHTTP capital letter count > allowed
        if (o1 instanceof Integer XMLHTTP) formatted = // violation
                String.format("int %d", XMLHTTP);
        else if (o1 instanceof Byte bYT) formatted = String.format("byte %d", bYT); // ok
        else formatted = String.format("Something else "+ o1.toString());

    }
}
