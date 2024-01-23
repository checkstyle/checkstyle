/*
AbbreviationAsWordInName
allowedAbbreviationLength = 1
allowedAbbreviations = XML
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.ArrayList;
import java.util.Locale;

public class InputAbbreviationAsWordInNameCheckEnhancedInstanceofAllowXmlLength1 {

    public void t(Object o1, Object o2) {
        if (!(o1 instanceof String STRING) // violation
                && (o2 instanceof Integer INTEGER)) {} // violation

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> aXML) { // ok, allowed XML
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a1) ?
                (o1 instanceof String aTXT) :   // violation
                (!(o1 instanceof String ssSTRING)); // violation

        String formatted;
        if (o1 instanceof Integer XMLHTTP) formatted = // violation
                String.format("int %d", XMLHTTP);
        else if (o1 instanceof Byte bYT) formatted = String.format("byte %d", bYT);
        else formatted = String.format("Something else "+ o1.toString());

    }
}
