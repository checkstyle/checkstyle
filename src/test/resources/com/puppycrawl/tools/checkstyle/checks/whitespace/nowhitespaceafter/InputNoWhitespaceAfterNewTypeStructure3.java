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

public class InputNoWhitespaceAfterNewTypeStructure3 {
    String[] c, d[];
    String [] e, f [];
            // 2 violations above:
            //          ''String' is followed by whitespace.'
            //          ''f' is followed by whitespace.'

    public enum Stooge {
        MOE, CURLY, LARRY,
    }
    static void checkArrayTypes1(ArrayTypes at, AnnotatedElement e) {
        if (!(at.cls()[0]  == Map.class    &&
              at.e()[0]    == Stooge.MOE  )) {

        }
    }

    static class ArrayTypes {
        Object[] cls;

        public Stooge[] e() {
            return null;
        }



        Object[] cls() {
            return cls;
        }

        public int[] a() {
            return null;
        }
    }
    int[][
    ]
     w
     [] // violation '']' is followed by whitespace.'
            [] ; // violation '']' is followed by whitespace.'
    int[][
     ]
     z
    [] // violation '']' is followed by whitespace.'
            [] ; // violation '']' is followed by whitespace.'

    static class P {
        private int x;

        public void set3(P p) {
              synchronized (this) { // violation ''synchronized' is followed by whitespace.'
                  p.x = get()[0];
              }
          }

        public void set4(P p) {
              synchronized(this) {
                  p.x = get()[0];
              }
          }

        private int[] get() {
            return null;
        }
    }

    private @Nullable int array2 @Nullable [] @Nullable [];
}
