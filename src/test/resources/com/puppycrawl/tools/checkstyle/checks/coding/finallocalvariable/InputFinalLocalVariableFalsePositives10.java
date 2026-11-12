/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives10 {

    // openjdk8/src/share/classes/sun/java2d/pipe/BufferedRenderPipe.java
    private void foo30() {
        int transx, transy;
        if (true) {
            if (true) {
                if (true) {
                }
                else {
                }
                transx = 1;
                transy = 2;
            }
            else {
                transx = 0;
                transy = 0;
            }
        }
        if (true) {
            transx = 4;
            transy = 1;
        }
        else {
            transx = transy = 0;
        }
    }

    // openjdk8/src/share/classes/sun/java2d/cmm/lcms/LCMSTransform.java
    private void foo31() throws Exception {
        String srcIL;
        if (true) {
            srcIL = "";
            if (srcIL != null) {
                return;
            }
        }
        if (true) {
            try {
                srcIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert rasters");
            }
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 12; x++) {
                    for (int i = 0; i < 5478; i++) {
                    }
                }
                for (int x = 0; x < 5; x++) {
                    for (int i = 0; i < 541; i++) {
                    }
                }
            }
        }
        else {
            try {
                srcIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert rasters");
            }
        }
    }

    // openjdk8/src/share/classes/sun/java2d/cmm/lcms/LCMSTransform.java
    private void foo32() throws Exception {
       String srcIL, dstIL;
        try {
            if (true) {
                dstIL = "";

                if (dstIL != null) {
                    srcIL = "";
                    if (srcIL != null) {
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new Exception("Unable to convert images");
        }
        if (true) {
            if (true) {
            }
            try {
                srcIL = "";
                dstIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert images");
            }
        }
        else {
                    dstIL = "";
        }
    }
}
