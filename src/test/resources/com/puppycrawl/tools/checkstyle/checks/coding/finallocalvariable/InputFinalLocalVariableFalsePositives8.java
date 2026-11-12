/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives8 {

    // openjdk8/src/share/classes/sun/rmi/server/LoaderHandler.java
    private void foo24() {
        final String a = "";
        String loader;
        synchronized (this) {
            while ("".equals(a)) {
                if (true) {
                }
            }
            if (true) {
                if (true) {
                }
                loader = "";
            }
            loader = "";
        }
    }

    // openjdk8/src/share/classes/sun/print/RasterPrinterJob.java
    private void foo25() {
        double w, h;
        if (true) {
            if (true) {
                w = 11.0;
                h = 12.2;
            }
        }
        if (true) {
            w = 1.0;
            h = 2.5;
        }
        return;
    }

    // openjdk8/src/share/classes/sun/print/PSStreamPrintJob.java
    private void foo26() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (true) {
                }
            }
        }
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof String) {
                }
                if (attrs[i] instanceof String) {
                }
            }
        }
    }

    // openjdk8/src/share/classes/sun/net/www/protocol/http/HttpURLConnection.java
    private void foo27() {
        String locUrl;
        try {
            locUrl = "";
            if (true) {
                return;
            }
        }
        catch (Exception mue) {

            locUrl = "";
        }
    }

    // openjdk8/src/share/classes/sun/misc/Launcher.java
    private void foo28() {
        final String a = "";
        String[] path;
        if (true) {
            while ("".equals(a)) {
            }
            path = new String[5];
            while ("".equals(a)) {
                if (true) {
                    path[1] = "";
                }
                else {
                    path[2] = "";
                }
            }
            if (true) {
                path[3] = "";
            }
            else {
                path[4] = "";
            }
            if (true) {
                path = new String[14];
            }
        }
        else {
            path = new String[81];
        }
        return;
    }
}
