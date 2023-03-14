/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
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

import org.w3c.dom.Node;

public class InputAbbreviationAsWordInNameCheckRecords {
    class myCLASS { // violation
        int INTEGER = 2; // violation
        void METHOD(){} // violation

        public myCLASS(String STRING) { // violation
            int INTEGER = 6; // violation

        }

    }

    record myRECORD1(String STRING) { // 2 violations

        void METHOD(){} // violation

        //ctor
        public myRECORD1(){
            this("string");
            int INTEGER = 6; // violation
        }

    }

    record myRECORD2() { // violation
        static int INTEGER = 6; // static ignored, all fields in record must be static

        //compact ctor
        public myRECORD2{
            int INTEGER = 2; // violation
        }
    }

    record myRECORD3(String STRING, int INTEGER, Node[] NODES) { // 4 violations

    }
}
