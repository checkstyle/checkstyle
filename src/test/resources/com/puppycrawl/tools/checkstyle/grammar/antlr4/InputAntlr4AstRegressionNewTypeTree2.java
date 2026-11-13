package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.awt.*;

import javax.net.ssl.KeyManager;

public class InputAntlr4AstRegressionNewTypeTree2 {
    private static class SSLSecurity {
        static KeyManager[] truncateArray(KeyManager[] kmaw,
                                          KeyManager[] keyManagers) {
            return null;
        }
    }


    void method3(int x) {
        KeyManager[] kmaw = null;
         if (x == 1) {
             kmaw = (javax.net.ssl.KeyManager[])
                       SSLSecurity.truncateArray(kmaw,
                           new javax.net.ssl.KeyManager [3]); // violation
         }
    }

    enum FPMethodArgs {

        IGNN,
        IIGNN,
        GNNII,
        GNNC;

        public Class<?>[] getMethodArguments(boolean isFPType) {
            Class<?> N = (isFPType) ? Float.TYPE : Integer.TYPE;
            Class<?> G = (isFPType) ? Graphics2D.class : Graphics.class;
            switch (this) {
                case IGNN:
                    return new Class<?>[]{Integer.TYPE, G, N, N};
                case IIGNN:
                    return new Class<?>[]{Integer.TYPE, Integer.TYPE, G, N, N};
                case GNNII:
                    return new Class<?>[]{G, N, N, Integer.TYPE, Integer.TYPE};
                case GNNC:
                    return new Class<?>[]{G, N, N, Character.TYPE};
                default:
                    throw new RuntimeException("Unknown method arguments!");
            }
        }
    }
}
