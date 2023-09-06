package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionMethodCall {
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
                    && this.getXorData()[1] != IntData.class // violation
                    || this.getXorData()[5] != IntData.class) {
                System.out.println(getXorData().length);
            }
        }

    }
}
