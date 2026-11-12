/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives {

    // spring-framework/spring-web/.../CommonsFileUploadSupport.java
    private void foo1() {
        String value;
        if (true) {
            try {
                value = "";
            }
            catch (Exception ex) {
                if (true) {

                }
                value = "";
            }
        }
        else {
            value = "";
        }
    }

    // spring-framework/spring-messaging/.../StompBrokerRelayMessageHandler.java
    private void foo2() {
        String stompAccessor;

        if (true) {
            throw new IllegalStateException(
                "No header accessor (not using the SimpMessagingTemplate?): ");
        }
        else if (true) {
            stompAccessor = "";
        }
        else if (true) {
            stompAccessor = "";
        }
        else {
            throw new IllegalStateException("Unexpected header accessor type ");
        }

        if (true) {
            if (true) {
                if (true) {
                }
                return;
            }
            stompAccessor.toString();
        }

        if (true) {
            return;
        }

        if (true) {
            if (true) {
            }
            stompAccessor = "";
            stompAccessor.toString();
            stompAccessor.toString();
            if (true) {
                stompAccessor.toString();
            }
        }
        else if (true) {
            if (true) {
                if (true) {
                }
                return;
            }
        }
        else {
            if (true) {
                if (true) {
                }
                return;
            }
        }
    }

    // spring-framework/spring-jdbc/.../SQLErrorCodesFactory.java
    private void foo3() {
        String errorCodes;

        try {
            if (true) {
            }
            else {
            }

            if (true) {
            }

            errorCodes = "";
            if (true) {
            }
        }
        catch (Exception ex) {
            errorCodes = "";
        }

        final String s = errorCodes;
    }
}
