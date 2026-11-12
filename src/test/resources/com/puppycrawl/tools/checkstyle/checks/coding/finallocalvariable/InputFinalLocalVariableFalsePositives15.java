/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives15 {

    // openjdk8/src/share/classes/javax/sound/sampled/AudioSystem.java
    private void foo45() {
        String mixer;
        if (true) {
            if (true) {
                if (true) {
                    mixer = "";
                    if (mixer != null) {
                        return;
                    }
                }
                else {
                    mixer = "";
                    if (mixer != null) {
                        return;
                    }
                }

            }
        }
        if (true) {
            mixer = "";
            if (mixer != null) {
                return;
            }
        }
    }

    // openjdk8/src/share/classes/javax/security/auth/SubjectDomainCombiner.java
    private void foo46() {
        String e;
        if (true) {
            synchronized (this) {
                e = "";

            }
            synchronized (this) {
                e = "";

            }
        }
    }

    // openjdk8/src/share/classes/javax/naming/spi/NamingManager.java
    private void foo47() {
        final String a = "";
        String factory;
        if (true) {
            factory = "";
        }
        if ("".equals(a)) {
            factory = "";
        }
    }

    // openjdk8/src/share/classes/java/util/regex/Pattern.java
    private void foo48() {
        int ch;
        if (true) {
            ch = 1;
        }
        if (true) {
            ch = 2;
        }
    }

    // openjdk8/src/share/classes/java/util/concurrent/locks/StampedLock.java
    private String foo49() {
        long next;
        if (!Thread.interrupted()) {
            if (true) {
                if (true) {
                    if ((next = 5) == 5)
                        return "next";
                }
                else if ((next = 5) == 5)
                    return "next";
            }
            if (true)
                return "0L";
            if (true)
                if ((next = 5) == 5)
                    return "next";
        }
        return "";
    }

    // openjdk8/src/share/classes/java/time/Duration.java
    private void foo50() {
        long nanos;
        try {
            nanos = 1L;
        }
        catch (Exception ex2) {
            nanos = 0L;
        }
    }
}
