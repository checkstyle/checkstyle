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

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameCheckRecordPatterns {

    void m(Object o) {
        // violation below, 'Abbreviation in name 'POINT'*.'
        if (o instanceof ColoredPoint POINT) {

        }
        // violation below,  'Abbreviation in name 'COLOR'*.'
        if (o instanceof ColoredPoint(int x, int y, String COLOR)) {

        }

        if (o instanceof Rectangle(ColoredPoint(int XXXXX,_, String ssSTRING),ColoredPoint _)) {}
        // 2 violations above:
        //                'Abbreviation in name 'XXXXX'*.'
        //               'Abbreviation in name 'ssSTRING'*.'
    }
    void m2(Object o) {
        switch (o) {
            // violation below, 'Abbreviation in name 'COLOR'*.'
            case ColoredPoint(int x, int y, String COLOR)  -> {}
            case Rectangle(ColoredPoint(int x, int YYYYY, String cCOLOOR), _)  -> {}
            // 2 violations above:
            //                  'Abbreviation in name 'YYYYY'*.'
            //                 'Abbreviation in name 'cCOLOOR'*.'
            default -> {}
        }
    }


    record ColoredPoint(int x, int y, String color) {}
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}
}
