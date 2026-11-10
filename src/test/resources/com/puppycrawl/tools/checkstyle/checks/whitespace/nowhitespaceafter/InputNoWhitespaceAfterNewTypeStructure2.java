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

public class InputNoWhitespaceAfterNewTypeStructure2 {
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

    public static class IntData {
        int[] xorData;

        IntData(Object srcPixel, Object xorPixel) {
            this.xorData = (int[]) srcPixel; // violation '')' is followed by whitespace.'
            this.xorData = (int[]) xorPixel; // violation '')' is followed by whitespace.'
        }

        protected void xorPixel(Object pixData) {
            int[] dstData = (int[]) pixData; // violation '')' is followed by whitespace.'
            for (int i = 0; i < dstData.length; i++) {
                dstData[i] ^= xorData[i];
            }
        }

        Object[] getXorData() {
            return new Object[5];
        }

        void method6() {
            if (getXorData().length == 5
                    && this.getXorData()[1] != IntData.class
                    || this.getXorData()[5] != IntData.class) {
                System.out.println(getXorData().length);
            }
        }

    }
    protected TreeSet<byte []> sortedPrefixes = createTreeSet();
    // violation above ''byte' is followed by whitespace.'

    TreeSet<byte[]> createTreeSet() {
        return null;
    }

    public TypeVariable<Class<String>>[] getTypeParameters() {
        ClassRepository info = getGenericInfo();
        if (info != null)
            return (TypeVariable<Class<String>>[])info.getTypeParameters();
        else
            return (TypeVariable<Class<String>>[])new TypeVariable<?>[0];
    }
    class ClassRepository {

        public Object getTypeParameters() {
            return null;
        }
    }
    class TypeVariable<E> {

    }

    private ClassRepository getGenericInfo() {
        return null;
    }
}
