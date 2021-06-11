package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

/* Config:
 * tokens = { ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS,
 *    BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED,
 *    METHOD_REF }
 *
 */
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
        slot.ci[5] = 10; // ok
        slot.ci = new int[6]; // ok
        double[] cZ = {1.1, 1.2}; // ok
        final double   dnZ = slot.sin2Phi               * cZ[1]; // ok

        final Ef ef = new Ef();
        final Transformer transformer =
            ef.forward ? ef.transformers[ef.transformers.length - 1] // ok
                    : ef.transformers[0]; // ok

    }

    void badMethod() {
        Slot slot = new Slot();
        slot.ci [5] = 10; // violation
        slot.ci = new int [6]; // violation
        double [] cZ = {1.1, 1.2}; // violation
        final double   dnZ = slot.sin2Phi               * cZ [1]; // violation

        final Ef ef = new Ef();
        final Transformer transformer =
            ef.forward ? ef.transformers [ef.transformers.length - 1] // violation
                    : ef.transformers [0]; // violation

        int[][
                ]
                a
                [
                        ][] ;
    }

}
