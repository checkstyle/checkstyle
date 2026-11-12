/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives2 {

    // spring-framework/spring-context/.../TaskExecutorFactoryBean.java
    private void foo4() {
        if (true) {
            try {
                int corePoolSize;
                if (true) {
                    corePoolSize = Integer.valueOf("1");
                    if (corePoolSize > 2) {
                    }
                    if (true) {
                        if (corePoolSize == 0) {
                            corePoolSize = 2;
                        }
                        else {
                        }
                    }
                }
                else {
                    corePoolSize = 1;
                }
            }
            catch (NumberFormatException ex) {
            }
        }
    }

    // spring-framework/spring-beans/.../SimpleInstantiationStrategy.java
    private void foo5() {
        if (true) {
            final String s = "";
            String constructorToUse;
            synchronized (s) {
                constructorToUse = "";
                if (constructorToUse == null) {
                    if (true) {
                    }
                    try {
                        if (System.getSecurityManager() != null) {
                            constructorToUse = "";
                        }
                        else {
                            constructorToUse = "";
                        }
                    }
                    catch (Exception ex) {
                    }
                }
            }
        }
        else {
        }
    }

    // openjdk8/src/windows/classes/sun/print/Win32PrintJob.java
    private void foo6() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
            }
        }

        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof Object) {
                }
                if (attrs[i] instanceof Object) {
                }
            }
        }
    }

    // openjdk8/src/windows/classes/sun/awt/windows/WInputMethod.java
    private void foo7() {
        int index;
        if (true) {
            index = 0;
        }
        else if (true) {
            index = 2;
        }
        else {
            return;
        }
        if (true) {
            index += 1;
        }
    }
}
