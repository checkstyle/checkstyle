package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputRegressionJavaClass1Statements {
    public int f1;
    Object f9;

    public void instructions() throws Exception {
        // used to let inputs compile
        boolean b = Math.random() > 0;

        boolean vboolean = true;
        int vint;
        String vstring;
        int[] varray = new int[5];
        Class<?> test4;
        Map<String, String[]> vmap = new HashMap<String, String[]>();

        // statements
        ;
        this.f1 = 0;

        // labels
        test_label1:

        // blocks
        {}
        if (true) ;
        if (true) {}
        if (true) {} else ;
        if (true) {} else {}
        if (b) { for (;;) ; }
        if (b) { for (;;) {} }
        for (int i = 0; i < 1; i++) {}
        for (int i = 0, j = 0; i < 1; i++, j += 2) {}
        for (int value: new int[]{}) ;
        for (String s : new String[]{}) ;
        for (final String s : new String[]{}) ;
        if (b) { while (true) ; }
        if (b) { while (true) {} }
        do {} while (false);
        synchronized (f9) {}

        switch (0) {
            case 1:
            case 0: break;
            default: break;
        }

        try {
            if (b) { throw new IOException(); }
            if (b) { throw new ArrayIndexOutOfBoundsException(); }
            throw new Exception();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
        } catch (Exception e) {
        } finally {}
        try (BufferedReader br = new BufferedReader(new InputStreamReader(null, "utf-8"))) {}
        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(null, "utf-8"));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(null, "utf-8"));) {}

        // access
        test4 = InputRegressionJavaClass1.class;
        test4 = List[].class;
        test4 = boolean[].class;
        varray[0] = 0;
        for (String[] s : new String[][]{{}}) ;
        for (Map.Entry<String, String[]> e : vmap.entrySet()) { }

        // others
        for (;;) {break;}
        test_label2:
        for (;;) {break test_label2;}
        if (b) { for (;;) {continue;} }
        if (b) { test_label3: for (;;) {continue test_label3;} }
        if (false) return;
        if (false) throw new Exception();
        assert(false);
        assert true : "false";
        f9 = (Object) f9;
        f9.equals(((vstring = "")));
        for (int i = 0; ((i) < (6+6)); i++) ;
        if ((b & b)) {}

        // ast error handling
        vint = vboolean ? (vint = 1) : (vint = 0);
        varray[vint] = Integer.parseInt("0");
    }
}
