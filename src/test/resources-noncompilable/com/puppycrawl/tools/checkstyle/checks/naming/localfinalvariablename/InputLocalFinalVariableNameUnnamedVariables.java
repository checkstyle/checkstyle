/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,RESOURCE

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

public class InputLocalFinalVariableNameUnnamedVariables {

    void testTryWithResource(Object obj) {
        try (var _ = lock()) {

        }
        catch (final Exception _) {
        }

        try (var __ = lock()) {   // violation, 'Name '__' must match pattern*.'

        }
        catch (final Exception __) {  // violation, 'Name '__' must match pattern*.'
        }

    }

    void testEnhancedForLoop() {
        for (final var _ : new int[0]) {
        }
        for (final var __ : new int[0]) { // violation, 'Name '__' must match pattern*.'
        }
        for (final var _BAD : new int[0]) { // violation, 'Name '_BAD' must match pattern*.'
        }
    }

    void testLocalVariable(Object obj) {
        final var _ = obj;
        final var __ = obj; // violation, 'Name '__' must match pattern*.'
        final var _BAD = obj; // violation, 'Name '_BAD' must match pattern*.'
    }

    public AutoCloseable lock() {
        return null;
    }
}
