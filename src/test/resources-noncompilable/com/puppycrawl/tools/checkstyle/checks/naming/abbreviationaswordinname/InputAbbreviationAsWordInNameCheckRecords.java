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
    class TestASDFClass { // violation
        int ASDFG = 2; // static ignored
        void FOOFOO(){} // violation

        public TestASDFClass(String STRING) { // violation for param
            int ZXCVG = 6; // violation

        }

    }

    record MyASDFRecord1(String STRING) { // violation x2

        void FOOFOO(){} // violation

        //ctor
        public MyASDFRecord1(){
            this("string");
            int ZXCVG = 6; // violation
        }

    }

    record MyASDFRecord2() { // violation
        static int ZXCVG = 6; // static ignored, all fields in record must be static

        //compact ctor
        public MyASDFRecord2{
            int ASDFG = 2; // violation
        }
    }

    record MyASDFRecord3(String MYSTRING, int MYINT, Node MYNODE) { // violation x3

    }
}
