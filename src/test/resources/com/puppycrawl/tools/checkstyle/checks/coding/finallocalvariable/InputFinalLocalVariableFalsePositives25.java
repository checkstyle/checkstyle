/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives25 {

    // spring-framework/spring-webmvc/.../method/annotation/RequestPartMethodArgumentResolver.java
    private void foo96() {
        Object arg;
        if (true) {
            arg = "a";
        }
        else if (true) {
            arg = "b";
        }
        else if (true) {
            arg = "c";
        }
        else if (true) {
            arg = "d";
        }
        else if (true) {
            arg = "e";
        }
        else if (true) {
            arg = "f";
        }
        else {
            try {
                arg = "g";
                if (arg != null) {
                    if (true) {
                    }
                }
            }
            catch (Exception ex) {
                arg = null;
            }
        }
    }

    // infinispan/commons/.../concurrent/jdk8backported/BoundedEquivalentConcurrentHashMapV8.java
    private void foo97() {
        if (true) {
            String evictedCopy;
            if (true) {
                evictedCopy = "b";
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                }
                evictedCopy = "d";
            }
        }
    }

    // infinispan/core/src/main/java/org/infinispan/util/concurrent/BoundedConcurrentHashMap.java
    private void foo98() {
        if (true) {
            String evictedCopy;
            if (true) {
                evictedCopy = "b";
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                }
                evictedCopy = "d";
            }
        }
    }

    // infinispan/persistence/soft-index/.../infinispan/persistence/sifs/IndexNode.java
    private void foo99() {
        int insertionPoint;
        if (true) {
            insertionPoint = 0;
        } else if (true) {
            insertionPoint = 1;
        } else {
            insertionPoint = 2;
            if (insertionPoint < 0) {
                insertionPoint = -insertionPoint - 1;
            } else {
                insertionPoint++;
            }
        }
    }

    // apache-struts/core/src/main/java/com/opensymphony/xwork2/util/LocalizedTextUtil.java
    private void foo100() {
        String result;
        if (true) {
            result = "a";
        } else {
            result = "b";
            if (result != null) {
            }
            result = "c";
        }
    }
}
