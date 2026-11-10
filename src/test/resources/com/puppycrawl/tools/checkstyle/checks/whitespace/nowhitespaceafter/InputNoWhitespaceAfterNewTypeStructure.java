/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, TYPECAST, \
         ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.net.ssl.KeyManager;

public class InputNoWhitespaceAfterNewTypeStructure {
    private static class Slot {
        double sin2Phi = 2 * StrictMath.sin(1.618033988749895);
        public int[] ci;
    }

    private static class Transformer {

    }

    private static class Ef {
        Transformer[] transformers = new Transformer[10];
        static boolean forward = true;
    }

    void goodMethod() {
        Slot slot = new Slot();
        slot.ci[5] = 10;
        slot.ci = new int[6];
        double[] cZ = {1.1, 1.2};
        final double   dnZ = slot.sin2Phi               * cZ[1];

        final Ef ef = new Ef();
        final Transformer transformer =
            ef.forward ? ef.transformers[ef.transformers.length - 1]
                    : ef.transformers[0];

    }

    void badMethod() {
        Slot slot = new Slot();
        slot.ci [5] = 10; // violation ''ci' is followed by whitespace.'
        slot.ci = new int [6]; // violation ''int' is followed by whitespace.'
        double [] cZ = {1.1, 1.2}; // violation ''double' is followed by whitespace.'
        final double   dnZ = slot.sin2Phi               * cZ [1];
        // violation above ''cZ' is followed by whitespace.'

        final Ef ef = new Ef();
        final Transformer transformer =
            ef.forward ? ef.transformers [ef.transformers.length - 1]
        // violation above ''transformers' is followed by whitespace.'
                    : ef.transformers [0]; // violation ''transformers' is followed by whitespace.'

        int[][
                ]
                 a
                  [] // violation ''a' is followed by whitespace.'
                        [] ; // violation '']' is followed by whitespace.'
                int[][
                 ]
                 b
                 [] // violation '']' is followed by whitespace.'
                        [] ; // violation '']' is followed by whitespace.'
    }

    void varargLong(@I String @L [] @K [] @J ... vararg2) { }
    @SuppressWarnings("unused")
    void withUpperBound(List<? extends int[][]> list) {}

    private static class SSLSecurity {
        static KeyManager[] truncateArray(KeyManager[] kmaw,
                                          KeyManager[] keyManagers) {
            return null;
        }
    }


    void method3(int x) {
        KeyManager[] kmaw = null;
         if (x == 1) {
             kmaw = (javax.net.ssl.KeyManager[]) // violation '')' is followed by whitespace.'
                       SSLSecurity.truncateArray(kmaw,
                           new javax.net.ssl.KeyManager [3]);
             // violation above ''KeyManager' is followed by whitespace.'
         }
    }

    public Object newInstance(Object[] objects){
            // Run constructor
        InputNoWhitespaceAfterNewTypeStructure tmpConstructor = null;
        return tmpConstructor.newInstance((Object[])null);
    }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface Nullable{}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface I {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface J {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface K {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface L {}
