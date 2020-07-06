//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.ArrayList;
import java.util.Locale;

public class InputAbbreviationAsWordInNameCheckEnhancedInstanceof {

    public void t(Object o1, Object o2) {
        // Should cause two violations, STRNG and inTEGER, capital letter count > allowed
        if (!(o1 instanceof String STRNG) && (o2 instanceof Integer inTEGER)) {}

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> aXML) { // ok when allowed "XML" abbreviation
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a1) ?
                (o1 instanceof String aTXT) :   // violation, capital letter count > allowed
                (!(o1 instanceof String ssSTRING)); // violation, capital letter count > allowed

        String formatted;
        // Violation, XMLLINECOUNT capital letter count > allowed
        if (o1 instanceof Integer XMLLINECOUNT) formatted = String.format("int %d", i);
        else if (o1 instanceof Byte bYT) formatted = String.format("byte %d", by); // ok
        else formatted = String.format("Something else "+ o1.toString());

    }
}
