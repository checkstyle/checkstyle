package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.net.ssl.KeyManager;

public class InputAntlr4AstRegressionNewTypeTree {
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
                  []
                        [] ; // violation
                int[][
                 ]
                 b
                 [] // violation
                        [] ; // violation
    }

    void varargLong(@I String @L [] @K [] @J ... vararg2) { } // ok
    @SuppressWarnings("unused")
    void withUpperBound(List<? extends int[][]> list) {}

    public static class IntData {
        int[] xorData;

        IntData(Object srcPixel, Object xorPixel) {
            this.xorData = (int[]) srcPixel; // violation
            this.xorData = (int[]) xorPixel; // violation
        }

        protected void xorPixel(Object pixData) {
            int[] dstData = (int[]) pixData; // violation
            for (int i = 0; i < dstData.length; i++) {
                dstData[i] ^= xorData[i];
            }
        }

        Object[] getXorData() {
            return new Object[5];
        }

        void method6() {
            if (getXorData().length == 5
                    && this.getXorData()[1] != IntData.class // ok
                    || this.getXorData()[5] != IntData.class) {
                System.out.println(getXorData().length);
            }
        }

    }
    protected TreeSet<byte []> sortedPrefixes = createTreeSet(); // violation

    TreeSet<byte[]> createTreeSet() { // ok
        return null;
    } // ok

    public Object newInstance(Object[] objects){
            // Run constructor
        InputAntlr4AstRegressionNewTypeTree tmpConstructor = null;
        return tmpConstructor.newInstance((Object[])null);
    }
}
