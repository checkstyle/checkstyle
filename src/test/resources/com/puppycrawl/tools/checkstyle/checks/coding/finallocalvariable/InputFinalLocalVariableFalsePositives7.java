/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives7 {

    // openjdk8/src/share/classes/sun/security/pkcs11/P11KeyGenerator.java
    private void foo21() throws Exception {
        try {
            final int keyType = 8;
            String[] attributes;
            switch (keyType) {
                case 1:
                case 2:
                case 3:
                    attributes = null;
                    break;
                default:
                    attributes = null;
                    break;
            }
            attributes = null;
            return;
        }
        catch (Exception e) {
            throw new Exception("Could not generate key", e);
        }
        finally {
        }
    }

    // openjdk8/src/share/classes/sun/security/krb5/internal/KDCReqBody.java
    private void foo22() throws Exception {
        String subDer;
        if (true) {
            subDer = "";
            if (true) {
                while ("".equals(subDer)) {
                }
                for (int i = 0; i < 1541; i++) {

                }
            }
            else {
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
        if (true) {
        }
        if (true) {
        }
        if (true) {
            if (true) {
                subDer = "";
                if (true) {
                    while ("".equals(subDer)) {
                    }
                }
                else {
                    throw new Exception();
                }
                if (true) {
                }
            }
            else {
                throw new Exception();
            }
        }
        if (true) {
            throw new Exception();
        }
    }

    // openjdk8/src/share/classes/sun/security/krb5/internal/KDCReq.java
    private void foo23() throws Exception {
        int bint;
        if (true) {
            throw new Exception();
        }
        if (true) {
            throw new Exception();
        }
        if (true) {
            bint = 1;
            if (true) {
                throw new Exception("");
            }
        }
        else {
            throw new Exception();
        }
        if (true) {
            bint = 2;
            if (bint == 4) {
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
    }
}
