/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.ToIntFunction;

public class InputUnnecessaryParenthesesCasts2 {
    public void fooConditionals(T element) {
        int x = 23;
        int y = 44;
        float k = 12f;
        boolean finished = true;
        boolean result = false;

        String filevalue = "FILEVALUE";
        if (!finished || !((boolean) filevalue.contains("O"))) {
            // no violation until https://github.com/checkstyle/checkstyle/issues/14872
            filevalue += "F";
        }
        else if (finished || !(boolean) filevalue.contains("O")) {
            filevalue += "G";
        }

        if (result && finished || ((int) 23.1 + 21) == 32) {
            y--;
        }

        // no violation until https://github.com/checkstyle/checkstyle/issues/14872
        if (!((boolean) filevalue.contains("G")) || finished) {
            x++;
        }
        else if (!(boolean) filevalue.contains("P") || finished) {
            filevalue += "p";
        }

        String[] a = {"s", "a", "1", "2", "3"};
        String[] abr = {"18", "z", "w", "30", "u", "vel"};
        Arrays.stream(a)
                .filter(s -> !((boolean) s.isEmpty()))
                // no violation below until https://github.com/checkstyle/checkstyle/issues/14872
                .toArray(String[]::new);

        Arrays.stream(abr).filter(s -> !(boolean) s.isEmpty())
                .toArray(String[]::new);

        Arrays.stream(a)//no violation until https://github.com/checkstyle/checkstyle/issues/14872
                .filter(s -> ((boolean) s.isEmpty()))
                .toArray(String[]::new);
        Arrays.stream(abr)
                .filter(s -> (boolean) s.isEmpty())
                .toArray(String[]::new);

        new HashSet<Integer>().stream()
                .filter(f -> f > ((int) 1.1 + 200));

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        if (((double) abs(10 - 2)) / 2 <= 0.01) {
            y += 10;
        }
        else if ((double) abs(10 - 2) / 2 >= 0.02) {
            x += 2;
        }

        final ToIntFunction<T> comparison1 = ((Comparable<T>) element)::compareTo; // no violation

        Bean bean = new Bean();
        assertEquals(1, ((int[]) bean.array)[0]); // no violation

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        float rest = ((float) (50 - System.currentTimeMillis())) / 1000;
        float stop = (float) (50 - System.currentTimeMillis()) / 1000;

        // no violation below as parentheses detection in ternary(?) operators
        // is handled by different logic and is dependent on QUESION token
        float pin = false ? ((float) 21) : ((float) 31);

        Object obj = "hello"; // no violation below
        String result1 = (obj instanceof String) ? ((String) obj) : "Default";

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        String op1 = ((String) obj) + ((Boolean) finished).toString();
        String op2 = (String) obj + ((Boolean) finished).toString();

        filevalue = "'" + ((char) 32) + "'";
        // violation above 'Unnecessary parentheses around expression.'
        filevalue = "'" + (char) 31 + "'"; // no violation

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        ck("name", (long) k, (long) ((byte) 0x22));
        ck("check", (long) k, (long) (byte) 0x24);
    }

    public void ck(String byt, long a, long c) {}
    static class T {}
    public class Bean {
        public Object array;
        public Bean() {
            array = new int[]{1, 2, 3};
        }
    }
}
