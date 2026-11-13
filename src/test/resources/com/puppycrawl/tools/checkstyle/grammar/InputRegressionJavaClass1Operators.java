package com.puppycrawl.tools.checkstyle.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputRegressionJavaClass1Operators {
    Object f9;

    public void instructions() throws Exception {
        // variables and types
        byte vbyte;
        boolean vboolean;
        char vchar;
        short vshort;
        int vint;
        long vlong;
        float vfloat;
        double vdouble;
        int[] varray;
        int varray2[];
        boolean test1 = true;

        // types
        String vstring;
        List<String> vlist;
        Map<String, String[]> vmap;
        int[] test2 = {};
        List<char[]> test3;
        Class<?> test4;
        List<? extends InputRegressionJavaClass1> test5;
        List<? extends List<Object>> test6;
        List<? extends List<List<Object>>> test7;
        List<? extends int[]> test8;
        List<? super InputRegressionJavaClass1> test9;

        // assignments and operators
        vint = 1 + 1;
        vint = 1 - 1;
        vint = 1 * 1;
        vint = 1 / 1;
        vint = 1 % 1;
        vint = 1 & 1;
        vint = 1 | 1;
        vint = 1 ^ 1;
        vint = ~1;
        vboolean = 1 != 0;
        vboolean = 1 == 0;
        vboolean = 1 > 0;
        vboolean = 1 >= 0;
        vboolean = 1 < 0;
        vboolean = 1 <= 0;
        vboolean = true && true;
        vboolean = true || true;
        vboolean = true ? true : false;
        vboolean = !true;
        vboolean = f9 instanceof Object;
        vint = 1 << 1;
        vint = 1 >> 1;
        vint = 1 >>> 1;
        vint += 1;
        vint -= 1;
        vint *= 1;
        vint /= 1;
        vint %= 1;
        vint &= 1;
        vint |= 1;
        vint ^= 1;
        vint <<= 1;
        vint >>= 1;
        vint >>>= 1;
        vint++;
        vint--;
        ++vint;
        --vint;
        String[] arrayinit = {};
        String[] arrayinit2 = {""};
        String[] arrayinit3 = {"", "",};

        // new
        varray = new int[]{};
        varray = new int[]{0};
        varray = new int[]{0, 1};
        varray = new int[5];
        vlist = new ArrayList<String>();
        vmap = new HashMap<String, String[]>();
        Object anonymous = new InputRegressionJavaClass1() {};
    }
}
