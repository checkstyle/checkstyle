/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives19 {

    // openjdk8/src/macosx/classes/sun/lwawt/macosx/CInputMethod.java
    private void foo64() {
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

    // apache-struts/core/.../mapper/DefaultActionMapper.java
    private void foo65() {
        String name;
        if (true) {
            name = "uri";
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

    // infinispan/remoting/transport/jgroups/JGroupsTransport.java
    private void foo66() {
        String cfg;
        if (true) {
            if (true) {
                cfg = "";
            }
            if (true) {
                cfg = "";
            }
            if (true) {
                cfg = "";
            }
        }
    }

    // hibernate-core/.../internal/javassist/BytecodeProviderImpl.java
    private void foo67() {
        String fastClass;
        try {
            fastClass = "";
            if (true) {
                if (fastClass == null) {
                }
                else {
                }
            }
        }
        catch (Throwable t) {
            fastClass = null;
        }
    }

    // guava-mvnstyle/guava/src/com/google/common/net/InetAddresses.java
    private void foo68() {
        int partsLo;
        if (true) {
            partsLo = 1;
            if ( --partsLo != 0) {
                return;
            }
            if (true) {
                return;
            }
        }
        else {
            partsLo = 0;
        }
    }

    // guava-mvnstyle/guava/src/com/google/common/collect/TreeMultiset.java
    private void foo69() {
        String node;
        if (true) {
            node = "";
            if (node == null) {
                return;
            }
            if (true) {
                node = "";
            }
        }
        else {
            node = "";
        }
    }
}
