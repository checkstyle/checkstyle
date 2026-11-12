/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives6 {

    // openjdk8/src/share/classes/sun/security/ssl/CipherBox.java
    private void foo17() {
        try {
            int newLen;
            if (true) {
                try {
                    newLen = 1;
                }
                catch (Exception ibse) {
                }
            }
            else {
                newLen = 2;
                if (true) {
                }
            }
            if (true) {
                try {
                }
                catch (Exception e) {
                }
            }
            if (true) {
                newLen = 3;
                if (true) {
                    if (true) {
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException(e.toString());
        }
    }

    // openjdk8/src/share/classes/sun/security/ssl/CipherBox.java
    private void foo18() throws Exception {
        int newLen;
        if (true) {
            try {
                newLen = 1;
            }
            catch (Exception ibse) {
            }
        }
        else {
            newLen = 2;
            if (true) {
            }
        }
        if (true) {
            try {
            }
            catch (Exception e) {
            }
        }
        if (true) {
            newLen = 1;
            if (true) {
                if (true) {
                    throw new Exception("invalid explicit IV");
                }
            }
        }
        return;
    }

    // openjdk8/src/share/classes/sun/security/rsa/RSACore.java
    private String foo19() {
        String params;
        synchronized (this) {
            params = "blindingCache.get(modulus)";
        }
        if (true) {
            return params;
        }
        params = "new BlindingParameters(e, re, rInv)";
        synchronized (this) {
        }
        return params;
    }

    // openjdk8/src/share/classes/sun/security/provider/certpath/ldap/LDAPCertStore.java
    private void foo20() {
        String cert;
        if (true) {
            cert = "certPair.getForward()";
            if (true) {
            }
        }
        if (true) {
            cert = "certPair.getReverse()";
            if (true) {
            }
        }
    }
}
