//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import org.w3c.dom.Node;

/*
 * Config:
 * allowedAbbreviationLength = 4
 * allowedAbbreviations = n/a
 * tokens = {CLASS_DEF,INTERFACE_DEF,ENUM_DEF,ANNOTATION_DEF,ANNOTATION_FIELD_DEF,
 *      PARAMETER_DEF,VARIABLE_DEF,METHOD_DEF,PATTERN_VARIABLE_DEF, RECORD_DEF,
 *      RECORD_COMPONENT_DEF}
 * ignoreStatic = true
 * ignoreFinal = true
 * ignoreStaticFinal = true
 */

public class InputAbbreviationAsWordInNameCheckRecords {
    class TESTCLASS { // violation
        int INTEGER = 2; // violation
        void METHOD(){} // violation

        public TESTCLASS(String STRING) { // violation for param
            int INTEGER = 6; // violation

        }

    }

    record MYRECORD1(String STRING) { // violation x2

        void METHOD(){} // violation

        //ctor
        public MYRECORD1(){
            this("string");
            int INTEGER = 6; // violation
        }

    }

    record MYRECORD2() { // violation
        static int INTEGER = 6; // static ignored, all fields in record must be static

        //compact ctor
        public MYRECORD2{
            int INTEGER = 2; // violation
        }
    }

    record MYRECORD3(String STRING, int INTEGER, Node[] NODES) { // violation x3

    }
}
