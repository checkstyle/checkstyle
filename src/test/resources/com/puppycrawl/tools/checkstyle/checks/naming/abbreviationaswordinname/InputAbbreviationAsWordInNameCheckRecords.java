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


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import org.w3c.dom.Node;

public class InputAbbreviationAsWordInNameCheckRecords {
    // violation below 'Abbreviation in name 'myCLASS''
    class myCLASS {
        // violation below 'Abbreviation in name 'INTEGER''
        int INTEGER = 2;
        // violation below 'Abbreviation in name 'METHOD''
        void METHOD(){}

        // violation below 'Abbreviation in name 'STRING''
        public myCLASS(String STRING) {
            // violation below 'Abbreviation in name 'INTEGER''
            int INTEGER = 6;

        }

    }

    record myRECORD1(String STRING) {
        // 2 violations above:
        //    'Abbreviation in name 'myRECORD1''
        //    'Abbreviation in name 'STRING''

        // violation below 'Abbreviation in name 'METHOD''
        void METHOD(){}

        //ctor
        public myRECORD1(){
            this("string");
            // violation below 'Abbreviation in name 'INTEGER''
            int INTEGER = 6;
        }

    }

    // violation below 'Abbreviation in name 'myRECORD2''
    record myRECORD2() {
        static int INTEGER = 6; // static ignored, all fields in record must be static

        //compact ctor
        public myRECORD2{
            // violation below 'Abbreviation in name 'INTEGER''
            int INTEGER = 2;
        }
    }

    record myRECORD3(String STRING, int INTEGER, Node[] NODES) {
        // 4 violations above:
        //    'Abbreviation in name 'myRECORD3''
        //    'Abbreviation in name 'STRING''
        //    'Abbreviation in name 'INTEGER''
        //    'Abbreviation in name 'NODES''

    }
}
