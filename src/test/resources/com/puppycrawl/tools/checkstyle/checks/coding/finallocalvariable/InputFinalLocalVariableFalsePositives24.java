/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives24 {

    // findbugs/src/java/edu/umd/cs/findbugs/ba/jsr305/TypeQualifierApplications.java
    private void foo92() {
        String tqa;
        if (true) {
            tqa = null;
            if (true) {
            }
        }
        else {
            if (true) {
            }
            tqa = "a";
            if (true) {
            }
            if (tqa == null) {
                if (true) {
                }
                tqa = "b";
                if (true) {
                    if (tqa == "abc") {
                    } else if (tqa != null) {
                    } else {
                    }
                }
            }
            if (tqa == "bcd") {
                tqa = null;
            }
            if (tqa == null) {
                if (true) {
                    if (true) {
                    }
                } else {
                    if (true) {
                    }
                    tqa = "c";
                    if (true) {
                    }
                }
            }
        }
    }

    // hibernate-core/.../internal/util/collections/BoundedConcurrentHashMap.java
    private void foo93() {
        if (true) {
            String evictedCopy;
            if (true) {
                final String evictedEntry = "a";
                evictedCopy = evictedEntry;
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                    evictedCopy.trim();
                }
                evictedCopy = evictedCopy.trim();
            }
        }
    }

    // spring-framework/spring-core/src/main/java/org/springframework/core/Conventions.java
    private void foo94() {
        String valueClass;
        if (true) {
            valueClass = "a";
        }
        else if (true) {
            valueClass = "b";
            if (valueClass == null) {
                if (true) {
                }
                if (true) {
                }
                valueClass = "c";
            }
        }
        else {
            valueClass = "d";
        }
    }

    // spring-framework/spring-test/.../springframework/test/annotation/ProfileValueUtils.java
    private void foo95() {
        String profileValueSource;
        if (true) {
            profileValueSource = "a";
        }
        else {
            try {
                profileValueSource = "b";
            }
            catch (Exception e) {
                if (true) {
                }
                profileValueSource = "c";
            }
        }
    }
}
