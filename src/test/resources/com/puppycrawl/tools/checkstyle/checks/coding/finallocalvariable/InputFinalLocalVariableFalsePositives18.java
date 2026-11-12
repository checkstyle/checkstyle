/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives18 {

    // openjdk8/src/share/classes/com/sun/tools/example/debug/tty/Commands.java
    private void foo59() {
        String spec;
        if (true) {
            spec = "";
        }
        if (true) {
            spec = "";
        }
    }

    // openjdk8/src/share/classes/com/sun/security/auth/callback/TextCallbackHandler.java
    private void foo60() throws Exception {
        final int a = 1;
        String text;
        switch (a) {
            case 1:
                text = "";
                break;
            case 2:
                text = "Warning: ";
                break;
            case 3:
                text = "Error: ";
                break;
            default:
                throw new Exception();
        }
        final String message = "";
        if (message != null) {
            text += message;
        }
    }

    // openjdk8/src/share/classes/com/sun/net/ssl/SSLSecurity.java
    private void foo61() {
        String tmaw;
        if (true) {
            tmaw = "";
            for (int i = 0; i < 10; i++) {
                if (true) {
                    if (true) {
                        tmaw = "";
                    }
                }
                else {
                    tmaw = "";
                }
            }
            if (true) {
                tmaw = "";
            }
        }
        else {
            tmaw = null;
        }
    }

    // openjdk8/src/share/classes/com/sun/jndi/dns/DnsContext.java
    private void foo62() {
        String znode;
        synchronized (this) {
            znode = "";
        }
        if (znode != null) {
            synchronized (znode) {
            }
            if (true) {
                if (true) {
                    synchronized (znode) {
                        if (true) {
                        }
                        else if (true) {
                        }
                        else {
                            return;
                        }
                    }
                    if (true) {
                        return;
                    }
                }
            }
        }
        synchronized (this) {
            znode = "";
        }
    }

    // openjdk8/src/share/classes/com/sun/java/swing/plaf/gtk/GTKColorType.java
    private void foo63() {
        float h;
        float l;
        float s;
        synchronized (this) {
            h = 1;
            l = 2;
            s = 3;
        }
        h = 4;
        l = 5;
        s = 6;
    }
}
