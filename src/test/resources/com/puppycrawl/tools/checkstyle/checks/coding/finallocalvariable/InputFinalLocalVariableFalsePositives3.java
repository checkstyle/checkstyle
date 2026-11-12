/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives3 {

    // openjdk8/src/solaris/classes/sun/print/UnixPrintJob.java
    private void foo8() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof String) {
                }
            }
        }
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof Object) {
                }
                if (attrs[i] instanceof String) {
                }
            }
        }
    }

    // openjdk8/src/solaris/classes/sun/java2d/xr/XRRenderer.java
    private void foo9() {
        int transx, transy;

        if (true) {
            if (true) {
                if (true) {
                }
                else {
                }
                transx = 2;
                transy = 1;
            }
            else {
                transx = 0;
                transy = 0;
            }
        }
        if (true) {
            transx = 1;
            transy = 2;
        }
        else {
            transx = transy = 0;
        }

        try {
        }
        finally {
        }
    }

    // openjdk8/src/solaris/classes/sun/awt/X11InputMethod.java
    private void foo10() {
        int index;
        if (false) {
            index = 0;
        }
        else if (true) {
            index = 2;
        }
        else {
            return;
        }
        if (false) {
            index += 1;
        }
    }

    // jdk8/src/solaris/classes/java/util/prefs/FileSystemPreferences.java
    private void foo11() {
        if (true)
            if (true)
                return;
        long lastModifiedTime;
        if (true) {
            lastModifiedTime = 1L;
            if (true) {
            }
        }
        else if (true) {
        }
        if (true) {
            lastModifiedTime = 2L;
            if (true) {
            }
        }
    }

    // openjdk8/src/share/classes/sun/util/locale/provider/LocaleResources.java
    private void foo12() {
        String numElemKey;
        if (true) {
            numElemKey = ".NumberElements";
            if (true) {
            }
        }
        if (true) {
            numElemKey = "";
            if (true) {
            }
        }
    }
}
