/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives21 {

    // elasticsearch/src/main/java/org/elasticsearch/index/search/child/ParentIdsFilter.java
    private void foo77() {
        if (true) {
            int docId;
            if (true) {
                docId = 21;
                if (docId != 1254) {
                }
                else {
                }
            }
            else {
                docId = 45234;
                if (true) {
                }
            }
            if (true) {
                docId = 3213213;
            }
        }
    }

    // elasticsearch/src/main/java/org/elasticsearch/common/inject/internal/BindingBuilder.java
    private void foo78() {
        String injectionPoints;
        if (true) {
            try {
                injectionPoints = "";
            }
            catch (Exception e) {
                injectionPoints = "";
            }
        }
        else {
            injectionPoints = "";
        }
    }

    // apache-struts/core/.../dispatcher/mapper/DefaultActionMapper.java
    private void foo79() {
        String name;
        if (true) {
            name = "";
        }
        else if (true) {
            name = "";
        }
        else if (true) {
            name = "";
        }
        else {
            name = "";
        }
        if (true) {
            if (true) {
                name = "";
            }
        }
    }

    // apache-ant/src/main/org/apache/tools/ant/types/PropertySet.java
    private void foo80() {
        String names;
        if (true) {
            names = "";
            if (true) {
                names = "";
            }
        }
        else {
            names = "";
        }
    }

    // apache-ant/src/main/org/apache/tools/ant/taskdefs/optional/net/FTPTaskMirrorImpl.java
    private void foo81() {
        StringBuffer msg;
        synchronized (this) {
            msg = new StringBuffer("   [");
        }
        synchronized (this) {
            msg = new StringBuffer("   [");
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/regionserver/HStore.java
    private void foo82() {
       String filesToCompact;
        try {
            synchronized (this) {
                filesToCompact = "";
                filesToCompact = "";
            }
        }
        finally {
        }
    }
}
