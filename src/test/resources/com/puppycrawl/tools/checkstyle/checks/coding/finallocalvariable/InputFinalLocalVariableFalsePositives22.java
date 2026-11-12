/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives22 {

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/regionserver/HStore.java
    private void foo83() {
        String key;
        if (true) {
            try {
                key = "";
            }
            catch (Exception e) {
                if (true) {
                    try {
                        key = "";
                    }
                    catch (Exception ex) {
                    }
                }
            }
        }
        else {
            key = "";
        }
    }

    // Hbase/hbase-server/.../hadoop/hbase/regionserver/DefaultHeapMemoryTuner.java
    private void foo84() {
        float newMemstoreSize;
        float newBlockCacheSize;
        if (true) {
            newBlockCacheSize = 354;
            newMemstoreSize = 534;
        }
        else if (true) {
            newBlockCacheSize = 213213;
            newMemstoreSize = 53425;
        }
        else {
            return;
        }
        if (true) {
            newMemstoreSize = 2;
        }
        else if (true) {
            newMemstoreSize = 3;
        }
        if (true) {
            newBlockCacheSize = 354;
        }
        else if (true) {
            newBlockCacheSize = 14;
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/master/AssignmentManager.java
    private void foo85() {
        boolean needNewPlan;
        try {
            if (true) {
            }
            if (true) {
                needNewPlan = true;
            }
            else {
                if (true) {
                }
                return;
            }
        }
        catch (Throwable t) {
            if (t instanceof Object) {
            }
            if (true) {
                if (true) {
                    if (t instanceof Object) {
                    }
                    else {
                    }
                }
                try {
                    needNewPlan = false;
                    if (true) {
                    }
                    else if (true) {
                        needNewPlan = true;
                    }
                }
                catch (Exception ie) {
                    return;
                }
            }
            else if (true) {
                needNewPlan = false;
            }
            else {
                needNewPlan = true;
            }
        }
    }
}
